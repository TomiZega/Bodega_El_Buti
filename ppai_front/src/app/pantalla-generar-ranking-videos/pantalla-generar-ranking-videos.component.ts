import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'pantalla-ranking-videos',
  templateUrl: './pantalla-generar-ranking-videos.component.html',
  styleUrls: ['./pantalla-generar-ranking-videos.component.css']
})
export class PantallaGenerarRankingVideosComponent{
  fechaDesde: string;
  fechaHasta: string;
  tiposResenas: string[]= ["Reseñas normales", "Reseñas de Sommelier", "Reseñas de Amigos"];
  tiposVisualizacion: string[] = ["Excel", "PDF", "En pantalla"];
  tipoResena: string;
  tipoVisualizacion: string;
  
  pantallaHabilitada: boolean = false;
  seccionFechas: boolean = false;
  seccionTipoResena: boolean = false;
  seccionFormaVisualizacion: boolean = false;
  seccionConfirmacion: boolean = false;
  constructor(private http: HttpClient) { }

  //1
  selecGenerarRankingVinos() {
    this.habilitarPantalla();
  };

  //2
  habilitarPantalla() {
    this.http.get('http://localhost:8080/generar-ranking-vinos', { responseType: 'text' })
      .subscribe({ next: data => { this.solicitarFechasDesdeHasta(); 
        this.pantallaHabilitada= true;
      } })
  }
  //4
  solicitarFechasDesdeHasta() {
    this.seccionFechas = true;
  };
  //5
  tomarSelecFechasDesdeHasta() {
    const params = new URLSearchParams();
    params.append('fechaDesde', this.fechaDesde);
    params.append('fechaHasta', this.fechaHasta);
    this.http.post(`http://localhost:8080/fechas-rango?${params.toString()}`, null, { responseType: 'text' })
      .subscribe({
        next: () => this.solicitarTipoResena(),
        error: error => { console.error('Error:', error); }
      });
  }
  solicitarTipoResena() {
    this.seccionTipoResena=true;
  };

  tomarTipoResena() {
    this.http.post('http://localhost:8080/tipo-resena', this.tipoResena, { responseType: 'text' })
      .subscribe({
        next: () => { this.solicitarFormaVisualizacion()},
        error: error => { console.error('Error:', error); }
      });
  }
  solicitarFormaVisualizacion() {
    this.seccionFormaVisualizacion=true
  };
  tomarSelecFormaVisualizacion() { 
    this.http.post('http://localhost:8080/tipo-visualizacion', this.tipoVisualizacion, { responseType: 'text' })
      .subscribe({
        next: () => this.solicitarConfirmacion(),
        error: error => { console.error('Error:', error); }
      });
  };

  solicitarConfirmacion() {
    this.seccionConfirmacion= true;
  };

  tomarConfirmacion() {
    this.http.get('http://localhost:8080/calificar-vinos', { responseType: 'blob' })
      .subscribe({
        next: (response: Blob) => {
          const url = window.URL.createObjectURL(response);
          const a = document.createElement('a');
          a.href = url;
          a.download = 'RankingVinos.xlsx';
          document.body.appendChild(a);
          a.click();
          window.URL.revokeObjectURL(url);
          document.body.removeChild(a);
          this.informarGeneracionExitosa();
        },
        error: error => { console.error('Error:', error); }
      });
  }
  

  informarGeneracionExitosa() {
    window.alert("Reporte generado correctamente!");
    this.http.get('http://localhost:8080/fin-cu');
  };
}
