import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { TokenStorageService } from '../../services/token/token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticatedRequestService {

  constructor(private httpClient: HttpClient, private tokenStorage: TokenStorageService) { }

  get(apiUrl:string): Observable<any> {
    let httpHeaders = new HttpHeaders().set('Authorization', 'Bearer ' + this.tokenStorage.getToken());
    // httpHeaders.set('Content-Type', 'application/json');
    return this.httpClient.get(
      apiUrl,
      {headers: httpHeaders}
      ).pipe(
      catchError(this.handleError)
    );
  }

  post(data: any, apiUrl:any,): Observable<any> {
    let httpHeaders = new HttpHeaders().set('Authorization', 'Bearer ' + this.tokenStorage.getToken());

    return this.httpClient.post(apiUrl, data,{headers: httpHeaders}).pipe(
      catchError(this.handleError)
    );
  }

  put(data: any, apiUrl:string): Observable<any> {
    let httpHeaders = new HttpHeaders().set('Authorization', 'Bearer ' + this.tokenStorage.getToken());

    return this.httpClient.put(apiUrl, data,{headers: httpHeaders}).pipe(
      catchError(this.handleError)
    );
  }

  patch(data: any, apiUrl:string): Observable<any> {
    let httpHeaders = new HttpHeaders().set('Authorization', 'Bearer ' + this.tokenStorage.getToken());

    return this.httpClient.patch(apiUrl, data,{headers: httpHeaders}).pipe(
      catchError(this.handleError)
    );
  }

  delete(apiUrl:string): Observable<any> {
    let httpHeaders = new HttpHeaders().set('Authorization', 'Bearer ' + this.tokenStorage.getToken());

    return this.httpClient.delete(apiUrl,{headers: httpHeaders}).pipe(
      catchError(this.handleError)
    );
  }


  // getUserBoard(): Observable<any> {
  //   let httpHeaders = new HttpHeaders()
  //   .set('Authorization', 'Bearer ' + this.tokenStorage.getToken() );

  //   return this.httpClient.get('http://localhost:8080/api/test/' + 'user', { responseType: 'text',headers: httpHeaders });
  // }


  // Handle API errors
handleError(error: HttpErrorResponse) {
  if (error.error instanceof ErrorEvent) {
    console.error('An error occurred:', error.error.message);
  } else {
    console.error(
      `Backend returned code ${error.status}, ` +
      `body was: ${error.error}`);
  }
  return throwError(
    'Something bad happened; please try again later.');
};



}
