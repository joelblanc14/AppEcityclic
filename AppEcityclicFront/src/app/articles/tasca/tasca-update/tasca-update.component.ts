import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { TascaService } from '../../../services/tasca.service';
import { Tasca } from '../../../models/tasca.interface';
import { Treballador } from '../../../models/treballador.interface';
import { Projecte } from '../../../models/projecte.interface';
import { TreballadorService } from '../../../services/treballador.service';
import { ProjecteService } from '../../../services/projecte.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-tasca-update',
  standalone: true,
  imports: [[ReactiveFormsModule, RouterModule, FormsModule, CommonModule]],
  templateUrl: './tasca-update.component.html',
  styleUrl: './tasca-update.component.css'
})
export class TascaUpdateComponent implements OnInit {

  public tascaForm: FormGroup;
  public empresaId!: number;
  public projecteId!: number;
  public tascaId!: number;

  constructor(
    private fb: FormBuilder,
    private tascaService: TascaService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.tascaForm = this.fb.group({
      nom: [''],
      descripcio: [''],
      estat: ['']
    });
  }

  ngOnInit(): void {
    this.empresaId = +this.route.snapshot.paramMap.get('empresaId')!;
    this.projecteId = +this.route.snapshot.paramMap.get('projecteId')!;
    this.tascaId = +this.route.snapshot.paramMap.get('tascaId')!;

    this.tascaService.getTascaById(this.empresaId, this.projecteId, this.tascaId).subscribe((tasca: Tasca) => {
      this.tascaForm.patchValue(tasca);
    });
  }



  onSubmit(): void {
    if (this.tascaForm.valid) {
      this.tascaService.updateTasca(this.empresaId, this.projecteId, this.tascaId, this.tascaForm.value).subscribe(
        (tasca: Tasca) => {
          console.log('Tasca actualitzada amb èxit:', tasca);
          this.router.navigate(['/empresa', this.empresaId, 'projecte', this.projecteId, 'tasques']);
        },
        (error) => {
          console.error('Error actualitzant la tasca: ', error);
        }
      );
    } else {
      console.warn('El formulari no és vàlid');
      console.log('Nom:', this.tascaForm.get('nom')?.status);
      console.log('Descripcio:', this.tascaForm.get('descripcio')?.status);
      console.log('Estat:', this.tascaForm.get('estat')?.status);
      console.log('Treballador:', this.tascaForm.get('treballador')?.status);
      console.log('Projecte:', this.tascaForm.get('projecte')?.status);
    }
  }
}
