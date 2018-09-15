package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.Forma;

import io.github.jhipster.application.domain.enumeration.Cor;

/**
 * A Entrada.
 */
@Entity
@Table(name = "entrada")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "entrada")
public class Entrada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "cliente", nullable = false)
    private String cliente;

    @NotNull
    @Column(name = "valor_total", nullable = false)
    private Long valorTotal;

    @Column(name = "valor_pago")
    private Long valorPago;

    @Column(name = "data")
    private Instant data;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento")
    private Forma formaPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "cor")
    private Cor cor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public Entrada cliente(String cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Long getValorTotal() {
        return valorTotal;
    }

    public Entrada valorTotal(Long valorTotal) {
        this.valorTotal = valorTotal;
        return this;
    }

    public void setValorTotal(Long valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Long getValorPago() {
        return valorPago;
    }

    public Entrada valorPago(Long valorPago) {
        this.valorPago = valorPago;
        return this;
    }

    public void setValorPago(Long valorPago) {
        this.valorPago = valorPago;
    }

    public Instant getData() {
        return data;
    }

    public Entrada data(Instant data) {
        this.data = data;
        return this;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public Forma getFormaPagamento() {
        return formaPagamento;
    }

    public Entrada formaPagamento(Forma formaPagamento) {
        this.formaPagamento = formaPagamento;
        return this;
    }

    public void setFormaPagamento(Forma formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Cor getCor() {
        return cor;
    }

    public Entrada cor(Cor cor) {
        this.cor = cor;
        return this;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entrada entrada = (Entrada) o;
        if (entrada.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entrada.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Entrada{" +
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
