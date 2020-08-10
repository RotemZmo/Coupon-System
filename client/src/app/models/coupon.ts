import { Company } from './company';
import { Type } from '@angular/core';
import { CouponCategory } from './coupon-category';

export class Coupon {

  constructor(public id:number,
    public title:string,
    public description:string,
    public company:Company,
    public amount:number,
    public price:number,
    public startDate:Date,
    public endDate:Date,
    public type:CouponCategory,
    public image:string,
    public active:boolean
    ){}

}
