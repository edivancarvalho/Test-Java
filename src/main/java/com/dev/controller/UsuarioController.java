package com.dev.controller;

import com.dev.domain.Usuario;
import com.dev.domain.dto.UsuarioDTO;
import com.dev.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Integer id) {
        Usuario obj = service.findById(id);
        return ResponseEntity.ok().body(new UsuarioDTO(obj));
    }

    @GetMapping("/")
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        List<Usuario> list = service.findAll();
        List<UsuarioDTO> listDTO = list.stream().map(obj -> new UsuarioDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping("/")
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioDTO objDTO) {
        Usuario newObj = service.create(objDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable Integer id, @Valid @RequestBody UsuarioDTO objDTO) {
        Usuario obj = service.update(id, objDTO);
        return ResponseEntity.ok().body(new UsuarioDTO(obj));
    }
    // É muito usado a exclusão logica, realizar a exclusão desta forma não é uma boa pratica em muitos casos,
    // pois despois que excluir não tem como volta atrás somente atravez de backup.
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
