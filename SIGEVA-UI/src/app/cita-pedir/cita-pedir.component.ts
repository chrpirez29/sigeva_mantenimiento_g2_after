import {Component, Input} from '@angular/core';
import {CitaConObjetos} from "../Model/cita-con-objetos";
import {CupoCitas} from "../Model/cupo-citas";
import {CentroSalud} from "../Model/centro-salud";
import {Vacuna} from "../Model/vacuna";
import {Paciente} from "../Model/paciente";
import {Rol} from "../Model/rol";

@Component({
  selector: 'app-cita-pedir',
  templateUrl: './cita-pedir.component.html',
  styleUrls: ['./cita-pedir.component.css']
})
export class CitaPedirComponent {

  @Input() cita: CitaConObjetos;
  constructor() {
    this.cita = new CitaConObjetos(new CupoCitas("", new CentroSalud("", "", 0, new Vacuna("", 0, 0, ""), ""), new Date()),
      0,
      new Paciente(new Rol("", ""),
        new CentroSalud("", "", 0, new Vacuna("", 0, 0), ""),
        "", "", "", "", "", "", "", "", 0, ""), "");
  }
}
