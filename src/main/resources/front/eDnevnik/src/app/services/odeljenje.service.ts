import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { MessageService } from '../services/message.service';

import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { OdeljenjeEntity } from '../Entities/OdeljenjeEntity';

@Injectable({
  providedIn: 'root'
})
export class OdeljenjeService {
  private odeljenjeUrl = environment.apiBaseUrl + '/odeljenje/';

  constructor(private httpClient: HttpClient, private messageService: MessageService) { }

  getOdeljenjeById(id: number): Observable<OdeljenjeEntity> {
  return this.httpClient
    .get<OdeljenjeEntity>(this.odeljenjeUrl + id)
    .pipe(
      tap(a => this.log(`Učitano odeljenje sa id-jem "${a.id}"`)),
      catchError(this.handleError<OdeljenjeEntity>('getOdeljenjeById')));
  }

  getAllOdeljenja(): Observable<OdeljenjeEntity[]> {
    return this.httpClient
      .get<OdeljenjeEntity[]>(this.odeljenjeUrl)
      .pipe(
        tap(_ => this.log(`Učitana odeljenja`)),
        catchError(this.handleError<OdeljenjeEntity[]>('getAllOdeljenja')));
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
