package com.julianbetov.automatedaudit.repository;

import com.julianbetov.automatedaudit.model.Files;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<Files, Long> {
    Optional<Files> findByAuditId(Long auditId);

    List<Files> id(Long id);
}