import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { PantallaGenerarRankingVinosComponent } from './pantalla-generar-ranking-videos/pantalla-generar-ranking-vinos.component';

@NgModule({
  declarations: [
    AppComponent,
    PantallaGenerarRankingVinosComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
