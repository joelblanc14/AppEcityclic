export interface Projecte {
  projecteId: number;
  nom: string;
  estat: string;
  descripcio?: string;
  empresaId: number;
  clientId?: number;
  dataInici: string;
  dataFi?: string;
}
