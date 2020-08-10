import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { CouponCustomerComponent } from './modules/customer/components/coupon-customer/coupon-customer.component';
import { AdminGuardGuard } from './shared/guards/admin-guard.guard';
import { CustomerGuardGuard } from './shared/guards/customer-guard.guard';
import { CompanyGuardGuard } from './shared/guards/company-guard.guard';
import { Page404Component } from './components/page404/page404.component';
import { UnauthorizedComponent } from './components/unauthorized/unauthorized.component';
import { ExpiredComponent } from './components/expired/expired.component';


const routes: Routes = [
  {path:"login", component: LoginComponent},
  {path:"home", component: HomeComponent},
  {path:"404", component:Page404Component},
  {path:"unauthorized", component:UnauthorizedComponent},
  {path:"expired",component:ExpiredComponent},
  {path:"", redirectTo:"home", pathMatch: "full"},
  {path:"customer", canActivateChild:[CustomerGuardGuard],
   loadChildren: ()=> import('./modules/customer/customer.module').then(m => m.CustomerModule)},
  {path:"admin",canActivateChild:[AdminGuardGuard],
   loadChildren: ()=> import('./modules/admin/admin.module').then(m => m.AdminModule)},
  {path:"company", canActivateChild:[CompanyGuardGuard],
   loadChildren: ()=> import('./modules/company/company.module').then(m => m.CompanyModule)},
  {path:"couponDetails", component: CouponCustomerComponent},
  {path:"**", redirectTo:'/404'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
