package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Entrada;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Entrada entity.
 */
public interface EntradaSearchRepository extends ElasticsearchRepository<Entrada, Long> {
}
