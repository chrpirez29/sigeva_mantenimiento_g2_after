export interface Token{
  idUsuario: string;
  rol: string;
}

export class Token {
  idUsuario: string;
  rol: string;

  constructor(idUsuario: string, rol: string) {
    this.idUsuario = idUsuario;
    this.rol = rol;
  }
}
