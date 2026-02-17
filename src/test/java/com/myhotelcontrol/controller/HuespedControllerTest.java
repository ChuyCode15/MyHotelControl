package com.myhotelcontrol.controller;

import com.myhotelcontrol.infra.helpers.exceptions.DuplicateResourceException;
import com.myhotelcontrol.services.HuespedService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class HuespedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HuespedService huespedService;

    @Test
    @DisplayName("Debería retornar 409 cuando el service lanza DuplicateResourceException")
    void registrarHuespedConflict() throws Exception {
        var json = """
                {
                    "idCard": 12345,
                    "nombre": "Juan Perez",
                    "email": "juan@mail.com"
                }                
                """;

        when(huespedService.registrarHuesped(any())).thenThrow(new DuplicateResourceException("Ya existe"));

        mockMvc.perform(post("/huesped")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))

                .andExpect(status().isConflict());

    }

}