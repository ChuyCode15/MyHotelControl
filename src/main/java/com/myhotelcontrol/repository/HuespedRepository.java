package com.myhotelcontrol.repository;

import com.myhotelcontrol.domain.huesped.Huesped;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface HuespedRepository extends JpaRepository<Huesped, UUID> {

    Boolean existsByIdCardAndActivoTrue(Integer idCard);

    Page<Huesped> findAllByActivoTrue(Pageable pageable);

    Boolean existsByTelefonoAndActivoTrue(@NotNull(message = "El teléfono es obligatorio") Integer telefono);

    Optional<Huesped> findByIdAndActivoTrue(UUID uuid);

    Boolean existsByIdAndActivoTrue(@NotNull(message = "El Id es obligatorio") UUID uuid);
}
