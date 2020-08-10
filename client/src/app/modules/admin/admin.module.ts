import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminRoutingModule } from './admin-routing.module';
import { MaterialModule } from '../material/material.module';
import { SharedModuleModule } from 'src/app/shared/shared-module/shared-module.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AdminLayoutComponent } from './components/admin-layout/admin-layout.component';
import { AdminManageUsersComponent } from './components/admin-manage-users/admin-manage-users.component';

import { AddUserComponent } from './components/add-user/add-user.component';



@NgModule({
  declarations: [AdminLayoutComponent, AdminManageUsersComponent, AddUserComponent],
  imports: [
    CommonModule,
    AdminRoutingModule,
    MaterialModule,
    SharedModuleModule,
    FormsModule,
    ReactiveFormsModule
  ],
  bootstrap:[AdminLayoutComponent]
})
export class AdminModule { }
