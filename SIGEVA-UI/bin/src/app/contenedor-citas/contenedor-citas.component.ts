import { Component, OnInit } from '@angular/core';
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

  constructor(private json: JsonService, private tokenService: TokenService) {
    this.idUsuario = String(tokenService.getIdUsuario());
  }

  ngOnInit(): void {
    this.getCitas();
  }

  getCitas(){
    let params = new HttpParams({
      fromObject: {
        idPaciente: "74467d37-9b85-49fc-b932-06125f80488e",
      }
    });
    this.json.getJsonPJ('/cita/obtenerCitasFuturasDelPaciente', params).subscribe(
      data => {
        this.citas = data;
      }
    );
  }
}
