import { Component, OnInit } from '@angular/core';
import { TascaService } from '../../../services/tasca.service';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Tasca } from '../../../models/tasca.interface';
import { CommonModule } from '@angular/common';

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

  constructor(
    private tascaService: TascaService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.empresaId = +this.route.snapshot.paramMap.get('empresaId')!;
    this.projecteId = +this.route.snapshot.paramMap.get('projecteId')!;

    this.getTasques();
  }

  getTasques(): void {
    this.tascaService.getTasquesByProjecteId(this.empresaId, this.projecteId).subscribe(tasques => {
      this.tasques = tasques;
    });
  }

  deleteTasca(tascaId: number): void {
    if (confirm('Estàs segur que vols borrar aquesta tasca?')) {
      this.tascaService.deleteTasca(this.empresaId, this.projecteId, tascaId).subscribe(() => {
        this.tasques = this.tasques.filter(tasca => tasca.tascaId !== tascaId);
      });
    }
  }
}
