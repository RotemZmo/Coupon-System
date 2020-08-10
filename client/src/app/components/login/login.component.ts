import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { MyErrorStateMatcher } from 'src/app/models/my-error-state-matcher';
import { MaterialModule } from 'src/app/modules/material/material.module';
import { Router } from '@angular/router';
import { Subscription, Observable } from 'rxjs';
import { ErrorStateMatcher } from '@angular/material/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';
import { TokenStorageService } from 'src/app/shared/services/token-storage.service';



interface ClientTypes {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})


export class LoginComponent implements OnInit, OnDestroy {

  private sub:Subscription

  loginForm: FormGroup

  hide = true;

  matcher = new ErrorStateMatcher()

  email = new FormControl('',
    [Validators.required,
      Validators.email])
  password = new FormControl('',
    [Validators.required,
    Validators.minLength(4),
    Validators.maxLength(20),
    ])
  clientType = new FormControl('', Validators.required)

  clientTypes: ClientTypes[] = [
    {value:'Customer', viewValue: 'Customer'},
    {value:'Company', viewValue: 'Company'},
    {value:'Admin', viewValue: 'Admin'}]



  constructor(private fb:FormBuilder, private router:Router, private snackBar:MatSnackBar
    ,private auth:AuthenticationService,private tokenStorage: TokenStorageService) {

  }

  ngOnInit(){
    this.loginForm = this.fb.group({
      email: this.email,
      password: this.password,
      clientType: this.clientType
    })
    this.loginForm.valueChanges.subscribe(console.log)
  }

  ngOnDestroy(): void {
    if (this.sub) {
      this.sub.unsubscribe()
    }
  }

  onSubmit() {
    this.loginForm.disable()
    this.sub = this.auth.login(this.loginForm.value).subscribe(
      respone => {
        this.tokenStorage.saveToken(respone.headers.get("Authorization"))
        if (this.loginForm.value.clientType === 'Admin') {
          this.router.navigate(['/admin'])
        } else if (this.loginForm.value.clientType === 'Company') {
          this.router.navigate(['/company'])
        } else if (this.loginForm.value.clientType === 'Customer') {
          this.router.navigate(['/customer'])
        }
      },
      error => {
        this.snackBar.open("Login failed", '', {duration: 3000})
        this.loginForm.enable()
      },

    )
  }

}
