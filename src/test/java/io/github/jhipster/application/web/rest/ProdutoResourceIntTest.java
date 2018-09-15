package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterciacimentApp;

import io.github.jhipster.application.domain.Produto;
import io.github.jhipster.application.repository.ProdutoRepository;
import io.github.jhipster.application.repository.search.ProdutoSearchRepository;
import io.github.jhipster.application.service.ProdutoService;
import io.github.jhipster.application.service.dto.ProdutoDTO;
import io.github.jhipster.application.service.mapper.ProdutoMapper;
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
 * Test class for the ProdutoResource REST controller.
 *
 * @see ProdutoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterciacimentApp.class)
public class ProdutoResourceIntTest {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBBBBBBB";

    private static final String DEFAULT_PESO = "AAAAAAAAAA";
    private static final String UPDATED_PESO = "BBBBBBBBBB";

    private static final String DEFAULT_MARCA = "AAAAAAAAAA";
    private static final String UPDATED_MARCA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_INSTRUCOES = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUCOES = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_EMBALAGEM = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_EMBALAGEM = "BBBBBBBBBB";

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoMapper produtoMapper;
    
    @Autowired
    private ProdutoService produtoService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.ProdutoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProdutoSearchRepository mockProdutoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProdutoMockMvc;

    private Produto produto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProdutoResource produtoResource = new ProdutoResource(produtoService);
        this.restProdutoMockMvc = MockMvcBuilders.standaloneSetup(produtoResource)
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
    public static Produto createEntity(EntityManager em) {
        Produto produto = new Produto()
            .codigo(DEFAULT_CODIGO)
            .nome(DEFAULT_NOME)
            .tipo(DEFAULT_TIPO)
            .categoria(DEFAULT_CATEGORIA)
            .peso(DEFAULT_PESO)
            .marca(DEFAULT_MARCA)
            .descricao(DEFAULT_DESCRICAO)
            .instrucoes(DEFAULT_INSTRUCOES)
            .tipoEmbalagem(DEFAULT_TIPO_EMBALAGEM);
        return produto;
    }

    @Before
    public void initTest() {
        produto = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduto() throws Exception {
        int databaseSizeBeforeCreate = produtoRepository.findAll().size();

        // Create the Produto
        ProdutoDTO produtoDTO = produtoMapper.toDto(produto);
        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoDTO)))
            .andExpect(status().isCreated());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeCreate + 1);
        Produto testProduto = produtoList.get(produtoList.size() - 1);
        assertThat(testProduto.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testProduto.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testProduto.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testProduto.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
        assertThat(testProduto.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testProduto.getMarca()).isEqualTo(DEFAULT_MARCA);
        assertThat(testProduto.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testProduto.getInstrucoes()).isEqualTo(DEFAULT_INSTRUCOES);
        assertThat(testProduto.getTipoEmbalagem()).isEqualTo(DEFAULT_TIPO_EMBALAGEM);

        // Validate the Produto in Elasticsearch
        verify(mockProdutoSearchRepository, times(1)).save(testProduto);
    }

    @Test
    @Transactional
    public void createProdutoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produtoRepository.findAll().size();

        // Create the Produto with an existing ID
        produto.setId(1L);
        ProdutoDTO produtoDTO = produtoMapper.toDto(produto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Produto in Elasticsearch
        verify(mockProdutoSearchRepository, times(0)).save(produto);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoRepository.findAll().size();
        // set the field null
        produto.setNome(null);

        // Create the Produto, which fails.
        ProdutoDTO produtoDTO = produtoMapper.toDto(produto);

        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoDTO)))
            .andExpect(status().isBadRequest());

        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProdutos() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList
        restProdutoMockMvc.perform(get("/api/produtos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produto.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.toString())))
            .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].instrucoes").value(hasItem(DEFAULT_INSTRUCOES.toString())))
            .andExpect(jsonPath("$.[*].tipoEmbalagem").value(hasItem(DEFAULT_TIPO_EMBALAGEM.toString())));
    }
    
    @Test
    @Transactional
    public void getProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get the produto
        restProdutoMockMvc.perform(get("/api/produtos/{id}", produto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(produto.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA.toString()))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.toString()))
            .andExpect(jsonPath("$.marca").value(DEFAULT_MARCA.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.instrucoes").value(DEFAULT_INSTRUCOES.toString()))
            .andExpect(jsonPath("$.tipoEmbalagem").value(DEFAULT_TIPO_EMBALAGEM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProduto() throws Exception {
        // Get the produto
        restProdutoMockMvc.perform(get("/api/produtos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();

        // Update the produto
        Produto updatedProduto = produtoRepository.findById(produto.getId()).get();
        // Disconnect from session so that the updates on updatedProduto are not directly saved in db
        em.detach(updatedProduto);
        updatedProduto
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .tipo(UPDATED_TIPO)
            .categoria(UPDATED_CATEGORIA)
            .peso(UPDATED_PESO)
            .marca(UPDATED_MARCA)
            .descricao(UPDATED_DESCRICAO)
            .instrucoes(UPDATED_INSTRUCOES)
            .tipoEmbalagem(UPDATED_TIPO_EMBALAGEM);
        ProdutoDTO produtoDTO = produtoMapper.toDto(updatedProduto);

        restProdutoMockMvc.perform(put("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoDTO)))
            .andExpect(status().isOk());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeUpdate);
        Produto testProduto = produtoList.get(produtoList.size() - 1);
        assertThat(testProduto.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testProduto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testProduto.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testProduto.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testProduto.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testProduto.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testProduto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testProduto.getInstrucoes()).isEqualTo(UPDATED_INSTRUCOES);
        assertThat(testProduto.getTipoEmbalagem()).isEqualTo(UPDATED_TIPO_EMBALAGEM);

        // Validate the Produto in Elasticsearch
        verify(mockProdutoSearchRepository, times(1)).save(testProduto);
    }

    @Test
    @Transactional
    public void updateNonExistingProduto() throws Exception {
        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();

        // Create the Produto
        ProdutoDTO produtoDTO = produtoMapper.toDto(produto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdutoMockMvc.perform(put("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Produto in Elasticsearch
        verify(mockProdutoSearchRepository, times(0)).save(produto);
    }

    @Test
    @Transactional
    public void deleteProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        int databaseSizeBeforeDelete = produtoRepository.findAll().size();

        // Get the produto
        restProdutoMockMvc.perform(delete("/api/produtos/{id}", produto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Produto in Elasticsearch
        verify(mockProdutoSearchRepository, times(1)).deleteById(produto.getId());
    }

    @Test
    @Transactional
    public void searchProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);
        when(mockProdutoSearchRepository.search(queryStringQuery("id:" + produto.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(produto), PageRequest.of(0, 1), 1));
        // Search the produto
        restProdutoMockMvc.perform(get("/api/_search/produtos?query=id:" + produto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produto.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.toString())))
            .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].instrucoes").value(hasItem(DEFAULT_INSTRUCOES.toString())))
            .andExpect(jsonPath("$.[*].tipoEmbalagem").value(hasItem(DEFAULT_TIPO_EMBALAGEM.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Produto.class);
        Produto produto1 = new Produto();
        produto1.setId(1L);
        Produto produto2 = new Produto();
        produto2.setId(produto1.getId());
        assertThat(produto1).isEqualTo(produto2);
        produto2.setId(2L);
        assertThat(produto1).isNotEqualTo(produto2);
        produto1.setId(null);
        assertThat(produto1).isNotEqualTo(produto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProdutoDTO.class);
        ProdutoDTO produtoDTO1 = new ProdutoDTO();
        produtoDTO1.setId(1L);
        ProdutoDTO produtoDTO2 = new ProdutoDTO();
        assertThat(produtoDTO1).isNotEqualTo(produtoDTO2);
        produtoDTO2.setId(produtoDTO1.getId());
        assertThat(produtoDTO1).isEqualTo(produtoDTO2);
        produtoDTO2.setId(2L);
        assertThat(produtoDTO1).isNotEqualTo(produtoDTO2);
        produtoDTO1.setId(null);
        assertThat(produtoDTO1).isNotEqualTo(produtoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(produtoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(produtoMapper.fromId(null)).isNull();
    }
}
