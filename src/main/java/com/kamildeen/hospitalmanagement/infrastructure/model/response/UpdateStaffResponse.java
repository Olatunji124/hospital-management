package com.kamildeen.hospitalmanagement.infrastructure.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateStaffResponse {
    private String uuid;
    private String name;
    private String registrationDate;
}
