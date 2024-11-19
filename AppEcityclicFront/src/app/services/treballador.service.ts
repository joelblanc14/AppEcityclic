import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Treballador } from '../models/treballador.interface';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TreballadorService {
  private baseUrl = `http://${environment.apiUrl}/api/empresa`;

  constructor(private http: HttpClient) { }

  getTreballadorsByEmpresaId(empresaId: number): Observable<Treballador[]> {
    return this.http.get<Treballador[]>(`${this.baseUrl}/${empresaId}/treballador`);
  }

  getTreballadorById(empresaId: number, id: number): Observable<Treballador> {
    return this.http.get<Treballador>(`${this.baseUrl}/${empresaId}/treballador/${id}`);
  }

  createTreballador(empresaId: number, treballador: Treballador): Observable<Treballador> {
    return this.http.post<Treballador>(`${this.baseUrl}/${empresaId}/treballador`, treballador);
  }

  updateTreballador(empresaId: number, id: number, treballador: Treballador): Observable<Treballador> {
    return this.http.put<Treballador>(`${this.baseUrl}/${empresaId}/treballador/${id}`, treballador);
  }

  deleteTreballador(empresaId: number, id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${empresaId}/treballador/${id}`);
  }
}
