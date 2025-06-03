import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { map, Observable } from 'rxjs';
import { JwtAuthenticationResponse } from '../../dto/jwt-authentication-response';
import { LoginDto } from '../../dto/login-dto';

const JWT_TOKEN = 'jwt-token';
const EMAIL = 'user-email';
const ROLE='user-role';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8080/auth'; 

  constructor(private http: HttpClient, private router: Router) {}

  register(data: { firstName: string; lastName: string; email: string; password: string; role: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/signup`, data);
  }   

  login(loginDto: LoginDto): Observable<JwtAuthenticationResponse> {
    console.log(loginDto);
    return this.http
      .post<JwtAuthenticationResponse>(
        `${this.apiUrl}/login`,
        loginDto
      )
      .pipe(
        map((jwt) => {
          // Importante: https://stackoverflow.com/questions/27067251/where-to-store-jwt-in-browser-how-to-protect-against-csrf
          sessionStorage.setItem(JWT_TOKEN, jwt.token);
          sessionStorage.setItem(EMAIL, jwt.email);
          sessionStorage.setItem(ROLE, jwt.role);
          return jwt;
       })
      );
  } 

  logout(): void {
    localStorage.removeItem('jwt_token');
    this.router.navigate(['/login']);
  }

  isAuthenticated() {
    return sessionStorage.getItem(JWT_TOKEN) != null;
  }

  token() {
    return sessionStorage.getItem(JWT_TOKEN);
  }

  role() {
    return sessionStorage.getItem(ROLE);
  }

}
