package com.kamildeen.hospitalmanagement.infrastructure.controllers;

import com.kamildeen.hospitalmanagement.infrastructure.model.response.RegistrationResponse;
import com.kamildeen.hospitalmanagement.usecases.StaffManagementService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StaffController.class)
class StaffControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StaffManagementService mockRegistrationService;

    @Test
    void testRegisterStaffOk() throws Exception {
        // Setup
        RegistrationResponse response = RegistrationResponse.builder()
                .uuid(UUID.randomUUID().toString())
                .name("Kaamil").build();
        when(mockRegistrationService.registerStaff("Kaamil")).thenReturn(response);

        // Run the test
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/register/Kaamil")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(mockRequest)
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Kaamill")));
    }

    @Test
    void testRegisterStaffNotFound() throws Exception {
        // Setup
        when(mockRegistrationService.registerStaff("Kaamil")).thenReturn(null);

        // Run the test
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/register/Kaamil")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound());
    }
}
