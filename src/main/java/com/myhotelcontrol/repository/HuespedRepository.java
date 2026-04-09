package com.myhotelcontrol.repository;

import com.myhotelcontrol.domain.huesped.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface HuespedRepository extends JpaRepository<Huesped, UUID> {

    Boolean existsByIdCardAndActivoTrue(Integer idCard);
}
