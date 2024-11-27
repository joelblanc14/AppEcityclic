import { Component, OnInit } from '@angular/core';
import { TascaService } from '../../../services/tasca.service';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Tasca } from '../../../models/tasca.interface';
import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { DeleteModalComponent } from '../../../shared/delete-modal/delete-modal.component';
import $ from 'jquery';
import 'datatables.net-bs5';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';
import { TranslateModule } from '@ngx-translate/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-tasca-list-projecte',
  standalone: true,
  imports: [CommonModule, RouterModule, TranslateModule],
  templateUrl: './tasca-list-projecte.component.html',
  styleUrl: './tasca-list-projecte.component.css'
})
export class TascaListProjecteComponent implements OnInit {
  tasques: Tasca[] = [];
  projecteId!: number;
  empresaId!: number;
  datatable: any;

  constructor(
    private tascaService: TascaService,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private snackBar: MatSnackBar,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.empresaId = +this.route.snapshot.paramMap.get('empresaId')!;
    this.projecteId = +this.route.snapshot.paramMap.get('projecteId')!;

    this.getTasques();
  }

  getTasques(): void {
    this.tascaService.getTasquesByProjecteId(this.empresaId, this.projecteId).subscribe(tasques => {
      this.tasques = tasques;
      this.initializeDataTable();
    });
  }

  initializeDataTable(): void {
    setTimeout(() => {
      this.datatable = $('#tascaTable').DataTable({
        pageLength: 10,
        order: [[0, 'asc']]
      });
    }, 1);
  }

  openDeleteModal(id: number) {
    const dialogRef = this.dialog.open(DeleteModalComponent, {
      data: { message: 'Estàs segur que vols eliminar aquesta tasca?' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleteTasca(id);
      }
    });
  }

  deleteTasca(tascaId: number): void {
    this.tascaService.deleteTasca(this.empresaId, this.projecteId, tascaId).subscribe(
      () => {
      this.tasques = this.tasques.filter(tasca => tasca.tascaId !== tascaId);
    },
    (error) => {
      console.log('Error eliminant tasca', error);
      this.openSnackBar('Error eliminant tasca', 'error-snackbar');
    });
    this.openSnackBar('Tasca eliminada correctament!', 'delete-snackbar');
  }

  openSnackBar(message: string, type: string): void {
    const emoji = type === 'error-snackbar' ? '⚠️' : '🗑️';
    const config = new MatSnackBarConfig();
    config.duration = 3000;
    config.panelClass = [type];

    this.snackBar.open(`${emoji} ${message}`, 'Okay', config);
  }
}
