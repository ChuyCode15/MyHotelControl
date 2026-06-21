import { Component, input, output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  template: `
    <header class="sticky top-0 z-40 bg-white border-b border-gray-200 px-6 py-3">
      <div class="flex items-center justify-between">
        <!-- Left: Toggle + Title -->
        <div class="flex items-center gap-4">
          <button (click)="toggleSidebar.emit()"
                  class="p-2 hover:bg-gray-100 rounded-lg transition-colors">
            <svg class="w-5 h-5 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16"/>
            </svg>
          </button>
          <div>
            <h2 class="text-lg font-semibold text-gray-800">{{ title() }}</h2>
            <p class="text-xs text-gray-500">{{ subtitle() }}</p>
          </div>
        </div>

        <!-- Right: Quick Actions -->
        <div class="flex items-center gap-3">
          <!-- Search -->
          <div class="relative">
            <input type="text"
                   placeholder="Buscar reserva..."
                   class="w-64 pl-10 pr-4 py-2 bg-gray-100 border-0 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-[#1e3a5f]/20 focus:bg-white transition-all">
            <svg class="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
            </svg>
          </div>

          <!-- Notifications -->
          <button class="relative p-2 hover:bg-gray-100 rounded-lg transition-colors">
            <svg class="w-5 h-5 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9"/>
            </svg>
            <span class="absolute top-1 right-1 w-2 h-2 bg-red-500 rounded-full"></span>
          </button>

          <!-- Date -->
          <div class="text-sm text-gray-500 hidden sm:block">
            {{ today | date:'EEEE d, MMMM y' }}
          </div>
        </div>
      </div>
    </header>
  `,
})
export class HeaderComponent {
  title = input('Reservas');
  subtitle = input('Gestión de reservaciones del hotel');
  toggleSidebar = output();

  today = new Date();
}
