package com.julianbetov.automatedaudit.service.implementation;

import com.julianbetov.automatedaudit.dto.request.AuditRequest;
import com.julianbetov.automatedaudit.dto.response.AuditResponse;
import com.julianbetov.automatedaudit.exceptions.ResourceNotFoundException;
import com.julianbetov.automatedaudit.mapper.AuditMapper;
import com.julianbetov.automatedaudit.model.Audit;
import com.julianbetov.automatedaudit.repository.AuditRepository;
import com.julianbetov.automatedaudit.service.interfaces.AuditService;
import com.julianbetov.automatedaudit.utils.MessageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditServiceImp implements AuditService {

    private static final String ENTITY_NAME = "Audit";
    private static final String NOMENCLATURE = ENTITY_NAME + "-service";
    private final AuditRepository repository;
    private final AuditMapper mapper;
    private final MessageService messageService;


    @Override
    @Transactional
    public AuditResponse save(AuditRequest request) {
        final String methodNomenclature = NOMENCLATURE + "-save";
        log.info("[{}] {} record to save: {}", methodNomenclature, ENTITY_NAME, request);
        Audit saved = repository.save(mapper.toEntity(request));
        log.info("[{}] {} record created with id: {}.", methodNomenclature, ENTITY_NAME, saved.getId());
        return mapper.toResponse(saved);
    }

    @Override
    public AuditResponse findById(Long id) {
        final String methodNomenclature = NOMENCLATURE + "-findById";
        log.info("[{}] Fetch {} record with id: {}", methodNomenclature, ENTITY_NAME, id);
        try {
            Optional<Audit> entity = repository.findById(id);
            if (entity.isEmpty()) {
                String msg = messageService.getMessage("crud.not.found", ENTITY_NAME, "id", id);
                log.warn("[{}] {}", methodNomenclature, msg);
                throw new ResourceNotFoundException(ENTITY_NAME, "id", id.toString());
            }
            log.info("[{}] Fetched {} record with id: {}: {}", methodNomenclature, ENTITY_NAME, id, entity.get());
            return mapper.toResponse(entity.get());
        } catch (ResourceNotFoundException e) {
            String retrieveErrorMsg = messageService.getMessage("crud.retrieve.error", ENTITY_NAME);
            log.warn("[{}] {}", methodNomenclature, retrieveErrorMsg);
            throw e;
        }
    }

    @Override
    public Page<AuditResponse> findAll(Pageable pageable) {
        final String methodNomenclature = NOMENCLATURE + "-findAll";
        log.info("[{}] Fetch all {} records.", methodNomenclature, ENTITY_NAME);
        System.out.println(repository.findAll(pageable));
        Page<AuditResponse> page = repository.findAll(pageable).map(mapper::toResponse);
        log.info("[{}] Fetched {} {} records.", methodNomenclature, page.getNumberOfElements(), ENTITY_NAME);
        return page;
    }

}
