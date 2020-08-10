import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Coupon } from 'src/app/models/coupon';
import { Customer } from 'src/app/models/customer';
import { ErrorService } from 'src/app/shared/services/error.service';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private httpClient: HttpClient,
    private errorService: ErrorService) { }

  public buyCoupon(couponId:number){
    return this.httpClient.post<number>("http://localhost:8080/customer/buy/" + couponId,"").pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public getAllCustomerCoupons(){
    return this.httpClient.get<Coupon[]>("http://localhost:8080/customer/all").pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public getCustomerCouponsByCategory(category:string){
    return this.httpClient.get<Coupon[]>("http://localhost:8080/customer/byCategory/"+category).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public getCustomerCouponsByPrice(price:number){
    return this.httpClient.get<Coupon[]>("http://localhost:8080/customer/byPrice/"+price).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public getCustomerDetails(){
    return this.httpClient.get<Customer>("http://localhost:8080/customer/details").pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public getAllAvaiableCoupons(){
    return this.httpClient.get<Coupon[]>("http://localhost:8080/customer/home").pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }
}
