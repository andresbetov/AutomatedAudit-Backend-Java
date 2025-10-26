package com.julianbetov.automatedaudit.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record AuditResponse(

    Long id,
    String referenceCode,
    String company,
    List<String> departments,
    LocalDateTime createdAt,
    FormResponse form

) {
}
