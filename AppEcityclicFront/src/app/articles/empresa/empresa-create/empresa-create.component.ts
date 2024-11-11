import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { EmpresaService } from '../../../services/empresa.service';
import { Router, RouterLink } from '@angular/router';
import { Empresa } from '../../../models/empresa.interface';
import { CommonModule } from '@angular/common';
import { cifValidator } from '../../../shared/validators/cif.validator';
@Component({
  selector: 'app-empresa-create',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterLink],
  templateUrl: './empresa-create.component.html',
  styleUrl: './empresa-create.component.css'
})
export class EmpresaCreateComponent {
  public empresaForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private empresaService: EmpresaService,
    private router: Router
  ) {
    this.empresaForm = this.fb.group({
      nom: ['', Validators.required],
      adreca: ['', Validators.required],
      cif: ['', [Validators.required, cifValidator()]]
    });
  }

  onSubmit(): void {
    if (this.empresaForm.valid) {
      const novaEmpresa: Empresa = this.empresaForm.value;
      this.empresaService.createEmpresa(novaEmpresa).subscribe(() => {
        this.router.navigate(['/empreses']);
      });
    }
  }
}
