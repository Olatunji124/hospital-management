package com.kamildeen.hospitalmanagement.infrastructure.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationResponse {
    private String uuid;
    private String name;
}
