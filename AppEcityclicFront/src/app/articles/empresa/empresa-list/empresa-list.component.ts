import { Component, OnInit } from '@angular/core';
import { Empresa } from '../../../models/empresa.interface';
import { EmpresaService } from '../../../services/empresa.service';
import { Router, RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DeleteModalComponent } from '../../../shared/delete-modal/delete-modal.component';
import { MatDialog } from '@angular/material/dialog';
import $ from 'jquery';
import 'datatables.net-bs5';

@Component({
  selector: 'app-empresa-list',
  standalone: true,
  imports: [RouterModule, ReactiveFormsModule, CommonModule],
  templateUrl: './empresa-list.component.html',
  styleUrl: './empresa-list.component.css'
})
export class EmpresaListComponent implements OnInit {

  public empreses: Empresa[] = [];
  itemToDelete: number | null = null;
  dataTable: any;

  constructor(private empresaService: EmpresaService,
    private router: Router,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.fetchEmpreses();
  }

  fetchEmpreses() {
    this.empresaService.getEmpreses().subscribe((data: Empresa[]) => {
      this.empreses = data.sort((a, b) => a.empresaId - b.empresaId);
      this.initializeDataTable();
    });
  }

  initializeDataTable() {
    setTimeout(() => {
      this.dataTable = $('#empresaTable').DataTable({
        pageLength: 10,
        order: [[0, 'asc']], // L'element 0 és el ID de l'empresa
        language: {
          url: '//cdn.datatables.net/plug-ins/1.13.7/i18n/ca.json' // URL de la llibreria de traducció en català
        }
      });
    }, 1); // 1ms per assegurar-nos que el DOM està completament carregat
  }

  openDeleteModal(id: number): void {
    const dialogRef = this.dialog.open(DeleteModalComponent, {
      width: 'auto',
      data: { message: 'Estàs segur que vols eliminar aquesta empresa?' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleteEmpresa(id);
      }
    });
  }

  deleteEmpresa(id: number): void {
    this.empresaService.deleteEmpresa(id).subscribe(() => {
      this.empreses = this.empreses.filter(empresa => empresa.empresaId !== id);
    });
  }
}
