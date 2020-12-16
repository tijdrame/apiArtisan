package com.emard.artisan.web.rest;

import com.emard.artisan.ApiArtisanApp;
import com.emard.artisan.domain.UserProduit;
import com.emard.artisan.repository.UserProduitRepository;
import com.emard.artisan.service.UserProduitService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UserProduitResource} REST controller.
 */
@SpringBootTest(classes = ApiArtisanApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserProduitResourceIT {

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private UserProduitRepository userProduitRepository;

    @Autowired
    private UserProduitService userProduitService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserProduitMockMvc;

    private UserProduit userProduit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProduit createEntity(EntityManager em) {
        UserProduit userProduit = new UserProduit()
            .deleted(DEFAULT_DELETED);
        return userProduit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProduit createUpdatedEntity(EntityManager em) {
        UserProduit userProduit = new UserProduit()
            .deleted(UPDATED_DELETED);
        return userProduit;
    }

    @BeforeEach
    public void initTest() {
        userProduit = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserProduit() throws Exception {
        int databaseSizeBeforeCreate = userProduitRepository.findAll().size();
        // Create the UserProduit
        restUserProduitMockMvc.perform(post("/api/user-produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProduit)))
            .andExpect(status().isCreated());

        // Validate the UserProduit in the database
        List<UserProduit> userProduitList = userProduitRepository.findAll();
        assertThat(userProduitList).hasSize(databaseSizeBeforeCreate + 1);
        UserProduit testUserProduit = userProduitList.get(userProduitList.size() - 1);
        assertThat(testUserProduit.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createUserProduitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userProduitRepository.findAll().size();

        // Create the UserProduit with an existing ID
        userProduit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserProduitMockMvc.perform(post("/api/user-produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProduit)))
            .andExpect(status().isBadRequest());

        // Validate the UserProduit in the database
        List<UserProduit> userProduitList = userProduitRepository.findAll();
        assertThat(userProduitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserProduits() throws Exception {
        // Initialize the database
        userProduitRepository.saveAndFlush(userProduit);

        // Get all the userProduitList
        restUserProduitMockMvc.perform(get("/api/user-produits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userProduit.getId().intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getUserProduit() throws Exception {
        // Initialize the database
        userProduitRepository.saveAndFlush(userProduit);

        // Get the userProduit
        restUserProduitMockMvc.perform(get("/api/user-produits/{id}", userProduit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userProduit.getId().intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserProduit() throws Exception {
        // Get the userProduit
        restUserProduitMockMvc.perform(get("/api/user-produits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserProduit() throws Exception {
        // Initialize the database
        userProduitService.save(userProduit);

        int databaseSizeBeforeUpdate = userProduitRepository.findAll().size();

        // Update the userProduit
        UserProduit updatedUserProduit = userProduitRepository.findById(userProduit.getId()).get();
        // Disconnect from session so that the updates on updatedUserProduit are not directly saved in db
        em.detach(updatedUserProduit);
        updatedUserProduit
            .deleted(UPDATED_DELETED);

        restUserProduitMockMvc.perform(put("/api/user-produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserProduit)))
            .andExpect(status().isOk());

        // Validate the UserProduit in the database
        List<UserProduit> userProduitList = userProduitRepository.findAll();
        assertThat(userProduitList).hasSize(databaseSizeBeforeUpdate);
        UserProduit testUserProduit = userProduitList.get(userProduitList.size() - 1);
        assertThat(testUserProduit.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingUserProduit() throws Exception {
        int databaseSizeBeforeUpdate = userProduitRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserProduitMockMvc.perform(put("/api/user-produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProduit)))
            .andExpect(status().isBadRequest());

        // Validate the UserProduit in the database
        List<UserProduit> userProduitList = userProduitRepository.findAll();
        assertThat(userProduitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserProduit() throws Exception {
        // Initialize the database
        userProduitService.save(userProduit);

        int databaseSizeBeforeDelete = userProduitRepository.findAll().size();

        // Delete the userProduit
        restUserProduitMockMvc.perform(delete("/api/user-produits/{id}", userProduit.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserProduit> userProduitList = userProduitRepository.findAll();
        assertThat(userProduitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
