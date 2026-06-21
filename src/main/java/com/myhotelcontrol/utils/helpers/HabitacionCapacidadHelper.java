package com.myhotelcontrol.utils.helpers;

import com.myhotelcontrol.domain.habitaciones.Habitacion;
import com.myhotelcontrol.enums.TamanoCama;

public final class HabitacionCapacidadHelper {

    private HabitacionCapacidadHelper() {
    }

    public static Integer calcularCapacidadMaxima(Habitacion habitacion) {
        return habitacion.getConfiguracionCamas().stream()
                .mapToInt(camas -> {
                    int capacidadPorCama = switch (camas.getTamanoCama()) {
                        case INDIVIDUAL -> 1;
                        case MATRIMONIAL, QUEEN_SIZE, KING_SIZE -> 2;
                    };
                    return capacidadPorCama * camas.getCantidad();
                })
                .sum();
    }

    public static Integer seleccionarTarifaSegunHuespedes(Habitacion habitacion, int cantidadHuespedes) {
        return switch (cantidadHuespedes) {
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 3;
            default -> 4;
        };
    }
}
