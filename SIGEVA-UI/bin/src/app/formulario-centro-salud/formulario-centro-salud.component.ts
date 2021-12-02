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
  public uuidCentroSalud = "";

  constructor(private json: JsonService) {
    this.centroSalud = new CentroSalud("", "", 0, new Vacuna("vacuna", 21, 15), "undefined");
    this.errorMessage = "";
    this.message = "";
    this.generandoCupos = "";
  }

  ngOnInit(): void {
    this.getConfiguracion();
  }

  getConfiguracion(){
    this.json.getJson('user/existConfCupos').subscribe((res: any) => {
      this.existeConfiguracion = JSON.parse(res);
    });
  }

  enviarDatosBack() {
    this.json.postJsonCrearCentro("user/newCentroSalud", this.centroSalud).subscribe(
      result => {
        this.errorMessage = "";
        this.generandoCupos = "";
        this.message = "Centro creado correctamente";
        console.log(result);
        this.generarCupos(result);
      }, err => {
        console.log(err);
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
        console.log("CUPOS GENERADOS");
      }, err => {
        this.errorMessage = err.error.message;
      });
  }
}
