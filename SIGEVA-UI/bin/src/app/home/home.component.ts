import { Component, OnInit } from '@angular/core';
import {TokenService} from "../Service/token.service";
import {Router} from "@angular/router";
import {UsuarioConObjetos} from "../Model/Usuario-con-objetos";
import {JsonService} from "../Service/json.service";
import {HttpParams} from "@angular/common/http";
import {Rol} from "../Model/rol";
import {CentroSalud} from "../Model/centro-salud";
import {Vacuna} from "../Model/vacuna";
import {VentanaEmergenteComponent} from "../ventana-emergente/ventana-emergente.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit {

  public usuario: UsuarioConObjetos;

  constructor(private tokenService: TokenService, private router: Router, private json: JsonService, public dialog: MatDialog) {
    this.usuario =new UsuarioConObjetos(new Rol("", ""), new CentroSalud("direccion", "nombre",1, new Vacuna("vacuna", 3, 15), ""),
      "", "", "", "", "", "", "", "", "");
  }

  ngOnInit(): void {
    if (this.tokenService.getToken() == null) {
      this.router.navigate(['/login']);
    }
    else {
      this.getUsuario();
    }
  }

  getUsuario(){
    let params = new HttpParams({
      fromObject: {
        idUsuario: String(this.tokenService.getIdUsuario())
      }
    });
    this.json.getJsonP("/user/getUsuarioById", params).subscribe(
      result => {
        this.usuario = JSON.parse(result)
        console.log(this.usuario);
      }, error => {
        console.log(error);
      });
  }

  openDialogCerrarSesion(){
    const dialogRef = this.dialog.open(VentanaEmergenteComponent, {
      data: {mensaje: '¿SEGURO QUE QUIERES CERRAR SESIÓN?', titulo: 'Cerrar Sesión'},
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.tokenService.removeToken();
        this.router.navigate(['/login']);
      }
    });
  }
}
