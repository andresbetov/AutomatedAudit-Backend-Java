package com.julianbetov.automatedaudit.service.interfaces;

import com.julianbetov.automatedaudit.dto.request.AuditRequest;
import com.julianbetov.automatedaudit.dto.response.AuditResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuditService {

    AuditResponse save(AuditRequest request);

    AuditResponse findById(Long id);

    Page<AuditResponse> findAll(Pageable pageable);

}
