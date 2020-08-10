import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/user';
import { ErrorService } from './error.service';
import { catchError, tap } from "rxjs/operators"
import { Router } from "@angular/router"
import { JwtHelperService } from '@auth0/angular-jwt';


const AUTH_API = 'http://localhost:8080/login';
const JWT = 'jwt-token';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {

  public jwtHelper: JwtHelperService = new JwtHelperService();

  constructor(private http: HttpClient,
    private errorService: ErrorService) {
}

login(credentials):Observable<HttpResponse<Object>>{
  return this.http.post(AUTH_API, {
    email: credentials.email,
    password: credentials.password
  },{observe:"response", headers: new HttpHeaders({ 'Content-Type': 'application/json' })}).pipe(
    catchError(err => this.errorService.errorHandler(err)));
}

public isAuthenticated(role:string):boolean{
  const token = sessionStorage.getItem(JWT)
  const tokenData = this.jwtHelper.decodeToken(token)
  const authority = tokenData.authorities[0].authority
  return (!this.jwtHelper.isTokenExpired(token) && (authority==="ROLE_"+role))
}

}

