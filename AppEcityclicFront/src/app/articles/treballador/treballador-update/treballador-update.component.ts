import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { TreballadorService } from '../../../services/treballador.service';
import { Treballador } from '../../../models/treballador.interface';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-treballador-update',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './treballador-update.component.html',
  styleUrls: ['./treballador-update.component.css']
})
export class TreballadorUpdateComponent implements OnInit {

  treballadorForm!: FormGroup;
  treballadorId!: number;
  empresaId!: number;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private treballadorService: TreballadorService
  ) {
    this.treballadorForm = this.fb.group({
      nom: ['', Validators.required],
      posicio: ['', Validators.required],
      salari: ['', [Validators.required, Validators.min(0)]],
      email: ['', [Validators.required, Validators.email]]
    });
  }

  ngOnInit(): void {
    this.treballadorId = +this.route.snapshot.paramMap.get('treballadorId')!;
    this.empresaId = +this.route.snapshot.paramMap.get('empresaId')!;
    this.getTreballador();
  }

  getTreballador(): void {
    this.treballadorService.getTreballadorById(this.empresaId, this.treballadorId).subscribe(
      (treballador: Treballador) => {
        this.treballadorForm.patchValue(treballador);
      },
      (error) => {
        console.error('Error al obtenir el treballador:', error);
      }
    );
  }

  ngOnSubmit(): void {
    if (this.treballadorForm.valid) {
      const updatedTreballador: Treballador = {
        ...this.treballadorForm.value,
        treballadorId: this.treballadorId,
        empresa: { empresaId: this.empresaId }
      };
      this.treballadorService.updateTreballador(this.empresaId, this.treballadorId, updatedTreballador).subscribe(
        () => {
          this.router.navigate(['/empresa', this.empresaId, 'treballadors']);
        },
        (error) => {
          console.error('Error al actualitzar el treballador:', error);
        }
      );
    }
  }
}
