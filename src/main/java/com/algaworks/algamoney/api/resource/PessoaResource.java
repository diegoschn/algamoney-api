package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import com.algaworks.algamoney.api.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
    public List<Pessoa> listar(){
        return pessoaRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('read')")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Pessoa cadastrar(@Valid @RequestBody Pessoa pessoa){
        return pessoaService.salvar(pessoa);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
    public ResponseEntity<Pessoa> buscar(@PathVariable Long id){
        Pessoa pessoaEncontrada = pessoaRepository.findOne(id);
        if(pessoaEncontrada!=null){
            return ResponseEntity.ok(pessoaEncontrada);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> atualizar(@PathVariable Long id,
            @RequestBody Pessoa pessoa){

        Pessoa pessoaId = pessoaRepository.findOne(id);
        if(pessoaId==null){
            return ResponseEntity.notFound().build();
        }else{
            pessoa.setId(id);
            Pessoa pessoaSalva = pessoaService.salvar(pessoa);
            return ResponseEntity.ok(pessoaSalva);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id){
        pessoaRepository.delete(id);
    }

}
