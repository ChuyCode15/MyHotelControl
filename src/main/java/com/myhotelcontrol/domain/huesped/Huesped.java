package com.myhotelcontrol.domain.huesped;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "clientes")
@Entity

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Huesped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true)
    private Integer idCard;

    private LocalDate fechaNacimiento;

    private Integer telefono;

    @Column(unique = true)
    private String correoElectronico;

    private String domicilio;

    @Column(nullable = false)
    private Boolean activo = true;

}
