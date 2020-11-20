package com.emard.artisan.service;

import com.emard.artisan.domain.Specialite;
import com.emard.artisan.repository.SpecialiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Specialite}.
 */
@Service
@Transactional
public class SpecialiteService {

    private final Logger log = LoggerFactory.getLogger(SpecialiteService.class);

    private final SpecialiteRepository specialiteRepository;

    public SpecialiteService(SpecialiteRepository specialiteRepository) {
        this.specialiteRepository = specialiteRepository;
    }

    /**
     * Save a specialite.
     *
     * @param specialite the entity to save.
     * @return the persisted entity.
     */
    public Specialite save(Specialite specialite) {
        log.debug("Request to save Specialite : {}", specialite);
        specialite.deleted(false);
        return specialiteRepository.save(specialite);
    }

    /**
     * Get all the specialites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Specialite> findAll(Pageable pageable) {
        log.debug("Request to get all Specialites");
        return specialiteRepository.findByDeletedFalseOrderByLibelle(pageable);
    }


    /**
     * Get one specialite by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Specialite> findOne(Long id) {
        log.debug("Request to get Specialite : {}", id);
        return specialiteRepository.findById(id);
    }

    /**
     * Delete the specialite by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Specialite : {}", id);
        Optional<Specialite> specialite = specialiteRepository.findById(id);
        if(specialite.isPresent()) specialite.get().deleted(true);
        specialiteRepository.save(specialite.get());
    }
}
