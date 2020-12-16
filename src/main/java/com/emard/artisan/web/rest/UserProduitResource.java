package com.emard.artisan.web.rest;

import com.emard.artisan.domain.UserProduit;
import com.emard.artisan.service.UserProduitService;
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
 * REST controller for managing {@link com.emard.artisan.domain.UserProduit}.
 */
@RestController
@RequestMapping("/api")
public class UserProduitResource {

    private final Logger log = LoggerFactory.getLogger(UserProduitResource.class);

    private static final String ENTITY_NAME = "userProduit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserProduitService userProduitService;

    public UserProduitResource(UserProduitService userProduitService) {
        this.userProduitService = userProduitService;
    }

    /**
     * {@code POST  /user-produits} : Create a new userProduit.
     *
     * @param userProduit the userProduit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userProduit, or with status {@code 400 (Bad Request)} if the userProduit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-produits")
    public ResponseEntity<UserProduit> createUserProduit(@RequestBody UserProduit userProduit) throws URISyntaxException {
        log.debug("REST request to save UserProduit : {}", userProduit);
        if (userProduit.getId() != null) {
            throw new BadRequestAlertException("A new userProduit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserProduit result = userProduitService.save(userProduit);
        return ResponseEntity.created(new URI("/api/user-produits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-produits} : Updates an existing userProduit.
     *
     * @param userProduit the userProduit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userProduit,
     * or with status {@code 400 (Bad Request)} if the userProduit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userProduit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-produits")
    public ResponseEntity<UserProduit> updateUserProduit(@RequestBody UserProduit userProduit) throws URISyntaxException {
        log.debug("REST request to update UserProduit : {}", userProduit);
        if (userProduit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserProduit result = userProduitService.save(userProduit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userProduit.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-produits} : get all the userProduits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userProduits in body.
     */
    @GetMapping("/user-produits")
    public ResponseEntity<List<UserProduit>> getAllUserProduits(Pageable pageable) {
        log.debug("REST request to get a page of UserProduits");
        Page<UserProduit> page = userProduitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-produits/:id} : get the "id" userProduit.
     *
     * @param id the id of the userProduit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userProduit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-produits/{id}")
    public ResponseEntity<UserProduit> getUserProduit(@PathVariable Long id) {
        log.debug("REST request to get UserProduit : {}", id);
        Optional<UserProduit> userProduit = userProduitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userProduit);
    }

    /**
     * {@code DELETE  /user-produits/:id} : delete the "id" userProduit.
     *
     * @param id the id of the userProduit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-produits/{id}")
    public ResponseEntity<Void> deleteUserProduit(@PathVariable Long id) {
        log.debug("REST request to delete UserProduit : {}", id);
        userProduitService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
