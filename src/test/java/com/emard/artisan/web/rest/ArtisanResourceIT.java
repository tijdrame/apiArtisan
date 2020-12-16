package com.emard.artisan.web.rest;

import com.emard.artisan.ApiArtisanApp;
import com.emard.artisan.domain.Artisan;
import com.emard.artisan.repository.ArtisanRepository;
import com.emard.artisan.service.ArtisanService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ArtisanResource} REST controller.
 */
@SpringBootTest(classes = ApiArtisanApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ArtisanResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_LANG_KEY = "AAAAAAAAAA";
    private static final String UPDATED_LANG_KEY = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private ArtisanRepository artisanRepository;

    @Autowired
    private ArtisanService artisanService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArtisanMockMvc;

    private Artisan artisan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artisan createEntity(EntityManager em) {
        Artisan artisan = new Artisan()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .login(DEFAULT_LOGIN)
            .langKey(DEFAULT_LANG_KEY)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .email(DEFAULT_EMAIL)
            .telephone(DEFAULT_TELEPHONE)
            .deleted(DEFAULT_DELETED);
        return artisan;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artisan createUpdatedEntity(EntityManager em) {
        Artisan artisan = new Artisan()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .login(UPDATED_LOGIN)
            .langKey(UPDATED_LANG_KEY)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .deleted(UPDATED_DELETED);
        return artisan;
    }

    @BeforeEach
    public void initTest() {
        artisan = createEntity(em);
    }

    @Test
    @Transactional
    public void createArtisan() throws Exception {
        int databaseSizeBeforeCreate = artisanRepository.findAll().size();
        // Create the Artisan
        restArtisanMockMvc.perform(post("/api/artisans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(artisan)))
            .andExpect(status().isCreated());

        // Validate the Artisan in the database
        List<Artisan> artisanList = artisanRepository.findAll();
        assertThat(artisanList).hasSize(databaseSizeBeforeCreate + 1);
        Artisan testArtisan = artisanList.get(artisanList.size() - 1);
        assertThat(testArtisan.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testArtisan.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testArtisan.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testArtisan.getLangKey()).isEqualTo(DEFAULT_LANG_KEY);
        assertThat(testArtisan.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testArtisan.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testArtisan.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testArtisan.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testArtisan.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createArtisanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = artisanRepository.findAll().size();

        // Create the Artisan with an existing ID
        artisan.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtisanMockMvc.perform(post("/api/artisans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(artisan)))
            .andExpect(status().isBadRequest());

        // Validate the Artisan in the database
        List<Artisan> artisanList = artisanRepository.findAll();
        assertThat(artisanList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = artisanRepository.findAll().size();
        // set the field null
        artisan.setNom(null);

        // Create the Artisan, which fails.


        restArtisanMockMvc.perform(post("/api/artisans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(artisan)))
            .andExpect(status().isBadRequest());

        List<Artisan> artisanList = artisanRepository.findAll();
        assertThat(artisanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = artisanRepository.findAll().size();
        // set the field null
        artisan.setPrenom(null);

        // Create the Artisan, which fails.


        restArtisanMockMvc.perform(post("/api/artisans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(artisan)))
            .andExpect(status().isBadRequest());

        List<Artisan> artisanList = artisanRepository.findAll();
        assertThat(artisanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArtisans() throws Exception {
        // Initialize the database
        artisanRepository.saveAndFlush(artisan);

        // Get all the artisanList
        restArtisanMockMvc.perform(get("/api/artisans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artisan.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].langKey").value(hasItem(DEFAULT_LANG_KEY)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getArtisan() throws Exception {
        // Initialize the database
        artisanRepository.saveAndFlush(artisan);

        // Get the artisan
        restArtisanMockMvc.perform(get("/api/artisans/{id}", artisan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(artisan.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.langKey").value(DEFAULT_LANG_KEY))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingArtisan() throws Exception {
        // Get the artisan
        restArtisanMockMvc.perform(get("/api/artisans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArtisan() throws Exception {
        // Initialize the database
        artisanService.save(artisan);

        int databaseSizeBeforeUpdate = artisanRepository.findAll().size();

        // Update the artisan
        Artisan updatedArtisan = artisanRepository.findById(artisan.getId()).get();
        // Disconnect from session so that the updates on updatedArtisan are not directly saved in db
        em.detach(updatedArtisan);
        updatedArtisan
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .login(UPDATED_LOGIN)
            .langKey(UPDATED_LANG_KEY)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .deleted(UPDATED_DELETED);

        restArtisanMockMvc.perform(put("/api/artisans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedArtisan)))
            .andExpect(status().isOk());

        // Validate the Artisan in the database
        List<Artisan> artisanList = artisanRepository.findAll();
        assertThat(artisanList).hasSize(databaseSizeBeforeUpdate);
        Artisan testArtisan = artisanList.get(artisanList.size() - 1);
        assertThat(testArtisan.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testArtisan.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testArtisan.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testArtisan.getLangKey()).isEqualTo(UPDATED_LANG_KEY);
        assertThat(testArtisan.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testArtisan.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testArtisan.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testArtisan.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testArtisan.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingArtisan() throws Exception {
        int databaseSizeBeforeUpdate = artisanRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtisanMockMvc.perform(put("/api/artisans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(artisan)))
            .andExpect(status().isBadRequest());

        // Validate the Artisan in the database
        List<Artisan> artisanList = artisanRepository.findAll();
        assertThat(artisanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArtisan() throws Exception {
        // Initialize the database
        artisanService.save(artisan);

        int databaseSizeBeforeDelete = artisanRepository.findAll().size();

        // Delete the artisan
        restArtisanMockMvc.perform(delete("/api/artisans/{id}", artisan.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Artisan> artisanList = artisanRepository.findAll();
        assertThat(artisanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
