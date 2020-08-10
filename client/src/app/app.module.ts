import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { LayoutComponent } from './components/layout/layout.component';
import { LoginComponent } from './components/login/login.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HomeComponent } from './components/home/home.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatFormFieldModule} from '@angular/material/form-field';
import { MatIconModule} from '@angular/material/icon';
import { MatInputModule} from '@angular/material/input';
import { MatSelectModule} from '@angular/material/select';
import { FormsModule, ReactiveFormsModule, FormControl, FormGroup } from '@angular/forms';
import { MatButton, MatButtonModule } from '@angular/material/button';
import { MaterialModule } from './modules/material/material.module';
import { AuthInterceptor } from './helper/auth.interceptor';
import { JwtModule } from '@auth0/angular-jwt';
import { Page404Component } from './components/page404/page404.component';
import { ExpiredComponent } from './components/expired/expired.component';
import { UnauthorizedComponent } from './components/unauthorized/unauthorized.component';




@NgModule({
  declarations: [
    LayoutComponent,
    LoginComponent,
    HomeComponent,
    Page404Component,
    ExpiredComponent,
    UnauthorizedComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    JwtModule
  ],
  providers: [ { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },],
  bootstrap: [LayoutComponent]
})
export class AppModule { }
