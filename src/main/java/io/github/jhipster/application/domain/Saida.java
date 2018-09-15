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
 * A Saida.
 */
@Entity
@Table(name = "saida")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "saida")
public class Saida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "eminente")
    private String eminente;

    @Column(name = "razao")
    private String razao;

    @NotNull
    @Column(name = "valor", nullable = false)
    private Long valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma")
    private Forma forma;

    @Column(name = "data")
    private Instant data;

    @Column(name = "observacoes")
    private String observacoes;

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

    public String getEminente() {
        return eminente;
    }

    public Saida eminente(String eminente) {
        this.eminente = eminente;
        return this;
    }

    public void setEminente(String eminente) {
        this.eminente = eminente;
    }

    public String getRazao() {
        return razao;
    }

    public Saida razao(String razao) {
        this.razao = razao;
        return this;
    }

    public void setRazao(String razao) {
        this.razao = razao;
    }

    public Long getValor() {
        return valor;
    }

    public Saida valor(Long valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public Forma getForma() {
        return forma;
    }

    public Saida forma(Forma forma) {
        this.forma = forma;
        return this;
    }

    public void setForma(Forma forma) {
        this.forma = forma;
    }

    public Instant getData() {
        return data;
    }

    public Saida data(Instant data) {
        this.data = data;
        return this;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public Saida observacoes(String observacoes) {
        this.observacoes = observacoes;
        return this;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Cor getCor() {
        return cor;
    }

    public Saida cor(Cor cor) {
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
        Saida saida = (Saida) o;
        if (saida.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), saida.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Saida{" +
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
