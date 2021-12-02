export interface Vacuna {
  id: string | undefined;
  nombre: string;
  diasEntreDosis: number;
  numDosis: number;
}

export class Vacuna {
  id: string | undefined;
  nombre: string;
  diasEntreDosis: number;
  numDosis: number;

  constructor(nombre: string, diasEntreDosis: number, numDosis: number, id?: string) {
    this.nombre = nombre;
    this.diasEntreDosis = diasEntreDosis;
    this.numDosis = numDosis;
    this.id = id;
  }
}
