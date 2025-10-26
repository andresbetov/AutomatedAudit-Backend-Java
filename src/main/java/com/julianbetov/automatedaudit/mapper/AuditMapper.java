package com.julianbetov.automatedaudit.mapper;

import com.julianbetov.automatedaudit.dto.request.AuditRequest;
import com.julianbetov.automatedaudit.dto.response.AuditResponse;
import com.julianbetov.automatedaudit.model.Audit;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", uses = FormMapper.class)
public interface AuditMapper {

    @Mapping(target = "form", source = "registries", qualifiedByName = "buildFormFromRegistries")
    Audit toEntity(AuditRequest request);

    AuditResponse toResponse(Audit audit);

    @AfterMapping
    default void setSystemFields(@MappingTarget Audit audit) {
        LocalDateTime now = LocalDateTime.now();
        audit.setCreatedAt(now);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String company = audit.getCompany().trim().toUpperCase();
        audit.setReferenceCode("AUD-" + company + "-" + now.format(fmt));
    }
}
