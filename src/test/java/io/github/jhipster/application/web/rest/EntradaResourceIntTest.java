package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterciacimentApp;

import io.github.jhipster.application.domain.Entrada;
import io.github.jhipster.application.repository.EntradaRepository;
import io.github.jhipster.application.repository.search.EntradaSearchRepository;
import io.github.jhipster.application.service.EntradaService;
import io.github.jhipster.application.service.dto.EntradaDTO;
import io.github.jhipster.application.service.mapper.EntradaMapper;
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
 * Test class for the EntradaResource REST controller.
 *
 * @see EntradaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterciacimentApp.class)
public class EntradaResourceIntTest {

    private static final String DEFAULT_CLIENTE = "AAAAAAAAAA";
    private static final String UPDATED_CLIENTE = "BBBBBBBBBB";

    private static final Long DEFAULT_VALOR_TOTAL = 1L;
    private static final Long UPDATED_VALOR_TOTAL = 2L;

    private static final Long DEFAULT_VALOR_PAGO = 1L;
    private static final Long UPDATED_VALOR_PAGO = 2L;

    private static final Instant DEFAULT_DATA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Forma DEFAULT_FORMA_PAGAMENTO = Forma.CHEQUE;
    private static final Forma UPDATED_FORMA_PAGAMENTO = Forma.DUPLICATA;

    private static final Cor DEFAULT_COR = Cor.VERMELHO;
    private static final Cor UPDATED_COR = Cor.VERDE;

    @Autowired
    private EntradaRepository entradaRepository;

    @Autowired
    private EntradaMapper entradaMapper;
    
    @Autowired
    private EntradaService entradaService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.EntradaSearchRepositoryMockConfiguration
     */
    @Autowired
    private EntradaSearchRepository mockEntradaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEntradaMockMvc;

    private Entrada entrada;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntradaResource entradaResource = new EntradaResource(entradaService);
        this.restEntradaMockMvc = MockMvcBuilders.standaloneSetup(entradaResource)
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
    public static Entrada createEntity(EntityManager em) {
        Entrada entrada = new Entrada()
            .cliente(DEFAULT_CLIENTE)
            .valorTotal(DEFAULT_VALOR_TOTAL)
            .valorPago(DEFAULT_VALOR_PAGO)
            .data(DEFAULT_DATA)
            .formaPagamento(DEFAULT_FORMA_PAGAMENTO)
            .cor(DEFAULT_COR);
        return entrada;
    }

    @Before
    public void initTest() {
        entrada = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntrada() throws Exception {
        int databaseSizeBeforeCreate = entradaRepository.findAll().size();

        // Create the Entrada
        EntradaDTO entradaDTO = entradaMapper.toDto(entrada);
        restEntradaMockMvc.perform(post("/api/entradas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entradaDTO)))
            .andExpect(status().isCreated());

        // Validate the Entrada in the database
        List<Entrada> entradaList = entradaRepository.findAll();
        assertThat(entradaList).hasSize(databaseSizeBeforeCreate + 1);
        Entrada testEntrada = entradaList.get(entradaList.size() - 1);
        assertThat(testEntrada.getCliente()).isEqualTo(DEFAULT_CLIENTE);
        assertThat(testEntrada.getValorTotal()).isEqualTo(DEFAULT_VALOR_TOTAL);
        assertThat(testEntrada.getValorPago()).isEqualTo(DEFAULT_VALOR_PAGO);
        assertThat(testEntrada.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testEntrada.getFormaPagamento()).isEqualTo(DEFAULT_FORMA_PAGAMENTO);
        assertThat(testEntrada.getCor()).isEqualTo(DEFAULT_COR);

        // Validate the Entrada in Elasticsearch
        verify(mockEntradaSearchRepository, times(1)).save(testEntrada);
    }

    @Test
    @Transactional
    public void createEntradaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entradaRepository.findAll().size();

        // Create the Entrada with an existing ID
        entrada.setId(1L);
        EntradaDTO entradaDTO = entradaMapper.toDto(entrada);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntradaMockMvc.perform(post("/api/entradas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entradaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Entrada in the database
        List<Entrada> entradaList = entradaRepository.findAll();
        assertThat(entradaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Entrada in Elasticsearch
        verify(mockEntradaSearchRepository, times(0)).save(entrada);
    }

    @Test
    @Transactional
    public void checkClienteIsRequired() throws Exception {
        int databaseSizeBeforeTest = entradaRepository.findAll().size();
        // set the field null
        entrada.setCliente(null);

        // Create the Entrada, which fails.
        EntradaDTO entradaDTO = entradaMapper.toDto(entrada);

        restEntradaMockMvc.perform(post("/api/entradas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entradaDTO)))
            .andExpect(status().isBadRequest());

        List<Entrada> entradaList = entradaRepository.findAll();
        assertThat(entradaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = entradaRepository.findAll().size();
        // set the field null
        entrada.setValorTotal(null);

        // Create the Entrada, which fails.
        EntradaDTO entradaDTO = entradaMapper.toDto(entrada);

        restEntradaMockMvc.perform(post("/api/entradas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entradaDTO)))
            .andExpect(status().isBadRequest());

        List<Entrada> entradaList = entradaRepository.findAll();
        assertThat(entradaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEntradas() throws Exception {
        // Initialize the database
        entradaRepository.saveAndFlush(entrada);

        // Get all the entradaList
        restEntradaMockMvc.perform(get("/api/entradas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entrada.getId().intValue())))
            .andExpect(jsonPath("$.[*].cliente").value(hasItem(DEFAULT_CLIENTE.toString())))
            .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(DEFAULT_VALOR_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].valorPago").value(hasItem(DEFAULT_VALOR_PAGO.intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].formaPagamento").value(hasItem(DEFAULT_FORMA_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].cor").value(hasItem(DEFAULT_COR.toString())));
    }
    
    @Test
    @Transactional
    public void getEntrada() throws Exception {
        // Initialize the database
        entradaRepository.saveAndFlush(entrada);

        // Get the entrada
        restEntradaMockMvc.perform(get("/api/entradas/{id}", entrada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entrada.getId().intValue()))
            .andExpect(jsonPath("$.cliente").value(DEFAULT_CLIENTE.toString()))
            .andExpect(jsonPath("$.valorTotal").value(DEFAULT_VALOR_TOTAL.intValue()))
            .andExpect(jsonPath("$.valorPago").value(DEFAULT_VALOR_PAGO.intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.formaPagamento").value(DEFAULT_FORMA_PAGAMENTO.toString()))
            .andExpect(jsonPath("$.cor").value(DEFAULT_COR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEntrada() throws Exception {
        // Get the entrada
        restEntradaMockMvc.perform(get("/api/entradas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntrada() throws Exception {
        // Initialize the database
        entradaRepository.saveAndFlush(entrada);

        int databaseSizeBeforeUpdate = entradaRepository.findAll().size();

        // Update the entrada
        Entrada updatedEntrada = entradaRepository.findById(entrada.getId()).get();
        // Disconnect from session so that the updates on updatedEntrada are not directly saved in db
        em.detach(updatedEntrada);
        updatedEntrada
            .cliente(UPDATED_CLIENTE)
            .valorTotal(UPDATED_VALOR_TOTAL)
            .valorPago(UPDATED_VALOR_PAGO)
            .data(UPDATED_DATA)
            .formaPagamento(UPDATED_FORMA_PAGAMENTO)
            .cor(UPDATED_COR);
        EntradaDTO entradaDTO = entradaMapper.toDto(updatedEntrada);

        restEntradaMockMvc.perform(put("/api/entradas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entradaDTO)))
            .andExpect(status().isOk());

        // Validate the Entrada in the database
        List<Entrada> entradaList = entradaRepository.findAll();
        assertThat(entradaList).hasSize(databaseSizeBeforeUpdate);
        Entrada testEntrada = entradaList.get(entradaList.size() - 1);
        assertThat(testEntrada.getCliente()).isEqualTo(UPDATED_CLIENTE);
        assertThat(testEntrada.getValorTotal()).isEqualTo(UPDATED_VALOR_TOTAL);
        assertThat(testEntrada.getValorPago()).isEqualTo(UPDATED_VALOR_PAGO);
        assertThat(testEntrada.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testEntrada.getFormaPagamento()).isEqualTo(UPDATED_FORMA_PAGAMENTO);
        assertThat(testEntrada.getCor()).isEqualTo(UPDATED_COR);

        // Validate the Entrada in Elasticsearch
        verify(mockEntradaSearchRepository, times(1)).save(testEntrada);
    }

    @Test
    @Transactional
    public void updateNonExistingEntrada() throws Exception {
        int databaseSizeBeforeUpdate = entradaRepository.findAll().size();

        // Create the Entrada
        EntradaDTO entradaDTO = entradaMapper.toDto(entrada);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntradaMockMvc.perform(put("/api/entradas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entradaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Entrada in the database
        List<Entrada> entradaList = entradaRepository.findAll();
        assertThat(entradaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Entrada in Elasticsearch
        verify(mockEntradaSearchRepository, times(0)).save(entrada);
    }

    @Test
    @Transactional
    public void deleteEntrada() throws Exception {
        // Initialize the database
        entradaRepository.saveAndFlush(entrada);

        int databaseSizeBeforeDelete = entradaRepository.findAll().size();

        // Get the entrada
        restEntradaMockMvc.perform(delete("/api/entradas/{id}", entrada.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Entrada> entradaList = entradaRepository.findAll();
        assertThat(entradaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Entrada in Elasticsearch
        verify(mockEntradaSearchRepository, times(1)).deleteById(entrada.getId());
    }

    @Test
    @Transactional
    public void searchEntrada() throws Exception {
        // Initialize the database
        entradaRepository.saveAndFlush(entrada);
        when(mockEntradaSearchRepository.search(queryStringQuery("id:" + entrada.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(entrada), PageRequest.of(0, 1), 1));
        // Search the entrada
        restEntradaMockMvc.perform(get("/api/_search/entradas?query=id:" + entrada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entrada.getId().intValue())))
            .andExpect(jsonPath("$.[*].cliente").value(hasItem(DEFAULT_CLIENTE.toString())))
            .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(DEFAULT_VALOR_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].valorPago").value(hasItem(DEFAULT_VALOR_PAGO.intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].formaPagamento").value(hasItem(DEFAULT_FORMA_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].cor").value(hasItem(DEFAULT_COR.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entrada.class);
        Entrada entrada1 = new Entrada();
        entrada1.setId(1L);
        Entrada entrada2 = new Entrada();
        entrada2.setId(entrada1.getId());
        assertThat(entrada1).isEqualTo(entrada2);
        entrada2.setId(2L);
        assertThat(entrada1).isNotEqualTo(entrada2);
        entrada1.setId(null);
        assertThat(entrada1).isNotEqualTo(entrada2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntradaDTO.class);
        EntradaDTO entradaDTO1 = new EntradaDTO();
        entradaDTO1.setId(1L);
        EntradaDTO entradaDTO2 = new EntradaDTO();
        assertThat(entradaDTO1).isNotEqualTo(entradaDTO2);
        entradaDTO2.setId(entradaDTO1.getId());
        assertThat(entradaDTO1).isEqualTo(entradaDTO2);
        entradaDTO2.setId(2L);
        assertThat(entradaDTO1).isNotEqualTo(entradaDTO2);
        entradaDTO1.setId(null);
        assertThat(entradaDTO1).isNotEqualTo(entradaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(entradaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(entradaMapper.fromId(null)).isNull();
    }
}
