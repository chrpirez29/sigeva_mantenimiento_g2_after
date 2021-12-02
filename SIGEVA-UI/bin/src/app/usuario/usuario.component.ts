import { Component, Input } from '@angular/core';
import {UsuarioConObjetos} from "../Model/Usuario-con-objetos";
import {CentroSalud} from "../Model/centro-salud";
import {Rol} from "../Model/rol";
import {VentanaEmergenteComponent} from "../ventana-emergente/ventana-emergente.component";
import {MatDialog} from "@angular/material/dialog";
import {JsonService} from "../Service/json.service";
import {Vacuna} from "../Model/vacuna";

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.css']
})

export class UsuarioComponent {
  @Input() usuario: UsuarioConObjetos;
  @Input() existeConfiguracion: boolean = false;
  message: string = "";
  usuarioEliminado = false;
  errorMessage: string = "";
  constructor(private json: JsonService, public dialog: MatDialog) {
    this.usuario = new UsuarioConObjetos(new Rol("", ""), new CentroSalud("direccion", "nombre",1, new Vacuna("vacuna", 3, 15), ""), "", "", "", "", "", "", "",
      "");
  }

  openDialogEliminar() {
    let self = this;
    const dialogRef = this.dialog.open(VentanaEmergenteComponent, {
      data: {mensaje: '¿SEGURO QUE QUIERES ELIMINAR AL USUARIO: ' + this.usuario.nombre + ' ' + this.usuario.apellidos + '?', titulo: 'Eliminar Usuario'},
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.usuarioEliminado = true;
        this.json.deleteJson("user/deleteUsuario", String(this.usuario.idUsuario)).subscribe(
          res => {
            this.message = "Usuario eliminado correctamente";
            this.errorMessage = "";
            this.usuarioEliminado = true;
          }, error => {
            this.errorMessage = error.error.message;
            setTimeout(function(){ self.errorMessage = "" }, 4000);
          });
      }
    });
  }
}
