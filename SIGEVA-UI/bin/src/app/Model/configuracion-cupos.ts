
export interface ConfiguracionCupos{
    duracionMinutos : number;
    numeroPacientes : number;
    duracionJornadaHoras : number;
    duracionJornadaMinutos : number;
    fechaInicio : string;
}

export class ConfiguracionCupos {
    duracionMinutos : number;
    numeroPacientes : number;
    duracionJornadaHoras : number;
    duracionJornadaMinutos : number;
    fechaInicio : string;

  constructor(duracionMinutos : number, numeroPacientes : number, duracionJornadaHoras : number,
              duracionJornadaMinutos : number, fechaInicio : string){

    this.numeroPacientes = numeroPacientes;
    this.duracionMinutos = duracionMinutos;
    this.duracionJornadaHoras = duracionJornadaHoras;
    this.duracionJornadaMinutos = duracionJornadaMinutos;
    this.fechaInicio = fechaInicio;


  }
}
