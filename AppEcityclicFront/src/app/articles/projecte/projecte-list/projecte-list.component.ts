import { Component, OnInit } from '@angular/core';
import { Projecte } from '../../../models/projecte.interface';
import { ProjecteService } from '../../../services/projecte.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

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

  constructor(
    private projecteService: ProjecteService,
    private route: ActivatedRoute,
    private router: Router
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
      },
      (error) => {
        console.error('Error carregant els projectes!', error);
      }
    );
  }

  deleteProject(projecteId: number): void {
    const confirmed = window.confirm('Estàs segur que vols eliminar aquest projecte?');

    if (confirmed) {
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
}
