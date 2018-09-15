package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Produto;
import io.github.jhipster.application.repository.ProdutoRepository;
import io.github.jhipster.application.repository.search.ProdutoSearchRepository;
import io.github.jhipster.application.service.dto.ProdutoDTO;
import io.github.jhipster.application.service.mapper.ProdutoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Produto.
 */
@Service
@Transactional
public class ProdutoService {

    private final Logger log = LoggerFactory.getLogger(ProdutoService.class);

    private final ProdutoRepository produtoRepository;

    private final ProdutoMapper produtoMapper;

    private final ProdutoSearchRepository produtoSearchRepository;

    public ProdutoService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper, ProdutoSearchRepository produtoSearchRepository) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
        this.produtoSearchRepository = produtoSearchRepository;
    }

    /**
     * Save a produto.
     *
     * @param produtoDTO the entity to save
     * @return the persisted entity
     */
    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        log.debug("Request to save Produto : {}", produtoDTO);
        Produto produto = produtoMapper.toEntity(produtoDTO);
        produto = produtoRepository.save(produto);
        ProdutoDTO result = produtoMapper.toDto(produto);
        produtoSearchRepository.save(produto);
        return result;
    }

    /**
     * Get all the produtos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProdutoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Produtos");
        return produtoRepository.findAll(pageable)
            .map(produtoMapper::toDto);
    }


    /**
     * Get one produto by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ProdutoDTO> findOne(Long id) {
        log.debug("Request to get Produto : {}", id);
        return produtoRepository.findById(id)
            .map(produtoMapper::toDto);
    }

    /**
     * Delete the produto by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Produto : {}", id);
        produtoRepository.deleteById(id);
        produtoSearchRepository.deleteById(id);
    }

    /**
     * Search for the produto corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProdutoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Produtos for query {}", query);
        return produtoSearchRepository.search(queryStringQuery(query), pageable)
            .map(produtoMapper::toDto);
    }
}
