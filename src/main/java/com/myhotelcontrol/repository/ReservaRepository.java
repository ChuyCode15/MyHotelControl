package com.myhotelcontrol.repository;

import com.myhotelcontrol.domain.reservas.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservaRepository extends JpaRepository<Reserva, UUID> {

}
