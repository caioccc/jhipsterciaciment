package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Entrada;
import io.github.jhipster.application.repository.EntradaRepository;
import io.github.jhipster.application.repository.search.EntradaSearchRepository;
import io.github.jhipster.application.service.dto.EntradaDTO;
import io.github.jhipster.application.service.mapper.EntradaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Entrada.
 */
@Service
@Transactional
public class EntradaService {

    private final Logger log = LoggerFactory.getLogger(EntradaService.class);

    private final EntradaRepository entradaRepository;

    private final EntradaMapper entradaMapper;

    private final EntradaSearchRepository entradaSearchRepository;

    public EntradaService(EntradaRepository entradaRepository, EntradaMapper entradaMapper, EntradaSearchRepository entradaSearchRepository) {
        this.entradaRepository = entradaRepository;
        this.entradaMapper = entradaMapper;
        this.entradaSearchRepository = entradaSearchRepository;
    }

    /**
     * Save a entrada.
     *
     * @param entradaDTO the entity to save
     * @return the persisted entity
     */
    public EntradaDTO save(EntradaDTO entradaDTO) {
        log.debug("Request to save Entrada : {}", entradaDTO);
        Entrada entrada = entradaMapper.toEntity(entradaDTO);
        entrada = entradaRepository.save(entrada);
        EntradaDTO result = entradaMapper.toDto(entrada);
        entradaSearchRepository.save(entrada);
        return result;
    }

    /**
     * Get all the entradas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EntradaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Entradas");
        return entradaRepository.findAll(pageable)
            .map(entradaMapper::toDto);
    }


    /**
     * Get one entrada by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<EntradaDTO> findOne(Long id) {
        log.debug("Request to get Entrada : {}", id);
        return entradaRepository.findById(id)
            .map(entradaMapper::toDto);
    }

    /**
     * Delete the entrada by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Entrada : {}", id);
        entradaRepository.deleteById(id);
        entradaSearchRepository.deleteById(id);
    }

    /**
     * Search for the entrada corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EntradaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Entradas for query {}", query);
        return entradaSearchRepository.search(queryStringQuery(query), pageable)
            .map(entradaMapper::toDto);
    }
}
