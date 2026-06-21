import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { SidebarComponent } from '../components/sidebar/sidebar.component';
import { HeaderComponent } from '../components/header/header.component';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CommonModule, RouterOutlet, SidebarComponent, HeaderComponent],
  template: `
    <div class="min-h-screen bg-gray-50">
      <app-sidebar [isOpen]="sidebarOpen()" />
      <div class="ml-64 transition-all duration-300">
        <app-header title="Reservas" subtitle="Gestión de reservaciones del hotel" (toggleSidebar)="sidebarOpen.set(!sidebarOpen())" />
        <main class="p-6">
          <router-outlet />
        </main>
      </div>
    </div>
  `,
})
export class LayoutComponent {
  sidebarOpen = signal(true);
}
