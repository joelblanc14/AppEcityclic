import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Client } from '../models/client.interface';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private apiUrl = `http://${environment.apiUrl}/api/client`;

  constructor(private http: HttpClient) { }

  getClientsByProjecte(projecteId: number): Observable<Client[]> {
    return this.http.get<Client[]>(`${this.apiUrl}/${projecteId}`);
  }

  getClientById(id: number): Observable<Client> {
      return this.http.get<Client>(`${this.apiUrl}/${id}/get`);
  }

  createClient(projecteId: number, client: Client): Observable<Client> {
      return this.http.post<Client>(`${this.apiUrl}/${projecteId}`, client);
  }

  updateClient(id: number, client: Client): Observable<Client> {
      return this.http.put<Client>(`${this.apiUrl}/${id}`, client);
  }

  deleteClient(id: number): Observable<void> {
      return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
