import { Component, OnInit } from '@angular/core';
import { Treballador } from '../../../models/treballador.interface';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { TreballadorService } from '../../../services/treballador.service';

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

  constructor(private treballadorService: TreballadorService, private route: ActivatedRoute, private router: Router) {}

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

  deleteTreballador(id: number): void {
    const confirmed = window.confirm('Estàs segur que vols borrar aquest treballador?');

    if (confirmed) {
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
}
