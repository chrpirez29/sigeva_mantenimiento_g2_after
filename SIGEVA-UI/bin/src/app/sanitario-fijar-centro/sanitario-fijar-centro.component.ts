import {Component, Input, OnInit} from '@angular/core';
import {CentroSalud} from "../Model/centro-salud";
import {JsonService} from "../Service/json.service";
import {UsuarioConObjetos} from "../Model/Usuario-con-objetos";
import {Rol} from "../Model/rol";
import {Vacuna} from "../Model/vacuna";

@Component({
  selector: 'app-sanitario-fijar-centro',
  templateUrl: './sanitario-fijar-centro.component.html',
  styleUrls: ['./sanitario-fijar-centro.component.css']
})
export class SanitarioFijarCentroComponent implements OnInit {

  @Input() sanitario: UsuarioConObjetos;
  newSanitario: UsuarioConObjetos;
  centros: CentroSalud[]
  newCentroSeleccionado: CentroSalud;
  errorMessage: string;
  message: string;

  constructor(private json: JsonService) {
    this.centros = [];
    this.sanitario = new UsuarioConObjetos(new Rol("", ""), new CentroSalud("direccion", "nombre",1, new Vacuna("vacuna", 3, 15), ""),"", "", "", "", "", "", "", "");
    this.newSanitario = new UsuarioConObjetos(new Rol("", ""),new CentroSalud("direccion", "nombre",1, new Vacuna("vacuna", 3, 15), ""),"", "", "", "", "", "", "", "");
    this.newCentroSeleccionado = new CentroSalud("direccion", "nombre",1, new Vacuna("vacuna", 3, 15), "");
    this.errorMessage = "";
    this.message = "";
  }

  ngOnInit(): void {
    this.getCentros();
    this.copiaSanitario();
  }

  copiaSanitario(){
    Object.assign(this.newSanitario, this.sanitario)
  }

  getCentros() {
    this.json.getJson("user/getCentros").subscribe(
      result=> {
        this.centros = JSON.parse(result);
        Object.assign(this.newCentroSeleccionado, this.newSanitario.centroSalud);
      }, error=> {
        console.log(error);
      });
  }

  // fijarCentro() {
  //   let self = this;
  //   this.centros.forEach(function (centro2: CentroSalud) {
  //     if (self.newCentroSeleccionado.nombreCentro === centro2.nombreCentro) {
  //       self.newSanitario.centroSalud = centro2;
  //     }
  //   })
  // }

  enviarDatosBack() {
    if (this.newCentroSeleccionado.nombreCentro === this.sanitario.centroSalud.nombreCentro){
      this.errorMessage = "Elige un centro distinto al ya fijado";
    }
    else {
      let self = this;
      this.json.putJsonSanitario("user/fijarCentro", this.newSanitario.username, this.newSanitario.centroSalud.id).subscribe(
        result => {
          Object.assign(this.sanitario.centroSalud, this.newCentroSeleccionado);
          this.message = "Centro fijado correctamente";
          setTimeout(function(){ self.message = ""; }, 2000);
        },err=> {
          this.errorMessage = err.error.message;
          console.log(err);
        });
    }
  }
}
