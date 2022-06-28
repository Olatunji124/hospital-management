package com.kamildeen.hospitalmanagement.domain.entity;

import com.kamildeen.hospitalmanagement.domain.entity.constants.RecordStatusConstant;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "staff")
public class StaffEntity {

    @Column(nullable = false, updatable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private LocalDateTime registrationDate;

    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private RecordStatusConstant recordStatus = RecordStatusConstant.ACTIVE;
}
