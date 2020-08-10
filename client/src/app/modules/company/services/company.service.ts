import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Coupon } from 'src/app/models/coupon';
import { Company } from 'src/app/models/company';
import { catchError } from 'rxjs/operators';
import { ErrorService } from 'src/app/shared/services/error.service';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(private httpClient: HttpClient,
    private errorService: ErrorService) { }

  public addCoupon(coupon:Coupon){
    return this.httpClient.post<Coupon>("http://localhost:8080/company/buyCoupon",coupon).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public updateCoupon(coupon:Coupon){
    return this.httpClient.put<Coupon>("http://localhost:8080/company/updateCoupon",coupon).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public deleteCoupon(couponId:number){
    return this.httpClient.delete<number>("http://localhost:8080/company/delete/"+couponId).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public getAllCompanyCoupons(){
    return this.httpClient.get<Coupon[]>("http://localhost:8080/company/all").pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public getCouponsByMaxPrice(price:number){
    return this.httpClient.get<Coupon[]>("http://localhost:8080/company/couponsByPrice/"+price).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public getCouponsByCategory(category:String){
    return this.httpClient.get<Coupon[]>("http://localhost:8080/company/couponsByCat/"+category).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public getCompanyDetails(){
    return this.httpClient.get<Company>("http://localhost:8080/company/details").pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public deactivateCoupon(couponId:number){
    return this.httpClient.put<number>("http://localhost:8080/company/deactivateCoup/" + couponId,"").pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public activateCoupon(couponId:number){
    return this.httpClient.put<number>("http://localhost:8080/company/activateCoupon/" + couponId,"").pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }
}
