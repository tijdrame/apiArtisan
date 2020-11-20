package com.emard.artisan.web.rest;

import com.emard.artisan.domain.TypeUser;
import com.emard.artisan.service.TypeUserService;
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
 * REST controller for managing {@link com.emard.artisan.domain.TypeUser}.
 */
@RestController
@RequestMapping("/api")
public class TypeUserResource {

    private final Logger log = LoggerFactory.getLogger(TypeUserResource.class);

    private static final String ENTITY_NAME = "typeUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeUserService typeUserService;

    public TypeUserResource(TypeUserService typeUserService) {
        this.typeUserService = typeUserService;
    }

    /**
     * {@code POST  /type-users} : Create a new typeUser.
     *
     * @param typeUser the typeUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeUser, or with status {@code 400 (Bad Request)} if the typeUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-users")
    public ResponseEntity<TypeUser> createTypeUser(@Valid @RequestBody TypeUser typeUser) throws URISyntaxException {
        log.debug("REST request to save TypeUser : {}", typeUser);
        if (typeUser.getId() != null) {
            throw new BadRequestAlertException("A new typeUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeUser result = typeUserService.save(typeUser);
        return ResponseEntity.created(new URI("/api/type-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-users} : Updates an existing typeUser.
     *
     * @param typeUser the typeUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeUser,
     * or with status {@code 400 (Bad Request)} if the typeUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-users")
    public ResponseEntity<TypeUser> updateTypeUser(@Valid @RequestBody TypeUser typeUser) throws URISyntaxException {
        log.debug("REST request to update TypeUser : {}", typeUser);
        if (typeUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeUser result = typeUserService.save(typeUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-users} : get all the typeUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeUsers in body.
     */
    @GetMapping("/type-users")
    public ResponseEntity<List<TypeUser>> getAllTypeUsers(Pageable pageable) {
        log.debug("REST request to get a page of TypeUsers");
        Page<TypeUser> page = typeUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-users/:id} : get the "id" typeUser.
     *
     * @param id the id of the typeUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-users/{id}")
    public ResponseEntity<TypeUser> getTypeUser(@PathVariable Long id) {
        log.debug("REST request to get TypeUser : {}", id);
        Optional<TypeUser> typeUser = typeUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeUser);
    }

    /**
     * {@code DELETE  /type-users/:id} : delete the "id" typeUser.
     *
     * @param id the id of the typeUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-users/{id}")
    public ResponseEntity<Void> deleteTypeUser(@PathVariable Long id) {
        log.debug("REST request to delete TypeUser : {}", id);
        typeUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
