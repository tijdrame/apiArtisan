package com.emard.artisan.service;

import com.emard.artisan.domain.UserProduit;
import com.emard.artisan.repository.UserProduitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserProduit}.
 */
@Service
@Transactional
public class UserProduitService {

    private final Logger log = LoggerFactory.getLogger(UserProduitService.class);

    private final UserProduitRepository userProduitRepository;

    public UserProduitService(UserProduitRepository userProduitRepository) {
        this.userProduitRepository = userProduitRepository;
    }

    /**
     * Save a userProduit.
     *
     * @param userProduit the entity to save.
     * @return the persisted entity.
     */
    public UserProduit save(UserProduit userProduit) {
        log.debug("Request to save UserProduit : {}", userProduit);
        return userProduitRepository.save(userProduit);
    }

    /**
     * Get all the userProduits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserProduit> findAll(Pageable pageable) {
        log.debug("Request to get all UserProduits");
        return userProduitRepository.findAll(pageable);
    }


    /**
     * Get one userProduit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserProduit> findOne(Long id) {
        log.debug("Request to get UserProduit : {}", id);
        return userProduitRepository.findById(id);
    }

    /**
     * Delete the userProduit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserProduit : {}", id);
        userProduitRepository.deleteById(id);
    }
}
