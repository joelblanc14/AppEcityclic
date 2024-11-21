import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = `http://${environment.apiUrl}/api/auth/login`;
  private token: string | null = null;

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(this.apiUrl, { username, password });
  }

  setToken(token: string): void {
    this.token = token;
    sessionStorage.setItem('authToken', token);
  }

  getToken(): string | null {
    return this.token || sessionStorage.getItem('authToken');
  }

  isAuthenticated(): boolean {
    return !!sessionStorage.getItem('authToken');
  }

  logout(): void {
    this.token = null;
    localStorage.removeItem('authToken');
  }
}
