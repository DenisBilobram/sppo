import { CanActivateFn } from '@angular/router';

import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthTokenGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    const code = route.queryParams["code"];
    console.log(code)
    if (code != null) {
      this.authService.exchangeToken(code).subscribe(
        response => {
          localStorage['access_token'] = response.access_token;
          localStorage['refresh_token'] = response.refresh_token;
          localStorage['id_token'] = response.id_token;
          this.router.navigateByUrl("/main");
        }
      )

    }

    return true;
  }
}
