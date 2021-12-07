import {Component, Input} from '@angular/core';
import {UsuarioConObjetos} from "../Model/Usuario-con-objetos";
import {CentroSalud} from "../Model/centro-salud";
import {Rol} from "../Model/rol";
import {MatDialog} from "@angular/material/dialog";
import {JsonService} from "../Service/json.service";
import {Vacuna} from "../Model/vacuna";

@Component({
  selector: 'app-usuariocentro',
  templateUrl: './usuario-centro.component.html',
  styleUrls: ['./usuario-centro.component.css']
})

export class UsuarioCentroComponent {
  @Input() usuario: UsuarioConObjetos;
  @Input() existeConfiguracion: boolean = false;
  message: string = "";
  usuarioEliminado = false;
  errorMessage: string = "";

  constructor(private json: JsonService, public dialog: MatDialog) {
    this.usuario = new UsuarioConObjetos(new Rol("", ""), new CentroSalud("direccion", "nombre", 1, new Vacuna("vacuna", 3, 15), ""), "", "", "", "", "", "", "",
      "");
  }
Seleccionar(){
	
}

}
