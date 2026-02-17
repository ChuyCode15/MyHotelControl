package com.myhotelcontrol.domain.huesped.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record DatosRegistroHuesped(

        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
        String nombre,

        @NotNull(message = "El documento de identidad es obligatorio")
        @Positive(message = "El idCard debe ser un número positivo")
        Integer idCard,

        @NotNull(message = "La fecha de nacimiento es obligatoria")
        @Past(message = "La fecha de nacimiento debe ser una fecha en el pasado")
        LocalDate fechaNacimiento,

        @NotNull(message = "El teléfono es obligatorio")
        Integer telefono,

        @NotBlank(message = "El correo electrónico es obligatorio")
        @Email(message = "El formato del correo electrónico es inválido")
        String correoElectronico,

        @NotBlank(message = "El domicilio es obligatorio")
        String domicilio

) {
}