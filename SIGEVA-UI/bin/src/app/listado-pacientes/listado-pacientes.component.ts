import {Component, OnInit} from '@angular/core';
import {Rol} from "../Model/rol";
import {HttpParams} from "@angular/common/http";
import {JsonService} from "../Service/json.service";
import {CitaConObjetos} from "../Model/cita-con-objetos";
import {Paciente} from "../Model/paciente";
import {CentroSalud} from "../Model/centro-salud";
import {MatDatepickerInputEvent} from '@angular/material/datepicker';
import {Vacuna} from "../Model/vacuna";
import {CupoCitas} from "../Model/cupo-citas";
import {FormControl} from "@angular/forms";
import {TokenService} from "../Service/token.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-listado-pacientes',
  templateUrl: './listado-pacientes.component.html',
  styleUrls: ['./listado-pacientes.component.css']
})
export class ListadoPacientesComponent implements OnInit {
  citaSeleccionada:CitaConObjetos;
  citas : CitaConObjetos[];
  pacienteSeleccionado:boolean;
  today : FormControl;
  dateSelectedIsToday : boolean;
  centroSaludUsuario: CentroSalud;

  constructor(private json:JsonService, private tokenService: TokenService, private router: Router) {
    this.citaSeleccionada = new CitaConObjetos(new CupoCitas("",new CentroSalud("direccion", "nombre",0, new Vacuna("vacuna", 0, 0), ""), new Date() ), 0, new Paciente(new Rol("0", ""),new CentroSalud("direccion", "nombre",1, new Vacuna("vacuna", 0, 0), ""), "", "", "", "",
      "", "", "", "", 0));
    this.centroSaludUsuario = new CentroSalud("direccion", "nombre",0, new Vacuna("vacuna", 0, 0), "")
    this.citas = [];
    this.today = new FormControl(new Date());
    this.pacienteSeleccionado = false;
    this.dateSelectedIsToday = true;
  }

  ngOnInit(): void {
    if (this.tokenService.getToken() == null) {
      this.router.navigate(['/login']);
    }
    else {
      this.getCentroSaludUsuario(this.tokenService.getIdUsuario());
    }
  }

  vacunar(cita : CitaConObjetos){
    this.citaSeleccionada = cita;
    this.pacienteSeleccionado = true;
  }

  dataChangeEvent(type: string, event: MatDatepickerInputEvent<Date>) {
    let fecha = event.value;
    let hoy: Date = new Date();
    this.dateSelectedIsToday = fecha?.getDate()==hoy.getDate() && fecha?.getMonth()==hoy.getMonth() && fecha?.getFullYear()==hoy.getFullYear();
    this.getPacientesFecha(event);
  }

  getPacientesFecha(event : MatDatepickerInputEvent<Date>){
    let params = new HttpParams({
      fromObject: {
        'centroSaludDTO': JSON.stringify(this.centroSaludUsuario),
        'fecha' : JSON.stringify(event.value),
      }
    });
    this.json.getJsonP("cita/obtenerCitasFecha", params).subscribe(
      result => {
        this.citas = JSON.parse(result);
      }, error => {
        console.log(error);
      });
  }

  aplicarDosis() {
    this.json.postJson('cita/vacunar',this.citaSeleccionada).subscribe((res: any) => {
      this.citaSeleccionada.paciente.numDosisAplicadas = this.citaSeleccionada.paciente.numDosisAplicadas + 1;
      this.pacienteSeleccionado = false;
    },err=> {
      console.log(err);
    });

  }

  citasHoy() {
    this.today = new FormControl(new Date());
    let params = new HttpParams({
      fromObject: {
        'centroSaludDTO': JSON.stringify(this.centroSaludUsuario),
        'fecha' : JSON.stringify(new Date()),
      }
    });
    this.json.getJsonP("cita/obtenerCitasFecha", params).subscribe(
      result => {
        this.citas = JSON.parse(result);
      }, error => {
        console.log(error);
      });
  }

  private getCentroSaludUsuario(idUsuario: string | null) {
    if(idUsuario == null){
      idUsuario = "";
    }

    let params = new HttpParams({
      fromObject: {
        'idUsuario': idUsuario,
      }
    });
    this.json.getJsonP("user/getCentroSanitario", params).subscribe(
      result => {
        this.centroSaludUsuario = JSON.parse(result);
      }, error => {
        console.log(error);
      });
  }
}
