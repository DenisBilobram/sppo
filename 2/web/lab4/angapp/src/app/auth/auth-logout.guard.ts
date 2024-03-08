import { CanActivateFn } from '@angular/router';

import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthLogoutGuard implements CanActivate {

    
  constructor(private authService: AuthService, private router: Router) {}

  postLogoutRedirectUri = "http://localhost:29885/main"

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    localStorage.removeItem("access_token");
    localStorage.removeItem("refresh_token");

    window.location.href = `http://localhost:29886/realms/lab4-app/protocol/openid-connect/logout?id_token_hint=${localStorage['id_token']}&post_logout_redirect_uri=${this.postLogoutRedirectUri}`;

    return true;
  }
}