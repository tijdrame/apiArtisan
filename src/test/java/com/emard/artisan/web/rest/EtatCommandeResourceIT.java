package com.emard.artisan.web.rest;

import com.emard.artisan.ApiArtisanApp;
import com.emard.artisan.domain.EtatCommande;
import com.emard.artisan.repository.EtatCommandeRepository;
import com.emard.artisan.service.EtatCommandeService;

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
 * Integration tests for the {@link EtatCommandeResource} REST controller.
 */
@SpringBootTest(classes = ApiArtisanApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EtatCommandeResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private EtatCommandeRepository etatCommandeRepository;

    @Autowired
    private EtatCommandeService etatCommandeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEtatCommandeMockMvc;

    private EtatCommande etatCommande;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtatCommande createEntity(EntityManager em) {
        EtatCommande etatCommande = new EtatCommande()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return etatCommande;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtatCommande createUpdatedEntity(EntityManager em) {
        EtatCommande etatCommande = new EtatCommande()
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);
        return etatCommande;
    }

    @BeforeEach
    public void initTest() {
        etatCommande = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtatCommande() throws Exception {
        int databaseSizeBeforeCreate = etatCommandeRepository.findAll().size();
        // Create the EtatCommande
        restEtatCommandeMockMvc.perform(post("/api/etat-commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatCommande)))
            .andExpect(status().isCreated());

        // Validate the EtatCommande in the database
        List<EtatCommande> etatCommandeList = etatCommandeRepository.findAll();
        assertThat(etatCommandeList).hasSize(databaseSizeBeforeCreate + 1);
        EtatCommande testEtatCommande = etatCommandeList.get(etatCommandeList.size() - 1);
        assertThat(testEtatCommande.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testEtatCommande.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEtatCommande.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createEtatCommandeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etatCommandeRepository.findAll().size();

        // Create the EtatCommande with an existing ID
        etatCommande.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtatCommandeMockMvc.perform(post("/api/etat-commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatCommande)))
            .andExpect(status().isBadRequest());

        // Validate the EtatCommande in the database
        List<EtatCommande> etatCommandeList = etatCommandeRepository.findAll();
        assertThat(etatCommandeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = etatCommandeRepository.findAll().size();
        // set the field null
        etatCommande.setLibelle(null);

        // Create the EtatCommande, which fails.


        restEtatCommandeMockMvc.perform(post("/api/etat-commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatCommande)))
            .andExpect(status().isBadRequest());

        List<EtatCommande> etatCommandeList = etatCommandeRepository.findAll();
        assertThat(etatCommandeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = etatCommandeRepository.findAll().size();
        // set the field null
        etatCommande.setCode(null);

        // Create the EtatCommande, which fails.


        restEtatCommandeMockMvc.perform(post("/api/etat-commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatCommande)))
            .andExpect(status().isBadRequest());

        List<EtatCommande> etatCommandeList = etatCommandeRepository.findAll();
        assertThat(etatCommandeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEtatCommandes() throws Exception {
        // Initialize the database
        etatCommandeRepository.saveAndFlush(etatCommande);

        // Get all the etatCommandeList
        restEtatCommandeMockMvc.perform(get("/api/etat-commandes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etatCommande.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getEtatCommande() throws Exception {
        // Initialize the database
        etatCommandeRepository.saveAndFlush(etatCommande);

        // Get the etatCommande
        restEtatCommandeMockMvc.perform(get("/api/etat-commandes/{id}", etatCommande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etatCommande.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingEtatCommande() throws Exception {
        // Get the etatCommande
        restEtatCommandeMockMvc.perform(get("/api/etat-commandes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtatCommande() throws Exception {
        // Initialize the database
        etatCommandeService.save(etatCommande);

        int databaseSizeBeforeUpdate = etatCommandeRepository.findAll().size();

        // Update the etatCommande
        EtatCommande updatedEtatCommande = etatCommandeRepository.findById(etatCommande.getId()).get();
        // Disconnect from session so that the updates on updatedEtatCommande are not directly saved in db
        em.detach(updatedEtatCommande);
        updatedEtatCommande
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);

        restEtatCommandeMockMvc.perform(put("/api/etat-commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEtatCommande)))
            .andExpect(status().isOk());

        // Validate the EtatCommande in the database
        List<EtatCommande> etatCommandeList = etatCommandeRepository.findAll();
        assertThat(etatCommandeList).hasSize(databaseSizeBeforeUpdate);
        EtatCommande testEtatCommande = etatCommandeList.get(etatCommandeList.size() - 1);
        assertThat(testEtatCommande.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testEtatCommande.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEtatCommande.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingEtatCommande() throws Exception {
        int databaseSizeBeforeUpdate = etatCommandeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtatCommandeMockMvc.perform(put("/api/etat-commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatCommande)))
            .andExpect(status().isBadRequest());

        // Validate the EtatCommande in the database
        List<EtatCommande> etatCommandeList = etatCommandeRepository.findAll();
        assertThat(etatCommandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEtatCommande() throws Exception {
        // Initialize the database
        etatCommandeService.save(etatCommande);

        int databaseSizeBeforeDelete = etatCommandeRepository.findAll().size();

        // Delete the etatCommande
        restEtatCommandeMockMvc.perform(delete("/api/etat-commandes/{id}", etatCommande.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EtatCommande> etatCommandeList = etatCommandeRepository.findAll();
        assertThat(etatCommandeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
