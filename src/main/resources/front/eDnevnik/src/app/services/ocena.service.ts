import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { MessageService } from '../services/message.service';

import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { OcenaEntity } from '../Entities/OcenaEntity';

@Injectable({
  providedIn: 'root'
})
export class OcenaService {

  private ocenaUrl = environment.apiBaseUrl + '/ocena/';

  constructor(private httpClient: HttpClient, private messageService: MessageService) { }

getAllOcena(): Observable<OcenaEntity[]> {
  return this.httpClient
    .get<OcenaEntity[]>(this.ocenaUrl)
    .pipe(
      tap(_ => this.log(`Učitane ocene`)),
      catchError(this.handleError<OcenaEntity[]>('getAllOcena')));
}

getOcenaById(id: number): Observable<OcenaEntity> {
  return this.httpClient
    .get<OcenaEntity>(this.ocenaUrl + id)
    .pipe(
      tap(a => this.log(`Učitana ocena sa id "${a.id}"`)),
      catchError(this.handleError<OcenaEntity>('getOcenaById')));
  }

  addNewOcena(ocena: OcenaEntity): Observable<OcenaEntity> {
    return this.httpClient
      .post<OcenaEntity>(this.ocenaUrl, ocena)
      .pipe(
        tap(a => this.log(`Dodata ocena sa id "${a.id}"`)),
        catchError(this.handleError<OcenaEntity>('addNewOcena')));
  }

private log(message: string) {
  this.messageService.add('OcenaService: ' + message);
}

private handleError<T> (operation = 'operation', result?: T) {
  return (error: any): Observable<T> => {
    console.error(error);
    this.log(`${operation} failed: ${error.message}`);
    return of(result as T);
  };
}
}

