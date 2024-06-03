import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { PantallaGenerarRankingVideosComponent } from './pantalla-generar-ranking-videos/pantalla-generar-ranking-videos.component';

@NgModule({
  declarations: [
    AppComponent,
    PantallaGenerarRankingVideosComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
