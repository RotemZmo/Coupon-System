import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { LayoutComponent } from './components/customer-layout/layout.component';
import { AllCouponsComponent } from './components/all-coupons/all-coupons.component';
import { MyPurchasesComponent } from './components/my-purchases/my-purchases.component';
import { MyDetailsComponent } from './components/my-details/my-details.component';
import { CouponComponent } from 'src/app/shared/coupon/coupon.component';

const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [{
      path: 'allCoupons',
      component: AllCouponsComponent
      },
      {
        path: 'myCoupons',
        component: MyPurchasesComponent
      },
      {
        path: 'myDetails',
        component: MyDetailsComponent
      },
     {
    path: 'couponDetails/:id',
    component: CouponComponent
    }
    ]
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
export class CustomerRoutingModule { }
