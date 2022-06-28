package com.kamildeen.hospitalmanagement.infrastructure.persistence.repositories;

import com.kamildeen.hospitalmanagement.domain.entity.StaffEntity;
import com.kamildeen.hospitalmanagement.domain.entity.constants.RecordStatusConstant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<StaffEntity, Long> {
    Optional<StaffEntity> findByNameAndRecordStatus(String name, RecordStatusConstant status);

    Optional<StaffEntity> findByUuidAndRecordStatus(String staffUuid, RecordStatusConstant active);
}
