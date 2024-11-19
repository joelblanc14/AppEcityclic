import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Projecte } from '../models/projecte.interface';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProjecteService {
  private baseUrl = `http://${environment.apiUrl}/api/empresa`;

  constructor(private http: HttpClient) { }

  getProjectesByEmpresaId(empresaId: number): Observable<Projecte[]> {
    return this.http.get<Projecte[]>(`${this.baseUrl}/${empresaId}/projecte`);
  }

  getProjecteById(empresaId: number, id: number): Observable<Projecte> {
    return this.http.get<Projecte>(`${this.baseUrl}/${empresaId}/projecte/${id}`);
  }

  createProjecte(empresaId: number ,projecte: Projecte): Observable<Projecte> {
    return this.http.post<Projecte>(`${this.baseUrl}/${empresaId}/projecte`, projecte);
  }

  updateProjecte(empresaId: number ,id: number, projecte: Projecte): Observable<Projecte> {
    return this.http.put<Projecte>(`${this.baseUrl}/${empresaId}/projecte/${id}`, projecte);
  }

  deleteProjecte(empresaId: number, id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${empresaId}/projecte/${id}`);
  }
}
