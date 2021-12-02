import { CentroSalud } from "./centro-salud";

export interface CupoCitas {
  uuid : string;
  centroSalud : CentroSalud;
  fechaYHoraInicio : Date;
 }


export class CupoCitas {
  uuid : string;
  centroSalud : CentroSalud;
  fechaYHoraInicio : Date;

  constructor(uuid : string, centroSalud : CentroSalud, fechaYHoraInicio : Date){
    this.uuid = uuid;
    this.centroSalud = centroSalud;
    this.fechaYHoraInicio = fechaYHoraInicio;
  }
}
