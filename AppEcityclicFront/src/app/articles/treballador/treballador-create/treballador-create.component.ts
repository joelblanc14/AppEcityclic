import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TreballadorService } from '../../../services/treballador.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Treballador } from '../../../models/treballador.interface';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-treballador-create',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './treballador-create.component.html',
  styleUrl: './treballador-create.component.css'
})
export class TreballadorCreateComponent implements OnInit{

  public treballadorForm!: FormGroup;
  public empresaId!: number;

  constructor(
    private fb: FormBuilder,
    private treballadorService: TreballadorService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.empresaId = +this.route.snapshot.paramMap.get('empresaId')!;
    this.initForm();
  }

  initForm(): void {
    this.treballadorForm = this.fb.group({
      nom: ['', Validators.required],
      posicio: ['', Validators.required],
      salari: ['', [Validators.required, Validators.min(0)]],
      email: ['', [Validators.required, Validators.email]]
    });
  }

  onSubmit(): void {
    if (this.treballadorForm.valid) {
      const newTreballador: Treballador = {
        ...this.treballadorForm.value,
        empresaId: this.empresaId
      };

      this.treballadorService.createTreballador(this.empresaId, newTreballador).subscribe(
        () => {
          console.log('Treballador creat correctament');
          this.router.navigate(['/empresa', this.empresaId, 'treballadors']);
        },
        (error) => {
          console.error('Error al crear treballador', error);
        }
      );
    }
  }
}
