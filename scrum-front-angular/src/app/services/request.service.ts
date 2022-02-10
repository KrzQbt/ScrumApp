import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RequestService {
  headers = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private httpClient: HttpClient) { }

  backlog(apiUrl:string): Observable<any> {
    return this.httpClient.get(apiUrl).pipe(
      catchError(this.handleError)
    );
  }

  get(apiUrl:string): Observable<any> {
    return this.httpClient.get(apiUrl).pipe(
      catchError(this.handleError)
    );
  }




  post(data: any, apiUrl:any): Observable<any> {
    return this.httpClient.post(apiUrl, data).pipe(
      catchError(this.handleError)
    );
  }

  // Edit/ Update 
  update(id: any, data: any, apiUrl:string): Observable<any> {
    return this.httpClient.put(apiUrl, data).pipe(
      catchError(this.handleError)
    );
  }

  patch(data: any, apiUrl:string){
    return this.httpClient.patch(apiUrl, data).pipe(
      catchError(this.handleError)
    );
  }

  // Delete
  delete(apiUrl:string): Observable<any> {
    return this.httpClient.delete(apiUrl).pipe(
      catchError(this.handleError)
    );
  }










  
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
