package com.quenti.smarttestui.service;

import com.quenti.smarttestui.domain.Header;
import com.quenti.smarttestui.repository.HeaderRepository;
import com.quenti.smarttestui.service.dto.HeaderDTO;
import com.quenti.smarttestui.service.mapper.HeaderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Header.
 */
@Service
@Transactional
public class HeaderService {

    private final Logger log = LoggerFactory.getLogger(HeaderService.class);

    @Inject
    private HeaderRepository headerRepository;

    @Inject
    private HeaderMapper headerMapper;

    /**
     * Save a header.
     *
     * @param headerDTO the entity to save
     * @return the persisted entity
     */
    public HeaderDTO save(HeaderDTO headerDTO) {
        log.debug("Request to save Header : {}", headerDTO);
        headerDTO.setActivo(true);
        Header header = headerMapper.headerDTOToHeader(headerDTO);
        header = headerRepository.save(header);
        HeaderDTO result = headerMapper.headerToHeaderDTO(header);
        return result;
    }

    /**
     *  Get all the headers.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<HeaderDTO> findAll() {
        log.debug("Request to get all Headers");
        List<HeaderDTO> result = headerRepository.findByActivoTrue().stream()
            .map(headerMapper::headerToHeaderDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one header by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public HeaderDTO findOne(Long id) {
        log.debug("Request to get Header : {}", id);
        Header header = headerRepository.findOne(id);
        HeaderDTO headerDTO = headerMapper.headerToHeaderDTO(header);
        return headerDTO;
    }

    /**
     *  Delete the  header by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Header : {}", id);
        HeaderDTO headerDTO = findOne(id);
        headerDTO.setActivo(false);
        Header header = headerMapper.headerDTOToHeader(headerDTO);
        headerRepository.save(header);
    }
}
