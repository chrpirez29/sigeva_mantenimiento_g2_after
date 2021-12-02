import { Component, OnInit } from '@angular/core';
import { CentroSalud } from '../Model/centro-salud';
import { JsonService } from '../Service/json.service';

@Component({
	selector: 'app-indicar-dosis-vacunas',
	templateUrl: './indicar-dosis-vacunas.component.html',
	styleUrls: ['./indicar-dosis-vacunas.component.css'],
	providers: [JsonService],
})
export class IndicarDosisVacunasComponent implements OnInit {
	public cs: CentroSalud[];
	public centroSeleccionado: string;
	public nVacunasActual : number;
	public vacunasAanadir : number;
	public idCentro : string;
	public message : string;
  public errorMessage: string;
  public existeConfiguracion = false;

	constructor(private json: JsonService) {
		this.cs = [];
		this.centroSeleccionado = "";
		this.nVacunasActual = 0;
		this.vacunasAanadir = 0;
		this.idCentro = "";
		this.message = "";
    this.errorMessage = "";
	}

	ngOnInit(): void {
		this.listarCentros();
    this.getConfiguracion();
	}

  getConfiguracion(){
    this.json.getJson('user/existConfCupos').subscribe((res: any) => {
      this.existeConfiguracion = JSON.parse(res);
    });
  }

	listarCentros() {
		this.json.getJson('user/getCentros').subscribe(
			(res: any) => {this.cs = JSON.parse(res);
				this.centroSeleccionado = this.cs[0].nombreCentro;
				this.nVacunasActual = this.cs[0].numVacunasDisponibles;
			}, error=>{
				console.log(error);
			});
	}

	centroSelect(){
		let self = this;
		this.cs.forEach(function(centro2 : CentroSalud){
			if(centro2.nombreCentro === self.centroSeleccionado){
				self.nVacunasActual = centro2.numVacunasDisponibles;
        if(centro2.id != null) {
          self.idCentro = centro2.id;
        }
			}
		});
	}

	putBackData(){
    let self = this;
    this.centroSelect();
		this.json.putJsonVacunas("user/modificarDosisDisponibles",this.idCentro, this.vacunasAanadir).subscribe(
			result => {
        this.nVacunasActual = this.nVacunasActual + this.vacunasAanadir;
        self.vacunasAanadir = 0;
        this.errorMessage = "";
				this.message = "Modificación correcta";
        setTimeout(function(){ self.message = ""; },2000);
			}, err=> {
        this.errorMessage = err.error.message;
        console.log(err);
      });
	}

}
