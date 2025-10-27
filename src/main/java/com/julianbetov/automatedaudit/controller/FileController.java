package com.julianbetov.automatedaudit.controller;

import com.julianbetov.automatedaudit.model.Files;
import com.julianbetov.automatedaudit.service.interfaces.FileService;
import com.julianbetov.automatedaudit.utils.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping(value = "files", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class FileController {

    private static final String ENTITY_NAME = "Files";
    private static final String NOMENCLATURE = ENTITY_NAME + "-controller";
    private final FileService service;
    private final MessageService messageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Files> create(
        @RequestParam("auditId") Long auditId,
        @RequestParam("json") MultipartFile jsonPart,
        @RequestParam("html") MultipartFile htmlPart,
        @RequestParam("pdf") MultipartFile pdf
    ) {
        final String methodNomenclature = NOMENCLATURE + "-create";
        log.info("[{}] Request to create a new {} record.", methodNomenclature, ENTITY_NAME);
        try {
            String json = new String(jsonPart.getBytes(), StandardCharsets.UTF_8);
            String html = new String(htmlPart.getBytes(), StandardCharsets.UTF_8);
            Files saved = service.save(auditId, json, html, pdf.getBytes());
            String msg = messageService.getMessage("crud.save.success", ENTITY_NAME);
            log.info("[{}] {}", methodNomenclature, msg);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Files>> getAll() {
        final String methodNomenclature = NOMENCLATURE + "-getAll";
        log.info("[{}] Request to retrieve all {} records.", methodNomenclature, ENTITY_NAME);
        Iterable<Files> response = service.findAll();
        String msg = messageService.getMessage("crud.retrieve.success", ENTITY_NAME);
        log.info("[{}] {}", methodNomenclature, msg);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{auditId}")
    public ResponseEntity<Files> getByAuditId(@PathVariable Long auditId) {
        final String methodNomenclature = NOMENCLATURE + "-getById";
        log.info("[{}] Request to retrieve {} record by id.", methodNomenclature, ENTITY_NAME);
        Files response = service.findByAuditId(auditId);
        String msg = messageService.getMessage("crud.retrieve.success", ENTITY_NAME);
        log.info("[{}] {}", methodNomenclature, msg);
        return ResponseEntity.ok(response);
    }

}
