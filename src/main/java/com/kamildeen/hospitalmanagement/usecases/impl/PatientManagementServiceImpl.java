package com.kamildeen.hospitalmanagement.usecases.impl;

import com.kamildeen.hospitalmanagement.domain.entity.PatientEntity;
import com.kamildeen.hospitalmanagement.domain.entity.StaffEntity;
import com.kamildeen.hospitalmanagement.domain.entity.constants.RecordStatusConstant;
import com.kamildeen.hospitalmanagement.infrastructure.model.response.PatientResponse;
import com.kamildeen.hospitalmanagement.infrastructure.persistence.repositories.PatientRepository;
import com.kamildeen.hospitalmanagement.infrastructure.persistence.repositories.StaffRepository;
import com.kamildeen.hospitalmanagement.usecases.PatientManagementService;
import com.kamildeen.hospitalmanagement.usecases.exceptions.NotFoundException;
import com.opencsv.CSVWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientManagementServiceImpl implements PatientManagementService {

    private final StaffRepository repository;
    private final PatientRepository patientRepository;

    @Override
    public List<PatientResponse> getPatients(String staffUuid) {
        Optional<StaffEntity> staffEntityOptional = repository.findByUuidAndRecordStatus(staffUuid, RecordStatusConstant.ACTIVE);
        if (staffEntityOptional.isEmpty()) {
            throw new NotFoundException("Not found. Staff with uuid " + staffUuid);
        }
        List<PatientEntity> patientList = patientRepository.findAll();
        return patientList.stream().filter(patient -> patient.getAge() >= 2).map(this::fromPatientEntityToCommand).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public String deletePatients(String staffUuid, String fromDate, String toDate) {
        Optional<StaffEntity> staffEntityOptional = repository.findByUuidAndRecordStatus(staffUuid, RecordStatusConstant.ACTIVE);
        if (staffEntityOptional.isEmpty()) {
            throw new NotFoundException("Not found. Staff with uuid " + staffUuid);
        }
        LocalDateTime startDate = LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay();
        LocalDateTime endDate = LocalDate.parse(toDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")).atTime(LocalTime.MAX);
        int deletedRecords = patientRepository.deleteAllByLastVisitDateBetween(startDate, endDate);
        return deletedRecords + " patient records deleted successfully.";
    }

    @Override
    public void downloadPatientDetails(HttpServletResponse httpServletResponse, String staffUuid, long patientId) {
        Optional<StaffEntity> staffEntityOptional = repository.findByUuidAndRecordStatus(staffUuid, RecordStatusConstant.ACTIVE);
        if (staffEntityOptional.isEmpty()) {
            throw new NotFoundException("Not found. Staff with uuid " + staffUuid);
        }
        Optional<PatientEntity> patientEntityOptional = patientRepository.findById(patientId);
        if (patientEntityOptional.isEmpty()) {
            throw new NotFoundException("Patient with id " + patientId + " not found.");
        }
        PatientEntity patient = patientEntityOptional.get();
        downloadCSVFile(httpServletResponse, patient);
    }

    private void downloadCSVFile(HttpServletResponse response, PatientEntity patient) {
        ByteArrayOutputStream stream;
        CSVWriter writer;
        try {
            stream = new ByteArrayOutputStream();
            OutputStreamWriter streamWriter = new OutputStreamWriter(stream);
            writer = new CSVWriter(streamWriter);
            String[] header = {"Id", "Name", "Age", "LastVisitDate"};
            writer.writeNext(header);
            String lastVisitDate = patient.getLastVisitDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String[] patientData = {patient.getId().toString(), patient.getName(), patient.getAge().toString(), lastVisitDate};
            writer.writeNext(patientData);
            streamWriter.flush();
            byte[] bytes = stream.toByteArray();
            writer.close();

            String fileName = "patient_%.csv" + patient.getName();
            response.setContentType("application/csv");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            InputStream inputStream = new ByteArrayInputStream(bytes);
            FileCopyUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException exception) {
            throw new RuntimeException("Sorry. Unable to process csv download.");
        }
    }

    private PatientResponse fromPatientEntityToCommand(PatientEntity patientEntity) {
        return PatientResponse.builder()
                .id(patientEntity.getId())
                .name(patientEntity.getName())
                .age(patientEntity.getAge())
                .lastVisitDate(patientEntity.getLastVisitDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .build();
    }
}
