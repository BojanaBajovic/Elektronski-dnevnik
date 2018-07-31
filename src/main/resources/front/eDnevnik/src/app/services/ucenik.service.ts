import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { MessageService } from '../services/message.service';

import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { UcenikEntity } from '../Entities/ucenik-enitity';

@Injectable({
  providedIn: 'root'
})
export class UcenikService {
  private ucenikUrl = environment.apiBaseUrl + '/ucenik/';

  constructor(private httpClient: HttpClient, private messageService: MessageService) { }

getUcenikById(id: number): Observable<UcenikEntity> {
  return this.httpClient
    .get<UcenikEntity>(this.ucenikUrl + id)
    .pipe(
      tap(a => this.log(`Učitan ucenik sa id "${a.id}"`)),
      catchError(this.handleError<UcenikEntity>('getUcenikById')));
  }
getAllUcenik(): Observable<UcenikEntity[]> {
  return this.httpClient
    .get<UcenikEntity[]>(this.ucenikUrl)
    .pipe(
      tap(_ => this.log(`Učitani ucenici`)),
      catchError(this.handleError<UcenikEntity[]>('getAllUcenik')));
}

addNewUcenik(ucenik: UcenikEntity): Observable<UcenikEntity> {
  return this.httpClient
    .post<UcenikEntity>(this.ucenikUrl, ucenik)
    .pipe(
      tap(a => this.log(`Dodat ucenik sa id "${a.id}"`)),
      catchError(this.handleError<UcenikEntity>('addNewUcenik')));
}

updateUcenik(ucenik: UcenikEntity): Observable<UcenikEntity> {
  return this.httpClient
    .put<UcenikEntity>(this.ucenikUrl + ucenik.id, ucenik)
    .pipe(
      tap(a => this.log(`Izmenjen ucenik sa id "${a.id}"`)),
      catchError(this.handleError<UcenikEntity>('updateUcenik')));
}

deleteUcenik (id: number): Observable<any> {
  return this.httpClient
  .delete<any>(this.ucenikUrl + id)
    .pipe(
      tap(a => this.log(`Obrisan ucenik sa id "${a.id}"`)),
      catchError(this.handleError<UcenikEntity>('deleteUcenik')));

}

private log(message: string) {
  this.messageService.add('UcenikService: ' + message);
}

private handleError<T> (operation = 'operation', result?: T) {
  return (error: any): Observable<T> => {
    console.error(error);
    this.log(`${operation} failed: ${error.message}`);
    return of(result as T);
  };
}
}
