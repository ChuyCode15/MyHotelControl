import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReservationCalendarComponent } from './components/reservation-calendar/reservation-calendar.component';
import { QuickSalesComponent } from './components/quick-sales/quick-sales.component';
import { ReservationListComponent } from './components/reservation-list/reservation-list.component';

@Component({
  selector: 'app-reservations-page',
  standalone: true,
  imports: [CommonModule, ReservationCalendarComponent, QuickSalesComponent, ReservationListComponent],
  template: `
    <div class="space-y-6">
      <!-- Page Header -->
      <div class="flex items-center justify-between">
        <div>
          <h1 class="text-2xl font-bold text-gray-900">Reservas</h1>
          <p class="text-sm text-gray-500 mt-1">Gestiona las reservaciones del hotel</p>
        </div>
        <div class="flex items-center gap-3">
          <button (click)="activeView.set('calendar')"
                  class="px-4 py-2 text-sm font-medium rounded-lg transition-colors"
                  [class]="activeView() === 'calendar'
                    ? 'bg-[#1e3a5f] text-white'
                    : 'bg-gray-100 text-gray-600 hover:bg-gray-200'">
            <span class="flex items-center gap-2">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"/>
              </svg>
              Calendario
            </span>
          </button>
          <button (click)="activeView.set('list')"
                  class="px-4 py-2 text-sm font-medium rounded-lg transition-colors"
                  [class]="activeView() === 'list'
                    ? 'bg-[#1e3a5f] text-white'
                    : 'bg-gray-100 text-gray-600 hover:bg-gray-200'">
            <span class="flex items-center gap-2">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 10h16M4 14h16M4 18h16"/>
              </svg>
              Lista
            </span>
          </button>
          <button (click)="activeView.set('quick')"
                  class="px-4 py-2 text-sm font-medium bg-[#e8a838] text-white rounded-lg hover:bg-[#f0c060] transition-colors flex items-center gap-2">
            <span class="text-lg">⚡</span>
            Nueva Reserva
          </button>
        </div>
      </div>

      <!-- Quick Sales Panel (Modal-like) -->
      @if (activeView() === 'quick') {
        <div class="fixed inset-0 z-50 bg-black/50 flex items-center justify-center p-6" (click)="activeView.set('calendar')">
          <div class="w-full max-w-4xl max-h-[90vh] overflow-y-auto" (click)="$event.stopPropagation()">
            <app-quick-sales (reservaCreated)="onReservaCreated()" />
          </div>
        </div>
      }

      <!-- Calendar View -->
      @if (activeView() === 'calendar') {
        <app-reservation-calendar />
      }

      <!-- List View -->
      @if (activeView() === 'list') {
        <app-reservation-list />
      }
    </div>
  `,
})
export class ReservationsPageComponent {
  activeView = signal<'calendar' | 'list' | 'quick'>('calendar');

  onReservaCreated() {
    this.activeView.set('calendar');
  }
}
