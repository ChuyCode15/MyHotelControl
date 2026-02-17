CREATE TABLE clientes (
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          nombre VARCHAR(255),
                          id_card INTEGER NOT NULL,
                          fecha_nacimiento DATE,
                          telefono INTEGER,
                          correo_electronico VARCHAR(255) NOT NULL,
                          domicilio VARCHAR(255),
                          activo BOOLEAN NOT NULL DEFAULT TRUE,

                          PRIMARY KEY (id),
                          CONSTRAINT uk_id_card UNIQUE (id_card),
                          CONSTRAINT uk_correo_electronico UNIQUE (correo_electronico)
);