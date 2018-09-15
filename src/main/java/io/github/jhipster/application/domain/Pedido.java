package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.Formato;

/**
 * A Pedido.
 */
@Entity
@Table(name = "pedido")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pedido")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "formato_entrega")
    private Formato formatoEntrega;

    @Column(name = "data")
    private Instant data;

    @NotNull
    @Column(name = "valor_unitario", nullable = false)
    private Long valorUnitario;

    @Column(name = "prazo")
    private String prazo;

    @Column(name = "foi_entregue")
    private Boolean foiEntregue;

    @Column(name = "foi_visualizado")
    private Boolean foiVisualizado;

    @Column(name = "saiu_entrega")
    private Boolean saiuEntrega;

    @Column(name = "observacoes")
    private String observacoes;

    @ManyToOne
    @JsonIgnoreProperties("pedidos")
    private Cliente cliente;

    @OneToOne
    @JoinColumn(unique = true)
    private Vendedor vendedor;

    @OneToMany(mappedBy = "pedido")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Item> items = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Formato getFormatoEntrega() {
        return formatoEntrega;
    }

    public Pedido formatoEntrega(Formato formatoEntrega) {
        this.formatoEntrega = formatoEntrega;
        return this;
    }

    public void setFormatoEntrega(Formato formatoEntrega) {
        this.formatoEntrega = formatoEntrega;
    }

    public Instant getData() {
        return data;
    }

    public Pedido data(Instant data) {
        this.data = data;
        return this;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public Long getValorUnitario() {
        return valorUnitario;
    }

    public Pedido valorUnitario(Long valorUnitario) {
        this.valorUnitario = valorUnitario;
        return this;
    }

    public void setValorUnitario(Long valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getPrazo() {
        return prazo;
    }

    public Pedido prazo(String prazo) {
        this.prazo = prazo;
        return this;
    }

    public void setPrazo(String prazo) {
        this.prazo = prazo;
    }

    public Boolean isFoiEntregue() {
        return foiEntregue;
    }

    public Pedido foiEntregue(Boolean foiEntregue) {
        this.foiEntregue = foiEntregue;
        return this;
    }

    public void setFoiEntregue(Boolean foiEntregue) {
        this.foiEntregue = foiEntregue;
    }

    public Boolean isFoiVisualizado() {
        return foiVisualizado;
    }

    public Pedido foiVisualizado(Boolean foiVisualizado) {
        this.foiVisualizado = foiVisualizado;
        return this;
    }

    public void setFoiVisualizado(Boolean foiVisualizado) {
        this.foiVisualizado = foiVisualizado;
    }

    public Boolean isSaiuEntrega() {
        return saiuEntrega;
    }

    public Pedido saiuEntrega(Boolean saiuEntrega) {
        this.saiuEntrega = saiuEntrega;
        return this;
    }

    public void setSaiuEntrega(Boolean saiuEntrega) {
        this.saiuEntrega = saiuEntrega;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public Pedido observacoes(String observacoes) {
        this.observacoes = observacoes;
        return this;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Pedido cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public Pedido vendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
        return this;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Set<Item> getItems() {
        return items;
    }

    public Pedido items(Set<Item> items) {
        this.items = items;
        return this;
    }

    public Pedido addItem(Item item) {
        this.items.add(item);
        item.setPedido(this);
        return this;
    }

    public Pedido removeItem(Item item) {
        this.items.remove(item);
        item.setPedido(null);
        return this;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
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
        Pedido pedido = (Pedido) o;
        if (pedido.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pedido.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pedido{" +
            "id=" + getId() +
            ", formatoEntrega='" + getFormatoEntrega() + "'" +
            ", data='" + getData() + "'" +
            ", valorUnitario=" + getValorUnitario() +
            ", prazo='" + getPrazo() + "'" +
            ", foiEntregue='" + isFoiEntregue() + "'" +
            ", foiVisualizado='" + isFoiVisualizado() + "'" +
            ", saiuEntrega='" + isSaiuEntrega() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            "}";
    }
}
