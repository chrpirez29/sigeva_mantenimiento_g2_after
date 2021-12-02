import { Component, } from '@angular/core';
import { CentroSalud  } from "../Model/centro-salud";
import { JsonService } from '../Service/json.service';
import { HttpParams } from "@angular/common/http";
import {CitaConObjetos} from "../Model/cita-con-objetos";
import {Paciente} from "../Model/paciente";
import {Rol} from "../Model/rol";
import {Vacuna} from "../Model/vacuna";

@Component({
	selector: 'app-solicitar-cita',
	templateUrl: './solicitar-cita.component.html',
	styleUrls: ['./solicitar-cita.component.css']
})

export class SolicitarCitaComponent {
	paciente: Paciente;
	mensaje: string;
	mensajeError: string;
	citas: CitaConObjetos[];
	solicitada: boolean;

	constructor(private json: JsonService) {
		this.paciente = new Paciente(new Rol("1", "Paciente"), new CentroSalud("direccion", "nombre",1, new Vacuna("vacuna", 3, 15), ""), "vasilesan", "", "", "",
      "", "", "", "", 0);
		this.citas = [];
		this.solicitada = false;
		this.mensaje = "SOLICITAR CITA";
		this.mensajeError = "";
	}

  solicitarCita() {
    let params = new HttpParams({
      fromObject: {
        uuidPaciente: "74467d37-9b85-49fc-b932-06125f80488e"
      }
    });
    this.json.getJsonP("cita/buscarYAsignarCitas", params).subscribe(
      result => {
        this.citas = JSON.parse(result);
        this.solicitada = true;
      }, error => {
        console.log(error);
      });
  }


  // solicitarCita(){
  //   this.getPacientePrueba();
  // }

  // getPacientePrueba(){
  //   this.json.getJson("cita/getPacientePrueba").subscribe(
  //     result => {
  //       let paciente : Paciente;
  //       paciente = JSON.parse(result);
  //       this.getCitas(paciente);
  //     }, error => {
  //       console.log(error);
  //     });
  // }
  //
  //
  // getCitas(paciente : Paciente) {
  //   this.json.postJson("cita/obtenerCitasFuturasDelPaciente", paciente).subscribe(
  //     result => {
  //       this.citas = JSON.parse(JSON.stringify(result));
  //       console.log(this.citas[0].cupo.fechaYHoraInicio)
  //       this.solicitada = true;
  //     }, error => {
  //       console.log(error);
  //     });
  // }

}
