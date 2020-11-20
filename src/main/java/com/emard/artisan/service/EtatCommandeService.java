package com.emard.artisan.service;

import com.emard.artisan.domain.EtatCommande;
import com.emard.artisan.repository.EtatCommandeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EtatCommande}.
 */
@Service
@Transactional
public class EtatCommandeService {

    private final Logger log = LoggerFactory.getLogger(EtatCommandeService.class);

    private final EtatCommandeRepository etatCommandeRepository;

    public EtatCommandeService(EtatCommandeRepository etatCommandeRepository) {
        this.etatCommandeRepository = etatCommandeRepository;
    }

    /**
     * Save a etatCommande.
     *
     * @param etatCommande the entity to save.
     * @return the persisted entity.
     */
    public EtatCommande save(EtatCommande etatCommande) {
        log.debug("Request to save EtatCommande : {}", etatCommande);
        etatCommande.deleted(false);
        return etatCommandeRepository.save(etatCommande);
    }

    /**
     * Get all the etatCommandes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EtatCommande> findAll(Pageable pageable) {
        log.debug("Request to get all EtatCommandes");
        return etatCommandeRepository.findByDeletedFalseOrderByLibelle(pageable);
    }


    /**
     * Get one etatCommande by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EtatCommande> findOne(Long id) {
        log.debug("Request to get EtatCommande : {}", id);
        return etatCommandeRepository.findById(id);
    }

    /**
     * Delete the etatCommande by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EtatCommande : {}", id);
        Optional<EtatCommande> etatComde = etatCommandeRepository.findById(id);
        if(etatComde.isPresent()) etatComde.get().deleted(true);
        etatCommandeRepository.save(etatComde.get());
    }
}
