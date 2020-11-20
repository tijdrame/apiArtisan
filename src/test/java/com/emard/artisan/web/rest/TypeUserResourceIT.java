package com.emard.artisan.web.rest;

import com.emard.artisan.ApiArtisanApp;
import com.emard.artisan.domain.TypeUser;
import com.emard.artisan.repository.TypeUserRepository;
import com.emard.artisan.service.TypeUserService;

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
 * Integration tests for the {@link TypeUserResource} REST controller.
 */
@SpringBootTest(classes = ApiArtisanApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TypeUserResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private TypeUserRepository typeUserRepository;

    @Autowired
    private TypeUserService typeUserService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeUserMockMvc;

    private TypeUser typeUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeUser createEntity(EntityManager em) {
        TypeUser typeUser = new TypeUser()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return typeUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeUser createUpdatedEntity(EntityManager em) {
        TypeUser typeUser = new TypeUser()
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);
        return typeUser;
    }

    @BeforeEach
    public void initTest() {
        typeUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeUser() throws Exception {
        int databaseSizeBeforeCreate = typeUserRepository.findAll().size();
        // Create the TypeUser
        restTypeUserMockMvc.perform(post("/api/type-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeUser)))
            .andExpect(status().isCreated());

        // Validate the TypeUser in the database
        List<TypeUser> typeUserList = typeUserRepository.findAll();
        assertThat(typeUserList).hasSize(databaseSizeBeforeCreate + 1);
        TypeUser testTypeUser = typeUserList.get(typeUserList.size() - 1);
        assertThat(testTypeUser.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTypeUser.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeUser.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createTypeUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeUserRepository.findAll().size();

        // Create the TypeUser with an existing ID
        typeUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeUserMockMvc.perform(post("/api/type-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeUser)))
            .andExpect(status().isBadRequest());

        // Validate the TypeUser in the database
        List<TypeUser> typeUserList = typeUserRepository.findAll();
        assertThat(typeUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeUserRepository.findAll().size();
        // set the field null
        typeUser.setLibelle(null);

        // Create the TypeUser, which fails.


        restTypeUserMockMvc.perform(post("/api/type-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeUser)))
            .andExpect(status().isBadRequest());

        List<TypeUser> typeUserList = typeUserRepository.findAll();
        assertThat(typeUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeUserRepository.findAll().size();
        // set the field null
        typeUser.setCode(null);

        // Create the TypeUser, which fails.


        restTypeUserMockMvc.perform(post("/api/type-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeUser)))
            .andExpect(status().isBadRequest());

        List<TypeUser> typeUserList = typeUserRepository.findAll();
        assertThat(typeUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeUsers() throws Exception {
        // Initialize the database
        typeUserRepository.saveAndFlush(typeUser);

        // Get all the typeUserList
        restTypeUserMockMvc.perform(get("/api/type-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTypeUser() throws Exception {
        // Initialize the database
        typeUserRepository.saveAndFlush(typeUser);

        // Get the typeUser
        restTypeUserMockMvc.perform(get("/api/type-users/{id}", typeUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeUser.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTypeUser() throws Exception {
        // Get the typeUser
        restTypeUserMockMvc.perform(get("/api/type-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeUser() throws Exception {
        // Initialize the database
        typeUserService.save(typeUser);

        int databaseSizeBeforeUpdate = typeUserRepository.findAll().size();

        // Update the typeUser
        TypeUser updatedTypeUser = typeUserRepository.findById(typeUser.getId()).get();
        // Disconnect from session so that the updates on updatedTypeUser are not directly saved in db
        em.detach(updatedTypeUser);
        updatedTypeUser
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);

        restTypeUserMockMvc.perform(put("/api/type-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeUser)))
            .andExpect(status().isOk());

        // Validate the TypeUser in the database
        List<TypeUser> typeUserList = typeUserRepository.findAll();
        assertThat(typeUserList).hasSize(databaseSizeBeforeUpdate);
        TypeUser testTypeUser = typeUserList.get(typeUserList.size() - 1);
        assertThat(testTypeUser.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypeUser.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeUser.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeUser() throws Exception {
        int databaseSizeBeforeUpdate = typeUserRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeUserMockMvc.perform(put("/api/type-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeUser)))
            .andExpect(status().isBadRequest());

        // Validate the TypeUser in the database
        List<TypeUser> typeUserList = typeUserRepository.findAll();
        assertThat(typeUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeUser() throws Exception {
        // Initialize the database
        typeUserService.save(typeUser);

        int databaseSizeBeforeDelete = typeUserRepository.findAll().size();

        // Delete the typeUser
        restTypeUserMockMvc.perform(delete("/api/type-users/{id}", typeUser.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeUser> typeUserList = typeUserRepository.findAll();
        assertThat(typeUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
