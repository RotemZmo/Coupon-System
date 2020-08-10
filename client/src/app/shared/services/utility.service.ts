import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Coupon } from 'src/app/models/coupon';
import { ErrorService } from './error.service';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UtilityService {

  constructor(private httpClient: HttpClient, private errorService:ErrorService) { }

  public findCoupon(couponId:number){
    return this.httpClient.get<Coupon>("http://localhost:8080/shared/coupon/" + couponId).pipe(
      catchError(err => this.errorService.errorHandler(err)));
  }
}
