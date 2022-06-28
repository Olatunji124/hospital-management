package com.kamildeen.hospitalmanagement.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patients")
public class PatientEntity {

    @Column(nullable = false, updatable = false)
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer age;

    private LocalDateTime lastVisitDate;
}
