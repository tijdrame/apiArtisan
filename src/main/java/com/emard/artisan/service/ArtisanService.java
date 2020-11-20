package com.emard.artisan.service;

import com.emard.artisan.domain.Artisan;
import com.emard.artisan.repository.ArtisanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Artisan}.
 */
@Service
@Transactional
public class ArtisanService {

    private final Logger log = LoggerFactory.getLogger(ArtisanService.class);

    private final ArtisanRepository artisanRepository;

    public ArtisanService(ArtisanRepository artisanRepository) {
        this.artisanRepository = artisanRepository;
    }

    /**
     * Save a artisan.
     *
     * @param artisan the entity to save.
     * @return the persisted entity.
     */
    public Artisan save(Artisan artisan) {
        log.debug("Request to save Artisan : {}", artisan);
        artisan.deleted(false);
        return artisanRepository.save(artisan);
    }

    /**
     * Get all the artisans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Artisan> findAll(Pageable pageable) {
        log.debug("Request to get all Artisans");
        return artisanRepository.findByDeletedFalseOrderByNomAscPrenomAsc(pageable);
    }


    /**
     * Get one artisan by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Artisan> findOne(Long id) {
        log.debug("Request to get Artisan : {}", id);
        return artisanRepository.findById(id);
    }

    /**
     * Delete the artisan by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Artisan : {}", id);
        Optional <Artisan> artisan = artisanRepository.findById(id);
        if(artisan.isPresent()) artisan.get().deleted(true);
        artisanRepository.save(artisan.get());
    }
}
