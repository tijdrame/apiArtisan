package com.emard.artisan.web.rest;

import com.emard.artisan.domain.Artisan;
import com.emard.artisan.service.ArtisanService;
import com.emard.artisan.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.emard.artisan.domain.Artisan}.
 */
@RestController
@RequestMapping("/api")
public class ArtisanResource {

    private final Logger log = LoggerFactory.getLogger(ArtisanResource.class);

    private static final String ENTITY_NAME = "artisan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtisanService artisanService;

    public ArtisanResource(ArtisanService artisanService) {
        this.artisanService = artisanService;
    }

    /**
     * {@code POST  /artisans} : Create a new artisan.
     *
     * @param artisan the artisan to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new artisan, or with status {@code 400 (Bad Request)} if the artisan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/artisans")
    public ResponseEntity<Artisan> createArtisan(@Valid @RequestBody Artisan artisan) throws URISyntaxException {
        log.debug("REST request to save Artisan : {}", artisan);
        if (artisan.getId() != null) {
            throw new BadRequestAlertException("A new artisan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Artisan result = artisanService.save(artisan);
        return ResponseEntity.created(new URI("/api/artisans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /artisans} : Updates an existing artisan.
     *
     * @param artisan the artisan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artisan,
     * or with status {@code 400 (Bad Request)} if the artisan is not valid,
     * or with status {@code 500 (Internal Server Error)} if the artisan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/artisans")
    public ResponseEntity<Artisan> updateArtisan(@Valid @RequestBody Artisan artisan) throws URISyntaxException {
        log.debug("REST request to update Artisan : {}", artisan);
        if (artisan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Artisan result = artisanService.save(artisan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, artisan.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /artisans} : get all the artisans.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of artisans in body.
     */
    @GetMapping("/artisans")
    public ResponseEntity<List<Artisan>> getAllArtisans(Pageable pageable) {
        log.debug("REST request to get a page of Artisans");
        Page<Artisan> page = artisanService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /artisans/:id} : get the "id" artisan.
     *
     * @param id the id of the artisan to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the artisan, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/artisans/{id}")
    public ResponseEntity<Artisan> getArtisan(@PathVariable Long id) {
        log.debug("REST request to get Artisan : {}", id);
        Optional<Artisan> artisan = artisanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(artisan);
    }

    /**
     * {@code DELETE  /artisans/:id} : delete the "id" artisan.
     *
     * @param id the id of the artisan to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/artisans/{id}")
    public ResponseEntity<Void> deleteArtisan(@PathVariable Long id) {
        log.debug("REST request to delete Artisan : {}", id);
        artisanService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
