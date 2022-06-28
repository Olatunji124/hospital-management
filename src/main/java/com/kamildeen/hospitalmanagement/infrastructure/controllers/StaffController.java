package com.kamildeen.hospitalmanagement.infrastructure.controllers;


import com.kamildeen.hospitalmanagement.infrastructure.model.request.UpdateStaffRequestJSON;
import com.kamildeen.hospitalmanagement.infrastructure.model.response.ApiResponseJSON;
import com.kamildeen.hospitalmanagement.infrastructure.model.response.RegistrationResponse;
import com.kamildeen.hospitalmanagement.infrastructure.model.response.UpdateStaffResponse;
import com.kamildeen.hospitalmanagement.usecases.StaffManagementService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping(value = "hospital-management/api/v1/staff")
@AllArgsConstructor

public class StaffController {

    private final StaffManagementService registrationService;

    @PostMapping(value = "/register/{name}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseJSON<RegistrationResponse>> registerStaff(@Valid @NotBlank(message = "staff name is required") @PathVariable("name") String name) {
        RegistrationResponse response = registrationService.registerStaff(name);
        ApiResponseJSON<RegistrationResponse> apiResponseJSON = new ApiResponseJSON<>("Staff registered successfully", response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseJSON<UpdateStaffResponse>> updateStaff(@Valid @NotBlank(message = "Staff uuid is required") @PathVariable("uuid") String staffUuid,
                                                                            @Valid @RequestBody UpdateStaffRequestJSON updateRequest) {
        UpdateStaffResponse response = registrationService.updateStaff(staffUuid, updateRequest.getName());
        ApiResponseJSON<UpdateStaffResponse> apiResponseJSON = new ApiResponseJSON<>("Staff record updated successfully.", response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }
}
