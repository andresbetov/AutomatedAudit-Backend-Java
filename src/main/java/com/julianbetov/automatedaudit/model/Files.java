package com.julianbetov.automatedaudit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id", updatable = false)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "audit_id", nullable = false)
    @JsonIgnore
    private Audit audit;

    @Lob
    private String jsonAnalysis;

    @Lob
    private String htmlAnalysis;

    @Lob
    private byte[] pdfAnalysis;

}
