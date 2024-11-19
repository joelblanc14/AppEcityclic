import { Component, OnInit } from '@angular/core';
import { Treballador } from '../../../models/treballador.interface';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { TreballadorService } from '../../../services/treballador.service';
import { MatDialog } from '@angular/material/dialog';
import { DeleteModalComponent } from '../../../shared/delete-modal/delete-modal.component';
import $ from 'jquery';
import 'datatables.net-bs5';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-treballador-list',
  standalone: true,
  imports: [RouterModule, ReactiveFormsModule, CommonModule, TranslateModule],
  templateUrl: './treballador-list.component.html',
  styleUrl: './treballador-list.component.css'
})
export class TreballadorListComponent implements OnInit{

  public treballadors: Treballador[] = [];
  public empresaId!: number;
  datatable: any;

  constructor(private treballadorService: TreballadorService,
    private route: ActivatedRoute,
    private router: Router,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.empresaId = +this.route.snapshot.paramMap.get('empresaId')!;
    this.loadTreballadors();
    console.log('El id de la empresa es ', this.empresaId);
  }

  loadTreballadors(): void {
    this.treballadorService.getTreballadorsByEmpresaId(this.empresaId).subscribe(
      (data: Treballador[]) => {
        this.treballadors = data;
        this.initializeDataTable();
      },
      (error) => {
        console.error('Error al carregar treballadors', error);
      }
    );
  }

  initializeDataTable(): void {
    setTimeout(() => {
      this.datatable = $('#treballadorTable').DataTable({
        pageLength: 10,
        order: [[0, 'asc']]
      });
    }, 1);
  }

  openDeleteModal(id: number): void {
    const dialogRef = this.dialog.open(DeleteModalComponent, {
      data: { message: 'Estàs segur que vols eliminar aquest treballador?' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleteTreballador(id);
      }
    });
  }

  deleteTreballador(id: number): void {
    this.treballadorService.deleteTreballador(this.empresaId ,id).subscribe(
      () => {
      this.treballadors = this.treballadors.filter(t => t.treballadorId !== id);
      console.log('Treballador eliminat correctament!');
      this.openSnackBar('Treballador eliminat correctament!', 'delete-snackbar');
    },
    (error) => {
      console.log('Error al eliminar treballador', error);
      this.openSnackBar('Error eliminant treballador', 'error-snackbar');
      }
    );
  }

  openSnackBar(message: string, type: string): void {
    const emoji = type === 'error-snackbar' ? '⚠️' : '🗑️';
    const config = new MatSnackBarConfig();
    config.duration = 3000;
    config.panelClass = [type];

    this.snackBar.open(`${emoji} ${message}`, 'Okay', config);
  }
}
