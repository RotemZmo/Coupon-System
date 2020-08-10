import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CompanyRoutingModule } from './company-routing.module';
import { MaterialModule } from '../material/material.module';
import { SharedModuleModule } from 'src/app/shared/shared-module/shared-module.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CompanyLayoutComponent } from './components/company-layout/company-layout.component';
import { CompanyDetailsComponent } from './components/company-details/company-details.component';
import { CouponManagmentComponent } from './components/coupon-managment/coupon-managment.component';
import { AllCouponsComponent } from './components/all-coupons/all-coupons.component';
import { CouponDetailsCompanyComponent } from './components/coupon-details-company/coupon-details-company.component';




@NgModule({
  declarations: [CompanyLayoutComponent, CompanyDetailsComponent, CouponManagmentComponent, AllCouponsComponent, CouponDetailsCompanyComponent],
  imports: [
    CommonModule,
    CompanyRoutingModule,
    MaterialModule,
    SharedModuleModule,
    ReactiveFormsModule,
    FormsModule
  ],
  bootstrap: [CompanyLayoutComponent]
})
export class CompanyModule { }
