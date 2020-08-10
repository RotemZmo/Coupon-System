import { Injectable } from '@angular/core';
import { CanActivate, CanActivateChild, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable,of } from 'rxjs';
import { AuthenticationService } from '../services/authentication.service';
import { TokenStorageService } from '../services/token-storage.service';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class CompanyGuardGuard implements CanActivate, CanActivateChild {

  public jwtHelper: JwtHelperService = new JwtHelperService();


  constructor(private authService: AuthenticationService, private tokenService:TokenStorageService,
    private router: Router) { }

    canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
          if (this.authService.isAuthenticated("Company")) {
              return of(true)
          } else{
            if(this.jwtHelper.isTokenExpired(this.tokenService.getToken())){
              this.tokenService.logout
              this.router.navigate(['expired'])
              return of(false);
            } else{
            this.router.navigate(['unauthorized']);
            return of(false);
            }
          }
  }
  canActivateChild(
    childRoute: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      return this.canActivate(childRoute, state)
  }

}
