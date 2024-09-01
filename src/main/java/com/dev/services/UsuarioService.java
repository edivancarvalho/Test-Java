package com.dev.services;

import com.dev.domain.Usuario;
import com.dev.domain.dto.UsuarioDTO;
import com.dev.repository.UsuarioRepository;
import com.dev.services.exceptions.EmailJaExistenteException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EmailJaExistenteException("Usuário não encontrado! Id: " + id));
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario create(UsuarioDTO objDTO) {
        if (emailExists(objDTO.getEmail())) {
            throw new EmailJaExistenteException("Email já existente!");
        }
        Usuario usuario = new Usuario(objDTO);
        return usuarioRepository.save(usuario);
    }

    public Usuario update(Integer id, @Valid UsuarioDTO objDTO) {
        Usuario existingUser = findById(id);

        if (emailExists(objDTO.getEmail()) && !existingUser.getEmail().equals(objDTO.getEmail())) {
            throw new EmailJaExistenteException("Email já existente!");
        }

        existingUser.setNome(objDTO.getNome());
        existingUser.setEmail(objDTO.getEmail());

        return usuarioRepository.save(existingUser);
    }

    public void delete(Integer id) {
        try {
            usuarioRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EmailJaExistenteException("Usuário não encontrado! Id: " + id);
        }
    }


    private boolean emailExists(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }
}