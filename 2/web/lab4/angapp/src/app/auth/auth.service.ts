import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, map, of, tap, throwError } from 'rxjs';
import { Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = 'http://localhost:29885';


  constructor(private http: HttpClient, private router: Router) {
  }

  status(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/api/auth/status`, {});
  }

  exchangeToken(code: string): Observable<any>{
    const body = new URLSearchParams();
    body.set('code', code);
  
    const headers = { 'Content-Type': 'application/x-www-form-urlencoded' };

    console.log("send request exchange")

    return this.http.post<any>(`${this.baseUrl}/api/auth/exchange-code`, body.toString(), { headers });
  }

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/api/auth/login`, {username, password});
  }

  register(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/api/auth/register`, {username, password});
  }

  logout(): Observable<any> {
    return this.http.post<any>('', {});
  }
}

