import { Component, signal, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReservaService } from '../../../../core/services/reserva.service';
import { Reserva, ESTADO_RESERVA_CONFIG, EstadoReserva } from '../../../../core/models/reserva.model';

@Component({
  selector: 'app-reservation-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden">
      <!-- Header -->
      <div class="p-4 border-b border-gray-200">
        <div class="flex items-center justify-between mb-4">
          <h3 class="font-semibold text-gray-800">Lista de Reservas</h3>
          <div class="flex items-center gap-2">
            <select [(ngModel)]="filtroEstado"
                    class="px-3 py-1.5 text-sm border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#1e3a5f]/20">
              <option value="TODOS">Todos los estados</option>
              @for (entry of estadoEntries; track entry[0]) {
                <option [value]="entry[0]">{{ entry[1].label }}</option>
              }
            </select>
            <input type="text"
                   [(ngModel)]="busqueda"
                   placeholder="Buscar por nombre..."
                   class="px-3 py-1.5 text-sm border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#1e3a5f]/20 w-48">
          </div>
        </div>

        <!-- Stats -->
        <div class="grid grid-cols-5 gap-4">
          @for (stat of stats; track stat.label) {
            <div class="text-center p-2 bg-gray-50 rounded-lg">
              <div class="text-lg font-bold" [ngClass]="stat.color">{{ stat.value }}</div>
              <div class="text-xs text-gray-500">{{ stat.label }}</div>
            </div>
          }
        </div>
      </div>

      <!-- Table -->
      <div class="overflow-x-auto">
        <table class="w-full">
          <thead class="bg-gray-50 border-b border-gray-200">
            <tr>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Huésped</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Habitación</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Entrada</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Salida</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Noches</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Total</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Estado</th>
              <th class="px-4 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">Acciones</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-100">
            @for (reserva of reservasFiltradas(); track reserva.id) {
              <tr class="hover:bg-gray-50 transition-colors">
                <td class="px-4 py-3">
                  <div class="flex items-center gap-3">
                    <div class="w-8 h-8 rounded-full bg-[#1e3a5f]/10 flex items-center justify-center text-sm font-medium text-[#1e3a5f]">
                      {{ reserva.nombreHuesped.charAt(0) }}
                    </div>
                    <div>
                      <div class="text-sm font-medium text-gray-800">{{ reserva.nombreHuesped }}</div>
                      <div class="text-xs text-gray-500">{{ reserva.huespedId | slice:0:8 }}...</div>
                    </div>
                  </div>
                </td>
                <td class="px-4 py-3">
                  <div class="text-sm font-medium text-gray-800">{{ reserva.numeroHabitacion }}</div>
                </td>
                <td class="px-4 py-3 text-sm text-gray-600">{{ formatDate(reserva.fechaEntrada) }}</td>
                <td class="px-4 py-3 text-sm text-gray-600">{{ formatDate(reserva.fechaSalida) }}</td>
                <td class="px-4 py-3 text-sm text-gray-600">{{ reserva.cantidadNoches }}</td>
                <td class="px-4 py-3">
                  <div class="text-sm font-medium text-gray-800">\${{ reserva.montoTotal }}</div>
                  <div class="text-xs text-green-600">Anticipo: \${{ reserva.montoAnticipo }}</div>
                </td>
                <td class="px-4 py-3">
                  <span class="px-2.5 py-0.5 text-xs font-medium rounded-full"
                        [ngClass]="getEstadoConfig(reserva.estado).bgColor + ' ' + getEstadoConfig(reserva.estado).color">
                    {{ getEstadoConfig(reserva.estado).label }}
                  </span>
                </td>
                <td class="px-4 py-3 text-right">
                  <div class="flex items-center justify-end gap-1">
                    <button (click)="verDetalle(reserva)"
                            class="p-1.5 hover:bg-gray-100 rounded-lg transition-colors"
                            title="Ver detalle">
                      <svg class="w-4 h-4 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/>
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"/>
                      </svg>
                    </button>
                    @if (reserva.estado !== 'CANCELADA' && reserva.estado !== 'NO_SHOW') {
                      <button (click)="cancelarReserva(reserva)"
                              class="p-1.5 hover:bg-red-50 rounded-lg transition-colors"
                              title="Cancelar">
                        <svg class="w-4 h-4 text-red-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
                        </svg>
                      </button>
                    }
                  </div>
                </td>
              </tr>
            }
          </tbody>
        </table>
      </div>

      <!-- Empty State -->
      @if (reservasFiltradas().length === 0) {
        <div class="p-12 text-center">
          <div class="text-4xl mb-4">📋</div>
          <div class="text-gray-600 font-medium">No hay reservas</div>
          <div class="text-sm text-gray-500 mt-1">Crea una nueva reserva usando la venta rápida</div>
        </div>
      }
    </div>
  `,
})
export class ReservationListComponent implements OnInit {
  private reservaService = inject(ReservaService);

  reservas = signal<Reserva[]>([]);
  busqueda = '';
  filtroEstado = 'TODOS';

  estadoEntries = Object.entries(ESTADO_RESERVA_CONFIG);

  stats = [
    { label: 'Hoy', value: 0, color: 'text-[#1e3a5f]' },
    { label: 'Check-in', value: 0, color: 'text-green-600' },
    { label: 'Check-out', value: 0, color: 'text-red-500' },
    { label: 'Pendientes', value: 0, color: 'text-amber-500' },
    { label: 'Ocupación', value: '0%', color: 'text-purple-600' },
  ];

  reservasFiltradas = signal<Reserva[]>([]);

  ngOnInit() {
    this.loadReservas();
  }

  loadReservas() {
    this.reservaService.obtenerReservas().subscribe({
      next: (reservas) => {
        this.reservas.set(reservas);
        this.updateStats(reservas);
        this.applyFilters();
      },
      error: () => {
        // Demo data
        const demo: Reserva[] = [
          {
            id: '1', huespedId: 'h1', nombreHuesped: 'Carlos García',
            habitacionId: '3', numeroHabitacion: '201',
            fechaEntrada: '2026-06-22', fechaSalida: '2026-06-25',
            cantidadNoches: 3, precioPorNoche: 120, montoAnticipo: 108, montoTotal: 360,
            estado: 'CONFIRMADA', fechaLimiteConfirmacion: '', fechaCreacion: '', activo: true,
          },
          {
            id: '2', huespedId: 'h2', nombreHuesped: 'María López',
            habitacionId: '5', numeroHabitacion: '301',
            fechaEntrada: '2026-06-21', fechaSalida: '2026-06-24',
            cantidadNoches: 3, precioPorNoche: 200, montoAnticipo: 180, montoTotal: 600,
            estado: 'CONFIRMADA', fechaLimiteConfirmacion: '', fechaCreacion: '', activo: true,
          },
          {
            id: '3', huespedId: 'h3', nombreHuesped: 'Pedro Martínez',
            habitacionId: '1', numeroHabitacion: '101',
            fechaEntrada: '2026-06-23', fechaSalida: '2026-06-26',
            cantidadNoches: 3, precioPorNoche: 80, montoAnticipo: 72, montoTotal: 240,
            estado: 'SOLICITUD', fechaLimiteConfirmacion: '', fechaCreacion: '', activo: true,
          },
          {
            id: '4', huespedId: 'h4', nombreHuesped: 'Ana Rodríguez',
            habitacionId: '4', numeroHabitacion: '202',
            fechaEntrada: '2026-06-24', fechaSalida: '2026-06-27',
            cantidadNoches: 3, precioPorNoche: 120, montoAnticipo: 108, montoTotal: 360,
            estado: 'RESERVADO', fechaLimiteConfirmacion: '', fechaCreacion: '', activo: true,
          },
          {
            id: '5', huespedId: 'h5', nombreHuesped: 'Luis Fernández',
            habitacionId: '2', numeroHabitacion: '102',
            fechaEntrada: '2026-06-20', fechaSalida: '2026-06-22',
            cantidadNoches: 2, precioPorNoche: 80, montoAnticipo: 48, montoTotal: 160,
            estado: 'CANCELADA', fechaLimiteConfirmacion: '', fechaCreacion: '', activo: true,
          },
        ];
        this.reservas.set(demo);
        this.updateStats(demo);
        this.reservasFiltradas.set(demo);
      },
    });
  }

  updateStats(reservas: Reserva[]) {
    const today = new Date().toISOString().split('T')[0];
    const activas = reservas.filter((r) => r.estado !== 'CANCELADA');

    this.stats = [
      { label: 'Hoy', value: activas.filter((r) => r.fechaEntrada === today).length, color: 'text-[#1e3a5f]' },
      { label: 'Check-in', value: activas.filter((r) => r.fechaEntrada === today).length, color: 'text-green-600' },
      { label: 'Check-out', value: activas.filter((r) => r.fechaSalida === today).length, color: 'text-red-500' },
      { label: 'Pendientes', value: activas.filter((r) => r.estado === 'SOLICITUD' || r.estado === 'RESERVADO').length, color: 'text-amber-500' },
      { label: 'Ocupación', value: Math.round((activas.length / 6) * 100) + '%', color: 'text-purple-600' },
    ];
  }

  applyFilters() {
    let filtered = this.reservas();

    if (this.filtroEstado !== 'TODOS') {
      filtered = filtered.filter((r) => r.estado === this.filtroEstado);
    }

    if (this.busqueda) {
      const term = this.busqueda.toLowerCase();
      filtered = filtered.filter((r) => r.nombreHuesped.toLowerCase().includes(term));
    }

    this.reservasFiltradas.set(filtered);
  }

  getEstadoConfig(estado: EstadoReserva) {
    return ESTADO_RESERVA_CONFIG[estado] || { label: estado, color: 'text-gray-700', bgColor: 'bg-gray-100' };
  }

  formatDate(dateStr: string): string {
    if (!dateStr) return '';
    const date = new Date(dateStr + 'T00:00:00');
    return date.toLocaleDateString('es-ES', { day: '2-digit', month: 'short' });
  }

  verDetalle(reserva: Reserva) {
    console.log('Ver detalle:', reserva);
  }

  cancelarReserva(reserva: Reserva) {
    if (confirm(`¿Estás seguro de cancelar la reserva de ${reserva.nombreHuesped}?`)) {
      this.reservaService.cancelarReserva(reserva.id).subscribe({
        next: () => {
          this.reservas.set(this.reservas().map((r) =>
            r.id === reserva.id ? { ...r, estado: 'CANCELADA' as EstadoReserva } : r
          ));
          this.applyFilters();
        },
        error: () => {
          this.reservas.set(this.reservas().map((r) =>
            r.id === reserva.id ? { ...r, estado: 'CANCELADA' as EstadoReserva } : r
          ));
          this.applyFilters();
        },
      });
    }
  }
}
