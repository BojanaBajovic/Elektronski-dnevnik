import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { MessageService } from '../services/message.service';

import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { PredajeEntity } from '../Entities/PredajeEntity';

@Injectable({
  providedIn: 'root'
})
export class PredajeService {
  private predajeUrl = environment.apiBaseUrl + '/predaje/';

  constructor(private httpClient: HttpClient, private messageService: MessageService) { }

  getPredajeById(id: number): Observable<PredajeEntity> {
    return this.httpClient
      .get<PredajeEntity>(this.predajeUrl + id)
      .pipe(
        tap(a => this.log(`Učitan predaje sa id "${a.id}"`)),
        catchError(this.handleError<PredajeEntity>('getPredajeById')));
  }

  getAllPredaje(): Observable<PredajeEntity[]> {
    return this.httpClient
      .get<PredajeEntity[]>(this.predajeUrl)
      .pipe(
        tap(_ => this.log(`Učitani predaje`)),
        catchError(this.handleError<PredajeEntity[]>('getAllPredaje')));
  }

  private log(message: string) {
    this.messageService.add('PredajeService: ' + message);
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
