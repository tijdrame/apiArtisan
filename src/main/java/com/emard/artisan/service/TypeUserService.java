package com.emard.artisan.service;

import com.emard.artisan.domain.TypeUser;
import com.emard.artisan.repository.TypeUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeUser}.
 */
@Service
@Transactional
public class TypeUserService {

    private final Logger log = LoggerFactory.getLogger(TypeUserService.class);

    private final TypeUserRepository typeUserRepository;

    public TypeUserService(TypeUserRepository typeUserRepository) {
        this.typeUserRepository = typeUserRepository;
    }

    /**
     * Save a typeUser.
     *
     * @param typeUser the entity to save.
     * @return the persisted entity.
     */
    public TypeUser save(TypeUser typeUser) {
        log.debug("Request to save TypeUser : {}", typeUser);
        typeUser.deleted(false);
        return typeUserRepository.save(typeUser);
    }

    /**
     * Get all the typeUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeUser> findAll(Pageable pageable) {
        log.debug("Request to get all TypeUsers");
        return typeUserRepository.findByDeletedFalseOrderByLibelle(pageable);
    }


    /**
     * Get one typeUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TypeUser> findOne(Long id) {
        log.debug("Request to get TypeUser : {}", id);
        return typeUserRepository.findById(id);
    }

    /**
     * Delete the typeUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeUser : {}", id);
        Optional<TypeUser> typeUser = typeUserRepository.findById(id);
        if(typeUser.isPresent()) typeUser.get().deleted(true);
        typeUserRepository.deleteById(id);
    }
}
