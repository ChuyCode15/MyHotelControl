-- RESERVAS
CREATE TABLE reservas
(
    id                          UUID           NOT NULL,
    id_huesped                  UUID           NOT NULL,
    nombre_huesped              VARCHAR(150)   NOT NULL,
    habitacion_id               UUID           NOT NULL,
    numero_habitacion           VARCHAR(20)    NOT NULL,
    fecha_entrada               DATE           NOT NULL,
    fecha_salida                DATE           NOT NULL,
    cantidad_noches             INTEGER        NOT NULL,  -- ✅ era numero_noches
    precio_noche                DECIMAL(19, 2) NOT NULL,
    monto_anticipo              DECIMAL(19, 2) DEFAULT 0.00,
    monto_total                 DECIMAL(19, 2) NOT NULL,
    estado                      VARCHAR(20)    NOT NULL,
    fecha_limite_confirmacion   TIMESTAMP,               -- ✅ Agregado
    fecha_creacion              TIMESTAMP      NOT NULL  DEFAULT CURRENT_TIMESTAMP,
    activo                      BOOLEAN        NOT NULL  DEFAULT TRUE,
    -- ✅ Quitamos finalizado, no está en la entidad

    PRIMARY KEY (id),
    CONSTRAINT fk_reserva_huesped
        FOREIGN KEY (id_huesped)
            REFERENCES huesped (id),
    CONSTRAINT fk_reserva_habitacion
        FOREIGN KEY (habitacion_id)
            REFERENCES habitaciones (id)
);

-- DISPONIBILIDAD
CREATE TABLE disponibilidad
(
    id                   UUID           NOT NULL,
    fecha                DATE           NOT NULL,
    habitacion_id        UUID           NOT NULL,
    habitacion_numero    VARCHAR(20)    NOT NULL,
    reserva_id           UUID,
    precio_dia           DECIMAL(19, 2),               -- ✅ Agregado
    fecha_salida_reserva DATE,                         -- ✅ Agregado
    noche_numero         INTEGER,                      -- ✅ Agregado
    total_noches         INTEGER,                      -- ✅ Agregado
    estado               VARCHAR(20),
    activo               BOOLEAN        NOT NULL DEFAULT TRUE,

    PRIMARY KEY (id),

    -- ✅ Garantiza que una habitación no se reserve dos veces el mismo día
    CONSTRAINT uk_fecha_habitacion
        UNIQUE (fecha, habitacion_id),

    CONSTRAINT fk_disponibilidad_reserva
        FOREIGN KEY (reserva_id)
            REFERENCES reservas (id),
    CONSTRAINT fk_disponibilidad_habitacion
        FOREIGN KEY (habitacion_id)
            REFERENCES habitaciones (id)
);