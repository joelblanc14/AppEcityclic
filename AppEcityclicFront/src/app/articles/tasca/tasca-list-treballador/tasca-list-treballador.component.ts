import { Component, OnInit } from '@angular/core';
import { Tasca } from '../../../models/tasca.interface';
import { TascaService } from '../../../services/tasca.service';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-tasca-list-treballador',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './tasca-list-treballador.component.html',
  styleUrl: './tasca-list-treballador.component.css'
})
export class TascaListTreballadorComponent implements OnInit {
  tasques: Tasca[] = [];
  treballadorId!: number;
  empresaId!: number;

  constructor(
    private tascaService: TascaService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.empresaId = +this.route.snapshot.paramMap.get('empresaId')!;
    this.treballadorId = +this.route.snapshot.paramMap.get('treballadorId')!;

    this.getTasques();
  }

  getTasques(): void {
    this.tascaService.getTasquesByTreballadorId(this.empresaId, this.treballadorId).subscribe(tasques => {
      this.tasques = tasques;
    });
  }
}
