package com.kamildeen.hospitalmanagement.infrastructure.controllers;

import com.kamildeen.hospitalmanagement.infrastructure.model.response.ApiResponseJSON;
import com.kamildeen.hospitalmanagement.usecases.PatientManagementService;
import com.kamildeen.hospitalmanagement.infrastructure.model.response.PatientResponse;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "hospital-management/api/v1/patient")
@AllArgsConstructor
public class PatientController {

    private PatientManagementService patientManagementService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseJSON<List<PatientResponse>>> getPatients(@Valid @RequestParam(value = "staffUuid") String staffUuid) {
        List<PatientResponse> response = patientManagementService.getPatients(staffUuid);
        ApiResponseJSON<List<PatientResponse>> apiResponseJSON = new ApiResponseJSON<>("Request processed successfully.", response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

    @GetMapping(value = "/download")
    public void downloadPatientDetails(HttpServletResponse httpServletResponse,
                                       @RequestParam(value = "staffUuid") String staffUuid,
                                       @RequestParam(value = "patientId") long patientId) {
        patientManagementService.downloadPatientDetails(httpServletResponse, staffUuid, patientId);
    }

    @DeleteMapping(value = "/delete-records", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseJSON<String>> deletePatients(@Valid @RequestParam(value = "staffUuid") String staffUuid,
                                                                  @DateTimeFormat(pattern = "25/06/2022") @RequestParam(value = "fromDate") String fromDate,
                                                                  @DateTimeFormat(pattern = "25/06/2022") @RequestParam(value = "toDate") String toDate) {
        String response = patientManagementService.deletePatients(staffUuid, fromDate, toDate);
        ApiResponseJSON<String> apiResponseJSON = new ApiResponseJSON<>(response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }
}
