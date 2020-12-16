package com.emard.artisan.service;

import com.emard.artisan.service.dto.UserDTO;
import com.emard.artisan.service.utils.IConstantes;
import com.emard.artisan.domain.Authority;
import com.emard.artisan.domain.Client;
import com.emard.artisan.domain.User;
import com.emard.artisan.repository.ClientRepository;
import com.emard.artisan.security.AuthoritiesConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Client}.
 */
@Service
@Transactional
public class ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;

    private final UserService userService;

    private final MailService mailService;
    //private final TypeUserService typeUserService;

    public ClientService(ClientRepository clientRepository, UserService userService, MailService mailService) {
        this.clientRepository = clientRepository;
        this.userService = userService;
        this.mailService = mailService;
        //this.typeUserService = typeUserService;
    }

    /**
     * Save a client.
     *
     * @param client the entity to save.
     * @return the persisted entity.
     */
    public Client save(Client client) {
        log.debug("Request to save Client : {}", client);
        client.deleted(false);
        if(client.getId()==null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setLogin(client.getLogin());
            userDTO.setFirstName(client.getPrenom());
            userDTO.setLastName(client.getNom());
            userDTO.setEmail(client.getEmail());
            User us = userService.createUser(userDTO);
            Authority authority = new Authority();
            authority.setName(AuthoritiesConstants.CLIENT);
            us.getAuthorities().add(authority);
            authority = new Authority();
            authority.setName(AuthoritiesConstants.USER);
            us.getAuthorities().add(authority);
            client.users(us);
            mailService.sendCreationEmail(us);
        }
        return clientRepository.save(client);
    }

    /**
     * Get all the clients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Client> findAll(Pageable pageable) {
        log.debug("Request to get all Clients");
        return clientRepository.findByDeletedFalseOrderByNomAscPrenomAsc(pageable);
    }


    /**
     * Get one client by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Client> findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findById(id);
    }

    /**
     * Delete the client by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        Optional <Client> client = clientRepository.findById(id);
        if(client.isPresent()) client.get().deleted(true);
        clientRepository.save(client.get());
    }
}
