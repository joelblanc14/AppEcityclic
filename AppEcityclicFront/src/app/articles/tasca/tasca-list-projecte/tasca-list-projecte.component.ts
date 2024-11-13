import { Component, OnInit } from '@angular/core';
import { TascaService } from '../../../services/tasca.service';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Tasca } from '../../../models/tasca.interface';
import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { DeleteModalComponent } from '../../../shared/delete-modal/delete-modal.component';
import $ from 'jquery';
import 'datatables.net-bs5';

@Component({
  selector: 'app-tasca-list-projecte',
  standalone: true,
  imports: [CommonModule, RouterModule],
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
    private dialog: MatDialog
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
        order: [[0, 'asc']],
        language: {
          url: '//cdn.datatables.net/plug-ins/1.13.7/i18n/ca.json'
        }
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
    this.tascaService.deleteTasca(this.empresaId, this.projecteId, tascaId).subscribe(() => {
      this.tasques = this.tasques.filter(tasca => tasca.tascaId !== tascaId);
    });
  }
}
