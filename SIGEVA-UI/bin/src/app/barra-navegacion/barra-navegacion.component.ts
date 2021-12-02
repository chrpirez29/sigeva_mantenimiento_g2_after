import { Component, OnInit } from '@angular/core';
import {TokenService} from "../Service/token.service";
import {MatDialog} from "@angular/material/dialog";
import {VentanaEmergenteComponent} from "../ventana-emergente/ventana-emergente.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-barra-navegacion',
  templateUrl: './barra-navegacion.component.html',
  styleUrls: ['./barra-navegacion.component.css']
})
export class BarraNavegacionComponent implements OnInit {

  superAdmin = false;
  isLogged = false;
  rol = "";
  existeConfiguracion = false;

  constructor(private tokenService: TokenService, public dialog: MatDialog, private router: Router) { }

  ngOnInit() {
    if (this.tokenService.getToken() != null) {
      this.isLogged = true;
      this.rol = String(this.tokenService.getRol());
    }
    else {
      this.isLogged = false;
    }

    if (this.superAdmin){
      this.isLogged = true;
      this.rol = "SuperAdmin";
    }
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
