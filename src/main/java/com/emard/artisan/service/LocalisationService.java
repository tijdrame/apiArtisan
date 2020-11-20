package com.emard.artisan.service;

import com.emard.artisan.domain.Localisation;
import com.emard.artisan.repository.LocalisationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Localisation}.
 */
@Service
@Transactional
public class LocalisationService {

    private final Logger log = LoggerFactory.getLogger(LocalisationService.class);

    private final LocalisationRepository localisationRepository;

    public LocalisationService(LocalisationRepository localisationRepository) {
        this.localisationRepository = localisationRepository;
    }

    /**
     * Save a localisation.
     *
     * @param localisation the entity to save.
     * @return the persisted entity.
     */
    public Localisation save(Localisation localisation) {
        log.debug("Request to save Localisation : {}", localisation);
        localisation.deleted(false);
        return localisationRepository.save(localisation);
    }

    /**
     * Get all the localisations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Localisation> findAll(Pageable pageable) {
        log.debug("Request to get all Localisations");
        return localisationRepository.findByDeletedFalseOrderByAdresse(pageable);
    }


    /**
     * Get one localisation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Localisation> findOne(Long id) {
        log.debug("Request to get Localisation : {}", id);
        return localisationRepository.findById(id);
    }

    /**
     * Delete the localisation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Localisation : {}", id);
        Optional<Localisation> localisation = localisationRepository.findById(id);
        if(localisation.isPresent()) localisation.get().deleted(true);
        localisationRepository.save(localisation.get());
    }
}
