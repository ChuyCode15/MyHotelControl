package com.myhotelcontrol.domain.habitaciones;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "habitaciones")
@Entity

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String nombre;

    @Column(unique = true)
    private String numero;

    @ElementCollection
    @CollectionTable(name = "habitacion_camas", joinColumns = @JoinColumn(name = "habitacion_id"))
    private List<DetalleCamas> configuracionCamas = new ArrayList<>();

    private BigDecimal precio;
    private BigDecimal precio2;
    private BigDecimal precio3;
    private BigDecimal precio4;

    private Boolean activo = true;

}
