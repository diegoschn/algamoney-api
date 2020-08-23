package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.LancamentoRepository;
import com.algaworks.algamoney.api.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private LancamentoService lancamentoService;

    @GetMapping
    public List<Lancamento> listar(){
        return lancamentoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lancamento> buscar(@PathVariable  Long id){
        Lancamento lancamentoId = lancamentoRepository.findOne(id);
        if(lancamentoId!=null){
            return ResponseEntity.ok(lancamentoId);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Lancamento cadastrar(@Valid @RequestBody  Lancamento lancamento){
        return lancamentoService.salvar(lancamento);
    }


}
