package com.kamildeen.hospitalmanagement.infrastructure.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientResponse {
    private long id;
    private String name;
    private int age;
    private String lastVisitDate;

    public PatientResponse(long id, String name, int age, String lastVisitDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.lastVisitDate = lastVisitDate;
    }
}
