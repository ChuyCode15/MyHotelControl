package com.myhotelcontrol.infra.helpers.exceptions.dto;

public record ErrorResponse(
        String mensaje,
        int codigo,
        long timestamp,
        String path
) {
}
