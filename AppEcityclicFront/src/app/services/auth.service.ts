import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = `${environment.apiUrl}/api/auth/login`;
  private token: string | null = null;

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post<{ token: string }>(this.apiUrl, { username, password }).pipe(
      tap(response => {
        this.setToken(response.token);
      })
    );
  }

  setToken(token: string): void {
    this.token = token;
    localStorage.setItem('authToken', token);
  }

  getToken(): string | null {
    const token = this.token || localStorage.getItem('authToken');
    console.log('Retrieved token:', token);
    return token;
  }

  logout(): void {
    this.token = null;
    localStorage.removeItem('authToken');
  }
}
