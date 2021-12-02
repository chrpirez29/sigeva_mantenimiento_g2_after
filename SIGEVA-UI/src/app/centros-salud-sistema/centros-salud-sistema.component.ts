import {Component, OnInit} from '@angular/core';
import {CentroSalud} from '../Model/centro-salud';
import {JsonService} from '../Service/json.service';

@Component({
  selector: 'app-centros-salud-sistema',
  templateUrl: './centros-salud-sistema.component.html',
  styleUrls: ['./centros-salud-sistema.component.css']
})
export class CentrosSaludSistemaComponent implements OnInit {
  centrosSalud: CentroSalud[];
  existeConfiguracion = false;

  constructor(private json: JsonService) {
    this.centrosSalud = [];
  }

  ngOnInit(): void {
    this.listarCentroSalud();
    this.getConfiguracion();
  }

  getConfiguracion() {
    this.json.getJson('cnfg/existConfCupos').subscribe((res: any) => {
      this.existeConfiguracion = JSON.parse(res);
    });
  }

  listarCentroSalud() {
    this.json.getJson("centro/getCentros").subscribe(
      (res: any) => {
        this.centrosSalud = JSON.parse(res);
      },
      error => {
        console.log(error);
      }
    );
  }
}
