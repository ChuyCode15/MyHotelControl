# Mapa de Desarrollo del Sistema

Este documento define la estructura modular del sistema, sus dependencias y el estado de desarrollo de cada componente.

## 1.0 Módulo de Reservas (Fase 1: MVP)
Este es el módulo central del sistema. Su objetivo es gestionar el ciclo de vida de una reserva sin permitir sobreventa, con un enfoque intuitivo para el recepcionista.

### 1.1 Gestión de Disponibilidad y Búsqueda
- **Reglas**:
    - Bloqueo de disponibilidad estricto (no sobreventa).
    - Estado de mantenimiento considerado como "ocupado".
    - Consideración de filtros por: fechas, noches, huéspedes (adultos/niños), tipo de habitación.
- **Acciones Pendientes (Deuda Técnica)**:
    - [ ] Implementar lógica avanzada de alertas para reservas no confirmadas (notificar al admin cuando una nueva búsqueda coincida con una reserva en `SOLICITUD`).

### 1.2 Gestión del Ciclo de Vida de la Reserva
- **Estados (MVP Implementado)**:
    - `SOLICITUD`: Reserva online o pendiente de revisión.
    - `RESERVADO`: Reserva confirmada por el personal sin anticipo requerido.
    - `CONFIRMADA`: Reserva con anticipo recibido y validado.
    - `CANCELADA`: Reserva cancelada (se guarda como registro histórico).
    - `HOSPEDAJE`: Check-in realizado, huésped activo en el hotel.
    - `NO_SHOW`: No se presentó en la fecha de check-in.
- **Acciones Pendientes (Deuda Técnica)**:
    - [ ] Implementar transiciones de estado automáticas (ej. mover a `NO_SHOW` si no hay check-in en la fecha de entrada).
    - [ ] Añadir campo `motivo_cancelacion` para auditoría.

### 1.3 Configuración de Tarifas y Anticipos (Contexto Multi-tenant)
- **Reglas**:
    - Cada Tenant puede configurar sus reglas de precios (hasta 5 tarifas por habitación).
    - Anticipos personalizables (porcentaje o monto fijo).
- **Acciones Pendientes (Deuda Técnica)**:
    - [ ] Crear el módulo de control de tarifas configurable por el admin del tenant.

---

## 2.0 Módulos Futuros (Roadmap)
- 2.1 Gestión de Huéspedes (CRM Ligero).
- 2.2 Inventario y Housekeeping (Estado de limpieza).
- 2.3 Facturación y Control de Pagos.
- 2.4 Reportes de ocupación y ventas.

---

## Lista de Pendientes / Deuda Técnica Documentada
*Para mantener la solidez del sistema sin frenar el desarrollo, las implementaciones rápidas se documentan aquí:*

| Módulo | Descripción de la simplificación actual | Referencia en código |
| :--- | :--- | :--- |
| Tarifas | Siempre se asigna la tarifa 1 por defecto al reservar. | `ReservasService` |
| Reglas de Estado | Transiciones de estado manuales (sin validación automática). | `ReservasController` |

---
*Nota: Este documento se actualizará conforme avancemos.*
