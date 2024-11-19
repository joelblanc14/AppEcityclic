import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { EmpresaService } from '../../../services/empresa.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Empresa } from '../../../models/empresa.interface';
import { CommonModule } from '@angular/common';
import { cifValidator } from '../../../shared/validators/cif.validator';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-empresa-update',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterModule, TranslateModule],
  templateUrl: './empresa-update.component.html',
  styleUrl: './empresa-update.component.css'
})
export class EmpresaUpdateComponent implements OnInit{

  public empresaForm: FormGroup;
  public empresaId: number = 0;

  constructor(
    private fb: FormBuilder,
    private empresaService: EmpresaService,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar
  ) {
    this.empresaForm = this.fb.group({
      nom: ['', Validators.required],
      adreca: ['', Validators.required],
      cif: ['', [Validators.required, cifValidator()]]
    });
  }

  ngOnInit(): void {
    this.empresaId = +this.route.snapshot.paramMap.get('id')!;
    this.fetchEmpresa();
  }

  fetchEmpresa(): void {
    this.empresaService.getEmpresaById(this.empresaId).subscribe((data: Empresa) => {
      this.empresaForm.patchValue(data);
    })
  }

  ngOnSubmit(): void {
    if (this.empresaForm.valid) {
      const updatedEmpresa: Empresa = {...this.empresaForm.value, empresaId: this.empresaId};
      this.empresaService.updateEmpresa(updatedEmpresa, this.empresaId).subscribe(() => {
        this.router.navigate(['/empreses'])
        this.openSnackBar('Empresa actualizada correctament!', 'update-snackbar');
      })
    } else {
      console.error('Error creant empresa');
      this.openSnackBar('Error creant empresa', 'error-snackbar');
    }
  }

  openSnackBar(message: string, type: string): void {
    const emoji = type === 'error-snackbar' ? '⚠️' : '👍';
    const config = new MatSnackBarConfig();
    config.duration = 3000;
    config.panelClass = [type];

    this.snackBar.open(`${emoji} ${message}`, 'Okay', config);
  }
}
