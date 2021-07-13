import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Categoria } from './categoria.model';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {

  categoriasUrl = `${environment.apiUrl}/categorias`;

  constructor(private http: HttpClient) { }

  listarTodas(): Promise<Categoria[]> {
    return this.http.get<Categoria[]>(`${this.categoriasUrl}`)
      .toPromise();
  }
}
