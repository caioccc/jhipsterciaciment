package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterciacimentApp;

import io.github.jhipster.application.domain.Saida;
import io.github.jhipster.application.repository.SaidaRepository;
import io.github.jhipster.application.repository.search.SaidaSearchRepository;
import io.github.jhipster.application.service.SaidaService;
import io.github.jhipster.application.service.dto.SaidaDTO;
import io.github.jhipster.application.service.mapper.SaidaMapper;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.application.domain.enumeration.Forma;
import io.github.jhipster.application.domain.enumeration.Cor;
/**
 * Test class for the SaidaResource REST controller.
 *
 * @see SaidaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterciacimentApp.class)
public class SaidaResourceIntTest {

    private static final String DEFAULT_EMINENTE = "AAAAAAAAAA";
    private static final String UPDATED_EMINENTE = "BBBBBBBBBB";

    private static final String DEFAULT_RAZAO = "AAAAAAAAAA";
    private static final String UPDATED_RAZAO = "BBBBBBBBBB";

    private static final Long DEFAULT_VALOR = 1L;
    private static final Long UPDATED_VALOR = 2L;

    private static final Forma DEFAULT_FORMA = Forma.CHEQUE;
    private static final Forma UPDATED_FORMA = Forma.DUPLICATA;

    private static final Instant DEFAULT_DATA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACOES = "BBBBBBBBBB";

    private static final Cor DEFAULT_COR = Cor.VERMELHO;
    private static final Cor UPDATED_COR = Cor.VERDE;

    @Autowired
    private SaidaRepository saidaRepository;

    @Autowired
    private SaidaMapper saidaMapper;
    
    @Autowired
    private SaidaService saidaService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.SaidaSearchRepositoryMockConfiguration
     */
    @Autowired
    private SaidaSearchRepository mockSaidaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSaidaMockMvc;

    private Saida saida;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SaidaResource saidaResource = new SaidaResource(saidaService);
        this.restSaidaMockMvc = MockMvcBuilders.standaloneSetup(saidaResource)
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
    public static Saida createEntity(EntityManager em) {
        Saida saida = new Saida()
            .eminente(DEFAULT_EMINENTE)
            .razao(DEFAULT_RAZAO)
            .valor(DEFAULT_VALOR)
            .forma(DEFAULT_FORMA)
            .data(DEFAULT_DATA)
            .observacoes(DEFAULT_OBSERVACOES)
            .cor(DEFAULT_COR);
        return saida;
    }

    @Before
    public void initTest() {
        saida = createEntity(em);
    }

    @Test
    @Transactional
    public void createSaida() throws Exception {
        int databaseSizeBeforeCreate = saidaRepository.findAll().size();

        // Create the Saida
        SaidaDTO saidaDTO = saidaMapper.toDto(saida);
        restSaidaMockMvc.perform(post("/api/saidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saidaDTO)))
            .andExpect(status().isCreated());

        // Validate the Saida in the database
        List<Saida> saidaList = saidaRepository.findAll();
        assertThat(saidaList).hasSize(databaseSizeBeforeCreate + 1);
        Saida testSaida = saidaList.get(saidaList.size() - 1);
        assertThat(testSaida.getEminente()).isEqualTo(DEFAULT_EMINENTE);
        assertThat(testSaida.getRazao()).isEqualTo(DEFAULT_RAZAO);
        assertThat(testSaida.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testSaida.getForma()).isEqualTo(DEFAULT_FORMA);
        assertThat(testSaida.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testSaida.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
        assertThat(testSaida.getCor()).isEqualTo(DEFAULT_COR);

        // Validate the Saida in Elasticsearch
        verify(mockSaidaSearchRepository, times(1)).save(testSaida);
    }

    @Test
    @Transactional
    public void createSaidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = saidaRepository.findAll().size();

        // Create the Saida with an existing ID
        saida.setId(1L);
        SaidaDTO saidaDTO = saidaMapper.toDto(saida);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaidaMockMvc.perform(post("/api/saidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Saida in the database
        List<Saida> saidaList = saidaRepository.findAll();
        assertThat(saidaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Saida in Elasticsearch
        verify(mockSaidaSearchRepository, times(0)).save(saida);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = saidaRepository.findAll().size();
        // set the field null
        saida.setValor(null);

        // Create the Saida, which fails.
        SaidaDTO saidaDTO = saidaMapper.toDto(saida);

        restSaidaMockMvc.perform(post("/api/saidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saidaDTO)))
            .andExpect(status().isBadRequest());

        List<Saida> saidaList = saidaRepository.findAll();
        assertThat(saidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSaidas() throws Exception {
        // Initialize the database
        saidaRepository.saveAndFlush(saida);

        // Get all the saidaList
        restSaidaMockMvc.perform(get("/api/saidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saida.getId().intValue())))
            .andExpect(jsonPath("$.[*].eminente").value(hasItem(DEFAULT_EMINENTE.toString())))
            .andExpect(jsonPath("$.[*].razao").value(hasItem(DEFAULT_RAZAO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].forma").value(hasItem(DEFAULT_FORMA.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES.toString())))
            .andExpect(jsonPath("$.[*].cor").value(hasItem(DEFAULT_COR.toString())));
    }
    
    @Test
    @Transactional
    public void getSaida() throws Exception {
        // Initialize the database
        saidaRepository.saveAndFlush(saida);

        // Get the saida
        restSaidaMockMvc.perform(get("/api/saidas/{id}", saida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(saida.getId().intValue()))
            .andExpect(jsonPath("$.eminente").value(DEFAULT_EMINENTE.toString()))
            .andExpect(jsonPath("$.razao").value(DEFAULT_RAZAO.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.forma").value(DEFAULT_FORMA.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES.toString()))
            .andExpect(jsonPath("$.cor").value(DEFAULT_COR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSaida() throws Exception {
        // Get the saida
        restSaidaMockMvc.perform(get("/api/saidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSaida() throws Exception {
        // Initialize the database
        saidaRepository.saveAndFlush(saida);

        int databaseSizeBeforeUpdate = saidaRepository.findAll().size();

        // Update the saida
        Saida updatedSaida = saidaRepository.findById(saida.getId()).get();
        // Disconnect from session so that the updates on updatedSaida are not directly saved in db
        em.detach(updatedSaida);
        updatedSaida
            .eminente(UPDATED_EMINENTE)
            .razao(UPDATED_RAZAO)
            .valor(UPDATED_VALOR)
            .forma(UPDATED_FORMA)
            .data(UPDATED_DATA)
            .observacoes(UPDATED_OBSERVACOES)
            .cor(UPDATED_COR);
        SaidaDTO saidaDTO = saidaMapper.toDto(updatedSaida);

        restSaidaMockMvc.perform(put("/api/saidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saidaDTO)))
            .andExpect(status().isOk());

        // Validate the Saida in the database
        List<Saida> saidaList = saidaRepository.findAll();
        assertThat(saidaList).hasSize(databaseSizeBeforeUpdate);
        Saida testSaida = saidaList.get(saidaList.size() - 1);
        assertThat(testSaida.getEminente()).isEqualTo(UPDATED_EMINENTE);
        assertThat(testSaida.getRazao()).isEqualTo(UPDATED_RAZAO);
        assertThat(testSaida.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testSaida.getForma()).isEqualTo(UPDATED_FORMA);
        assertThat(testSaida.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testSaida.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
        assertThat(testSaida.getCor()).isEqualTo(UPDATED_COR);

        // Validate the Saida in Elasticsearch
        verify(mockSaidaSearchRepository, times(1)).save(testSaida);
    }

    @Test
    @Transactional
    public void updateNonExistingSaida() throws Exception {
        int databaseSizeBeforeUpdate = saidaRepository.findAll().size();

        // Create the Saida
        SaidaDTO saidaDTO = saidaMapper.toDto(saida);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaidaMockMvc.perform(put("/api/saidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Saida in the database
        List<Saida> saidaList = saidaRepository.findAll();
        assertThat(saidaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Saida in Elasticsearch
        verify(mockSaidaSearchRepository, times(0)).save(saida);
    }

    @Test
    @Transactional
    public void deleteSaida() throws Exception {
        // Initialize the database
        saidaRepository.saveAndFlush(saida);

        int databaseSizeBeforeDelete = saidaRepository.findAll().size();

        // Get the saida
        restSaidaMockMvc.perform(delete("/api/saidas/{id}", saida.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Saida> saidaList = saidaRepository.findAll();
        assertThat(saidaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Saida in Elasticsearch
        verify(mockSaidaSearchRepository, times(1)).deleteById(saida.getId());
    }

    @Test
    @Transactional
    public void searchSaida() throws Exception {
        // Initialize the database
        saidaRepository.saveAndFlush(saida);
        when(mockSaidaSearchRepository.search(queryStringQuery("id:" + saida.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(saida), PageRequest.of(0, 1), 1));
        // Search the saida
        restSaidaMockMvc.perform(get("/api/_search/saidas?query=id:" + saida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saida.getId().intValue())))
            .andExpect(jsonPath("$.[*].eminente").value(hasItem(DEFAULT_EMINENTE.toString())))
            .andExpect(jsonPath("$.[*].razao").value(hasItem(DEFAULT_RAZAO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].forma").value(hasItem(DEFAULT_FORMA.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES.toString())))
            .andExpect(jsonPath("$.[*].cor").value(hasItem(DEFAULT_COR.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Saida.class);
        Saida saida1 = new Saida();
        saida1.setId(1L);
        Saida saida2 = new Saida();
        saida2.setId(saida1.getId());
        assertThat(saida1).isEqualTo(saida2);
        saida2.setId(2L);
        assertThat(saida1).isNotEqualTo(saida2);
        saida1.setId(null);
        assertThat(saida1).isNotEqualTo(saida2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaidaDTO.class);
        SaidaDTO saidaDTO1 = new SaidaDTO();
        saidaDTO1.setId(1L);
        SaidaDTO saidaDTO2 = new SaidaDTO();
        assertThat(saidaDTO1).isNotEqualTo(saidaDTO2);
        saidaDTO2.setId(saidaDTO1.getId());
        assertThat(saidaDTO1).isEqualTo(saidaDTO2);
        saidaDTO2.setId(2L);
        assertThat(saidaDTO1).isNotEqualTo(saidaDTO2);
        saidaDTO1.setId(null);
        assertThat(saidaDTO1).isNotEqualTo(saidaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(saidaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(saidaMapper.fromId(null)).isNull();
    }
}
