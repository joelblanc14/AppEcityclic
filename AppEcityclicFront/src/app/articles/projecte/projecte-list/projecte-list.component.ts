import { Component, OnInit } from '@angular/core';
import { Projecte } from '../../../models/projecte.interface';
import { ProjecteService } from '../../../services/projecte.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { DeleteModalComponent } from '../../../shared/delete-modal/delete-modal.component';
import $ from 'jquery';
import 'datatables.net-bs5';

@Component({
  selector: 'app-projecte-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './projecte-list.component.html',
  styleUrl: './projecte-list.component.css'
})
export class ProjecteListComponent implements OnInit {

  public empresaId!: number;
  public projectes: Projecte[] = [];
  datatable: any;

  constructor(
    private projecteService: ProjecteService,
    private route: ActivatedRoute,
    private router: Router,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.empresaId = +this.route.snapshot.paramMap.get('empresaId')!;
    this.getProjectes();
  }

  getProjectes(): void {
    this.projecteService.getProjectesByEmpresaId(this.empresaId).subscribe(
      (data) => {
        console.log('Projectes recuperats:', data);
        this.projectes = data.sort((a, b) => a.projecteId - b.projecteId);
        this.initializeDataTable();
      },
      (error) => {
        console.error('Error carregant els projectes!', error);
      }
    );
  }

  initializeDataTable(): void {
    setTimeout(() => {
      this.datatable = $('#projecteTable').DataTable({
        pageLength: 10,
        order: [[0, 'asc']]
      });
    }, 1);
  }

  openDeleteModal(id: number) {
    const dialogRef = this.dialog.open(DeleteModalComponent, {
      data: { message: 'Estàs segur que vols eliminar aquest projecte?' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleteProject(id);
      }
    });
  }

  deleteProject(projecteId: number): void {
    this.projecteService.deleteProjecte(this.empresaId, projecteId).subscribe(
      () => {
        console.log('Projecte eliminat correctament');
        this.getProjectes();
      },
      (error) => {
        console.error('Error borrant el projecte: ', error);
      }
    );
  }
}
