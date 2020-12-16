package com.emard.artisan.service;

import com.emard.artisan.domain.Artisan;
import com.emard.artisan.domain.Authority;
import com.emard.artisan.domain.User;
import com.emard.artisan.repository.ArtisanRepository;
import com.emard.artisan.security.AuthoritiesConstants;
import com.emard.artisan.service.dto.UserDTO;

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

    private final UserService userService;

    private final MailService mailService;

    public ArtisanService(ArtisanRepository artisanRepository, UserService userService, MailService mailService) {
        this.artisanRepository = artisanRepository;
        this.userService = userService;
        this.mailService = mailService;
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
        if(artisan.getId()==null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setLogin(artisan.getLogin());
            userDTO.setFirstName(artisan.getPrenom());
            userDTO.setLastName(artisan.getNom());
            userDTO.setEmail(artisan.getEmail());
            User us = userService.createUser(userDTO);
            Authority authority = new Authority();
            authority.setName(AuthoritiesConstants.ARTISAN);
            us.getAuthorities().add(authority);
            authority = new Authority();
            authority.setName(AuthoritiesConstants.USER);
            us.getAuthorities().add(authority);
            artisan.user(us);
            mailService.sendCreationEmail(us);
        }
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