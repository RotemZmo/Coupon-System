import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { CompanyLayoutComponent } from './components/company-layout/company-layout.component';
import { AllCouponsComponent } from './components/all-coupons/all-coupons.component';
import { CouponManagmentComponent } from './components/coupon-managment/coupon-managment.component';
import { CompanyDetailsComponent } from './components/company-details/company-details.component';
import { CouponDetailsCompanyComponent } from './components/coupon-details-company/coupon-details-company.component';



const routes: Routes = [
{
  path:'',
  component:CompanyLayoutComponent,
  children:[{
    path:'allCoupons',
    component:AllCouponsComponent
  },
  {
    path:'myDetails',
    component:CompanyDetailsComponent
  },
  {
    path:'manageCoupons',
    component:CouponManagmentComponent
  },
  {
    path: 'couponDetails/:id',
    component:CouponDetailsCompanyComponent
  }]
}
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class CompanyRoutingModule { }
