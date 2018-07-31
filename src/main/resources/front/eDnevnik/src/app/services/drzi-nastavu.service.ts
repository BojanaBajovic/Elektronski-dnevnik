import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { MessageService } from '../services/message.service';

import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { DrziNastavuEntity } from '../Entities/DrziNastavuEntity';

@Injectable({
  providedIn: 'root'
})
export class DrziNastavuService {

  private drziNastavuUrl = environment.apiBaseUrl + '/drziNastavu/';

  constructor(private httpClient: HttpClient, private messageService: MessageService) { }



  getAlldrziNastavu(): Observable<DrziNastavuEntity[]> {
  return this.httpClient
    .get<DrziNastavuEntity[]>(this.drziNastavuUrl)
    .pipe(
      tap(_ => this.log(`Učitani drziNastavu`)),
      catchError(this.handleError<DrziNastavuEntity[]>('getAlldrziNastavu')));
}
private log(message: string) {
  this.messageService.add('DrziNastavuService: ' + message);
}

private handleError<T> (operation = 'operation', result?: T) {
  return (error: any): Observable<T> => {
    console.error(error);
    this.log(`${operation} failed: ${error.message}`);
    return of(result as T);
  };
}
}
