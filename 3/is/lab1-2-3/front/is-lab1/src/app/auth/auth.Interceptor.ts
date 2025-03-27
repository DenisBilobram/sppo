import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, of, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);

  return next(req).pipe(
    catchError(error => {
      if (error.status === 401) {
        if (typeof window !== 'undefined' && window.localStorage) {
          localStorage.removeItem('authToken');
        }
        router.navigate(['/auth/login']);
      }
      return throwError(() => error);
    })
  );
};