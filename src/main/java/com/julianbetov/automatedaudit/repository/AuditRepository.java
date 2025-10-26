package com.julianbetov.automatedaudit.repository;

import com.julianbetov.automatedaudit.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<Audit, Long> {
}
