package com.algaworks.algamoney.api.repository.lancamento;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.model.Lancamento_;
import com.algaworks.algamoney.api.repository.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteriaQuery = builder.createQuery(Lancamento.class);

        //Aqui estou fazendo o seguinte: select * from Lancamento
        Root<Lancamento> root = criteriaQuery.from(Lancamento.class);

        //criar as restrições - tipo... where nome ilike ou where dataVencimentoDe ou dataVencimentoAte
        //ou os três juntos
        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
        criteriaQuery.where(predicates);

        TypedQuery<Lancamento> query = entityManager.createQuery(criteriaQuery);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
    }




    private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter,
                                        CriteriaBuilder builder, Root<Lancamento> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
            predicates.add(builder.like(
                    builder.lower(root.get(Lancamento_.descricao)), "%"
                            + lancamentoFilter.getDescricao().toLowerCase() + ""));
        }

        if (lancamentoFilter.getDataVencimentoDe() != null) {
            predicates.add(
                    builder.greaterThanOrEqualTo
                            (root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoDe()));
        }

        if (lancamentoFilter.getDataVencimentoAte() != null) {
            predicates.add(
                    builder.lessThanOrEqualTo
                            (root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoAte()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<Lancamento> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

    private Long total(LancamentoFilter lancamentoFilter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = criteriaBuilder.createQuery(Long.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(lancamentoFilter, criteriaBuilder, root);
        criteria.where(predicates);

        criteria.select(criteriaBuilder.count(root));
        return entityManager.createQuery(criteria).getSingleResult();
    }
}

