import {Component, OnInit} from '@angular/core';
import {CentroSalud} from '../Model/centro-salud';
import {ActivatedRoute, Params, Router} from "@angular/router";
import {HttpParams} from "@angular/common/http";
import {JsonService} from '../Service/json.service';
import {MatDialog} from '@angular/material/dialog';
import {VentanaEmergenteComponent} from '../ventana-emergente/ventana-emergente.component';
import {Vacuna} from "../Model/vacuna";

@Component({
  selector: 'app-modificacion-centro-salud',
  templateUrl: './modificacion-centro-salud.component.html',
  styleUrls: ['./modificacion-centro-salud.component.css']
})
export class ModificacionCentroSaludComponent implements OnInit {
  public cs: CentroSalud;
  public idCS: string;
  public message: string;
  public errorMessage: string;

  constructor(private json: JsonService, private rutaActu: ActivatedRoute, private router: Router, public dialog: MatDialog) {
    this.cs = new CentroSalud("direccion", "nombre", 1, new Vacuna("vacuna", 3, 15), "");
    this.idCS = "";
    this.errorMessage = "";
    this.message = "";
  }

  ngOnInit(): void {
    this.rutaActu.params.subscribe(
      (pr: Params) => {
        this.idCS = pr['idCentroSalud'];
      }
    );
    this.centroSaludById();

  }

  centroSaludById() {
    let params = new HttpParams({
      fromObject: {
        idCentroSalud: this.idCS
      }
    });
    this.json.getJsonP("user/getCentroSaludById", params).subscribe(
      result => {
        this.cs = JSON.parse(result);
      }, error => {
        console.log(error);
      });
  }

  openDialogCancelar() {
    const dialogRef = this.dialog.open(VentanaEmergenteComponent, {
      data: {mensaje: '¿SEGURO QUE QUIERES CANCELAR LA EDICIÓN?', titulo: 'Cancelar Edición'},
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.router.navigate(['/centrosSistema']);
      }
    });
  }

  openDialogGuardar() {
    const dialogRef = this.dialog.open(VentanaEmergenteComponent, {
      data: {mensaje: '¿SEGURO QUE QUIERES GUARDAR LA EDICIÓN?', titulo: 'Guardar Edición'},
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.modificarCentroSalud();
      }
    });
  }

  modificarCentroSalud() {
    let self = this;

    this.json.postJson("user/updateCS", this.cs).subscribe(
      result => {
        this.message = "Centro modificado correctamente";
        setTimeout(function () {
          self.router.navigate(['centrosSalud']);
        }, 3000);
        this.errorMessage = "";
      }, error => {
        this.message = "";
        this.errorMessage = error.error.message;
      });
  }
}
