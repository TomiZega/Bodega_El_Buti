import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.css']
})
export class AppComponent {
  title = 'ppai_front';


  habilitarPantalla(){}
  solicitarFechasDesdeHasta(){}
  tomarSelecFechasDesdeHasta(){}
  solicitarTipoResena(){}
  tomarTipoResena(){}
  solicitarFormaVisualizacion(){}
  tomarSelecVisualizacion(){}
  solicitarConfirmacion(){}
  tomarConfirmacion(){}
  informarGeneracionExitosa(){};
  downloadExcel(){}
}
