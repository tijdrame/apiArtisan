package com.emard.artisan.web.rest;

import com.emard.artisan.ApiArtisanApp;
import com.emard.artisan.domain.Specialite;
import com.emard.artisan.repository.SpecialiteRepository;
import com.emard.artisan.service.SpecialiteService;

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
 * Integration tests for the {@link SpecialiteResource} REST controller.
 */
@SpringBootTest(classes = ApiArtisanApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SpecialiteResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HAVING_GENRE = false;
    private static final Boolean UPDATED_HAVING_GENRE = true;

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private SpecialiteRepository specialiteRepository;

    @Autowired
    private SpecialiteService specialiteService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpecialiteMockMvc;

    private Specialite specialite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Specialite createEntity(EntityManager em) {
        Specialite specialite = new Specialite()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .havingGenre(DEFAULT_HAVING_GENRE)
            .deleted(DEFAULT_DELETED);
        return specialite;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Specialite createUpdatedEntity(EntityManager em) {
        Specialite specialite = new Specialite()
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .havingGenre(UPDATED_HAVING_GENRE)
            .deleted(UPDATED_DELETED);
        return specialite;
    }

    @BeforeEach
    public void initTest() {
        specialite = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpecialite() throws Exception {
        int databaseSizeBeforeCreate = specialiteRepository.findAll().size();
        // Create the Specialite
        restSpecialiteMockMvc.perform(post("/api/specialites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(specialite)))
            .andExpect(status().isCreated());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeCreate + 1);
        Specialite testSpecialite = specialiteList.get(specialiteList.size() - 1);
        assertThat(testSpecialite.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testSpecialite.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSpecialite.isHavingGenre()).isEqualTo(DEFAULT_HAVING_GENRE);
        assertThat(testSpecialite.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createSpecialiteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = specialiteRepository.findAll().size();

        // Create the Specialite with an existing ID
        specialite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecialiteMockMvc.perform(post("/api/specialites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(specialite)))
            .andExpect(status().isBadRequest());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = specialiteRepository.findAll().size();
        // set the field null
        specialite.setLibelle(null);

        // Create the Specialite, which fails.


        restSpecialiteMockMvc.perform(post("/api/specialites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(specialite)))
            .andExpect(status().isBadRequest());

        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = specialiteRepository.findAll().size();
        // set the field null
        specialite.setCode(null);

        // Create the Specialite, which fails.


        restSpecialiteMockMvc.perform(post("/api/specialites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(specialite)))
            .andExpect(status().isBadRequest());

        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSpecialites() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        // Get all the specialiteList
        restSpecialiteMockMvc.perform(get("/api/specialites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specialite.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].havingGenre").value(hasItem(DEFAULT_HAVING_GENRE.booleanValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSpecialite() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        // Get the specialite
        restSpecialiteMockMvc.perform(get("/api/specialites/{id}", specialite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(specialite.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.havingGenre").value(DEFAULT_HAVING_GENRE.booleanValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSpecialite() throws Exception {
        // Get the specialite
        restSpecialiteMockMvc.perform(get("/api/specialites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpecialite() throws Exception {
        // Initialize the database
        specialiteService.save(specialite);

        int databaseSizeBeforeUpdate = specialiteRepository.findAll().size();

        // Update the specialite
        Specialite updatedSpecialite = specialiteRepository.findById(specialite.getId()).get();
        // Disconnect from session so that the updates on updatedSpecialite are not directly saved in db
        em.detach(updatedSpecialite);
        updatedSpecialite
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .havingGenre(UPDATED_HAVING_GENRE)
            .deleted(UPDATED_DELETED);

        restSpecialiteMockMvc.perform(put("/api/specialites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSpecialite)))
            .andExpect(status().isOk());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeUpdate);
        Specialite testSpecialite = specialiteList.get(specialiteList.size() - 1);
        assertThat(testSpecialite.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testSpecialite.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSpecialite.isHavingGenre()).isEqualTo(UPDATED_HAVING_GENRE);
        assertThat(testSpecialite.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingSpecialite() throws Exception {
        int databaseSizeBeforeUpdate = specialiteRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecialiteMockMvc.perform(put("/api/specialites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(specialite)))
            .andExpect(status().isBadRequest());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSpecialite() throws Exception {
        // Initialize the database
        specialiteService.save(specialite);

        int databaseSizeBeforeDelete = specialiteRepository.findAll().size();

        // Delete the specialite
        restSpecialiteMockMvc.perform(delete("/api/specialites/{id}", specialite.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
