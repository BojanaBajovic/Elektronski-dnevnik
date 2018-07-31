import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { MessageService } from '../services/message.service';

import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { AdminEntity } from '../entities/admin-entity';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private adminUrl = environment.apiBaseUrl + '/admin/';

  constructor(private httpClient: HttpClient, private messageService: MessageService) { }

  // deleteAdmin(id: number): Observable<any> {
  //   return this.httpClient.delete<any>(this.adminUrl + '/' + id)
  //   .pipe(
  //     tap(a => this.log(`deleted admin id: ${id}`)),
  //     catchError(this.handleError<any>('deleteAdmin')));
  // }

  deleteAdmin (id: number): Observable<any> {
    return this.httpClient
    .delete<any>(this.adminUrl + id)
      .pipe(
        tap(a => this.log(`Obrisan administrator sa id "${a.id}"`)),
        catchError(this.handleError<AdminEntity>('deleteAdmin')));

  }

  getAdminById(id: number): Observable<AdminEntity> {
    return this.httpClient
      .get<AdminEntity>(this.adminUrl + id)
      .pipe(
        tap(a => this.log(`Učitan admin sa id "${a.id}"`)),
        catchError(this.handleError<AdminEntity>('getAdminById')));
  }

  getAllAdmin(): Observable<AdminEntity[]> {
    return this.httpClient
      .get<AdminEntity[]>(this.adminUrl)
      .pipe(
        tap(_ => this.log(`Učitani admini`)),
        catchError(this.handleError<AdminEntity[]>('getAllAdmin')));
  }

  addNewAdmin(admin: AdminEntity): Observable<AdminEntity> {
    return this.httpClient
      .post<AdminEntity>(this.adminUrl, admin)
      .pipe(
        tap(a => this.log(`Dodat admmin sa id "${a.id}"`)),
        catchError(this.handleError<AdminEntity>('addNewAdmin')));
  }

  updateAdmin(admin: AdminEntity): Observable<AdminEntity> {
    return this.httpClient
      .put<AdminEntity>(this.adminUrl + admin.id, admin)
      .pipe(
        tap(a => this.log(`Izmenjen admin sa id "${a.id}"`)),
        catchError(this.handleError<AdminEntity>('updateAdmin')));
  }

  searchAdmin(term: string): Observable<AdminEntity[]> {
    if (!term.trim()) {
      return this.getAllAdmin();
    }

    return this.httpClient
      .get<AdminEntity[]>(`${this.adminUrl}?naziv=${term}`)
      .pipe(
        tap(_ => this.log(`Nadjeni admini sa nazivom "${term}"`)),
        catchError(this.handleError<AdminEntity[]>('searchAdmin', []))
    );
  }

  private log(message: string) {
    this.messageService.add('AdminService: ' + message);
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}

