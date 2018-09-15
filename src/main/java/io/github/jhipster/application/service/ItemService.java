package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Item;
import io.github.jhipster.application.repository.ItemRepository;
import io.github.jhipster.application.repository.search.ItemSearchRepository;
import io.github.jhipster.application.service.dto.ItemDTO;
import io.github.jhipster.application.service.mapper.ItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Item.
 */
@Service
@Transactional
public class ItemService {

    private final Logger log = LoggerFactory.getLogger(ItemService.class);

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    private final ItemSearchRepository itemSearchRepository;

    public ItemService(ItemRepository itemRepository, ItemMapper itemMapper, ItemSearchRepository itemSearchRepository) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
        this.itemSearchRepository = itemSearchRepository;
    }

    /**
     * Save a item.
     *
     * @param itemDTO the entity to save
     * @return the persisted entity
     */
    public ItemDTO save(ItemDTO itemDTO) {
        log.debug("Request to save Item : {}", itemDTO);
        Item item = itemMapper.toEntity(itemDTO);
        item = itemRepository.save(item);
        ItemDTO result = itemMapper.toDto(item);
        itemSearchRepository.save(item);
        return result;
    }

    /**
     * Get all the items.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Items");
        return itemRepository.findAll(pageable)
            .map(itemMapper::toDto);
    }


    /**
     * Get one item by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ItemDTO> findOne(Long id) {
        log.debug("Request to get Item : {}", id);
        return itemRepository.findById(id)
            .map(itemMapper::toDto);
    }

    /**
     * Delete the item by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Item : {}", id);
        itemRepository.deleteById(id);
        itemSearchRepository.deleteById(id);
    }

    /**
     * Search for the item corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ItemDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Items for query {}", query);
        return itemSearchRepository.search(queryStringQuery(query), pageable)
            .map(itemMapper::toDto);
    }
}
