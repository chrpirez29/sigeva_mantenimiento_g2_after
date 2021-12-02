import {CupoCitas} from "./cupo-citas";
import {Paciente} from "./paciente";

export interface CitaConObjetos{
  cupo : CupoCitas;
  dosis : number;
  uuidCita : string | undefined;
}


export class CitaConObjetos {
  cupo : CupoCitas;
  dosis : number;
  paciente : Paciente;
  uuidCita : string | undefined;

  constructor(cupo:CupoCitas, dosis:number, paciente:Paciente, uuidCita?: string) {
    this.cupo = cupo;
    this.dosis = dosis;
    this.paciente = paciente;
    this.uuidCita = String(uuidCita);
  }

}
