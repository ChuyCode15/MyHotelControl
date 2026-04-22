-- 1. Crear primero la tabla que NO tiene dependencias (Reservas)
CREATE TABLE reservas (
                          id                UUID           NOT NULL,
                          id_huesped        UUID           NOT NULL,
                          nombre_huesped    VARCHAR(150)   NOT NULL,
                          habitacion_id     UUID           NOT NULL,
                          numero_habitacion VARCHAR(20)    NOT NULL,
                          fecha_entrada     DATE           NOT NULL,
                          fecha_salida      DATE           NOT NULL,
                          numero_noches     INTEGER        NOT NULL,
                          precio_noche      DECIMAL(19, 2) NOT NULL,
                          monto_anticipo    DECIMAL(19, 2) DEFAULT 0.00,
                          monto_total       DECIMAL(19, 2) NOT NULL,
                          estado            VARCHAR(20)    NOT NULL,
                          fecha_creacion    TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          activo            BOOLEAN        NOT NULL DEFAULT TRUE,
                          finalizado        BOOLEAN        NOT NULL DEFAULT FALSE,
                          PRIMARY KEY (id)
);

-- 2. Crear la tabla que DEPENDE de reservas (Disponibilidad)
CREATE TABLE disponibilidad (
                                id                UUID        NOT NULL,
                                fecha             DATE        NOT NULL,
                                habitacion_id     UUID        NOT NULL,
                                habitacion_numero VARCHAR(20) NOT NULL,
                                reserva_id        UUID,
                                estado            VARCHAR(20),
                                activo            BOOLEAN     NOT NULL DEFAULT TRUE,
                                PRIMARY KEY (id)
);

