import { Component, signal, inject, output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReservaService } from '../../../../core/services/reserva.service';
import { HabitacionDisponible, DatosBusquedaReserva } from '../../../../core/models/reserva.model';

@Component({
  selector: 'app-quick-sales',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden">
      <!-- Header -->
      <div class="p-4 border-b border-gray-200 flex items-center justify-between">
        <div class="flex items-center gap-3">
          <div class="w-10 h-10 bg-[#e8a838]/10 rounded-lg flex items-center justify-center">
            <span class="text-xl">⚡</span>
          </div>
          <div>
            <h3 class="font-semibold text-gray-800">Venta Rápida</h3>
            <p class="text-xs text-gray-500">Reserva en 3 pasos</p>
          </div>
        </div>
        <div class="flex items-center gap-2">
          @for (step of steps; track step.num; let i = $index) {
            <div class="flex items-center">
              <div class="w-8 h-8 rounded-full flex items-center justify-center text-xs font-medium transition-colors"
                   [class]="currentStep() >= step.num
                     ? 'bg-[#1e3a5f] text-white'
                     : 'bg-gray-100 text-gray-500'">
                {{ step.num }}
              </div>
              @if (i < steps.length - 1) {
                <div class="w-12 h-0.5 mx-1"
                     [class]="currentStep() > step.num ? 'bg-[#1e3a5f]' : 'bg-gray-200'"></div>
              }
            </div>
          }
        </div>
      </div>

      <!-- Step 1: Guests & Dates -->
      @if (currentStep() === 1) {
        <div class="p-6">
          <h4 class="text-sm font-medium text-gray-700 mb-4">¿Cuántos huéspedes y por cuántas noches?</h4>
          <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
            <!-- Adults -->
            <div>
              <label class="block text-sm text-gray-600 mb-2">Adultos</label>
              <div class="flex items-center gap-3">
                <button (click)="decrementar('adultos')"
                        class="w-10 h-10 rounded-lg border border-gray-300 flex items-center justify-center hover:bg-gray-50 transition-colors">
                  <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 12H4"/>
                  </svg>
                </button>
                <span class="text-2xl font-semibold w-8 text-center">{{ adultos() }}</span>
                <button (click)="incrementar('adultos')"
                        class="w-10 h-10 rounded-lg border border-gray-300 flex items-center justify-center hover:bg-gray-50 transition-colors">
                  <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"/>
                  </svg>
                </button>
              </div>
            </div>

            <!-- Children -->
            <div>
              <label class="block text-sm text-gray-600 mb-2">Niños</label>
              <div class="flex items-center gap-3">
                <button (click)="decrementar('ninos')"
                        class="w-10 h-10 rounded-lg border border-gray-300 flex items-center justify-center hover:bg-gray-50 transition-colors">
                  <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 12H4"/>
                  </svg>
                </button>
                <span class="text-2xl font-semibold w-8 text-center">{{ ninos() }}</span>
                <button (click)="incrementar('ninos')"
                        class="w-10 h-10 rounded-lg border border-gray-300 flex items-center justify-center hover:bg-gray-50 transition-colors">
                  <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"/>
                  </svg>
                </button>
              </div>
            </div>

            <!-- Nights -->
            <div>
              <label class="block text-sm text-gray-600 mb-2">Noches</label>
              <div class="flex items-center gap-3">
                <button (click)="decrementar('noches')"
                        class="w-10 h-10 rounded-lg border border-gray-300 flex items-center justify-center hover:bg-gray-50 transition-colors">
                  <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 12H4"/>
                  </svg>
                </button>
                <span class="text-2xl font-semibold w-8 text-center">{{ noches() }}</span>
                <button (click)="incrementar('noches')"
                        class="w-10 h-10 rounded-lg border border-gray-300 flex items-center justify-center hover:bg-gray-50 transition-colors">
                  <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"/>
                  </svg>
                </button>
              </div>
            </div>
          </div>

          <!-- Date Picker -->
          <div class="mt-6">
            <label class="block text-sm text-gray-600 mb-2">Fecha de entrada</label>
            <input type="date"
                   [(ngModel)]="fechaEntrada"
                   [min]="minDate"
                   class="w-full md:w-auto px-4 py-2.5 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-[#1e3a5f]/20 focus:border-[#1e3a5f]">
          </div>

          <button (click)="buscarHabitaciones()"
                  class="mt-6 w-full md:w-auto px-6 py-3 bg-[#1e3a5f] text-white rounded-lg font-medium hover:bg-[#2d5986] transition-colors flex items-center justify-center gap-2">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
            </svg>
            Buscar Habitaciones
          </button>
        </div>
      }

      <!-- Step 2: Available Rooms -->
      @if (currentStep() === 2) {
        <div class="p-6">
          <div class="flex items-center justify-between mb-4">
            <h4 class="text-sm font-medium text-gray-700">
              {{ habitacionesDisponibles().length }} habitaciones disponibles
            </h4>
            <button (click)="currentStep.set(1)" class="text-sm text-[#1e3a5f] hover:underline">
              Modificar búsqueda
            </button>
          </div>

          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            @for (hab of habitacionesDisponibles(); track hab.id) {
              <div class="border border-gray-200 rounded-lg p-4 hover:border-[#1e3a5f] transition-colors cursor-pointer"
                   [class.border-[#1e3a5f]]="selectedRoom()?.id === hab.id"
                   [class.bg-blue-50]="selectedRoom()?.id === hab.id"
                   (click)="selectedRoom.set(hab)">
                <div class="flex items-start justify-between mb-3">
                  <div>
                    <div class="text-lg font-semibold text-gray-800">{{ hab.numero }}</div>
                    <div class="text-sm text-gray-500">{{ hab.nombre }}</div>
                  </div>
                  <div class="text-right">
                    <div class="text-lg font-bold text-[#1e3a5f]">\${{ hab.precioPorNoche }}</div>
                    <div class="text-xs text-gray-500">por noche</div>
                  </div>
                </div>

                <div class="flex flex-wrap gap-1 mb-3">
                  @for (cama of hab.camas; track cama) {
                    <span class="px-2 py-0.5 bg-gray-100 text-gray-600 text-xs rounded-full">
                      {{ cama }}
                    </span>
                  }
                  <span class="px-2 py-0.5 bg-gray-100 text-gray-600 text-xs rounded-full">
                    Max {{ hab.capacidadMaxima }} personas
                  </span>
                </div>

                <div class="border-t border-gray-100 pt-3 flex items-center justify-between">
                  <div>
                    <div class="text-xs text-gray-500">Total {{ noches() }} noches</div>
                    <div class="text-sm font-semibold text-gray-800">\${{ hab.precioTotal }}</div>
                  </div>
                  <div class="text-right">
                    <div class="text-xs text-gray-500">Anticipo 30%</div>
                    <div class="text-sm font-semibold text-green-600">\${{ hab.anticipoRequerido }}</div>
                  </div>
                </div>
              </div>
            }
          </div>

          @if (habitacionesDisponibles().length === 0 && !loading()) {
            <div class="text-center py-12">
              <div class="text-4xl mb-4">😔</div>
              <div class="text-gray-600 font-medium">No hay habitaciones disponibles</div>
              <div class="text-sm text-gray-500 mt-1">Intenta con otras fechas o cantidad de huéspedes</div>
            </div>
          }

          @if (selectedRoom()) {
            <div class="mt-6 flex items-center justify-between bg-[#1e3a5f]/5 rounded-lg p-4">
              <div>
                <div class="text-sm text-gray-600">Habitación seleccionada: <strong>{{ selectedRoom()?.numero }}</strong></div>
                <div class="text-sm text-gray-500">Total: <strong>\${{ selectedRoom()?.precioTotal }}</strong> | Anticipo: <strong>\${{ selectedRoom()?.anticipoRequerido }}</strong></div>
              </div>
              <button (click)="currentStep.set(3)"
                      class="px-6 py-2.5 bg-[#e8a838] text-white rounded-lg font-medium hover:bg-[#f0c060] transition-colors">
                Continuar
              </button>
            </div>
          }
        </div>
      }

      <!-- Step 3: Confirm -->
      @if (currentStep() === 3) {
        <div class="p-6">
          <h4 class="text-sm font-medium text-gray-700 mb-4">Confirmar Reserva</h4>

          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <!-- Reservation Summary -->
            <div class="bg-gray-50 rounded-lg p-4">
              <h5 class="font-medium text-gray-800 mb-3">Resumen</h5>
              <div class="space-y-2 text-sm">
                <div class="flex justify-between">
                  <span class="text-gray-500">Habitación</span>
                  <span class="font-medium">{{ selectedRoom()?.numero }} - {{ selectedRoom()?.nombre }}</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-500">Fecha entrada</span>
                  <span class="font-medium">{{ fechaEntrada }}</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-500">Fecha salida</span>
                  <span class="font-medium">{{ fechaSalida }}</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-500">Noches</span>
                  <span class="font-medium">{{ noches() }}</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-500">Huéspedes</span>
                  <span class="font-medium">{{ adultos() }} adultos, {{ ninos() }} niños</span>
                </div>
                <div class="border-t border-gray-200 pt-2 mt-2">
                  <div class="flex justify-between">
                    <span class="text-gray-500">Precio por noche</span>
                    <span class="font-medium">\${{ selectedRoom()?.precioPorNoche }}</span>
                  </div>
                  <div class="flex justify-between text-base">
                    <span class="font-medium text-gray-800">Total</span>
                    <span class="font-bold text-[#1e3a5f]">\${{ selectedRoom()?.precioTotal }}</span>
                  </div>
                  <div class="flex justify-between text-green-600">
                    <span class="font-medium">Anticipo requerido (30%)</span>
                    <span class="font-bold">\${{ selectedRoom()?.anticipoRequerido }}</span>
                  </div>
                </div>
              </div>
            </div>

            <!-- Guest Info -->
            <div>
              <h5 class="font-medium text-gray-800 mb-3">Datos del Huésped</h5>
              <div class="space-y-4">
                <div>
                  <label class="block text-sm text-gray-600 mb-1">Nombre completo *</label>
                  <input type="text"
                         [(ngModel)]="huespedNombre"
                         placeholder="Nombre del huésped"
                         class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-[#1e3a5f]/20 focus:border-[#1e3a5f]">
                </div>
                <div>
                  <label class="block text-sm text-gray-600 mb-1">Documento de identidad *</label>
                  <input type="text"
                         [(ngModel)]="huespedDocumento"
                         placeholder="Número de documento"
                         class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-[#1e3a5f]/20 focus:border-[#1e3a5f]">
                </div>
                <div>
                  <label class="block text-sm text-gray-600 mb-1">Teléfono</label>
                  <input type="tel"
                         [(ngModel)]="huespedTelefono"
                         placeholder="Teléfono de contacto"
                         class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-[#1e3a5f]/20 focus:border-[#1e3a5f]">
                </div>
                <div>
                  <label class="block text-sm text-gray-600 mb-1">Correo electrónico</label>
                  <input type="email"
                         [(ngModel)]="huespedEmail"
                         placeholder="correo@ejemplo.com"
                         class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-[#1e3a5f]/20 focus:border-[#1e3a5f]">
                </div>
                <div>
                  <label class="block text-sm text-gray-600 mb-1">Notas</label>
                  <textarea [(ngModel)]="notas"
                            rows="2"
                            placeholder="Solicitudes especiales..."
                            class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-[#1e3a5f]/20 focus:border-[#1e3a5f] resize-none"></textarea>
                </div>
              </div>
            </div>
          </div>

          <div class="mt-6 flex items-center justify-between">
            <button (click)="currentStep.set(2)"
                    class="px-4 py-2 text-gray-600 hover:text-gray-800 transition-colors">
              ← Volver
            </button>
            <div class="flex items-center gap-3">
              <button (click)="registrarReserva('RESERVADO')"
                      class="px-6 py-2.5 border border-[#1e3a5f] text-[#1e3a5f] rounded-lg font-medium hover:bg-[#1e3a5f]/5 transition-colors">
                Registrar Sin Anticipo
              </button>
              <button (click)="registrarReserva('CONFIRMADA')"
                      class="px-6 py-2.5 bg-[#e8a838] text-white rounded-lg font-medium hover:bg-[#f0c060] transition-colors">
                Confirmar Con Anticipo
              </button>
            </div>
          </div>
        </div>
      }

      <!-- Loading -->
      @if (loading()) {
        <div class="p-6 flex items-center justify-center">
          <div class="flex items-center gap-3 text-gray-500">
            <svg class="animate-spin w-5 h-5" fill="none" viewBox="0 0 24 24">
              <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"/>
              <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"/>
            </svg>
            Buscando habitaciones disponibles...
          </div>
        </div>
      }
    </div>
  `,
})
export class QuickSalesComponent {
  private reservaService = inject(ReservaService);

  reservaCreated = output<any>();

  currentStep = signal(1);
  loading = signal(false);
  habitacionesDisponibles = signal<HabitacionDisponible[]>([]);
  selectedRoom = signal<HabitacionDisponible | null>(null);

  adultos = signal(2);
  ninos = signal(0);
  noches = signal(1);
  fechaEntrada = this.getMinDate();

  huespedNombre = '';
  huespedDocumento = '';
  huespedTelefono = '';
  huespedEmail = '';
  notas = '';

  minDate = this.getMinDate();
  fechaSalida = '';

  steps = [
    { num: 1, label: 'Huéspedes' },
    { num: 2, label: 'Habitación' },
    { num: 3, label: 'Confirmar' },
  ];

  incrementar(field: 'adultos' | 'ninos' | 'noches') {
    if (field === 'adultos' && this.adultos() < 10) this.adultos.set(this.adultos() + 1);
    if (field === 'ninos' && this.ninos() < 6) this.ninos.set(this.ninos() + 1);
    if (field === 'noches' && this.noches() < 30) this.noches.set(this.noches() + 1);
  }

  decrementar(field: 'adultos' | 'ninos' | 'noches') {
    if (field === 'adultos' && this.adultos() > 1) this.adultos.set(this.adultos() - 1);
    if (field === 'ninos' && this.ninos() > 0) this.ninos.set(this.ninos() - 1);
    if (field === 'noches' && this.noches() > 1) this.noches.set(this.noches() - 1);
  }

  buscarHabitaciones() {
    const totalHuespedes = this.adultos() + this.ninos();
    const busqueda: DatosBusquedaReserva = {
      fechaEntrada: this.fechaEntrada,
      cantidadNoches: this.noches(),
      cantidadHuespedes: totalHuespedes,
    };

    const fechaSalidaDate = new Date(this.fechaEntrada);
    fechaSalidaDate.setDate(fechaSalidaDate.getDate() + this.noches());
    this.fechaSalida = fechaSalidaDate.toISOString().split('T')[0];

    this.loading.set(true);
    this.reservaService.buscarDisponibilidad(busqueda).subscribe({
      next: (habitaciones) => {
        this.habitacionesDisponibles.set(habitaciones);
        this.loading.set(false);
        this.currentStep.set(2);
      },
      error: () => {
        // Demo data
        this.habitacionesDisponibles.set([
          {
            id: '1', nombre: 'Individual', numero: '101', camas: ['1 individual'],
            capacidadMaxima: 1, precioPorNoche: 80, precioTotal: 80 * this.noches(),
            anticipoRequerido: 80 * this.noches() * 0.3,
          },
          {
            id: '3', nombre: 'Doble', numero: '201', camas: ['1 matrimonial'],
            capacidadMaxima: 2, precioPorNoche: 120, precioTotal: 120 * this.noches(),
            anticipoRequerido: 120 * this.noches() * 0.3,
          },
          {
            id: '5', nombre: 'Suite', numero: '301', camas: ['1 king size', '2 individuales'],
            capacidadMaxima: 4, precioPorNoche: 200, precioTotal: 200 * this.noches(),
            anticipoRequerido: 200 * this.noches() * 0.3,
          },
        ]);
        this.loading.set(false);
        this.currentStep.set(2);
      },
    });
  }

  registrarReserva(estado: 'RESERVADO' | 'CONFIRMADA') {
    console.log('Registrando reserva:', {
      habitacion: this.selectedRoom(),
      huesped: { nombre: this.huespedNombre, doc: this.huespedDocumento },
      estado,
    });
    // TODO: Implement real registration
    this.reservaCreated.emit({ success: true });
    this.resetForm();
  }

  resetForm() {
    this.currentStep.set(1);
    this.selectedRoom.set(null);
    this.habitacionesDisponibles.set([]);
    this.huespedNombre = '';
    this.huespedDocumento = '';
    this.huespedTelefono = '';
    this.huespedEmail = '';
    this.notas = '';
  }

  private getMinDate(): string {
    return new Date().toISOString().split('T')[0];
  }
}
