package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.VendedorService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.VendedorDTO;
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
 * REST controller for managing Vendedor.
 */
@RestController
@RequestMapping("/api")
public class VendedorResource {

    private final Logger log = LoggerFactory.getLogger(VendedorResource.class);

    private static final String ENTITY_NAME = "vendedor";

    private final VendedorService vendedorService;

    public VendedorResource(VendedorService vendedorService) {
        this.vendedorService = vendedorService;
    }

    /**
     * POST  /vendedors : Create a new vendedor.
     *
     * @param vendedorDTO the vendedorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vendedorDTO, or with status 400 (Bad Request) if the vendedor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vendedors")
    @Timed
    public ResponseEntity<VendedorDTO> createVendedor(@Valid @RequestBody VendedorDTO vendedorDTO) throws URISyntaxException {
        log.debug("REST request to save Vendedor : {}", vendedorDTO);
        if (vendedorDTO.getId() != null) {
            throw new BadRequestAlertException("A new vendedor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VendedorDTO result = vendedorService.save(vendedorDTO);
        return ResponseEntity.created(new URI("/api/vendedors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vendedors : Updates an existing vendedor.
     *
     * @param vendedorDTO the vendedorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vendedorDTO,
     * or with status 400 (Bad Request) if the vendedorDTO is not valid,
     * or with status 500 (Internal Server Error) if the vendedorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vendedors")
    @Timed
    public ResponseEntity<VendedorDTO> updateVendedor(@Valid @RequestBody VendedorDTO vendedorDTO) throws URISyntaxException {
        log.debug("REST request to update Vendedor : {}", vendedorDTO);
        if (vendedorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VendedorDTO result = vendedorService.save(vendedorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vendedorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vendedors : get all the vendedors.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of vendedors in body
     */
    @GetMapping("/vendedors")
    @Timed
    public ResponseEntity<List<VendedorDTO>> getAllVendedors(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Vendedors");
        Page<VendedorDTO> page;
        if (eagerload) {
            page = vendedorService.findAllWithEagerRelationships(pageable);
        } else {
            page = vendedorService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/vendedors?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vendedors/:id : get the "id" vendedor.
     *
     * @param id the id of the vendedorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vendedorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vendedors/{id}")
    @Timed
    public ResponseEntity<VendedorDTO> getVendedor(@PathVariable Long id) {
        log.debug("REST request to get Vendedor : {}", id);
        Optional<VendedorDTO> vendedorDTO = vendedorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vendedorDTO);
    }

    /**
     * DELETE  /vendedors/:id : delete the "id" vendedor.
     *
     * @param id the id of the vendedorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vendedors/{id}")
    @Timed
    public ResponseEntity<Void> deleteVendedor(@PathVariable Long id) {
        log.debug("REST request to delete Vendedor : {}", id);
        vendedorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/vendedors?query=:query : search for the vendedor corresponding
     * to the query.
     *
     * @param query the query of the vendedor search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/vendedors")
    @Timed
    public ResponseEntity<List<VendedorDTO>> searchVendedors(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Vendedors for query {}", query);
        Page<VendedorDTO> page = vendedorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/vendedors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
