import { Component, OnInit } from '@angular/core';
import {Rol} from "../Model/rol";
import {CentroSalud} from "../Model/centro-salud";
import {UsuarioConObjetos} from "../Model/Usuario-con-objetos";
import {JsonService} from "../Service/json.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {HttpParams} from "@angular/common/http";
import {VentanaEmergenteComponent} from "../ventana-emergente/ventana-emergente.component";
import {MatDialog} from "@angular/material/dialog";
import {Vacuna} from "../Model/vacuna";
import {enc, SHA256} from "crypto-js";

@Component({
  selector: 'app-editar-usuario',
  templateUrl: './editar-usuario.component.html',
  styleUrls: ['./editar-usuario.component.css']
})
export class EditarUsuarioComponent implements OnInit {

  public centros: CentroSalud[];
  public usuario: UsuarioConObjetos;
  public message: string;
  public errorMessage: string;
  public idUsuario: string;
  public newPassword: string;
  public hide = false;
  public newCentro: CentroSalud;

  constructor(private json: JsonService, private rutaActiva: ActivatedRoute, public dialog: MatDialog, private router:Router) {
    this.centros = [];
    this.usuario = new UsuarioConObjetos(new Rol("", ""), new CentroSalud("direccion", "nombre",1, new Vacuna("vacuna", 3, 15), ""),
      "", "", "", "", "", "", "", "");
    this.errorMessage = "";
    this.message = "";
    this.idUsuario = "";
    this.newPassword = "";
    this.newCentro = new CentroSalud("", "", 0, new Vacuna("", 0, 0), "");
  }

  ngOnInit() {
    this.message = "";
    this.errorMessage = "";
    this.getCentros();
    this.rutaActiva.params.subscribe(
      (params: Params) => {
        this.idUsuario = params['idUsuario'];
      }
    );
    this.getUsuarioById();
  }

  getCentros() {
    this.json.getJson("user/getCentros").subscribe(
      result => {
        this.centros = JSON.parse(result);
      }, error => {
        console.log(error);
      });
  }

  capturarFile(event: any) {
    let self = this;
    let file = event.target.files[0];
    let reader = new FileReader();
    reader.onload = function () {
      if (typeof reader.result === "string") {
        self.usuario.imagen = ("data:image/png;base64," + btoa(reader.result));
      }
    }
    reader.readAsBinaryString(file);
  }

  onChangeCentro($event: any) {
    console.log($event);
	this.usuario.centroSalud = $event;

  }



  getUsuarioById() {
    let params = new HttpParams({
      fromObject: {
        idUsuario: this.idUsuario
      }
    });
    this.json.getJsonP("user/getUsuarioById", params).subscribe(
      result => {
        this.usuario = JSON.parse(result);
        this.usuario.fechaNacimiento = this.usuario.fechaNacimiento.substr(0, 10);
		this.newCentro = this.usuario.centroSalud;
      }, error => {
        console.log(error);
      });
  }

  openDialogCancelar() {
    const dialogRef = this.dialog.open(VentanaEmergenteComponent, {
      data: {mensaje: '�SEGURO QUE QUIERES CANCELAR LA EDICI�N?', titulo: 'Cancelar Edici�n'},
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.router.navigate(['usuariosSistema']);
      }
    });
  }

  checkNewPassword(){
    if (this.newPassword != ""){
      this.usuario.hashPassword = SHA256(this.newPassword).toString(enc.Hex);
    }
  }

  openDialogGuardar() {
    this.checkNewPassword();
    let self = this;
    const dialogRef = this.dialog.open(VentanaEmergenteComponent, {
      data: {mensaje: '�SEGURO QUE QUIERES GUARDAR LA EDICI�N?', titulo: 'Guardar Edici�n'},
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log(this.usuario.centroSalud);
      if (result) {
        this.json.postJson("user/updateUsuario", this.usuario).subscribe(
          res => {
            this.message = "Usuario editado correctamente";
            setTimeout(function(){ self.router.navigate(['usuariosSistema']); }, 3000);
            this.errorMessage = "";
          }, error => {
            this.errorMessage = "Error al editar el usuario";
            this.message = "";
          });
      }
    });
  }
}
