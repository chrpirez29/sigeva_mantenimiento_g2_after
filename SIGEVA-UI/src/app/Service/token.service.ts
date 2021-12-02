import {Injectable} from "@angular/core";
import {Token} from "../Model/token";

const ID_USUARIO = 'IdUsuario';
const ROL_USUARIO = 'Rol';

@Injectable({
  providedIn: 'root'
})

export class TokenService {

  setIdUsuario(idUsuario: string) {
    window.sessionStorage.removeItem(ID_USUARIO);
    window.sessionStorage.setItem(ID_USUARIO, idUsuario);
  }

  getIdUsuario() {
    return window.sessionStorage.getItem(ID_USUARIO);
  }

  setRol(rol: string) {
    window.sessionStorage.removeItem(ROL_USUARIO);
    window.sessionStorage.setItem(ROL_USUARIO, rol);
  }

  getRol() {
    return window.sessionStorage.getItem(ROL_USUARIO);
  }

  getToken() {
    if (this.getIdUsuario() != null) {
      return new Token(String(this.getIdUsuario()), String(this.getRol()));
    }
    return null;
  }

  removeToken() {
    window.sessionStorage.removeItem(ID_USUARIO);
    window.sessionStorage.removeItem(ROL_USUARIO);
  }
}
