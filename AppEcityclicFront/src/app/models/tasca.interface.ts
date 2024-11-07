export interface Tasca {
  tascaId: number;
  nom: string;
  descripcio?: string;
  estat: string;
  treballadorId?: number;
  projecteId: number;
  dataInici: string;
  dataFi?: string;
}
