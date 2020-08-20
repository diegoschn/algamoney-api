package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.model.Categoria;
import com.algaworks.algamoney.api.repository.CategoriaRepository;
import com.algaworks.algamoney.api.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;


@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> listar(){
        return categoriaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscar(@PathVariable Long id){
        Categoria categoriaEncontrada = categoriaRepository.findOne(id);
        if(categoriaEncontrada!=null){
            return ResponseEntity.ok(categoriaEncontrada);
        }else{
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<Categoria> cadastrar(@Valid @RequestBody Categoria categoria){
        Categoria categoriaSalva = categoriaService.salvar(categoria);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaSalva);
    }

    @PutMapping("/{id}")
        public ResponseEntity<Categoria> atualizar(@PathVariable Long id,
           @RequestBody Categoria categoria){

        Categoria categoriaId = categoriaRepository.findOne(id);
        if(categoriaId==null){
            return ResponseEntity.notFound().build();
        }else{
            categoria.setId(id);
            Categoria categoriaSalva = categoriaService.salvar(categoria);
            return ResponseEntity.ok(categoriaSalva);
        }
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id){
        categoriaRepository.delete(id);
    }

}


