package com.julianbetov.automatedaudit.controller;

import com.julianbetov.automatedaudit.dto.request.AuditRequest;
import com.julianbetov.automatedaudit.dto.response.AuditResponse;
import com.julianbetov.automatedaudit.service.interfaces.AuditService;
import com.julianbetov.automatedaudit.utils.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "audit", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuditController {

    private static final String ENTITY_NAME = "Audit";
    private static final String NOMENCLATURE = ENTITY_NAME + "-controller";
    private final AuditService service;
    private final MessageService messageService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuditResponse> create(
        @RequestBody @Valid AuditRequest request
    ) {
        final String methodNomenclature = NOMENCLATURE + "-create";
        log.info("[{}] Request to create a new {} record.", methodNomenclature, ENTITY_NAME);
        AuditResponse response = service.save(request);
        String msg = messageService.getMessage("crud.save.success", ENTITY_NAME);
        log.info("[{}] {}", methodNomenclature, msg);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditResponse> getById(@PathVariable Long id) {
        final String methodNomenclature = NOMENCLATURE + "-getById";
        log.info("[{}] Request to retrieve {} record by id.", methodNomenclature, ENTITY_NAME);
        AuditResponse response = service.findById(id);
        String msg = messageService.getMessage("crud.retrieve.success", ENTITY_NAME);
        log.info("[{}] {}", methodNomenclature, msg);
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<Page<AuditResponse>> getAll(
        @PageableDefault(size = 20, sort = "company") Pageable pageable
    ) {
        final String methodNomenclature = NOMENCLATURE + "-getAll";
        log.info("[{}] Request to retrieve all {} records.", methodNomenclature, ENTITY_NAME);
        Page<AuditResponse> response = service.findAll(pageable);
        log.info("[{}] {} records retrieved.", methodNomenclature, ENTITY_NAME);
        return ResponseEntity.ok(response);
    }

}
