import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Empresa } from '../models/empresa.interface';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EmpresaService {
  private apiUrl = `${environment.apiUrl}/api/empresa`;

  constructor(private http:HttpClient) { }

  getEmpreses(): Observable<Empresa[]> {
    return this.http.get<Empresa[]>(this.apiUrl);
  }

  getEmpresaById(id: number): Observable<Empresa> {
    return this.http.get<Empresa>(`${this.apiUrl}/${id}`);
  }

  createEmpresa(empresa: Empresa): Observable<Empresa> {
    return this.http.post<Empresa>(this.apiUrl, empresa);
  }


  updateEmpresa(empresa: Empresa, id: number): Observable<Empresa> {
    return this.http.put<Empresa>(`${this.apiUrl}/${id}`, empresa);
  }

  deleteEmpresa(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
