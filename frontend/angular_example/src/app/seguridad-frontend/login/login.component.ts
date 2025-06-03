import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { LoginDto } from '../../dto/login-dto';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [CommonModule, FormsModule, RouterModule]
})
export class LoginComponent {
  loginDTO:LoginDto=new LoginDto("","");

  constructor(private authService: AuthService, private router: Router) {}
  ngOnInit(): void {
    this.authService.logout();
  }
  enviar(): void {
    this.authService.login(this.loginDTO).subscribe({
      next: res => {
        console.log(res);
        //this.redirigirPorRol(res.role);
      },
      error: () => alert('Credenciales incorrectas')
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