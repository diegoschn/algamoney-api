package com.algaworks.algamoney.api.repository;

import com.algaworks.algamoney.api.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {


}
