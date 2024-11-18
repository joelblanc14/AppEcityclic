import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Projecte } from '../../../models/projecte.interface';
import { ProjecteService } from '../../../services/projecte.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';
@Component({
  selector: 'app-projecte-update',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './projecte-update.component.html',
  styleUrl: './projecte-update.component.css'
})
export class ProjecteUpdateComponent implements OnInit{

  public projecteForm: FormGroup;
  public empresaId!: number;
  public projecteId!: number;

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
    this.projecteId = +this.route.snapshot.paramMap.get('projecteId')!;
    this.getProjectes();
  }

  getProjectes(): void {
    this.projecteService.getProjecteById(this.empresaId, this.projecteId).subscribe(
      (data) => {
        this.projecteForm.patchValue(data);
      },
      (error) => {
        console.error('Error carregant el projecte')
      }
    );
  }

  onSubmit(): void {
    this.projecteService.updateProjecte(this.empresaId, this.projecteId, this.projecteForm.value).subscribe(
      () => {
        console.log('Projecte actualitzat correctament!');
        this.router.navigate(['/empresa', this.empresaId, 'projecte']);
        this.openSnackBar('Projecte actualitzat correctament!', 'update-snackbar');
      },
      (error) => {
        console.error('Error al actualitzar el projecte', error);
        this.openSnackBar('Error actualitzant projecte', 'error-snackbar');
      }
    )
  }

  openSnackBar(message: string, type: string): void {
    const emoji = type === 'error-snackbar' ? '⚠️' : '👍';
    const config = new MatSnackBarConfig();
    config.duration = 3000;
    config.panelClass = [type];

    this.snackBar.open(`${emoji} ${message}`, 'Okay', config);
  }
}
