import { Router } from '@angular/router';
import { AuthGuard } from '../guard/auth.guard';
import { AuthService } from '../service/auth.service';
import { TestBed } from '@angular/core/testing';

describe('AuthGuard', () => {
  let guard: AuthGuard;
  let authServiceSpy: jasmine.SpyObj<AuthService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(() => {
    authServiceSpy = jasmine.createSpyObj('AuthService', ['isAuthenticated']);
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    TestBed.configureTestingModule({
      providers: [
        AuthGuard,
        { provide: AuthService, useValue: authServiceSpy },
        { provide: Router, useValue: routerSpy },
      ]
    });

    guard = TestBed.inject(AuthGuard);
  });

  it('should allow access if authenticated', () => {
    authServiceSpy.isAuthenticated.and.returnValue(true);
    expect(guard.canActivate()).toBeTrue();
  });

  it('should deny access and redirect to login if not authenticated', () => {
    authServiceSpy.isAuthenticated.and.returnValue(false);
    expect(guard.canActivate()).toBeFalse();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/login']);
  });
});