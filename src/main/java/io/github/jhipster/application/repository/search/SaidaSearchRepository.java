package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Saida;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Saida entity.
 */
public interface SaidaSearchRepository extends ElasticsearchRepository<Saida, Long> {
}
