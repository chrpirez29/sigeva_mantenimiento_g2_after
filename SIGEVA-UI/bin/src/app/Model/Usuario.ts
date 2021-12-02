

export interface Usuario{
  rol: string;
  centroSalud: string;
  username: string;
  correo: string;
  hashPassword:string;
  dni:string;
  nombre:string;
  apellidos:string;
  fechaNacimiento:string;
  imagen:string;
}

export class Usuario {
  rol: string;
  centroSalud: string;
  username: string;
  correo: string;
  hashPassword:string;
  dni:string;
  nombre:string;
  apellidos:string;
  fechaNacimiento:string;
  imagen:string;
  id: string | undefined;

  constructor(rol: string, centroSalud: string, username: string, correo: string, hashPassword:string, dni:string,
              nombre:string, apellidos:string, fechaNacimiento:string, imagen:string, id?: string){

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
    this.id = id;
  }

}
