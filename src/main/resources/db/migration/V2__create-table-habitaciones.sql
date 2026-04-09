CREATE TABLE habitaciones
(
    id      UUID           NOT NULL,
    nombre  VARCHAR(100)   NOT NULL,
    numero  INTEGER        NOT NULL,
    precio  DECIMAL(19, 2) NOT NULL,
    precio2 DECIMAL(19, 2),
    precio3 DECIMAL(19, 2),
    precio4 DECIMAL(19, 2),
    activo  BOOLEAN        NOT NULL DEFAULT TRUE, -- <--- Agregado para el Repositorio
    PRIMARY KEY (id),
    CONSTRAINT uk_habitacion_nombre UNIQUE (nombre)
);

CREATE TABLE habitacion_camas
(
    habitacion_id UUID        NOT NULL,
    tamano_cama   VARCHAR(50) NOT NULL,
    cantidad      INTEGER     NOT NULL,
    CONSTRAINT fk_habitacion_camas_id FOREIGN KEY (habitacion_id) REFERENCES habitaciones (id) ON DELETE CASCADE
);