import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { MessageService } from '../services/message.service';

import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { NastavnikEntity } from '../Entities/NastavnikEntity';

@Injectable({
  providedIn: 'root'
})
export class NastavnikService {
  private nastavnikUrl = environment.apiBaseUrl + '/nastavnik/';

  constructor(private httpClient: HttpClient, private messageService: MessageService) { }

  getNastavnikById(id: number): Observable<NastavnikEntity> {
    return this.httpClient
      .get<NastavnikEntity>(this.nastavnikUrl + id)
      .pipe(
        tap(a => this.log(`Učitan nastavnik sa id "${a.id}"`)),
        catchError(this.handleError<NastavnikEntity>('getNastavnikById')));
  }

  getAllNastavnik(): Observable<NastavnikEntity[]> {
    return this.httpClient
      .get<NastavnikEntity[]>(this.nastavnikUrl)
      .pipe(
        tap(_ => this.log(`Učitani nastavnici`)),
        catchError(this.handleError<NastavnikEntity[]>('getAllNastavnik')));
  }

  addNewNastavnik(nastavnik: NastavnikEntity): Observable<NastavnikEntity> {
    return this.httpClient
      .post<NastavnikEntity>(this.nastavnikUrl, nastavnik)
      .pipe(
        tap(a => this.log(`Dodat nastavnik sa id "${a.id}"`)),
        catchError(this.handleError<NastavnikEntity>('addNewNastavnik')));
  }

  updateNastavnik(nastavnik: NastavnikEntity): Observable<NastavnikEntity> {
    return this.httpClient
      .put<NastavnikEntity>(this.nastavnikUrl + nastavnik.id, nastavnik)
      .pipe(
        tap(a => this.log(`Izmenjen nastavnik sa id "${a.id}"`)),
        catchError(this.handleError<NastavnikEntity>('updateNastavnik')));
  }

  deleteNastavnik(nastavnik: NastavnikEntity): Observable<any> {
    return this.httpClient.delete<MessageService>(`${this.nastavnikUrl}${nastavnik.id}`)
      .pipe(
        tap(_ => this.log(`Obrisan nastavnik sa id "${nastavnik.id}"`)),
        catchError(this.handleError<NastavnikEntity>('deleteNastavnik')));
  }

  searchNastavnik(term: string): Observable<NastavnikEntity[]> {
    if (!term.trim()) {
      return this.getAllNastavnik();
    }

    return this.httpClient
      .get<NastavnikEntity[]>(`${this.nastavnikUrl}?naziv=${term}`)
      .pipe(
        tap(_ => this.log(`Nadjeni nastavnici sa nazivom "${term}"`)),
        catchError(this.handleError<NastavnikEntity[]>('searchNastavnik', []))
    );
  }

  private log(message: string) {
    this.messageService.add('NastavnikService: ' + message);
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}

