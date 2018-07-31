import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { MessageService } from '../services/message.service';

import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { RoditeljEntity } from '../Entities/RoditeljEntity';


@Injectable({
  providedIn: 'root'
})
export class RoditeljService {
  private roditeljUrl = environment.apiBaseUrl + '/roditelj/';

  constructor(private httpClient: HttpClient, private messageService: MessageService) { }

  getRoditeljById(id: number): Observable<RoditeljEntity> {
    return this.httpClient
      .get<RoditeljEntity>(this.roditeljUrl + id)
      .pipe(
        tap(a => this.log(`Učitan roditelj sa id "${a.id}"`)),
        catchError(this.handleError<RoditeljEntity>('getRoditeljById')));
  }

  getAllRoditelj(): Observable<RoditeljEntity[]> {
    return this.httpClient
      .get<RoditeljEntity[]>(this.roditeljUrl)
      .pipe(
        tap(_ => this.log(`Učitani roditelji`)),
        catchError(this.handleError<RoditeljEntity[]>('getAllRoditelj')));
  }

  addNewRoditelj(roditelj: RoditeljEntity): Observable<RoditeljEntity> {
    return this.httpClient
      .post<RoditeljEntity>(this.roditeljUrl, roditelj)
      .pipe(
        tap(a => this.log(`Dodat roditelj sa id "${a.id}"`)),
        catchError(this.handleError<RoditeljEntity>('addNewRoditelj')));
  }

  updateRoditelj(roditelj: RoditeljEntity): Observable<RoditeljEntity> {
    return this.httpClient
      .put<RoditeljEntity>(this.roditeljUrl + roditelj.id, roditelj)
      .pipe(
        tap(a => this.log(`Izmenjen roditelj sa id "${a.id}"`)),
        catchError(this.handleError<RoditeljEntity>('updateRoditelj')));
  }

  deleteRoditelj(roditelj: RoditeljEntity): Observable<any> {
    return this.httpClient.delete<MessageService>(`${this.roditeljUrl}${roditelj.id}`)
      .pipe(
        tap(_ => this.log(`Obrisan roditelj sa id "${roditelj.id}"`)),
        catchError(this.handleError<RoditeljEntity>('deleteRoditelj')));
  }

  searchRoditelj(term: string): Observable<RoditeljEntity[]> {
    if (!term.trim()) {
      return this.getAllRoditelj();
    }

    return this.httpClient
      .get<RoditeljEntity[]>(`${this.roditeljUrl}?naziv=${term}`)
      .pipe(
        tap(_ => this.log(`Nadjeni roditelji sa nazivom "${term}"`)),
        catchError(this.handleError<RoditeljEntity[]>('searchRoditelj', []))
    );
  }

  private log(message: string) {
    this.messageService.add('RoditeljService: ' + message);
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
