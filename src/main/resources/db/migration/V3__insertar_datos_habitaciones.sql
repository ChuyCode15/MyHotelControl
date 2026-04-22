-- V3__insertar_datos_habitaciones.sql

-- Insertamos 10 habitaciones con UUIDs generados al vuelo
INSERT INTO habitaciones (id, nombre, numero, precio, precio2, precio3, precio4, activo) VALUES
                                                                                             (RANDOM_UUID(), 'Suite Presidencial', 101, 1500.00, 1600.00, 1700.00, 1800.00, TRUE),
                                                                                             (RANDOM_UUID(), 'Habitación Deluxe', 102, 800.00, 900.00, 1000.00, 1100.00, TRUE),
                                                                                             (RANDOM_UUID(), 'Habitación Estándar A', 103, 500.00, 550.00, 600.00, 650.00, TRUE),
                                                                                             (RANDOM_UUID(), 'Habitación Estándar B', 104, 500.00, 550.00, 600.00, 650.00, TRUE),
                                                                                             (RANDOM_UUID(), 'Suite Familiar', 105, 1200.00, 1300.00, 1400.00, 1500.00, TRUE),
                                                                                             (RANDOM_UUID(), 'Habitación Económica', 106, 300.00, 350.00, 400.00, 450.00, TRUE),
                                                                                             (RANDOM_UUID(), 'Suite Junior', 107, 950.00, 1050.00, 1150.00, 1250.00, TRUE),
                                                                                             (RANDOM_UUID(), 'Habitación Ejecutiva', 108, 1100.00, 1200.00, 1300.00, 1400.00, TRUE),
                                                                                             (RANDOM_UUID(), 'Habitación Vista Mar', 109, 1300.00, 1400.00, 1500.00, 1600.00, TRUE),
                                                                                             (RANDOM_UUID(), 'Loft Moderno', 110, 1400.00, 1500.00, 1600.00, 1700.00, TRUE);

-- Insertamos las configuraciones de camas vinculadas a los IDs generados
-- Nota: H2 permite usar una subconsulta para obtener el ID recién creado.
INSERT INTO habitacion_camas (habitacion_id, tamano_cama, cantidad)
SELECT id, 'KING_SIZE', 1 FROM habitaciones WHERE nombre = 'Suite Presidencial';

INSERT INTO habitacion_camas (habitacion_id, tamano_cama, cantidad)
SELECT id, 'QUEEN_SIZE', 2 FROM habitaciones WHERE nombre = 'Habitación Deluxe';

INSERT INTO habitacion_camas (habitacion_id, tamano_cama, cantidad)
SELECT id, 'INDIVIDUAL', 2 FROM habitaciones WHERE nombre = 'Habitación Estándar A';

INSERT INTO habitacion_camas (habitacion_id, tamano_cama, cantidad)
SELECT id, 'INDIVIDUAL', 2 FROM habitaciones WHERE nombre = 'Habitación Estándar B';

INSERT INTO habitacion_camas (habitacion_id, tamano_cama, cantidad)
SELECT id, 'KING_SIZE', 1 FROM habitaciones WHERE nombre = 'Suite Familiar';

INSERT INTO habitacion_camas (habitacion_id, tamano_cama, cantidad)
SELECT id, 'INDIVIDUAL', 1 FROM habitaciones WHERE nombre = 'Suite Familiar';

INSERT INTO habitacion_camas (habitacion_id, tamano_cama, cantidad)
SELECT id, 'INDIVIDUAL', 1 FROM habitaciones WHERE nombre = 'Habitación Económica';

INSERT INTO habitacion_camas (habitacion_id, tamano_cama, cantidad)
SELECT id, 'QUEEN_SIZE', 1 FROM habitaciones WHERE nombre = 'Suite Junior';

INSERT INTO habitacion_camas (habitacion_id, tamano_cama, cantidad)
SELECT id, 'KING_SIZE', 1 FROM habitaciones WHERE nombre = 'Habitación Ejecutiva';

INSERT INTO habitacion_camas (habitacion_id, tamano_cama, cantidad)
SELECT id, 'QUEEN_SIZE', 1 FROM habitaciones WHERE nombre = 'Habitación Vista Mar';

INSERT INTO habitacion_camas (habitacion_id, tamano_cama, cantidad)
SELECT id, 'KING_SIZE', 1 FROM habitaciones WHERE nombre = 'Loft Moderno';