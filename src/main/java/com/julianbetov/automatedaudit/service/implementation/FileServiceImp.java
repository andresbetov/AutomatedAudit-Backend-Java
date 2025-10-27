package com.julianbetov.automatedaudit.service.implementation;

import com.julianbetov.automatedaudit.exceptions.ResourceConflictException;
import com.julianbetov.automatedaudit.exceptions.ResourceNotFoundException;
import com.julianbetov.automatedaudit.model.Audit;
import com.julianbetov.automatedaudit.model.Files;
import com.julianbetov.automatedaudit.repository.AuditRepository;
import com.julianbetov.automatedaudit.repository.FileRepository;
import com.julianbetov.automatedaudit.service.interfaces.FileService;
import com.julianbetov.automatedaudit.utils.MessageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImp implements FileService {

    private static final String ENTITY_NAME = "Audit";
    private static final String NOMENCLATURE = ENTITY_NAME + "-service";
    private final FileRepository repository;
    private final AuditRepository auditRepository;
    private final MessageService messageService;

    @Override
    @Transactional
    public Files save(Long auditId, String json, String html, byte[] pdf) {
        final String methodNomenclature = NOMENCLATURE + "-save";
        log.info("[{}] {} audit to save files: {}", methodNomenclature, ENTITY_NAME, String.valueOf(auditId));
        Audit audit = auditRepository.findById(auditId)
            .orElseThrow(() -> new ResourceConflictException("Audit", "id", String.valueOf(auditId)));
        Files files = repository.findByAuditId(auditId).orElseGet(Files::new);
        files.setAudit(audit);
        files.setJsonAnalysis(json);
        files.setHtmlAnalysis(html);
        files.setPdfAnalysis(pdf);
        Files saved = repository.save(files);
        log.info("[{}] {} record created with id: {}.", methodNomenclature, ENTITY_NAME, saved.getId());
        return saved;
    }

    @Override
    @Transactional
    public List<Files> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Files findByAuditId(Long auditId) {
        final String methodNomenclature = NOMENCLATURE + "-findById";
        log.info("[{}] Fetch {} record with id: {}", methodNomenclature, ENTITY_NAME, auditId);
        try {
            System.out.println("auditId:" + auditId);
            Optional<Files> entity = repository.findByAuditId(auditId);
            if (entity.isEmpty()) {
                String msg = messageService.getMessage("crud.not.found", ENTITY_NAME, "id", auditId);
                log.warn("[{}] {}", methodNomenclature, msg);
                throw new ResourceNotFoundException(ENTITY_NAME, "id", auditId.toString());
            }
            log.info("[{}] Fetched {} record with id: {}: {}", methodNomenclature, ENTITY_NAME, auditId, entity.get());
            return entity.get();
        } catch (ResourceNotFoundException e) {
            String retrieveErrorMsg = messageService.getMessage("crud.retrieve.error", ENTITY_NAME);
            log.warn("[{}] {}", methodNomenclature, retrieveErrorMsg);
            throw e;
        }
    }



}
