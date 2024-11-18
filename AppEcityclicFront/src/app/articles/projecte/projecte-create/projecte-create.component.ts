import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProjecteService } from '../../../services/projecte.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';
@Component({
  selector: 'app-projecte-create',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './projecte-create.component.html',
  styleUrl: './projecte-create.component.css'
})
export class ProjecteCreateComponent implements OnInit{

  public projecteForm: FormGroup;
  public empresaId!: number;

  constructor(
    private fb: FormBuilder,
    private projecteService: ProjecteService,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar
  ) {
    this.projecteForm = this.fb.group({
      nom: ['', Validators.required],
      estat: ['', Validators.required],
      descripcio: ['']
    });
  }

  ngOnInit(): void {
    this.empresaId = +this.route.snapshot.paramMap.get('empresaId')!;
  }

  onSubmit(): void {
    if (this.projecteForm.valid) {
      console.log('Datos del proyecto a crear:', this.projecteForm.value);
      this.projecteService.createProjecte(this.empresaId ,this.projecteForm.value).subscribe(
        (response) => {
          console.log('Proyecto creado:', response);
          this.router.navigate(['/empresa', this.empresaId, 'projecte']);
          this.openSnackBar('Projecte creat correctament!', 'create-snackbar');
        },
        (error) => {
          console.error('Error creant el projecte: ', error);
          this.openSnackBar('Error creant el projecte', 'error-snackbar');
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
