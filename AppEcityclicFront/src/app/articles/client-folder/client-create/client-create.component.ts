import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ClientService } from '../../../services/client.service';
import { Client } from '../../../models/client.interface';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-client-create',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './client-create.component.html',
  styleUrl: './client-create.component.css'
})
export class ClientCreateComponent implements OnInit{

  public clientsForm!: FormGroup;
  public empresaId!: number;
  public projecteId!: number;

  constructor(
    private fb: FormBuilder,
    private clientService: ClientService,
    private route: ActivatedRoute,
    private router: Router
  ){}

  ngOnInit(): void {
    this.empresaId = +this.route.snapshot.paramMap.get('empresaId')!;
    this.projecteId = +this.route.snapshot.paramMap.get('projecteId')!;
    this.initForm();
  }

  initForm(): void {
    this.clientsForm = this.fb.group({
      nom: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      adreca: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.clientsForm.valid) {
      const newClient: Client = {
        ...this.clientsForm.value,
        empresaId: this.empresaId,
        projecteId: this.projecteId
      };

      this.clientService.createClient(this.empresaId, this.projecteId, newClient).subscribe(
        () => {
          console.log('Client creat correctament');
          this.router.navigate(['/empresa', this.empresaId, 'projecte', this.projecteId, 'client']);
        },
        (error) => {
          console.error('Error al crear client', error);
        }
      );
    }
  }
}
