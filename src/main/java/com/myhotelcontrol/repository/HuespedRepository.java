package com.myhotelcontrol.repository;

import com.myhotelcontrol.domain.huesped.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HuespedRepository extends JpaRepository<Huesped, Long> {
    Optional<Huesped> findByIdCardAndActivoTrue(Integer idCard);
}
