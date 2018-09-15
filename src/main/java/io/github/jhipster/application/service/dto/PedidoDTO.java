package io.github.jhipster.application.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.application.domain.enumeration.Formato;

/**
 * A DTO for the Pedido entity.
 */
public class PedidoDTO implements Serializable {

    private Long id;

    private Formato formatoEntrega;

    private Instant data;

    @NotNull
    private Long valorUnitario;

    private String prazo;

    private Boolean foiEntregue;

    private Boolean foiVisualizado;

    private Boolean saiuEntrega;

    private String observacoes;

    private Long clienteId;

    private Long vendedorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Formato getFormatoEntrega() {
        return formatoEntrega;
    }

    public void setFormatoEntrega(Formato formatoEntrega) {
        this.formatoEntrega = formatoEntrega;
    }

    public Instant getData() {
        return data;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public Long getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Long valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getPrazo() {
        return prazo;
    }

    public void setPrazo(String prazo) {
        this.prazo = prazo;
    }

    public Boolean isFoiEntregue() {
        return foiEntregue;
    }

    public void setFoiEntregue(Boolean foiEntregue) {
        this.foiEntregue = foiEntregue;
    }

    public Boolean isFoiVisualizado() {
        return foiVisualizado;
    }

    public void setFoiVisualizado(Boolean foiVisualizado) {
        this.foiVisualizado = foiVisualizado;
    }

    public Boolean isSaiuEntrega() {
        return saiuEntrega;
    }

    public void setSaiuEntrega(Boolean saiuEntrega) {
        this.saiuEntrega = saiuEntrega;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(Long vendedorId) {
        this.vendedorId = vendedorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PedidoDTO pedidoDTO = (PedidoDTO) o;
        if (pedidoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pedidoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PedidoDTO{" +
            "id=" + getId() +
            ", formatoEntrega='" + getFormatoEntrega() + "'" +
            ", data='" + getData() + "'" +
            ", valorUnitario=" + getValorUnitario() +
            ", prazo='" + getPrazo() + "'" +
            ", foiEntregue='" + isFoiEntregue() + "'" +
            ", foiVisualizado='" + isFoiVisualizado() + "'" +
            ", saiuEntrega='" + isSaiuEntrega() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            ", cliente=" + getClienteId() +
            ", vendedor=" + getVendedorId() +
            "}";
    }
}
