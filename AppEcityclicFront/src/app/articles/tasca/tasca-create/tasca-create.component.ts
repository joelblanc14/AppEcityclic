import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TascaService } from '../../../services/tasca.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Tasca } from '../../../models/tasca.interface';
import { CommonModule } from '@angular/common';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';

@Component({
  selector: 'app-tasca-create',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './tasca-create.component.html',
  styleUrl: './tasca-create.component.css'
})
export class TascaCreateComponent implements OnInit{

  public tascaForm: FormGroup;
  public empresaId!: number;
  public projecteId!: number;
  public tascaId!: number;

  constructor(
    private fb: FormBuilder,
    private tascaService: TascaService,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar
  ) {
    this.tascaForm = this.fb.group({
      nom: ['', Validators.required],
      estat: ['', Validators.required],
      descripcio: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.empresaId = +this.route.snapshot.paramMap.get('empresaId')!;
    this.projecteId = +this.route.snapshot.paramMap.get('projecteId')!;
  }

  getTasca(): void {
    this.tascaService.getTascaById(this.empresaId, this.projecteId, this.tascaId).subscribe(
      (tasca: Tasca) => {
        this.tascaForm.patchValue(tasca);
      },
      (error) => {
        console.error('Error al obtenir tasca: ', error)
      }
    );
  }

  onSubmit(): void {
    if (this.tascaForm.valid) {
      console.log('Dades de la tasca a crear: ', this.tascaForm.value);
      this.tascaService.createTasca(this.empresaId, this.projecteId, this.tascaForm.value).subscribe(
        (task: Tasca) => {
          console.log('Tasca creada: ', task);
          this.router.navigate(['/empresa', this.empresaId, 'projecte', this.projecteId, 'tasques']);
          this.openSnackBar('Tasca creada correctament!', 'create-snackbar');
        },
        (error) => {
          console.error('Error creant la tasca: ', error);
          this.openSnackBar('Error creant la tasca', 'error-snackbar');
        }
      )
    }
  }

  openSnackBar(message: string, type: string): void {
    const emoji = type === 'error-snackbar' ? '⚠️' : '✅';
    const config = new MatSnackBarConfig();
    config.duration = 3000;
    config.panelClass = [type];

    this.snackBar.open(`${emoji} ${message}`, 'Okay', config);
  }
}
