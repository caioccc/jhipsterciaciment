package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Vendedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Vendedor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {

    @Query(value = "select distinct vendedor from Vendedor vendedor left join fetch vendedor.clientes",
        countQuery = "select count(distinct vendedor) from Vendedor vendedor")
    Page<Vendedor> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct vendedor from Vendedor vendedor left join fetch vendedor.clientes")
    List<Vendedor> findAllWithEagerRelationships();

    @Query("select vendedor from Vendedor vendedor left join fetch vendedor.clientes where vendedor.id =:id")
    Optional<Vendedor> findOneWithEagerRelationships(@Param("id") Long id);

}
