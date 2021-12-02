import {Component,} from '@angular/core';
import {CentroSalud} from "../Model/centro-salud";
import {JsonService} from '../Service/json.service';
import {HttpParams} from "@angular/common/http";
import {CitaConObjetos} from "../Model/cita-con-objetos";
import {Paciente} from "../Model/paciente";
import {Rol} from "../Model/rol";
import {Vacuna} from "../Model/vacuna";
import {TokenService} from "../Service/token.service";

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

  constructor(private json: JsonService, private token: TokenService) {
    this.paciente = new Paciente(new Rol("1", "Paciente"), new CentroSalud("direccion", "nombre", 1, new Vacuna("vacuna", 3, 15), ""), "vasilesan", "", "", "",
      "", "", "", "", 0);
    this.citas = [];
    this.solicitada = false;
    this.mensaje = "SOLICITAR CITA";
    this.mensajeError = "";
  }

  solicitarCita() {
    let params = new HttpParams({
      fromObject: {
        uuidPaciente: String(this.token.getIdUsuario())
      }
    });
    this.json.getJsonPJ("cita/buscarYAsignarCitas", params).subscribe(
      result => {
        this.citas = JSON.parse(JSON.stringify(result));
        this.solicitada = true;
        this.mensajeError = "";
        if (this.citas.length == 1 && this.citas[0].dosis == 1){
          this.mensajeError = "No se puede asignar la segunda cita por superar el límite de fecha. Contacte con el administrador.";
        }
      }, error => {
        this.mensajeError = error.error.message;
      });
  }
}
