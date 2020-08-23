package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa salvar(Pessoa pessoa){
        return pessoaRepository.save(pessoa);
    }

    public Pessoa buscarPessoaPeloCodigo(Long codigo){
        Pessoa pessoaSalva = pessoaRepository.findOne(codigo);
        if(pessoaSalva==null){
            throw new EmptyResultDataAccessException(1);
        }

        return pessoaSalva;
    }
}

