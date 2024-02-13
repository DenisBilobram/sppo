import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const headers = new HttpHeaders({
  'Content-Type': 'application/json',
});


@Injectable({
  providedIn: 'root'
})
export class MainService {

  private baseUrl = 'http://localhost:29885';

  constructor(private http: HttpClient) { }

  getRequestsHistory(): Observable<any> {
    return this.http.get(`${this.baseUrl}/api/main/requests`, {headers});
  }

  createRequest(x: number, y: number, r: number): Observable<any> {

    const body = { x, y, r };

    return this.http.post(`${this.baseUrl}/api/main/requests`, body, {headers});
  }

  clearRequestsHistory() {

    return this.http.delete(`${this.baseUrl}/api/main/requests`, {headers});

  }
  
}
