import {Component, OnInit} from '@angular/core';
import {JsonService} from "../Service/json.service";

@Component({
  selector: 'app-mensaje-configuracion',
  templateUrl: './mensaje-configuracion.component.html',
  styleUrls: ['./mensaje-configuracion.component.css']
})
export class MensajeConfiguracionComponent implements OnInit {

  public existeConfiguracion = true;

  constructor(private json: JsonService) {
  }

  ngOnInit(): void {
    this.getConfiguracion();
  }

  getConfiguracion() {
    this.json.getJson('cnfg/existConfCupos').subscribe((res: any) => {
      this.existeConfiguracion = JSON.parse(res);
    });
  }

}
