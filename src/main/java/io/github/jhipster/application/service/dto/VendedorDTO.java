package io.github.jhipster.application.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Vendedor entity.
 */
public class VendedorDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    private String email;

    private String telefone;

    private Set<ClienteDTO> clientes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Set<ClienteDTO> getClientes() {
        return clientes;
    }

    public void setClientes(Set<ClienteDTO> clientes) {
        this.clientes = clientes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VendedorDTO vendedorDTO = (VendedorDTO) o;
        if (vendedorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vendedorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VendedorDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefone='" + getTelefone() + "'" +
            "}";
    }
}
