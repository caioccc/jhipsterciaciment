package io.github.jhipster.application.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.application.domain.enumeration.Forma;
import io.github.jhipster.application.domain.enumeration.Cor;

/**
 * A DTO for the Saida entity.
 */
public class SaidaDTO implements Serializable {

    private Long id;

    private String eminente;

    private String razao;

    @NotNull
    private Long valor;

    private Forma forma;

    private Instant data;

    private String observacoes;

    private Cor cor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEminente() {
        return eminente;
    }

    public void setEminente(String eminente) {
        this.eminente = eminente;
    }

    public String getRazao() {
        return razao;
    }

    public void setRazao(String razao) {
        this.razao = razao;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public Forma getForma() {
        return forma;
    }

    public void setForma(Forma forma) {
        this.forma = forma;
    }

    public Instant getData() {
        return data;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SaidaDTO saidaDTO = (SaidaDTO) o;
        if (saidaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), saidaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SaidaDTO{" +
            "id=" + getId() +
            ", eminente='" + getEminente() + "'" +
            ", razao='" + getRazao() + "'" +
            ", valor=" + getValor() +
            ", forma='" + getForma() + "'" +
            ", data='" + getData() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            ", cor='" + getCor() + "'" +
            "}";
    }
}
