import {Component, OnInit} from '@angular/core';
import {JsonService} from "../Service/json.service";
import {TokenService} from "../Service/token.service";
import {HttpParams} from "@angular/common/http";
import {CitaConObjetos} from "../Model/cita-con-objetos";

@Component({
  selector: 'app-contenedor-citas',
  templateUrl: './contenedor-citas.component.html',
  styleUrls: ['./contenedor-citas.component.css']
})
export class ContenedorCitasComponent implements OnInit {

  citas: CitaConObjetos[] = [];
  idUsuario: string;
  msgNoCitas = "";

  constructor(private json: JsonService, private tokenService: TokenService) {
    this.idUsuario = String(tokenService.getIdUsuario());
  }

  ngOnInit(): void {
    this.getCitas();
  }

  getCitas() {
    let params = new HttpParams({
      fromObject: {
        idPaciente: String(this.tokenService.getIdUsuario())
      }
    });
    this.json.getJsonPJ('/cita/obtenerCitasFuturasDelPaciente', params).subscribe(
      data => {
        this.citas = data;
        if (this.citas.length == 0) {
          this.msgNoCitas = "No hay citas asignadas";
        }
      }
    );
  }
}
