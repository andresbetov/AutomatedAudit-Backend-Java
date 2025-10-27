package com.julianbetov.automatedaudit.service.interfaces;

import com.julianbetov.automatedaudit.model.Files;

import java.util.List;

public interface FileService {

    Files save(Long auditId, String json, String html, byte[] pdf);

    List<Files> findAll();

    Files findByAuditId(Long auditId);

}
