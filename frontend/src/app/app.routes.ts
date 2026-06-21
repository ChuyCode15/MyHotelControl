import { Routes } from '@angular/router';
import { LayoutComponent } from './shared/layout/layout.component';
import { ReservationsPageComponent } from './features/reservations/reservations-page.component';

export const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: '', redirectTo: 'reservas', pathMatch: 'full' },
      { path: 'reservas', component: ReservationsPageComponent },
      { path: 'dashboard', redirectTo: 'reservas' },
      { path: 'habitaciones', redirectTo: 'reservas' },
      { path: 'huespedes', redirectTo: 'reservas' },
    ],
  },
];
