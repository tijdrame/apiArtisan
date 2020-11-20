package com.emard.artisan.web.rest;

import com.emard.artisan.ApiArtisanApp;
import com.emard.artisan.domain.Localisation;
import com.emard.artisan.repository.LocalisationRepository;
import com.emard.artisan.service.LocalisationService;

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
 * Integration tests for the {@link LocalisationResource} REST controller.
 */
@SpringBootTest(classes = ApiArtisanApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LocalisationResourceIT {

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private LocalisationRepository localisationRepository;

    @Autowired
    private LocalisationService localisationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocalisationMockMvc;

    private Localisation localisation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Localisation createEntity(EntityManager em) {
        Localisation localisation = new Localisation()
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .adresse(DEFAULT_ADRESSE)
            .deleted(DEFAULT_DELETED);
        return localisation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Localisation createUpdatedEntity(EntityManager em) {
        Localisation localisation = new Localisation()
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .adresse(UPDATED_ADRESSE)
            .deleted(UPDATED_DELETED);
        return localisation;
    }

    @BeforeEach
    public void initTest() {
        localisation = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalisation() throws Exception {
        int databaseSizeBeforeCreate = localisationRepository.findAll().size();
        // Create the Localisation
        restLocalisationMockMvc.perform(post("/api/localisations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(localisation)))
            .andExpect(status().isCreated());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeCreate + 1);
        Localisation testLocalisation = localisationList.get(localisationList.size() - 1);
        assertThat(testLocalisation.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testLocalisation.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testLocalisation.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testLocalisation.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createLocalisationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localisationRepository.findAll().size();

        // Create the Localisation with an existing ID
        localisation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalisationMockMvc.perform(post("/api/localisations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(localisation)))
            .andExpect(status().isBadRequest());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLocalisations() throws Exception {
        // Initialize the database
        localisationRepository.saveAndFlush(localisation);

        // Get all the localisationList
        restLocalisationMockMvc.perform(get("/api/localisations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localisation.getId().intValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getLocalisation() throws Exception {
        // Initialize the database
        localisationRepository.saveAndFlush(localisation);

        // Get the localisation
        restLocalisationMockMvc.perform(get("/api/localisations/{id}", localisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(localisation.getId().intValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingLocalisation() throws Exception {
        // Get the localisation
        restLocalisationMockMvc.perform(get("/api/localisations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalisation() throws Exception {
        // Initialize the database
        localisationService.save(localisation);

        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();

        // Update the localisation
        Localisation updatedLocalisation = localisationRepository.findById(localisation.getId()).get();
        // Disconnect from session so that the updates on updatedLocalisation are not directly saved in db
        em.detach(updatedLocalisation);
        updatedLocalisation
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .adresse(UPDATED_ADRESSE)
            .deleted(UPDATED_DELETED);

        restLocalisationMockMvc.perform(put("/api/localisations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLocalisation)))
            .andExpect(status().isOk());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
        Localisation testLocalisation = localisationList.get(localisationList.size() - 1);
        assertThat(testLocalisation.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testLocalisation.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testLocalisation.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testLocalisation.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingLocalisation() throws Exception {
        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocalisationMockMvc.perform(put("/api/localisations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(localisation)))
            .andExpect(status().isBadRequest());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocalisation() throws Exception {
        // Initialize the database
        localisationService.save(localisation);

        int databaseSizeBeforeDelete = localisationRepository.findAll().size();

        // Delete the localisation
        restLocalisationMockMvc.perform(delete("/api/localisations/{id}", localisation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
