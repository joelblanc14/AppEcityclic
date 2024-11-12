import { Component, OnInit } from '@angular/core';
import { Treballador } from '../../../models/treballador.interface';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { TreballadorService } from '../../../services/treballador.service';
import { MatDialog } from '@angular/material/dialog';
import { DeleteModalComponent } from '../../../shared/delete-modal/delete-modal.component';

@Component({
  selector: 'app-treballador-list',
  standalone: true,
  imports: [RouterModule, ReactiveFormsModule, CommonModule],
  templateUrl: './treballador-list.component.html',
  styleUrl: './treballador-list.component.css'
})
export class TreballadorListComponent implements OnInit{

  public treballadors: Treballador[] = [];
  public empresaId!: number;

  constructor(private treballadorService: TreballadorService, private route: ActivatedRoute, private router: Router, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.empresaId = +this.route.snapshot.paramMap.get('empresaId')!;
    this.loadTreballadors();
    console.log('El id de la empresa es ', this.empresaId);
  }

  loadTreballadors(): void {
    this.treballadorService.getTreballadorsByEmpresaId(this.empresaId).subscribe(
      (data: Treballador[]) => {
        this.treballadors = data;
      },
      (error) => {
        console.error('Error al carregar treballadors', error);
      }
    );
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
    },
    (error) => {
      console.log('Error al eliminar treballador', error);
      }
    );
  }
}
