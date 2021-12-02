import { Component, OnInit } from '@angular/core';
import { JsonService } from '../Service/json.service';
import { Rol } from "../Model/rol";
import { CentroSalud } from "../Model/centro-salud";
import { UsuarioConObjetos } from "../Model/Usuario-con-objetos";
import { Vacuna } from "../Model/vacuna";
import { SHA256, enc } from "crypto-js";

@Component({
	selector: 'app-crear-usuarios',
	templateUrl: './crear-usuarios.component.html',
	styleUrls: ['./crear-usuarios.component.css'],
	providers: [JsonService],
})
export class CrearUsuariosComponent implements OnInit {

	public roles: Rol[];
	public centros: CentroSalud[];
	public usuario: UsuarioConObjetos;
	public message: string;
	public errorMessage: string;
	public pwd: string;
	public existeConfiguracion = false;
	public hide = true;

	constructor(private json: JsonService) {
		this.roles = [];
		this.centros = []

		this.usuario = new UsuarioConObjetos(new Rol("", ""), new CentroSalud("direccion", "nombre", 1, new Vacuna("vacuna", 3, 15), ""),
			"", "", "", "", "", "", "", "");
		this.errorMessage = "";
		this.message = "";
		this.pwd = "";
	}

	ngOnInit() {
		this.message = "";
		this.errorMessage = "";
		this.getRoles();
		this.getCentros();
		this.getConfiguracion();
	}

	getConfiguracion() {
		this.json.getJson('user/existConfCupos').subscribe((res: any) => {
			this.existeConfiguracion = JSON.parse(res);
		});
	}

	getRoles() {
		this.json.getJson("user/getRoles").subscribe(
			result => {
				this.roles = JSON.parse(result);
				this.usuario.rol = this.roles[0];
			}, error => {
				console.log(error);
			});
	}

	getCentros() {
		this.json.getJson("user/getCentros").subscribe(
			result => {
				this.centros = JSON.parse(result);
				this.usuario.centroSalud = this.centros[0];
			}, error => {
				console.log(error);
			});
	}

	capturarFile(event: any) {
		let file = event.target.files[0];
		this.imageConverter(file);
	}

	imageConverter(file: Blob) {
		let self = this;
		let reader = new FileReader();
		reader.onload = function() {
			if (typeof reader.result === "string") {
				self.usuario.imagen = ("data:image/png;base64," + btoa(reader.result));
			}
		}
		reader.readAsBinaryString(file);
	}

	encriptarPwd() {
		this.usuario.hashPassword = SHA256(this.pwd).toString(enc.Hex);
	}

	checkImage() {
		if (this.usuario.imagen == "") {
			this.usuario.imagen = "data:image/png;base64,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";
		}
	}

	enviarDatosBack() {
		this.encriptarPwd();
		this.checkImage();
		this.json.postJson("user/crearUsuario" + this.usuario.rol.nombre, this.usuario).subscribe(
			result => {
				if (result === null) {
					console.log("Fecha: " + this.usuario.fechaNacimiento);
					this.errorMessage = "";
					this.message = "Usuario creado correctamente"
				}
				setTimeout('document.location.reload()', 2000);
			}, err => {
				this.errorMessage = err.error.message;
				console.log(err);
			});
	}

	onChangeCentro($event: any) {
		this.usuario.centroSalud = $event;
	}

	onChangeRol($event: any) {
		this.usuario.rol = $event;
	}
}
