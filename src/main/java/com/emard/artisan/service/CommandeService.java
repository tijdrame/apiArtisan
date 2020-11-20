package com.emard.artisan.service;

import com.emard.artisan.domain.Commande;
import com.emard.artisan.repository.CommandeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Commande}.
 */
@Service
@Transactional
public class CommandeService {

    private final Logger log = LoggerFactory.getLogger(CommandeService.class);

    private final CommandeRepository commandeRepository;

    public CommandeService(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    /**
     * Save a commande.
     *
     * @param commande the entity to save.
     * @return the persisted entity.
     */
    public Commande save(Commande commande) {
        log.debug("Request to save Commande : {}", commande);
        commande.deleted(false);
        return commandeRepository.save(commande);
    }

    /**
     * Get all the commandes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Commande> findAll(Pageable pageable) {
        log.debug("Request to get all Commandes");
        return commandeRepository.findByDeletedFalseOrderByDateCommandeDesc(pageable);
    }


    /**
     * Get one commande by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Commande> findOne(Long id) {
        log.debug("Request to get Commande : {}", id);
        return commandeRepository.findById(id);
    }

    /**
     * Delete the commande by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Commande : {}", id);
        Optional<Commande> commande = commandeRepository.findById(id);
        if(commande.isPresent()) commande.get().deleted(true);
        commandeRepository.save(commande.get());
    }
}
