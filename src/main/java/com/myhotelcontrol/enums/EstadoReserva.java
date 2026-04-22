package com.myhotelcontrol.enums;

public enum EstadoReserva {
    PENDIENTE,   // Creada pero sin anticipo (bloqueo temporal)
    CONFIRMADA,  // Anticipo recibido (bloqueo legal)
    CANCELADA,   // Liberada
    NO_SHOW
}
