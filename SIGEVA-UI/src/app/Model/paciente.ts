import {UsuarioConObjetos} from "./Usuario-con-objetos";
import {Rol} from "./rol";
import {CentroSalud} from "./centro-salud";

export interface Paciente {
  numDosisAplicadas: number;

  aplicarDosis(): void;
}


export class Paciente extends UsuarioConObjetos {
  numDosisAplicadas: number;

  constructor(rol: Rol, centroSalud: CentroSalud, username: string, correo: string, hashPassword: string, dni: string,
              nombre: string, apellidos: string, fechaNacimiento: string, imagen: string,
              numDosisAplicadas: number, idUsuario?: string) {
    super(rol, centroSalud, username, correo, hashPassword, dni, nombre, apellidos, fechaNacimiento, imagen, idUsuario);
    this.numDosisAplicadas = numDosisAplicadas;
  }

  aplicarDosis() {
    this.numDosisAplicadas = this.numDosisAplicadas + 1;
  }


}

