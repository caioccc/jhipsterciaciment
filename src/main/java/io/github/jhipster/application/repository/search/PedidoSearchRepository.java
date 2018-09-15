package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Pedido;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Pedido entity.
 */
public interface PedidoSearchRepository extends ElasticsearchRepository<Pedido, Long> {
}
