package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.SaidaService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.SaidaDTO;
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
 * REST controller for managing Saida.
 */
@RestController
@RequestMapping("/api")
public class SaidaResource {

    private final Logger log = LoggerFactory.getLogger(SaidaResource.class);

    private static final String ENTITY_NAME = "saida";

    private final SaidaService saidaService;

    public SaidaResource(SaidaService saidaService) {
        this.saidaService = saidaService;
    }

    /**
     * POST  /saidas : Create a new saida.
     *
     * @param saidaDTO the saidaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new saidaDTO, or with status 400 (Bad Request) if the saida has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/saidas")
    @Timed
    public ResponseEntity<SaidaDTO> createSaida(@Valid @RequestBody SaidaDTO saidaDTO) throws URISyntaxException {
        log.debug("REST request to save Saida : {}", saidaDTO);
        if (saidaDTO.getId() != null) {
            throw new BadRequestAlertException("A new saida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SaidaDTO result = saidaService.save(saidaDTO);
        return ResponseEntity.created(new URI("/api/saidas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /saidas : Updates an existing saida.
     *
     * @param saidaDTO the saidaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated saidaDTO,
     * or with status 400 (Bad Request) if the saidaDTO is not valid,
     * or with status 500 (Internal Server Error) if the saidaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/saidas")
    @Timed
    public ResponseEntity<SaidaDTO> updateSaida(@Valid @RequestBody SaidaDTO saidaDTO) throws URISyntaxException {
        log.debug("REST request to update Saida : {}", saidaDTO);
        if (saidaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SaidaDTO result = saidaService.save(saidaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, saidaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /saidas : get all the saidas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of saidas in body
     */
    @GetMapping("/saidas")
    @Timed
    public ResponseEntity<List<SaidaDTO>> getAllSaidas(Pageable pageable) {
        log.debug("REST request to get a page of Saidas");
        Page<SaidaDTO> page = saidaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/saidas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /saidas/:id : get the "id" saida.
     *
     * @param id the id of the saidaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the saidaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/saidas/{id}")
    @Timed
    public ResponseEntity<SaidaDTO> getSaida(@PathVariable Long id) {
        log.debug("REST request to get Saida : {}", id);
        Optional<SaidaDTO> saidaDTO = saidaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(saidaDTO);
    }

    /**
     * DELETE  /saidas/:id : delete the "id" saida.
     *
     * @param id the id of the saidaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/saidas/{id}")
    @Timed
    public ResponseEntity<Void> deleteSaida(@PathVariable Long id) {
        log.debug("REST request to delete Saida : {}", id);
        saidaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/saidas?query=:query : search for the saida corresponding
     * to the query.
     *
     * @param query the query of the saida search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/saidas")
    @Timed
    public ResponseEntity<List<SaidaDTO>> searchSaidas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Saidas for query {}", query);
        Page<SaidaDTO> page = saidaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/saidas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
