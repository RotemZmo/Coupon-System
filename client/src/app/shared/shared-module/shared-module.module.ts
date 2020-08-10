import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CouponComponent } from '../coupon/coupon.component';
import { CouponCardComponent } from '../cards/coupon-card/coupon-card.component';
import { MaterialModule } from 'src/app/modules/material/material.module';


const shared =[CouponComponent,CouponCardComponent]

@NgModule({
  declarations: [shared],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [
    shared
  ]
})
export class SharedModuleModule { }
