import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Inject, Output } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';

@Component({
  selector: 'app-delete-modal',
  standalone: true,
  imports: [CommonModule, MatDialogModule],
  templateUrl: './delete-modal.component.html',
  styleUrl: './delete-modal.component.css'
})
export class DeleteModalComponent {
  // Emissor d'event per confirmar l'eliminació
  @Output() confirmDelete = new EventEmitter<void>();

  constructor(
    public dialogRef: MatDialogRef<DeleteModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { message: string }
  ){}

  onConfirmDelete(): void {
    this.confirmDelete.emit();
    this.dialogRef.close(true); // Tanca el modal i envia el resultat
  }

  onCancel(): void {
    this.dialogRef.close(false); // Tanca el modal sense confirmar
  }

}
