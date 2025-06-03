import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../service/auth.service';

export const authInterceptor: HttpInterceptorFn = (request, next) => {
  // In standalone lambdas, injection is done with the inject function
  let auth: AuthService = inject(AuthService);
  let token = auth.token();

  if (token == null) {
    return next(request);
  } else {
    return next(
      request.clone({
        headers: request.headers.set('Authorization', `Bearer ${token}`),
      })
    );
  }
};