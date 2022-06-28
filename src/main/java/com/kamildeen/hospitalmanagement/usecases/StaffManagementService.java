package com.kamildeen.hospitalmanagement.usecases;

import com.kamildeen.hospitalmanagement.infrastructure.model.response.RegistrationResponse;
import com.kamildeen.hospitalmanagement.infrastructure.model.response.UpdateStaffResponse;

public interface StaffManagementService {
    RegistrationResponse registerStaff(String name);

    UpdateStaffResponse updateStaff(String staffUuid, String name);
}
