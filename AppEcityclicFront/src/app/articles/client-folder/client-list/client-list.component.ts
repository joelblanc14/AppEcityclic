import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Client } from '../../../models/client.interface';
import { ClientService } from '../../../services/client.service';

@Component({
  selector: 'app-client-list',
  standalone: true,
  imports: [RouterModule, ReactiveFormsModule, CommonModule],

  templateUrl: './client-list.component.html',
  styleUrl: './client-list.component.css'
})
export class ClientListComponent  implements OnInit{

    public clients: Client[] = [];
    public empresaId: number = 0;
    public projecteId: number = 0;

    constructor(private clientService: ClientService, private router: Router, private route: ActivatedRoute){}


    ngOnInit(): void {
      this.empresaId = +this.route.snapshot.paramMap.get('empresaId')!;
      this.projecteId = +this.route.snapshot.paramMap.get('projecteId')!;
      this.loadClients();
    }

    loadClients(): void {
      this.clientService.getClientsByProjecte(this.empresaId, this.projecteId).subscribe(
        (clients: Client[]) => {
            console.log('Clients received:', clients);
            this.clients = clients;
        },
        (error) => {
            console.error('Error fetching clients:', error);
        }
    );
    }
    deleteClient(id: number): void {
      const confirmed = window.confirm('Estàs segur que vols borrar aquest client?');

      if (confirmed) {
        this.clientService.deleteClient(this.empresaId, id).subscribe(
          () => {
          this.clients = this.clients.filter(client => client.clientId !== id);
          this.loadClients();
          console.log('Client eliminat correctament!');
        },
        (error: any) => {
          console.error('Error al eliminar client', error);
        }
        );
      }
    }
}
