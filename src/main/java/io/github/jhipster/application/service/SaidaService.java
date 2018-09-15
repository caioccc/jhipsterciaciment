package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Saida;
import io.github.jhipster.application.repository.SaidaRepository;
import io.github.jhipster.application.repository.search.SaidaSearchRepository;
import io.github.jhipster.application.service.dto.SaidaDTO;
import io.github.jhipster.application.service.mapper.SaidaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Saida.
 */
@Service
@Transactional
public class SaidaService {

    private final Logger log = LoggerFactory.getLogger(SaidaService.class);

    private final SaidaRepository saidaRepository;

    private final SaidaMapper saidaMapper;

    private final SaidaSearchRepository saidaSearchRepository;

    public SaidaService(SaidaRepository saidaRepository, SaidaMapper saidaMapper, SaidaSearchRepository saidaSearchRepository) {
        this.saidaRepository = saidaRepository;
        this.saidaMapper = saidaMapper;
        this.saidaSearchRepository = saidaSearchRepository;
    }

    /**
     * Save a saida.
     *
     * @param saidaDTO the entity to save
     * @return the persisted entity
     */
    public SaidaDTO save(SaidaDTO saidaDTO) {
        log.debug("Request to save Saida : {}", saidaDTO);
        Saida saida = saidaMapper.toEntity(saidaDTO);
        saida = saidaRepository.save(saida);
        SaidaDTO result = saidaMapper.toDto(saida);
        saidaSearchRepository.save(saida);
        return result;
    }

    /**
     * Get all the saidas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SaidaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Saidas");
        return saidaRepository.findAll(pageable)
            .map(saidaMapper::toDto);
    }


    /**
     * Get one saida by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SaidaDTO> findOne(Long id) {
        log.debug("Request to get Saida : {}", id);
        return saidaRepository.findById(id)
            .map(saidaMapper::toDto);
    }

    /**
     * Delete the saida by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Saida : {}", id);
        saidaRepository.deleteById(id);
        saidaSearchRepository.deleteById(id);
    }

    /**
     * Search for the saida corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SaidaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Saidas for query {}", query);
        return saidaSearchRepository.search(queryStringQuery(query), pageable)
            .map(saidaMapper::toDto);
    }
}
