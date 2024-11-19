import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Tasca } from '../models/tasca.interface';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TascaService {
  private apiUrl = `http://${environment.apiUrl}/api/empresa`;

  constructor(private http: HttpClient) { }

  getTasquesByProjecteId(empresaId: number, projecteId: number): Observable<Tasca[]> {
    return this.http.get<Tasca[]>(`${this.apiUrl}/${empresaId}/projecte/${projecteId}/tasca`);
  }

  getTasquesByTreballadorId(empresaId: number, treballadorId: number): Observable<Tasca[]> {
    return this.http.get<Tasca[]>(`${this.apiUrl}/${empresaId}/treballador/${treballadorId}/tasca`);
  }

  getTascaById(empresaId: number, projecteId: number, tascaId: number): Observable<Tasca> {
    return this.http.get<Tasca>(`${this.apiUrl}/${empresaId}/projecte/${projecteId}/tasca/${tascaId}`);
  }

  createTasca(empresaId: number, projecteId: number, tasca: Tasca): Observable<Tasca> {
    return this.http.post<Tasca>(`${this.apiUrl}/${empresaId}/projecte/${projecteId}/tasca`, tasca);
  }

  updateTasca(empresaId: number, projecteId: number, tascaId: number, tasca: Tasca): Observable<Tasca> {
    return this.http.put<Tasca>(`${this.apiUrl}/${empresaId}/projecte/${projecteId}/tasca/${tascaId}`, tasca);
  }

  deleteTasca(empresaId: number, projecteId: number, id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${empresaId}/projecte/${projecteId}/tasca/${id}`);
  }
}
