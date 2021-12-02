import {CentroSalud} from "./centro-salud";
import {Rol} from "./rol";

export interface UsuarioConObjetos {
  rol: Rol;
  centroSalud: CentroSalud;
  username: string;
  correo: string;
  hashPassword:string;
  dni:string;
  nombre:string;
  apellidos:string;
  fechaNacimiento:string;
  imagen:string;
  idUsuario: string | undefined;
}

export class UsuarioConObjetos {
  rol: Rol;
  centroSalud: CentroSalud;
  username: string;
  correo: string;
  hashPassword:string;
  dni:string;
  nombre:string;
  apellidos:string;
  fechaNacimiento:string;
  imagen:string;
  idUsuario: string | undefined;

  constructor(rol: Rol, centroSalud: CentroSalud, username: string, correo: string, hashPassword:string, dni:string,
              nombre:string, apellidos:string, fechaNacimiento:string, imagen:string, idUsuario?: string){

    this.rol = rol;
    this.centroSalud = centroSalud;
    this.username = username;
    this.correo = correo;
    this.hashPassword = hashPassword;
    this.dni = dni;
    this.nombre = nombre;
    this.apellidos = apellidos;
    this.fechaNacimiento = fechaNacimiento;
    this.imagen = imagen;
    this.idUsuario = String(idUsuario);
  }



}
