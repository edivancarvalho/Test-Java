package com.dev.domain.dto;

import com.dev.domain.Usuario;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Integer id;
    @NotBlank(message = "O campo NOME é obrigatório.")
    protected String nome;

    @NotBlank(message = "O campo EMAIL é obrigatório.")
    @Column(unique = true)
    protected String email;

    public UsuarioDTO(Usuario obj) {
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.email = obj.getEmail();
    }
}
