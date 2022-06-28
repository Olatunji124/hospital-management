package com.kamildeen.hospitalmanagement.infrastructure.persistence.repositories;

import com.kamildeen.hospitalmanagement.domain.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    @Modifying
    @Query("DELETE FROM PatientEntity s WHERE s.lastVisitDate >= ?1 and s.lastVisitDate <= ?2")
    int deleteAllByLastVisitDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
