import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyDetailsComponent } from './components/my-details/my-details.component';
import { MyPurchasesComponent } from './components/my-purchases/my-purchases.component';
import { AllCouponsComponent } from './components/all-coupons/all-coupons.component';
import { LayoutComponent } from './components/customer-layout/layout.component';
import { CustomerRoutingModule } from './customer-routing.module';
import { SharedModuleModule } from 'src/app/shared/shared-module/shared-module.module';
import { CouponComponent } from '../../shared/coupon/coupon.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../material/material.module';
import { CouponCustomerComponent } from './components/coupon-customer/coupon-customer.component';



@NgModule({
  declarations: [LayoutComponent, MyDetailsComponent, MyPurchasesComponent, AllCouponsComponent, CouponCustomerComponent, ],
  imports: [
    CommonModule,
    CustomerRoutingModule,
    SharedModuleModule,
    ReactiveFormsModule,
    MaterialModule
  ],
  providers: [],
  bootstrap: [LayoutComponent]
})
export class CustomerModule { }
