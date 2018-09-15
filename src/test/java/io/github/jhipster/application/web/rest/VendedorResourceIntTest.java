package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterciacimentApp;

import io.github.jhipster.application.domain.Vendedor;
import io.github.jhipster.application.repository.VendedorRepository;
import io.github.jhipster.application.repository.search.VendedorSearchRepository;
import io.github.jhipster.application.service.VendedorService;
import io.github.jhipster.application.service.dto.VendedorDTO;
import io.github.jhipster.application.service.mapper.VendedorMapper;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VendedorResource REST controller.
 *
 * @see VendedorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterciacimentApp.class)
public class VendedorResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    @Autowired
    private VendedorRepository vendedorRepository;

    @Mock
    private VendedorRepository vendedorRepositoryMock;

    @Autowired
    private VendedorMapper vendedorMapper;
    

    @Mock
    private VendedorService vendedorServiceMock;

    @Autowired
    private VendedorService vendedorService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.VendedorSearchRepositoryMockConfiguration
     */
    @Autowired
    private VendedorSearchRepository mockVendedorSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVendedorMockMvc;

    private Vendedor vendedor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VendedorResource vendedorResource = new VendedorResource(vendedorService);
        this.restVendedorMockMvc = MockMvcBuilders.standaloneSetup(vendedorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vendedor createEntity(EntityManager em) {
        Vendedor vendedor = new Vendedor()
            .nome(DEFAULT_NOME)
            .email(DEFAULT_EMAIL)
            .telefone(DEFAULT_TELEFONE);
        return vendedor;
    }

    @Before
    public void initTest() {
        vendedor = createEntity(em);
    }

    @Test
    @Transactional
    public void createVendedor() throws Exception {
        int databaseSizeBeforeCreate = vendedorRepository.findAll().size();

        // Create the Vendedor
        VendedorDTO vendedorDTO = vendedorMapper.toDto(vendedor);
        restVendedorMockMvc.perform(post("/api/vendedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendedorDTO)))
            .andExpect(status().isCreated());

        // Validate the Vendedor in the database
        List<Vendedor> vendedorList = vendedorRepository.findAll();
        assertThat(vendedorList).hasSize(databaseSizeBeforeCreate + 1);
        Vendedor testVendedor = vendedorList.get(vendedorList.size() - 1);
        assertThat(testVendedor.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testVendedor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testVendedor.getTelefone()).isEqualTo(DEFAULT_TELEFONE);

        // Validate the Vendedor in Elasticsearch
        verify(mockVendedorSearchRepository, times(1)).save(testVendedor);
    }

    @Test
    @Transactional
    public void createVendedorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vendedorRepository.findAll().size();

        // Create the Vendedor with an existing ID
        vendedor.setId(1L);
        VendedorDTO vendedorDTO = vendedorMapper.toDto(vendedor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVendedorMockMvc.perform(post("/api/vendedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vendedor in the database
        List<Vendedor> vendedorList = vendedorRepository.findAll();
        assertThat(vendedorList).hasSize(databaseSizeBeforeCreate);

        // Validate the Vendedor in Elasticsearch
        verify(mockVendedorSearchRepository, times(0)).save(vendedor);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendedorRepository.findAll().size();
        // set the field null
        vendedor.setNome(null);

        // Create the Vendedor, which fails.
        VendedorDTO vendedorDTO = vendedorMapper.toDto(vendedor);

        restVendedorMockMvc.perform(post("/api/vendedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendedorDTO)))
            .andExpect(status().isBadRequest());

        List<Vendedor> vendedorList = vendedorRepository.findAll();
        assertThat(vendedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVendedors() throws Exception {
        // Initialize the database
        vendedorRepository.saveAndFlush(vendedor);

        // Get all the vendedorList
        restVendedorMockMvc.perform(get("/api/vendedors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())));
    }
    
    public void getAllVendedorsWithEagerRelationshipsIsEnabled() throws Exception {
        VendedorResource vendedorResource = new VendedorResource(vendedorServiceMock);
        when(vendedorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restVendedorMockMvc = MockMvcBuilders.standaloneSetup(vendedorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restVendedorMockMvc.perform(get("/api/vendedors?eagerload=true"))
        .andExpect(status().isOk());

        verify(vendedorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllVendedorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        VendedorResource vendedorResource = new VendedorResource(vendedorServiceMock);
            when(vendedorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restVendedorMockMvc = MockMvcBuilders.standaloneSetup(vendedorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restVendedorMockMvc.perform(get("/api/vendedors?eagerload=true"))
        .andExpect(status().isOk());

            verify(vendedorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getVendedor() throws Exception {
        // Initialize the database
        vendedorRepository.saveAndFlush(vendedor);

        // Get the vendedor
        restVendedorMockMvc.perform(get("/api/vendedors/{id}", vendedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vendedor.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVendedor() throws Exception {
        // Get the vendedor
        restVendedorMockMvc.perform(get("/api/vendedors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVendedor() throws Exception {
        // Initialize the database
        vendedorRepository.saveAndFlush(vendedor);

        int databaseSizeBeforeUpdate = vendedorRepository.findAll().size();

        // Update the vendedor
        Vendedor updatedVendedor = vendedorRepository.findById(vendedor.getId()).get();
        // Disconnect from session so that the updates on updatedVendedor are not directly saved in db
        em.detach(updatedVendedor);
        updatedVendedor
            .nome(UPDATED_NOME)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE);
        VendedorDTO vendedorDTO = vendedorMapper.toDto(updatedVendedor);

        restVendedorMockMvc.perform(put("/api/vendedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendedorDTO)))
            .andExpect(status().isOk());

        // Validate the Vendedor in the database
        List<Vendedor> vendedorList = vendedorRepository.findAll();
        assertThat(vendedorList).hasSize(databaseSizeBeforeUpdate);
        Vendedor testVendedor = vendedorList.get(vendedorList.size() - 1);
        assertThat(testVendedor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testVendedor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testVendedor.getTelefone()).isEqualTo(UPDATED_TELEFONE);

        // Validate the Vendedor in Elasticsearch
        verify(mockVendedorSearchRepository, times(1)).save(testVendedor);
    }

    @Test
    @Transactional
    public void updateNonExistingVendedor() throws Exception {
        int databaseSizeBeforeUpdate = vendedorRepository.findAll().size();

        // Create the Vendedor
        VendedorDTO vendedorDTO = vendedorMapper.toDto(vendedor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVendedorMockMvc.perform(put("/api/vendedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vendedor in the database
        List<Vendedor> vendedorList = vendedorRepository.findAll();
        assertThat(vendedorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Vendedor in Elasticsearch
        verify(mockVendedorSearchRepository, times(0)).save(vendedor);
    }

    @Test
    @Transactional
    public void deleteVendedor() throws Exception {
        // Initialize the database
        vendedorRepository.saveAndFlush(vendedor);

        int databaseSizeBeforeDelete = vendedorRepository.findAll().size();

        // Get the vendedor
        restVendedorMockMvc.perform(delete("/api/vendedors/{id}", vendedor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Vendedor> vendedorList = vendedorRepository.findAll();
        assertThat(vendedorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Vendedor in Elasticsearch
        verify(mockVendedorSearchRepository, times(1)).deleteById(vendedor.getId());
    }

    @Test
    @Transactional
    public void searchVendedor() throws Exception {
        // Initialize the database
        vendedorRepository.saveAndFlush(vendedor);
        when(mockVendedorSearchRepository.search(queryStringQuery("id:" + vendedor.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(vendedor), PageRequest.of(0, 1), 1));
        // Search the vendedor
        restVendedorMockMvc.perform(get("/api/_search/vendedors?query=id:" + vendedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vendedor.class);
        Vendedor vendedor1 = new Vendedor();
        vendedor1.setId(1L);
        Vendedor vendedor2 = new Vendedor();
        vendedor2.setId(vendedor1.getId());
        assertThat(vendedor1).isEqualTo(vendedor2);
        vendedor2.setId(2L);
        assertThat(vendedor1).isNotEqualTo(vendedor2);
        vendedor1.setId(null);
        assertThat(vendedor1).isNotEqualTo(vendedor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VendedorDTO.class);
        VendedorDTO vendedorDTO1 = new VendedorDTO();
        vendedorDTO1.setId(1L);
        VendedorDTO vendedorDTO2 = new VendedorDTO();
        assertThat(vendedorDTO1).isNotEqualTo(vendedorDTO2);
        vendedorDTO2.setId(vendedorDTO1.getId());
        assertThat(vendedorDTO1).isEqualTo(vendedorDTO2);
        vendedorDTO2.setId(2L);
        assertThat(vendedorDTO1).isNotEqualTo(vendedorDTO2);
        vendedorDTO1.setId(null);
        assertThat(vendedorDTO1).isNotEqualTo(vendedorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vendedorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vendedorMapper.fromId(null)).isNull();
    }
}
