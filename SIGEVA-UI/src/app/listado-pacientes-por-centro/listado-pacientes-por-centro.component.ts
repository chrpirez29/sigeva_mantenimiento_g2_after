import { Component, OnInit } from '@angular/core';
import { JsonService } from '../Service/json.service';
import { HttpParams } from "@angular/common/http";
import { Rol } from '../Model/rol';
import { UsuarioConObjetos } from "../Model/Usuario-con-objetos";

@Component({
	selector: 'app-listado-pacientes',
	templateUrl: './listado-pacientes-por-centro.component.html',
	styleUrls: ['./listado-pacientes-por-centro.component.css']
})
export class ListadoPacientesPorCentroComponent implements OnInit {
	usuarios: UsuarioConObjetos[];
	existeConfiguracion = false;
	roles: Rol[];
	rolMostrado: string;

	constructor(private json: JsonService) {
		this.usuarios = [];
		this.roles = [];
		this.rolMostrado = "Paciente";
	}


ngOnInit(): void {
	this.cargarRoles();
	this.cargarUsuarios();
}

cargarRoles() {
	this.json.getJson("user/getRoles").subscribe(
		(res: any) => {
			this.roles = JSON.parse(res);
		},
		error => {
			console.log(error);
		}
	);
}

cambiarRolaId() {
	if (this.rolMostrado != "Todos") {
		let self = this;
		this.roles.forEach(function(rol: Rol) {
			if (self.rolMostrado === rol.nombre) {
				self.rolMostrado = rol.id;
			}
		})
	}
}

cargarUsuarios() {
    this.cambiarRolaId();
	let params = new HttpParams({
      fromObject: {
        rol: this.rolMostrado
      }
    });
	this.json.getJsonP("user/getUsuariosByPacienteAndCentroSalud",params).subscribe(
		result => {
			this.usuarios = JSON.parse(result);
		}, error => {
			console.log(error);
		});
}
}
