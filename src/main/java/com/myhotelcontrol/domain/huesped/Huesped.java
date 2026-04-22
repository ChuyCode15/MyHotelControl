package com.myhotelcontrol.domain.huesped;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Table(name = "huesped")
@Entity

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Huesped {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nombre;

    @Column(unique = true)
    private Integer idCard;

    private LocalDate fechaNacimiento;

    @Column(unique = true)
    private Integer telefono;

    @Column(unique = true)
    private String correoElectronico;

    private String domicilio;

    @Column(nullable = false)
    private Boolean activo = true;

}
