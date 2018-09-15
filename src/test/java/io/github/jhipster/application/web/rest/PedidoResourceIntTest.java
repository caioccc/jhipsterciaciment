package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterciacimentApp;

import io.github.jhipster.application.domain.Pedido;
import io.github.jhipster.application.repository.PedidoRepository;
import io.github.jhipster.application.repository.search.PedidoSearchRepository;
import io.github.jhipster.application.service.PedidoService;
import io.github.jhipster.application.service.dto.PedidoDTO;
import io.github.jhipster.application.service.mapper.PedidoMapper;
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

import io.github.jhipster.application.domain.enumeration.Formato;
/**
 * Test class for the PedidoResource REST controller.
 *
 * @see PedidoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterciacimentApp.class)
public class PedidoResourceIntTest {

    private static final Formato DEFAULT_FORMATO_ENTREGA = Formato.COM_DESCARREGO;
    private static final Formato UPDATED_FORMATO_ENTREGA = Formato.SEM_DESCARREGO;

    private static final Instant DEFAULT_DATA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_VALOR_UNITARIO = 1L;
    private static final Long UPDATED_VALOR_UNITARIO = 2L;

    private static final String DEFAULT_PRAZO = "AAAAAAAAAA";
    private static final String UPDATED_PRAZO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FOI_ENTREGUE = false;
    private static final Boolean UPDATED_FOI_ENTREGUE = true;

    private static final Boolean DEFAULT_FOI_VISUALIZADO = false;
    private static final Boolean UPDATED_FOI_VISUALIZADO = true;

    private static final Boolean DEFAULT_SAIU_ENTREGA = false;
    private static final Boolean UPDATED_SAIU_ENTREGA = true;

    private static final String DEFAULT_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACOES = "BBBBBBBBBB";

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;
    
    @Autowired
    private PedidoService pedidoService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.PedidoSearchRepositoryMockConfiguration
     */
    @Autowired
    private PedidoSearchRepository mockPedidoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPedidoMockMvc;

    private Pedido pedido;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PedidoResource pedidoResource = new PedidoResource(pedidoService);
        this.restPedidoMockMvc = MockMvcBuilders.standaloneSetup(pedidoResource)
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
    public static Pedido createEntity(EntityManager em) {
        Pedido pedido = new Pedido()
            .formatoEntrega(DEFAULT_FORMATO_ENTREGA)
            .data(DEFAULT_DATA)
            .valorUnitario(DEFAULT_VALOR_UNITARIO)
            .prazo(DEFAULT_PRAZO)
            .foiEntregue(DEFAULT_FOI_ENTREGUE)
            .foiVisualizado(DEFAULT_FOI_VISUALIZADO)
            .saiuEntrega(DEFAULT_SAIU_ENTREGA)
            .observacoes(DEFAULT_OBSERVACOES);
        return pedido;
    }

    @Before
    public void initTest() {
        pedido = createEntity(em);
    }

    @Test
    @Transactional
    public void createPedido() throws Exception {
        int databaseSizeBeforeCreate = pedidoRepository.findAll().size();

        // Create the Pedido
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);
        restPedidoMockMvc.perform(post("/api/pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isCreated());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeCreate + 1);
        Pedido testPedido = pedidoList.get(pedidoList.size() - 1);
        assertThat(testPedido.getFormatoEntrega()).isEqualTo(DEFAULT_FORMATO_ENTREGA);
        assertThat(testPedido.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testPedido.getValorUnitario()).isEqualTo(DEFAULT_VALOR_UNITARIO);
        assertThat(testPedido.getPrazo()).isEqualTo(DEFAULT_PRAZO);
        assertThat(testPedido.isFoiEntregue()).isEqualTo(DEFAULT_FOI_ENTREGUE);
        assertThat(testPedido.isFoiVisualizado()).isEqualTo(DEFAULT_FOI_VISUALIZADO);
        assertThat(testPedido.isSaiuEntrega()).isEqualTo(DEFAULT_SAIU_ENTREGA);
        assertThat(testPedido.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);

        // Validate the Pedido in Elasticsearch
        verify(mockPedidoSearchRepository, times(1)).save(testPedido);
    }

    @Test
    @Transactional
    public void createPedidoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pedidoRepository.findAll().size();

        // Create the Pedido with an existing ID
        pedido.setId(1L);
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPedidoMockMvc.perform(post("/api/pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Pedido in Elasticsearch
        verify(mockPedidoSearchRepository, times(0)).save(pedido);
    }

    @Test
    @Transactional
    public void checkValorUnitarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = pedidoRepository.findAll().size();
        // set the field null
        pedido.setValorUnitario(null);

        // Create the Pedido, which fails.
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        restPedidoMockMvc.perform(post("/api/pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isBadRequest());

        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPedidos() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList
        restPedidoMockMvc.perform(get("/api/pedidos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pedido.getId().intValue())))
            .andExpect(jsonPath("$.[*].formatoEntrega").value(hasItem(DEFAULT_FORMATO_ENTREGA.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].valorUnitario").value(hasItem(DEFAULT_VALOR_UNITARIO.intValue())))
            .andExpect(jsonPath("$.[*].prazo").value(hasItem(DEFAULT_PRAZO.toString())))
            .andExpect(jsonPath("$.[*].foiEntregue").value(hasItem(DEFAULT_FOI_ENTREGUE.booleanValue())))
            .andExpect(jsonPath("$.[*].foiVisualizado").value(hasItem(DEFAULT_FOI_VISUALIZADO.booleanValue())))
            .andExpect(jsonPath("$.[*].saiuEntrega").value(hasItem(DEFAULT_SAIU_ENTREGA.booleanValue())))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES.toString())));
    }
    
    @Test
    @Transactional
    public void getPedido() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get the pedido
        restPedidoMockMvc.perform(get("/api/pedidos/{id}", pedido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pedido.getId().intValue()))
            .andExpect(jsonPath("$.formatoEntrega").value(DEFAULT_FORMATO_ENTREGA.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.valorUnitario").value(DEFAULT_VALOR_UNITARIO.intValue()))
            .andExpect(jsonPath("$.prazo").value(DEFAULT_PRAZO.toString()))
            .andExpect(jsonPath("$.foiEntregue").value(DEFAULT_FOI_ENTREGUE.booleanValue()))
            .andExpect(jsonPath("$.foiVisualizado").value(DEFAULT_FOI_VISUALIZADO.booleanValue()))
            .andExpect(jsonPath("$.saiuEntrega").value(DEFAULT_SAIU_ENTREGA.booleanValue()))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPedido() throws Exception {
        // Get the pedido
        restPedidoMockMvc.perform(get("/api/pedidos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePedido() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();

        // Update the pedido
        Pedido updatedPedido = pedidoRepository.findById(pedido.getId()).get();
        // Disconnect from session so that the updates on updatedPedido are not directly saved in db
        em.detach(updatedPedido);
        updatedPedido
            .formatoEntrega(UPDATED_FORMATO_ENTREGA)
            .data(UPDATED_DATA)
            .valorUnitario(UPDATED_VALOR_UNITARIO)
            .prazo(UPDATED_PRAZO)
            .foiEntregue(UPDATED_FOI_ENTREGUE)
            .foiVisualizado(UPDATED_FOI_VISUALIZADO)
            .saiuEntrega(UPDATED_SAIU_ENTREGA)
            .observacoes(UPDATED_OBSERVACOES);
        PedidoDTO pedidoDTO = pedidoMapper.toDto(updatedPedido);

        restPedidoMockMvc.perform(put("/api/pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isOk());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
        Pedido testPedido = pedidoList.get(pedidoList.size() - 1);
        assertThat(testPedido.getFormatoEntrega()).isEqualTo(UPDATED_FORMATO_ENTREGA);
        assertThat(testPedido.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testPedido.getValorUnitario()).isEqualTo(UPDATED_VALOR_UNITARIO);
        assertThat(testPedido.getPrazo()).isEqualTo(UPDATED_PRAZO);
        assertThat(testPedido.isFoiEntregue()).isEqualTo(UPDATED_FOI_ENTREGUE);
        assertThat(testPedido.isFoiVisualizado()).isEqualTo(UPDATED_FOI_VISUALIZADO);
        assertThat(testPedido.isSaiuEntrega()).isEqualTo(UPDATED_SAIU_ENTREGA);
        assertThat(testPedido.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);

        // Validate the Pedido in Elasticsearch
        verify(mockPedidoSearchRepository, times(1)).save(testPedido);
    }

    @Test
    @Transactional
    public void updateNonExistingPedido() throws Exception {
        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();

        // Create the Pedido
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPedidoMockMvc.perform(put("/api/pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Pedido in Elasticsearch
        verify(mockPedidoSearchRepository, times(0)).save(pedido);
    }

    @Test
    @Transactional
    public void deletePedido() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        int databaseSizeBeforeDelete = pedidoRepository.findAll().size();

        // Get the pedido
        restPedidoMockMvc.perform(delete("/api/pedidos/{id}", pedido.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Pedido in Elasticsearch
        verify(mockPedidoSearchRepository, times(1)).deleteById(pedido.getId());
    }

    @Test
    @Transactional
    public void searchPedido() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);
        when(mockPedidoSearchRepository.search(queryStringQuery("id:" + pedido.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(pedido), PageRequest.of(0, 1), 1));
        // Search the pedido
        restPedidoMockMvc.perform(get("/api/_search/pedidos?query=id:" + pedido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pedido.getId().intValue())))
            .andExpect(jsonPath("$.[*].formatoEntrega").value(hasItem(DEFAULT_FORMATO_ENTREGA.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].valorUnitario").value(hasItem(DEFAULT_VALOR_UNITARIO.intValue())))
            .andExpect(jsonPath("$.[*].prazo").value(hasItem(DEFAULT_PRAZO.toString())))
            .andExpect(jsonPath("$.[*].foiEntregue").value(hasItem(DEFAULT_FOI_ENTREGUE.booleanValue())))
            .andExpect(jsonPath("$.[*].foiVisualizado").value(hasItem(DEFAULT_FOI_VISUALIZADO.booleanValue())))
            .andExpect(jsonPath("$.[*].saiuEntrega").value(hasItem(DEFAULT_SAIU_ENTREGA.booleanValue())))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pedido.class);
        Pedido pedido1 = new Pedido();
        pedido1.setId(1L);
        Pedido pedido2 = new Pedido();
        pedido2.setId(pedido1.getId());
        assertThat(pedido1).isEqualTo(pedido2);
        pedido2.setId(2L);
        assertThat(pedido1).isNotEqualTo(pedido2);
        pedido1.setId(null);
        assertThat(pedido1).isNotEqualTo(pedido2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PedidoDTO.class);
        PedidoDTO pedidoDTO1 = new PedidoDTO();
        pedidoDTO1.setId(1L);
        PedidoDTO pedidoDTO2 = new PedidoDTO();
        assertThat(pedidoDTO1).isNotEqualTo(pedidoDTO2);
        pedidoDTO2.setId(pedidoDTO1.getId());
        assertThat(pedidoDTO1).isEqualTo(pedidoDTO2);
        pedidoDTO2.setId(2L);
        assertThat(pedidoDTO1).isNotEqualTo(pedidoDTO2);
        pedidoDTO1.setId(null);
        assertThat(pedidoDTO1).isNotEqualTo(pedidoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pedidoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pedidoMapper.fromId(null)).isNull();
    }
}
