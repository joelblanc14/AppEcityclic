import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { EmpresaService } from '../../../services/empresa.service';
import { Router, RouterLink } from '@angular/router';
import { Empresa } from '../../../models/empresa.interface';
import { CommonModule } from '@angular/common';
import { cifValidator } from '../../../shared/validators/cif.validator';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';
import { TranslateModule } from '@ngx-translate/core';
@Component({
  selector: 'app-empresa-create',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterLink, TranslateModule],
  templateUrl: './empresa-create.component.html',
  styleUrl: './empresa-create.component.css'
})
export class EmpresaCreateComponent {
  public empresaForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private empresaService: EmpresaService,
    private router: Router,
    private snackBar: MatSnackBar
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
        this.router.navigate(['/empresa']);
        this.openSnackBar('Empresa creada correctament!', 'create-snackbar');
      });
    } else {
      console.error('Error al crear empresa');
      this.openSnackBar("Error al crear l'empresa", 'error-snackbar');
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
