package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Vendedor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Vendedor entity.
 */
public interface VendedorSearchRepository extends ElasticsearchRepository<Vendedor, Long> {
}
