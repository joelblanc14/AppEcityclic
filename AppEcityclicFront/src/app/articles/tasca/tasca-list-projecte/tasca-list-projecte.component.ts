import { Component, OnInit } from '@angular/core';
import { TascaService } from '../../../services/tasca.service';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Tasca } from '../../../models/tasca.interface';
import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { DeleteModalComponent } from '../../../shared/delete-modal/delete-modal.component';

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
    });
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
