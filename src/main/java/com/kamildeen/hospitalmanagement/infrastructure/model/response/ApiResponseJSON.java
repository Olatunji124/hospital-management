package com.kamildeen.hospitalmanagement.infrastructure.model.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonPropertyOrder({"message", "data"})
@NoArgsConstructor
public class ApiResponseJSON<T> {

    public ApiResponseJSON(String message) {
        this.message = message;
        this.data = null;
    }

    public ApiResponseJSON(String message, T data) {
        this.message = message;
        this.data = data;
    }

    private String message;
    private T data;
}
