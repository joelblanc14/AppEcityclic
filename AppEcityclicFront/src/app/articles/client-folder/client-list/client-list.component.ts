import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Client } from '../../../models/client.interface';
import { ClientService } from '../../../services/client.service';
import { MatDialog } from '@angular/material/dialog';
import { DeleteModalComponent } from '../../../shared/delete-modal/delete-modal.component';

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

    constructor(private clientService: ClientService, private router: Router, private route: ActivatedRoute, private dialog: MatDialog){}


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

    openDeleteModal(id: number): void {
      const dialogRef = this.dialog.open(DeleteModalComponent, {
        data: { message: 'Estàs segur que vols eliminar aquest client?' }
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          this.deleteClient(id);
        }
      });
    }

    deleteClient(id: number): void {
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
