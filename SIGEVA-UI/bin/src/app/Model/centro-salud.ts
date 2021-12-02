import {Vacuna} from "./vacuna";

export interface CentroSalud{

	nombreCentro : string;
	numVacunasDisponibles : number;
  	direccion: string;
  	vacuna : Vacuna;
	id : string;
}

export class CentroSalud {

	constructor(direccion: string, nombreCentro : string,numVacunasDisponibles : number, vacuna:Vacuna, id: string){
    this.nombreCentro = nombreCentro;
	  this.numVacunasDisponibles = numVacunasDisponibles;
    this.direccion = direccion;
    this.vacuna = vacuna;
    this.id = id;

	}
}
