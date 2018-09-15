package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.EntradaService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.EntradaDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Entrada.
 */
@RestController
@RequestMapping("/api")
public class EntradaResource {

    private final Logger log = LoggerFactory.getLogger(EntradaResource.class);

    private static final String ENTITY_NAME = "entrada";

    private final EntradaService entradaService;

    public EntradaResource(EntradaService entradaService) {
        this.entradaService = entradaService;
    }

    /**
     * POST  /entradas : Create a new entrada.
     *
     * @param entradaDTO the entradaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entradaDTO, or with status 400 (Bad Request) if the entrada has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entradas")
    @Timed
    public ResponseEntity<EntradaDTO> createEntrada(@Valid @RequestBody EntradaDTO entradaDTO) throws URISyntaxException {
        log.debug("REST request to save Entrada : {}", entradaDTO);
        if (entradaDTO.getId() != null) {
            throw new BadRequestAlertException("A new entrada cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntradaDTO result = entradaService.save(entradaDTO);
        return ResponseEntity.created(new URI("/api/entradas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entradas : Updates an existing entrada.
     *
     * @param entradaDTO the entradaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entradaDTO,
     * or with status 400 (Bad Request) if the entradaDTO is not valid,
     * or with status 500 (Internal Server Error) if the entradaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entradas")
    @Timed
    public ResponseEntity<EntradaDTO> updateEntrada(@Valid @RequestBody EntradaDTO entradaDTO) throws URISyntaxException {
        log.debug("REST request to update Entrada : {}", entradaDTO);
        if (entradaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EntradaDTO result = entradaService.save(entradaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entradaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entradas : get all the entradas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of entradas in body
     */
    @GetMapping("/entradas")
    @Timed
    public ResponseEntity<List<EntradaDTO>> getAllEntradas(Pageable pageable) {
        log.debug("REST request to get a page of Entradas");
        Page<EntradaDTO> page = entradaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/entradas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /entradas/:id : get the "id" entrada.
     *
     * @param id the id of the entradaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entradaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/entradas/{id}")
    @Timed
    public ResponseEntity<EntradaDTO> getEntrada(@PathVariable Long id) {
        log.debug("REST request to get Entrada : {}", id);
        Optional<EntradaDTO> entradaDTO = entradaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entradaDTO);
    }

    /**
     * DELETE  /entradas/:id : delete the "id" entrada.
     *
     * @param id the id of the entradaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entradas/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntrada(@PathVariable Long id) {
        log.debug("REST request to delete Entrada : {}", id);
        entradaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/entradas?query=:query : search for the entrada corresponding
     * to the query.
     *
     * @param query the query of the entrada search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/entradas")
    @Timed
    public ResponseEntity<List<EntradaDTO>> searchEntradas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Entradas for query {}", query);
        Page<EntradaDTO> page = entradaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/entradas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
