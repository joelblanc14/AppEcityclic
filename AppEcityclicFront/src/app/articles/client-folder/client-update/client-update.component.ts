import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ClientService } from '../../../services/client.service';
import { Client } from '../../../models/client.interface';
import { CommonModule } from '@angular/common';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';

@Component({
  selector: 'app-client-update',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './client-update.component.html',
  styleUrl: './client-update.component.css'
})
export class ClientUpdateComponent implements OnInit {

  clientForm!: FormGroup;
  clientId!: number;
  empresaId!: number;
  projecteId!: number;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private clientService: ClientService,
    private snackBar: MatSnackBar
  ) {
    this.clientForm = this.fb.group({
      nom: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      adreca: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.clientId = +this.route.snapshot.paramMap.get('clientId')!;
    this.empresaId = +this.route.snapshot.paramMap.get('empresaId')!;
    this.projecteId = +this.route.snapshot.paramMap.get('projecteId')!;
    this.getClient();
  }

  getClient(): void {
    this.clientService.getClientById(this.empresaId, this.clientId).subscribe(
      (client: Client) => {
        console.log('El client es ', client);
        if (client) {
          this.clientForm.patchValue(client);
          console.log('Valor del formulari després de patchValue:', this.clientForm.value);
        } else {
          console.error('Client no trobat.');
        }
      },
      (error) => {
        console.error('Error al obtenir el client:', error);
      }
    );
  }


  ngOnSubmit(): void {
    if (this.clientForm.valid) {
      const updatedClient: Client = {
        ...this.clientForm.value,
        clientId: this.clientId,
        empresa: { empresaId: this.empresaId },
        projecte: { projecteId: this.projecteId }
      };
      this.clientService.updateClient(this.empresaId, this.clientId, updatedClient).subscribe(
        () => {
          this.router.navigate(['/empresa', this.empresaId, 'projecte', this.projecteId, 'client']);
          this.openSnackBar('Client actualitzat correctament!', 'update-snackbar');
        },
        (error) => {
          console.error('Error al actualitzar el client:', error);
          this.openSnackBar('Error actualitzant client', 'error-snackbar');
        }
      );
    }
  }

  openSnackBar(message: string, type: string): void {
    const emoji = type === 'error-snackbar' ? '❌' : '👍';
    const config = new MatSnackBarConfig();
    config.duration = 3000;
    config.panelClass = [type];

    this.snackBar.open(`${emoji} ${message}`, 'Okay', config);
  }
}
