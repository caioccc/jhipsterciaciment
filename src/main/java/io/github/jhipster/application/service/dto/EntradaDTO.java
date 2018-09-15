package io.github.jhipster.application.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.application.domain.enumeration.Forma;
import io.github.jhipster.application.domain.enumeration.Cor;

/**
 * A DTO for the Entrada entity.
 */
public class EntradaDTO implements Serializable {

    private Long id;

    @NotNull
    private String cliente;

    @NotNull
    private Long valorTotal;

    private Long valorPago;

    private Instant data;

    private Forma formaPagamento;

    private Cor cor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Long getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Long valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Long getValorPago() {
        return valorPago;
    }

    public void setValorPago(Long valorPago) {
        this.valorPago = valorPago;
    }

    public Instant getData() {
        return data;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public Forma getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(Forma formaPagamento) {
        this.formaPagamento = formaPagamento;
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

        EntradaDTO entradaDTO = (EntradaDTO) o;
        if (entradaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entradaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntradaDTO{" +
            "id=" + getId() +
            ", cliente='" + getCliente() + "'" +
            ", valorTotal=" + getValorTotal() +
            ", valorPago=" + getValorPago() +
            ", data='" + getData() + "'" +
            ", formaPagamento='" + getFormaPagamento() + "'" +
            ", cor='" + getCor() + "'" +
            "}";
    }
}
