import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({providedIn: 'root'})
export class ServiceNameService {
  constructor(private http: HttpClient) { }
  
}
@Injectable({
  providedIn: 'root'
})
export class GestorService {

  constructor( private http: HttpClient) { }
  //2
  habilitarPantalla(){
    this.http.get("/generar-ranking-vinos")
  }
  
}
