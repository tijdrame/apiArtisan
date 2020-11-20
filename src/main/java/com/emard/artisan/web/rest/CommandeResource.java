package com.emard.artisan.web.rest;

import com.emard.artisan.domain.Commande;
import com.emard.artisan.service.CommandeService;
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
 * REST controller for managing {@link com.emard.artisan.domain.Commande}.
 */
@RestController
@RequestMapping("/api")
public class CommandeResource {

    private final Logger log = LoggerFactory.getLogger(CommandeResource.class);

    private static final String ENTITY_NAME = "commande";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommandeService commandeService;

    public CommandeResource(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    /**
     * {@code POST  /commandes} : Create a new commande.
     *
     * @param commande the commande to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commande, or with status {@code 400 (Bad Request)} if the commande has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commandes")
    public ResponseEntity<Commande> createCommande(@RequestBody Commande commande) throws URISyntaxException {
        log.debug("REST request to save Commande : {}", commande);
        if (commande.getId() != null) {
            throw new BadRequestAlertException("A new commande cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Commande result = commandeService.save(commande);
        return ResponseEntity.created(new URI("/api/commandes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commandes} : Updates an existing commande.
     *
     * @param commande the commande to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commande,
     * or with status {@code 400 (Bad Request)} if the commande is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commande couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commandes")
    public ResponseEntity<Commande> updateCommande(@RequestBody Commande commande) throws URISyntaxException {
        log.debug("REST request to update Commande : {}", commande);
        if (commande.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Commande result = commandeService.save(commande);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commande.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /commandes} : get all the commandes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commandes in body.
     */
    @GetMapping("/commandes")
    public ResponseEntity<List<Commande>> getAllCommandes(Pageable pageable) {
        log.debug("REST request to get a page of Commandes");
        Page<Commande> page = commandeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /commandes/:id} : get the "id" commande.
     *
     * @param id the id of the commande to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commande, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commandes/{id}")
    public ResponseEntity<Commande> getCommande(@PathVariable Long id) {
        log.debug("REST request to get Commande : {}", id);
        Optional<Commande> commande = commandeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commande);
    }

    /**
     * {@code DELETE  /commandes/:id} : delete the "id" commande.
     *
     * @param id the id of the commande to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commandes/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        log.debug("REST request to delete Commande : {}", id);
        commandeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
