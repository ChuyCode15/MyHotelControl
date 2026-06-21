import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  Reserva,
  DatosBusquedaReserva,
  HabitacionDisponible,
  DatosRegistroReserva,
} from '../models/reserva.model';

@Injectable({ providedIn: 'root' })
export class ReservaService {
  private http = inject(HttpClient);
  private apiUrl = '/api/reservaciones';

  buscarDisponibilidad(
    busqueda: DatosBusquedaReserva
  ): Observable<HabitacionDisponible[]> {
    return this.http.post<HabitacionDisponible[]>(
      `${this.apiUrl}/buscar-disponibilidad`,
      busqueda
    );
  }

  registrarReserva(datos: DatosRegistroReserva): Observable<Reserva> {
    return this.http.post<Reserva>(this.apiUrl, datos);
  }

  obtenerReservas(): Observable<Reserva[]> {
    return this.http.get<Reserva[]>(this.apiUrl);
  }

  obtenerReservaPorId(id: string): Observable<Reserva> {
    return this.http.get<Reserva>(`${this.apiUrl}/${id}`);
  }

  cancelarReserva(id: string): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}/${id}/cancelar`, {});
  }
}
