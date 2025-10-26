package com.julianbetov.automatedaudit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String referenceCode;

    @NotBlank
    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private List<String> departments;

    @NotNull
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "form_id", nullable = false)
    private Form form;

    @Override
    public String toString() {
        return "Audit{" +
            "id=" + id +
            ", referenceCode='" + referenceCode + '\'' +
            ", company='" + company + '\'' +
            ", departments=" + departments +
            ", createdAt=" + createdAt +
            ", form=" + form +
            '}';
    }
}
