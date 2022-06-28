package com.kamildeen.hospitalmanagement.usecases;

import com.kamildeen.hospitalmanagement.infrastructure.model.response.PatientResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface PatientManagementService {
    List<PatientResponse> getPatients(String staffUuid);

    String deletePatients(String staffUuid, String fromDate, String toDate);

    void downloadPatientDetails(HttpServletResponse httpServletResponse, String staffUuid, long patientId);
}
