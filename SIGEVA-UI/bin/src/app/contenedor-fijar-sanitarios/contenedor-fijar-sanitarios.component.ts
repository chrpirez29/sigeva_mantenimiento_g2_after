import { Component, OnInit } from '@angular/core';
import { JsonService } from "../Service/json.service";
import { HttpParams } from "@angular/common/http";
import { UsuarioConObjetos } from "../Model/Usuario-con-objetos";
import { Rol } from "../Model/rol";

@Component({
	selector: 'app-fijar-sanitarios',
	templateUrl: './contenedor-fijar-sanitarios.component.html',
	styleUrls: ['./contenedor-fijar-sanitarios.component.css']
})
export class ContenedorFijarSanitariosComponent implements OnInit {

	sanitarios: UsuarioConObjetos[];
	roles: Rol[];

	constructor(private json: JsonService) {
		this.sanitarios = [];
		this.roles = [new Rol("", "")];
	}

	ngOnInit(): void {
		this.getRoles();
	}

	getRoles() {
		this.json.getJson("user/getRoles").subscribe(
			result => {
				this.roles = JSON.parse(result);
				this.getRolSanitario();
			}, error => {
				console.log(error);
			});
	}

	getRolSanitario() {
		let self = this;
		this.roles.forEach(function(rol: Rol) {
			if (rol.nombre === "Sanitario") {
				self.getParams(rol);
			}
		})
	}

	getParams(rolAux: Rol) {
		let params = new HttpParams({
			fromObject: {
				rol: rolAux.id,
			}
		});
		this.json.getJsonP("user/getUsuariosByRol/", params).subscribe(
			result => {
				this.sanitarios = JSON.parse(result)
			}, error => {
				console.log(error);
			});
	}
}
