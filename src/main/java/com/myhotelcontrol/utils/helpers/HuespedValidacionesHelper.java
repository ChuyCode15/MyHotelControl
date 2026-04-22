package com.myhotelcontrol.utils.helpers;

import com.myhotelcontrol.domain.habitaciones.Habitacion;
import com.myhotelcontrol.domain.habitaciones.dto.DatosDetalleHabitacion;
import com.myhotelcontrol.domain.huesped.Huesped;
import com.myhotelcontrol.infra.helpers.exceptions.DuplicateResourceException;
import com.myhotelcontrol.infra.helpers.exceptions.NotFoundResorceException;
import com.myhotelcontrol.repository.HuespedRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HuespedValidacionesHelper {

    private final HuespedRepository huespedRepository;

    public void validaHuespedNoExiste(@NotNull(message = "El Id Card es obligatorio")Integer idCard) {
        if (huespedRepository.existsByIdCardAndActivoTrue(idCard)) {
            throw new DuplicateResourceException("ya existe en el sistema un huésped el Id card: " + idCard + " ya esta registrado!");
        }
    }

    public void validaHuespedExisteId(UUID uuid) {
        if (!huespedRepository.existsByIdAndActivoTrue(uuid)) {
            throw new NotFoundResorceException("No existe un huésped con el Id : '" + uuid + "' registrado!");
        }
    }

    public void validaNumeroNoExiste(@NotNull(message = "El teléfono es obligatorio") Integer telefono) {
        if (huespedRepository.existsByTelefonoAndActivoTrue(telefono)) {
            throw new DuplicateResourceException("ya existe un huésped el sistema con el numero:" + telefono + " ya esta registrado!");
        }
    }

    public Huesped buscarHuespedId(UUID uuid) {
        var huespedEncontrado = huespedRepository.findByIdAndActivoTrue(uuid)
                .orElseThrow(
                        () -> new NotFoundResorceException("No se encontro ningun huesped registrado con ese id")
                );
        return huespedEncontrado;
    }

}
