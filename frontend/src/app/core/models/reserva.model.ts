export interface Reserva {
  id: string;
  huespedId: string;
  nombreHuesped: string;
  habitacionId: string;
  numeroHabitacion: string;
  fechaEntrada: string;
  fechaSalida: string;
  cantidadNoches: number;
  precioPorNoche: number;
  montoAnticipo: number;
  montoTotal: number;
  estado: EstadoReserva;
  fechaLimiteConfirmacion: string;
  fechaCreacion: string;
  activo: boolean;
}

export type EstadoReserva =
  | 'SOLICITUD'
  | 'RESERVADO'
  | 'CONFIRMADA'
  | 'CANCELADA'
  | 'NO_SHOW';

export interface DatosBusquedaReserva {
  fechaEntrada: string;
  cantidadNoches: number;
  cantidadHuespedes: number;
}

export interface HabitacionDisponible {
  id: string;
  nombre: string;
  numero: string;
  camas: string[];
  capacidadMaxima: number;
  precioPorNoche: number;
  precioTotal: number;
  anticipoRequerido: number;
}

export interface DatosRegistroReserva {
  huespedId: string;
  habitacionId: string;
  fechaEntrada: string;
  cantidadNoches: number;
}

export const ESTADO_RESERVA_CONFIG: Record<EstadoReserva, { label: string; color: string; bgColor: string }> = {
  SOLICITUD: { label: 'Solicitud', color: 'text-amber-700', bgColor: 'bg-amber-100' },
  RESERVADO: { label: 'Reservado', color: 'text-blue-700', bgColor: 'bg-blue-100' },
  CONFIRMADA: { label: 'Confirmada', color: 'text-green-700', bgColor: 'bg-green-100' },
  CANCELADA: { label: 'Cancelada', color: 'text-red-700', bgColor: 'bg-red-100' },
  NO_SHOW: { label: 'No Show', color: 'text-gray-700', bgColor: 'bg-gray-100' },
};
