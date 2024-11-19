import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Client } from '../models/client.interface';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private apiUrl = `http://${environment.apiUrl}/api/empresa`;

  constructor(private http: HttpClient) { }

  getClientsByProjecte(empresaId: number, projecteId: number): Observable<Client[]> {
    return this.http.get<Client[]>(`${this.apiUrl}/${empresaId}/client/${projecteId}`);
  }

  getClientById(empresaId: number, id: number): Observable<Client> {
    return this.http.get<Client>(`${this.apiUrl}/${empresaId}/client/${id}/get`);
  }

  createClient(empresaId: number, projecteId: number, client: Client): Observable<Client> {
    return this.http.post<Client>(`${this.apiUrl}/${empresaId}/client/${projecteId}`, client);
  }

  updateClient(empresaId: number, id: number, client: Client): Observable<Client> {
    return this.http.put<Client>(`${this.apiUrl}/${empresaId}/client/${id}`, client);
  }

  deleteClient(empresaId: number, id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${empresaId}/client/${id}`);
  }
}
