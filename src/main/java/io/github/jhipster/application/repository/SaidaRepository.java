package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Saida;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Saida entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaidaRepository extends JpaRepository<Saida, Long> {

}
