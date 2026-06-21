import { Component, input, output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

interface MenuItem {
  icon: string;
  label: string;
  route: string;
  active?: boolean;
}

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <aside class="fixed left-0 top-0 h-full w-64 bg-[#1e3a5f] text-white flex flex-col z-50 transition-transform duration-300"
           [class.-translate-x-full]="!isOpen()"
           [class.translate-x-0]="isOpen()">
      <!-- Logo -->
      <div class="p-4 border-b border-white/10">
        <div class="flex items-center gap-3">
          <div class="w-10 h-10 bg-[#e8a838] rounded-lg flex items-center justify-center font-bold text-lg">
            H
          </div>
          <div>
            <h1 class="font-semibold text-sm">MyHotelControl</h1>
            <p class="text-xs text-white/60">Sistema de Gestión</p>
          </div>
        </div>
      </div>

      <!-- Navigation -->
      <nav class="flex-1 p-4 space-y-1 overflow-y-auto">
        @for (item of menuItems; track item.label) {
          <a [routerLink]="item.route"
             class="flex items-center gap-3 px-3 py-2.5 rounded-lg text-sm transition-colors"
             [class]="item.active
               ? 'bg-white/15 text-white'
               : 'text-white/70 hover:bg-white/10 hover:text-white'">
            <span class="text-lg" [innerHTML]="item.icon"></span>
            <span>{{ item.label }}</span>
          </a>
        }
      </nav>

      <!-- Footer -->
      <div class="p-4 border-t border-white/10">
        <div class="flex items-center gap-3">
          <div class="w-8 h-8 bg-[#e8a838]/20 rounded-full flex items-center justify-center text-sm font-medium">
            A
          </div>
          <div class="flex-1 min-w-0">
            <p class="text-sm font-medium truncate">Admin</p>
            <p class="text-xs text-white/50 truncate">Recepcionista</p>
          </div>
        </div>
      </div>
    </aside>
  `,
})
export class SidebarComponent {
  isOpen = input(true);

  menuItems: MenuItem[] = [
    { icon: '🏠', label: 'Dashboard', route: '/dashboard' },
    { icon: '📅', label: 'Reservas', route: '/reservas', active: true },
    { icon: '🛏️', label: 'Habitaciones', route: '/habitaciones' },
    { icon: '👤', label: 'Huéspedes', route: '/huespedes' },
    { icon: '🧾', label: 'Check-in/out', route: 'checkin' },
    { icon: '📦', label: 'Inventario', route: '/inventario' },
    { icon: '📊', label: 'Reportes', route: '/reportes' },
    { icon: '⚙️', label: 'Configuración', route: '/configuracion' },
  ];
}
