package com.kamildeen.hospitalmanagement.infrastructure.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamildeen.hospitalmanagement.infrastructure.model.response.ApiResponseJSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointHandler extends BasicAuthenticationEntryPoint {
    @Override
    public void afterPropertiesSet() {
        setRealmName("Hospital_management");
        super.afterPropertiesSet();
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ApiResponseJSON<String> apiResponse = new ApiResponseJSON<>();
        apiResponse.setMessage("Unauthorized Access");
        apiResponse.setData("Invalid client credential");
        byte[] responseToSend = restResponseBytes(apiResponse);
        response.getOutputStream().write(responseToSend);
    }

    private byte[] restResponseBytes(ApiResponseJSON<String> apiResponse) throws JsonProcessingException {
        String serialized = new ObjectMapper().writeValueAsString(apiResponse);
        return serialized.getBytes();
    }
}
