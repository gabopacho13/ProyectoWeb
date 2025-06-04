import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  imports: [CommonModule, FormsModule]
})
export class RegisterComponent {
  firstName = '';
  lastName = '';
  email = '';
  password = '';
  role = 'COMERCIANTE';

  constructor(private authService: AuthService, private router: Router) {}

  registrar(): void {
    const registerData = {
      firstName: this.firstName,
      lastName: this.lastName,
      email: this.email,
      password: this.password,
      role: this.role
    };

    this.authService.register(registerData).subscribe({
      next: res => {

        console.log(res);
        this.router.navigate(['/login']);
        alert('Usuario registrado exitosamente');
      },
      error: () => alert('Error al registrar usuario')
    });
  }

  redirigirPorRol(rol: string): void {
    switch (rol) {
      case 'ADMIN':
        this.router.navigate(['/admin']);
        break;
      case 'CARAVANERO':
        this.router.navigate(['/caravana']);
        break;
      case 'COMERCIANTE':
      default:
        this.router.navigate(['/producto']);
        break;
    }
  }
}
