import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { AdminLayoutComponent } from './components/admin-layout/admin-layout.component';
import { AdminManageUsersComponent } from './components/admin-manage-users/admin-manage-users.component';
import { AddUserComponent } from './components/add-user/add-user.component';
import { AdminGuardGuard } from 'src/app/shared/guards/admin-guard.guard';



const routes: Routes = [{
  path:'',
  component:AdminLayoutComponent,
  children:[{
    path:'home',
    component:AdminManageUsersComponent
  },
  {
    path:'addUser',
    component: AddUserComponent
  }

  ],
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
export class AdminRoutingModule { }
