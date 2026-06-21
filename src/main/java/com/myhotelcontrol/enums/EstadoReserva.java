package com.myhotelcontrol.enums;

public enum EstadoReserva {
    SOLICITUD,  // Reserva online o pendiente de revisión por el personal
    RESERVADO,  // Reserva creada o confirmada por el personal sin anticipo requerido
    CONFIRMADA, // Anticipo recibido y confirmado
    CANCELADA,  // Reserva cancelada (histórico, nunca se borra)
    NO_SHOW     // No se presentó en la fecha de check-in
}
