import {Component, OnInit} from '@angular/core';
import {JsonService} from '../Service/json.service';
import {CentroSalud} from '../Model/centro-salud';
import {Vacuna} from "../Model/vacuna";
import {HttpParams} from "@angular/common/http";

@Component({
  selector: 'app-formulario-centro-salud',
  templateUrl: './formulario-centro-salud.component.html',
  styleUrls: ['./formulario-centro-salud.component.css'],
  providers: [JsonService],
})
export class FormularioCentroSaludComponent implements OnInit {

  centroSalud: CentroSalud;
  public message: string;
  public errorMessage: string;
  public generandoCupos: string;
  public existeConfiguracion = false;

  constructor(private json: JsonService) {
    this.centroSalud = new CentroSalud("", "", 0, new Vacuna("vacuna", 21, 15), "undefined");
    this.errorMessage = "";
    this.message = "";
    this.generandoCupos = "";
  }

  ngOnInit(): void {
    this.getConfiguracion();
  }

  getConfiguracion() {
    this.json.getJson('cnfg/existConfCupos').subscribe((res: any) => {
      this.existeConfiguracion = JSON.parse(res);
    });
  }

  enviarDatosBack() {
    this.json.postJsonCrearCentro("centro/newCentroSalud", this.centroSalud).subscribe(
      result => {
        this.errorMessage = "";
        this.generandoCupos = "";
        this.message = "Centro creado correctamente";
        setTimeout('document.location.reload()', 2000);
        this.generarCupos(result);
      }, err => {
        this.generandoCupos = "";
        this.errorMessage = err.error.message;
      });
  }

  generarCupos(idCentroSalud: string) {
    console.log(idCentroSalud);
    let params = new HttpParams({
      fromObject: {
        uuidCentroSalud: idCentroSalud
      }
    });
    this.json.getJsonPJ("cupo/prepararCupos", params).subscribe(
      result => {
        this.message = "";
      }, err => {
        this.errorMessage = err.error.message;
      });
  }
}
