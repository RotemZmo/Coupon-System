import { Injectable } from '@angular/core';

const JWT = 'jwt-token';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() { }

  public logout(){
    window.sessionStorage.clear();
  }

  public saveToken(token: string){
    window.sessionStorage.setItem(JWT,token);
  }

  public getToken(): string{
    return window.sessionStorage.getItem(JWT);
  }


}
