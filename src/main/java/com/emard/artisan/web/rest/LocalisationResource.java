package com.emard.artisan.web.rest;

import com.emard.artisan.domain.Localisation;
import com.emard.artisan.service.LocalisationService;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.emard.artisan.domain.Localisation}.
 */
@RestController
@RequestMapping("/api")
public class LocalisationResource {

    private final Logger log = LoggerFactory.getLogger(LocalisationResource.class);

    private static final String ENTITY_NAME = "localisation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocalisationService localisationService;

    public LocalisationResource(LocalisationService localisationService) {
        this.localisationService = localisationService;
    }

    /**
     * {@code POST  /localisations} : Create a new localisation.
     *
     * @param localisation the localisation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new localisation, or with status {@code 400 (Bad Request)} if the localisation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/localisations")
    public ResponseEntity<Localisation> createLocalisation(@RequestBody Localisation localisation) throws URISyntaxException {
        log.debug("REST request to save Localisation : {}", localisation);
        if (localisation.getId() != null) {
            throw new BadRequestAlertException("A new localisation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Localisation result = localisationService.save(localisation);
        return ResponseEntity.created(new URI("/api/localisations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /localisations} : Updates an existing localisation.
     *
     * @param localisation the localisation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated localisation,
     * or with status {@code 400 (Bad Request)} if the localisation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the localisation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/localisations")
    public ResponseEntity<Localisation> updateLocalisation(@RequestBody Localisation localisation) throws URISyntaxException {
        log.debug("REST request to update Localisation : {}", localisation);
        if (localisation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Localisation result = localisationService.save(localisation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, localisation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /localisations} : get all the localisations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of localisations in body.
     */
    @GetMapping("/localisations")
    public ResponseEntity<List<Localisation>> getAllLocalisations(Pageable pageable) {
        log.debug("REST request to get a page of Localisations");
        Page<Localisation> page = localisationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /localisations/:id} : get the "id" localisation.
     *
     * @param id the id of the localisation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the localisation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/localisations/{id}")
    public ResponseEntity<Localisation> getLocalisation(@PathVariable Long id) {
        log.debug("REST request to get Localisation : {}", id);
        Optional<Localisation> localisation = localisationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(localisation);
    }

    /**
     * {@code DELETE  /localisations/:id} : delete the "id" localisation.
     *
     * @param id the id of the localisation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/localisations/{id}")
    public ResponseEntity<Void> deleteLocalisation(@PathVariable Long id) {
        log.debug("REST request to delete Localisation : {}", id);
        localisationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
