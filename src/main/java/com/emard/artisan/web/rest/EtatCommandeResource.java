package com.emard.artisan.web.rest;

import com.emard.artisan.domain.EtatCommande;
import com.emard.artisan.service.EtatCommandeService;
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
 * REST controller for managing {@link com.emard.artisan.domain.EtatCommande}.
 */
@RestController
@RequestMapping("/api")
public class EtatCommandeResource {

    private final Logger log = LoggerFactory.getLogger(EtatCommandeResource.class);

    private static final String ENTITY_NAME = "etatCommande";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtatCommandeService etatCommandeService;

    public EtatCommandeResource(EtatCommandeService etatCommandeService) {
        this.etatCommandeService = etatCommandeService;
    }

    /**
     * {@code POST  /etat-commandes} : Create a new etatCommande.
     *
     * @param etatCommande the etatCommande to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etatCommande, or with status {@code 400 (Bad Request)} if the etatCommande has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etat-commandes")
    public ResponseEntity<EtatCommande> createEtatCommande(@Valid @RequestBody EtatCommande etatCommande) throws URISyntaxException {
        log.debug("REST request to save EtatCommande : {}", etatCommande);
        if (etatCommande.getId() != null) {
            throw new BadRequestAlertException("A new etatCommande cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtatCommande result = etatCommandeService.save(etatCommande);
        return ResponseEntity.created(new URI("/api/etat-commandes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etat-commandes} : Updates an existing etatCommande.
     *
     * @param etatCommande the etatCommande to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etatCommande,
     * or with status {@code 400 (Bad Request)} if the etatCommande is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etatCommande couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etat-commandes")
    public ResponseEntity<EtatCommande> updateEtatCommande(@Valid @RequestBody EtatCommande etatCommande) throws URISyntaxException {
        log.debug("REST request to update EtatCommande : {}", etatCommande);
        if (etatCommande.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EtatCommande result = etatCommandeService.save(etatCommande);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etatCommande.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /etat-commandes} : get all the etatCommandes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etatCommandes in body.
     */
    @GetMapping("/etat-commandes")
    public ResponseEntity<List<EtatCommande>> getAllEtatCommandes(Pageable pageable) {
        log.debug("REST request to get a page of EtatCommandes");
        Page<EtatCommande> page = etatCommandeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etat-commandes/:id} : get the "id" etatCommande.
     *
     * @param id the id of the etatCommande to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etatCommande, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etat-commandes/{id}")
    public ResponseEntity<EtatCommande> getEtatCommande(@PathVariable Long id) {
        log.debug("REST request to get EtatCommande : {}", id);
        Optional<EtatCommande> etatCommande = etatCommandeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etatCommande);
    }

    /**
     * {@code DELETE  /etat-commandes/:id} : delete the "id" etatCommande.
     *
     * @param id the id of the etatCommande to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etat-commandes/{id}")
    public ResponseEntity<Void> deleteEtatCommande(@PathVariable Long id) {
        log.debug("REST request to delete EtatCommande : {}", id);
        etatCommandeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
