import { Component, signal, computed, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReservaService } from '../../../../core/services/reserva.service';
import { Reserva, ESTADO_RESERVA_CONFIG, EstadoReserva } from '../../../../core/models/reserva.model';

@Component({
  selector: 'app-reservation-calendar',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden">
      <!-- Calendar Header -->
      <div class="p-4 border-b border-gray-200 flex items-center justify-between">
        <div class="flex items-center gap-4">
          <button (click)="previousWeek()"
                  class="p-2 hover:bg-gray-100 rounded-lg transition-colors">
            <svg class="w-5 h-5 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7"/>
            </svg>
          </button>
          <h3 class="font-semibold text-gray-800">{{ weekLabel() }}</h3>
          <button (click)="nextWeek()"
                  class="p-2 hover:bg-gray-100 rounded-lg transition-colors">
            <svg class="w-5 h-5 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"/>
            </svg>
          </button>
          <button (click)="goToToday()"
                  class="px-3 py-1.5 text-sm bg-[#1e3a5f] text-white rounded-lg hover:bg-[#2d5986] transition-colors">
            Hoy
          </button>
        </div>

        <!-- Legend -->
        <div class="flex items-center gap-4 text-xs">
          @for (entry of legendItems; track entry.estado) {
            <div class="flex items-center gap-1.5">
              <span class="w-3 h-3 rounded" [ngClass]="entry.bgClass"></span>
              <span class="text-gray-600">{{ entry.label }}</span>
            </div>
          }
        </div>
      </div>

      <!-- Calendar Grid -->
      <div class="overflow-x-auto">
        <div class="min-w-[900px]">
          <!-- Day Headers -->
          <div class="grid grid-cols-8 border-b border-gray-100 sticky top-0 bg-white z-10">
            <div class="p-3 text-xs font-medium text-gray-500 border-r border-gray-100">
              Habitación
            </div>
            @for (day of weekDays(); track day.date) {
              <div class="p-3 text-center border-r border-gray-100"
                   [class.bg-blue-50]="day.isToday"
                   [class.font-semibold]="day.isToday">
                <div class="text-xs text-gray-500">{{ day.dayName }}</div>
                <div class="text-lg" [class.text-[#1e3a5f]]="day.isToday">{{ day.dayNumber }}</div>
                <div class="text-xs text-gray-400">{{ day.month }}</div>
              </div>
            }
          </div>

          <!-- Room Rows -->
          @for (room of rooms(); track room.id) {
            <div class="grid grid-cols-8 border-b border-gray-100 hover:bg-gray-50 transition-colors">
              <!-- Room Info -->
              <div class="p-3 border-r border-gray-100 flex flex-col justify-center">
                <div class="font-medium text-sm text-gray-800">{{ room.numero }}</div>
                <div class="text-xs text-gray-500">{{ room.nombre }}</div>
                <div class="text-xs text-gray-400 mt-1">Cap: {{ room.capacidad }}</div>
              </div>

              <!-- Day Cells -->
              @for (day of weekDays(); track day.date) {
                <div class="border-r border-gray-100 p-0.5 min-h-[60px] relative cursor-pointer"
                     [class.bg-blue-50]="day.isToday"
                     (click)="onCellClick(room.id, day.date)">
                  @for (reserva of getReservasForRoomAndDay(room.id, day.date); track reserva.id) {
                    <div class="rounded px-1.5 py-1 text-xs cursor-pointer transition-all hover:opacity-80 mb-0.5"
                         [ngClass]="getReservaColorClass(reserva)"
                         (click)="onReservaClick($event, reserva)"
                         [title]="reserva.nombreHuesped + ' - ' + reserva.estado">
                      <div class="font-medium truncate">{{ reserva.nombreHuesped }}</div>
                      @if (isReservaStartOnDay(reserva, day.date)) {
                        <div class="text-[10px] opacity-75">{{ reserva.cantidadNoches }}n</div>
                      }
                    </div>
                  }
                </div>
              }
            </div>
          }

          <!-- Occupancy Footer -->
          <div class="grid grid-cols-8 bg-gray-50 sticky bottom-0">
            <div class="p-3 text-xs font-medium text-gray-500 border-r border-gray-100">
              Ocupación
            </div>
            @for (day of weekDays(); track day.date) {
              <div class="p-3 text-center border-r border-gray-100">
                <div class="text-sm font-semibold text-gray-800">{{ getOccupancyForDay(day.date) }}%</div>
                <div class="w-full bg-gray-200 rounded-full h-1.5 mt-1">
                  <div class="bg-[#1e3a5f] h-1.5 rounded-full transition-all"
                       [style.width.%]="getOccupancyForDay(day.date)"></div>
                </div>
              </div>
            }
          </div>
        </div>
      </div>
    </div>
  `,
})
export class ReservationCalendarComponent implements OnInit {
  private reservaService = inject(ReservaService);

  currentDate = signal(new Date());
  reservas = signal<Reserva[]>([]);

  rooms = signal([
    { id: '1', numero: '101', nombre: 'Individual', capacidad: 1 },
    { id: '2', numero: '102', nombre: 'Individual', capacidad: 1 },
    { id: '3', numero: '201', nombre: 'Doble', capacidad: 2 },
    { id: '4', numero: '202', nombre: 'Doble', capacidad: 2 },
    { id: '5', numero: '301', nombre: 'Suite', capacidad: 4 },
    { id: '6', numero: '302', nombre: 'Familiar', capacidad: 5 },
  ]);

  weekDays = computed(() => {
    const start = this.getStartOfWeek(this.currentDate());
    const days = [];
    const today = new Date();
    today.setHours(0, 0, 0, 0);

    for (let i = 0; i < 7; i++) {
      const date = new Date(start);
      date.setDate(date.getDate() + i);
      const dateStr = date.toISOString().split('T')[0];
      const dayDate = new Date(dateStr + 'T00:00:00');

      days.push({
        date: dateStr,
        dayName: date.toLocaleDateString('es-ES', { weekday: 'short' }),
        dayNumber: date.getDate(),
        month: date.toLocaleDateString('es-ES', { month: 'short' }),
        isToday: dayDate.getTime() === today.getTime(),
      });
    }
    return days;
  });

  weekLabel = computed(() => {
    const days = this.weekDays();
    if (days.length === 0) return '';
    const start = new Date(days[0].date);
    const end = new Date(days[6].date);
    return `${start.toLocaleDateString('es-ES', { day: 'numeric', month: 'short' })} - ${end.toLocaleDateString('es-ES', { day: 'numeric', month: 'short', year: 'numeric' })}`;
  });

  legendItems = [
    { estado: 'SOLICITUD', label: 'Solicitud', bgClass: 'bg-amber-400' },
    { estado: 'RESERVADO', label: 'Reservado', bgClass: 'bg-blue-400' },
    { estado: 'CONFIRMADA', label: 'Confirmada', bgClass: 'bg-green-400' },
    { estado: 'CANCELADA', label: 'Cancelada', bgClass: 'bg-red-300' },
  ];

  ngOnInit() {
    this.loadReservas();
  }

  loadReservas() {
    this.reservaService.obtenerReservas().subscribe({
      next: (reservas) => this.reservas.set(reservas),
      error: () => {
        // Demo data for preview
        this.reservas.set([
          {
            id: '1', huespedId: 'h1', nombreHuesped: 'Carlos García',
            habitacionId: '3', numeroHabitacion: '201',
            fechaEntrada: this.weekDays()[1]?.date || '', fechaSalida: this.weekDays()[4]?.date || '',
            cantidadNoches: 3, precioPorNoche: 120, montoAnticipo: 108, montoTotal: 360,
            estado: 'CONFIRMADA', fechaLimiteConfirmacion: '', fechaCreacion: '', activo: true,
          },
          {
            id: '2', huespedId: 'h2', nombreHuesped: 'María López',
            habitacionId: '5', numeroHabitacion: '301',
            fechaEntrada: this.weekDays()[0]?.date || '', fechaSalida: this.weekDays()[3]?.date || '',
            cantidadNoches: 3, precioPorNoche: 200, montoAnticipo: 180, montoTotal: 600,
            estado: 'CONFIRMADA', fechaLimiteConfirmacion: '', fechaCreacion: '', activo: true,
          },
          {
            id: '3', huespedId: 'h3', nombreHuesped: 'Pedro Martínez',
            habitacionId: '1', numeroHabitacion: '101',
            fechaEntrada: this.weekDays()[2]?.date || '', fechaSalida: this.weekDays()[5]?.date || '',
            cantidadNoches: 3, precioPorNoche: 80, montoAnticipo: 72, montoTotal: 240,
            estado: 'SOLICITUD', fechaLimiteConfirmacion: '', fechaCreacion: '', activo: true,
          },
        ]);
      },
    });
  }

  getReservasForRoomAndDay(roomId: string, dayDate: string): Reserva[] {
    return this.reservas().filter((r) => {
      if (r.habitacionId !== roomId) return false;
      const start = new Date(r.fechaEntrada);
      const end = new Date(r.fechaSalida);
      const day = new Date(dayDate);
      return day >= start && day < end && r.estado !== 'CANCELADA';
    });
  }

  isReservaStartOnDay(reserva: Reserva, dayDate: string): boolean {
    return reserva.fechaEntrada === dayDate;
  }

  getReservaColorClass(reserva: Reserva): string {
    const config = ESTADO_RESERVA_CONFIG[reserva.estado as EstadoReserva];
    return `${config?.bgColor || 'bg-gray-100'} ${config?.color || 'text-gray-700'}`;
  }

  getOccupancyForDay(dayDate: string): number {
    const totalRooms = this.rooms().length;
    const occupiedRooms = this.getReservasForRoomAndDay('', dayDate).length;
    // Simplified: count unique rooms occupied
    const occupied = new Set(
      this.reservas()
        .filter((r) => {
          const start = new Date(r.fechaEntrada);
          const end = new Date(r.fechaSalida);
          const day = new Date(dayDate);
          return day >= start && day < end && r.estado !== 'CANCELADA';
        })
        .map((r) => r.habitacionId)
    ).size;
    return Math.round((occupied / totalRooms) * 100);
  }

  previousWeek() {
    const current = new Date(this.currentDate());
    current.setDate(current.getDate() - 7);
    this.currentDate.set(current);
  }

  nextWeek() {
    const current = new Date(this.currentDate());
    current.setDate(current.getDate() + 7);
    this.currentDate.set(current);
  }

  goToToday() {
    this.currentDate.set(new Date());
  }

  onCellClick(roomId: string, dayDate: string) {
    console.log('Cell clicked:', roomId, dayDate);
  }

  onReservaClick(event: Event, reserva: Reserva) {
    event.stopPropagation();
    console.log('Reserva clicked:', reserva);
  }

  private getStartOfWeek(date: Date): Date {
    const d = new Date(date);
    const day = d.getDay();
    const diff = d.getDate() - day + (day === 0 ? -6 : 1);
    d.setDate(diff);
    d.setHours(0, 0, 0, 0);
    return d;
  }
}
