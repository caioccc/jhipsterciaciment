package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Vendedor;
import io.github.jhipster.application.repository.VendedorRepository;
import io.github.jhipster.application.repository.search.VendedorSearchRepository;
import io.github.jhipster.application.service.dto.VendedorDTO;
import io.github.jhipster.application.service.mapper.VendedorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Vendedor.
 */
@Service
@Transactional
public class VendedorService {

    private final Logger log = LoggerFactory.getLogger(VendedorService.class);

    private final VendedorRepository vendedorRepository;

    private final VendedorMapper vendedorMapper;

    private final VendedorSearchRepository vendedorSearchRepository;

    public VendedorService(VendedorRepository vendedorRepository, VendedorMapper vendedorMapper, VendedorSearchRepository vendedorSearchRepository) {
        this.vendedorRepository = vendedorRepository;
        this.vendedorMapper = vendedorMapper;
        this.vendedorSearchRepository = vendedorSearchRepository;
    }

    /**
     * Save a vendedor.
     *
     * @param vendedorDTO the entity to save
     * @return the persisted entity
     */
    public VendedorDTO save(VendedorDTO vendedorDTO) {
        log.debug("Request to save Vendedor : {}", vendedorDTO);
        Vendedor vendedor = vendedorMapper.toEntity(vendedorDTO);
        vendedor = vendedorRepository.save(vendedor);
        VendedorDTO result = vendedorMapper.toDto(vendedor);
        vendedorSearchRepository.save(vendedor);
        return result;
    }

    /**
     * Get all the vendedors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<VendedorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Vendedors");
        return vendedorRepository.findAll(pageable)
            .map(vendedorMapper::toDto);
    }

    /**
     * Get all the Vendedor with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<VendedorDTO> findAllWithEagerRelationships(Pageable pageable) {
        return vendedorRepository.findAllWithEagerRelationships(pageable).map(vendedorMapper::toDto);
    }
    

    /**
     * Get one vendedor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<VendedorDTO> findOne(Long id) {
        log.debug("Request to get Vendedor : {}", id);
        return vendedorRepository.findOneWithEagerRelationships(id)
            .map(vendedorMapper::toDto);
    }

    /**
     * Delete the vendedor by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Vendedor : {}", id);
        vendedorRepository.deleteById(id);
        vendedorSearchRepository.deleteById(id);
    }

    /**
     * Search for the vendedor corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<VendedorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Vendedors for query {}", query);
        return vendedorSearchRepository.search(queryStringQuery(query), pageable)
            .map(vendedorMapper::toDto);
    }
}
