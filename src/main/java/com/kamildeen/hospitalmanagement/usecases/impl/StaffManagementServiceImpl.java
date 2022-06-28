package com.kamildeen.hospitalmanagement.usecases.impl;

import com.kamildeen.hospitalmanagement.domain.entity.StaffEntity;
import com.kamildeen.hospitalmanagement.domain.entity.constants.RecordStatusConstant;
import com.kamildeen.hospitalmanagement.infrastructure.model.response.RegistrationResponse;
import com.kamildeen.hospitalmanagement.infrastructure.persistence.repositories.StaffRepository;
import com.kamildeen.hospitalmanagement.usecases.StaffManagementService;
import com.kamildeen.hospitalmanagement.infrastructure.model.response.UpdateStaffResponse;
import com.kamildeen.hospitalmanagement.usecases.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StaffManagementServiceImpl implements StaffManagementService {

    private final StaffRepository repository;

    @Override
    public RegistrationResponse registerStaff(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new BadRequestException("Staff name is required.");
        }
        Optional<StaffEntity> staffEntityOptional = repository.findByNameAndRecordStatus(name, RecordStatusConstant.ACTIVE);
        if (staffEntityOptional.isPresent()) {
            throw new RuntimeException("Staff has been registered already.");
        }
        String uuid = UUID.randomUUID().toString();
        StaffEntity staff = StaffEntity.builder()
                .name(name)
                .uuid(uuid)
                .registrationDate(LocalDateTime.now())
                .build();
        repository.saveAndFlush(staff);
        return RegistrationResponse.builder()
                .uuid(staff.getUuid())
                .name(staff.getName()).build();
    }

    @Override
    public UpdateStaffResponse updateStaff(String staffUuid, String name) {
        Optional<StaffEntity> staffEntityOptional = repository.findByUuidAndRecordStatus(staffUuid, RecordStatusConstant.ACTIVE);
        if (staffEntityOptional.isEmpty()) {
            throw new IllegalArgumentException("Not found. Staff with uuid " + staffUuid);
        }
        StaffEntity staff = staffEntityOptional.get();
        staff.setName(name);
        repository.saveAndFlush(staff);
        return UpdateStaffResponse.builder()
                .uuid(staff.getUuid())
                .name(staff.getName())
                .registrationDate(staff.getRegistrationDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .build();
    }
}
