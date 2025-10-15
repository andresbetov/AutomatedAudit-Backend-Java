package com.julianbetov.automatedaudit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Registry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank
    private String question;

    @NotBlank
    private String answer;

    private String remarks;

    //  This property is really necessary?
    @ManyToOne(optional = false)
    @JoinColumn(name = "form_id", nullable = false)
    private Form form;

    @Override
    public String toString() {
        return "Registry{" +
            ", question='" + question + '\'' +
            ", answer='" + answer + '\'' +
            ", remarks='" + remarks +
            '}';
    }
}
