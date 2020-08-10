import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ClientType } from 'src/app/models/clientType';
import { Company } from 'src/app/models/company';
import { Customer } from 'src/app/models/customer';
import { AdminService } from '../../services/admin.service';
import { Router, ActivatedRoute } from '@angular/router';
import { NewUser } from 'src/app/models/new-user';
import { User } from 'src/app/models/user';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {
  hide = true;
  userForm:FormGroup
  userId:number
  company:Company = new Company(1,'default','a@a',0.1,0,true)
  customer:Customer = new Customer(1,'a','a','a@a',true)
  user:NewUser
  oldUser:User
  clientType:ClientType[] =[
    ClientType.Company,
    ClientType.Customer,
    ClientType.Admin
  ]
  cType:any
  email = new FormControl('',Validators.required)
  password = new FormControl('',Validators.required)
  firstName = new FormControl('',Validators.required)
  lastName = new FormControl('',Validators.required)
  name = new FormControl('',Validators.required)
  fee = new FormControl(0.10,Validators.required)

  constructor(private adminService:AdminService, private fb:FormBuilder, private router:Router, private route:ActivatedRoute,
    private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.cType = this.route.snapshot.queryParamMap.get("cType")
    if(this.route.snapshot.queryParamMap.get("userId")!=undefined){
    this.userId = parseInt(this.route.snapshot.queryParamMap.get("userId"))}
    if(this.userId!=undefined && this.cType!=undefined ){
      this.userForm = this.fb.group({
        email: this.email,
        firstName: this.firstName,
        lastName: this.lastName,
        name: this.name,
        fee: this.fee
      })
      switch(this.cType){
        case ClientType.Company:
          this.setCompany()
          break;
        case ClientType.Customer:
          this.setCustomer();
          break;
      }
      this.adminService.getUser(this.userId).subscribe(
        user=>this.oldUser = user,
        error=>this.snackBar.open(error, '', {duration: 3000})
      )

    } else {
      this.userForm = this.fb.group({
        email: this.email,
        firstName: this.firstName,
        lastName: this.lastName,
        name: this.name,
        fee: this.fee,
        password:this.password
      })
    }

  }

  addCustomer(){
    this.user = new NewUser();
    this.user.clientType = ClientType.Customer
    this.user.email = this.userForm.get('email').value
    this.user.firstName = this.userForm.get('firstName').value
    this.user.lastName = this.userForm.get('lastName').value
    this.user.password = this.userForm.get('password').value
    this.userForm.disable
    this.adminService.addUser(this.user).subscribe(
      ()=>this.snackBar.open('New customer successfully added', '', {duration: 3000}),
      error=>this.snackBar.open(error, '', {duration: 3000})
    )
  }

  updateCustomer(){
    this.customer.email = this.userForm.get('email').value
    this.customer.firstName = this.userForm.get('firstName').value
    this.customer.lastName = this.userForm.get('lastName').value
    this.oldUser.email = this.userForm.get('email').value
    this.adminService.updateCustomer(this.customer).subscribe(
      ()=>this.snackBar.open('Customer updated successfully added', '', {duration: 3000}),
      error=>this.snackBar.open(error, '', {duration: 3000})
    )
    this.adminService.updateUser(this.oldUser).subscribe(
      ()=>console.log("success user"),
      error=>this.snackBar.open(error, '', {duration: 3000})
    )
    this.userForm.disable
  }

  addCompany(){
    this.user = new NewUser();
    this.user.clientType = ClientType.Company
    this.user.email = this.userForm.get('email').value
    this.user.name = this.userForm.get('name').value
    this.user.fee = this.userForm.get('fee').value
    this.user.password = this.userForm.get('password').value
    this.userForm.disable
    this.adminService.addUser(this.user).subscribe(
      ()=>this.snackBar.open('New company successfully added', '', {duration: 3000}),
      error=>this.snackBar.open(error, '', {duration: 3000})
    )
  }

  updateCompany(){
    this.company.email = this.userForm.get('email').value
    this.company.fee = this.userForm.get('fee').value
    this.oldUser.email = this.userForm.get('email').value
    this.adminService.updateCompany(this.company).subscribe(
      ()=>this.snackBar.open('Company successfully updated', '', {duration: 3000}),
      error=>this.snackBar.open(error, '', {duration: 3000})
    )
    this.adminService.updateUser(this.oldUser).subscribe(
      ()=>console.log("success user"),
      error=>this.snackBar.open(error, '', {duration: 3000})
    )
    this.userForm.disable
  }

  setCompany(){
    this.adminService.getCompany(this.userId).subscribe(
      company=>{this.company=company
        this.setForm()
      },
      error=> this.snackBar.open(error, '', {duration: 3000})
    )
  }

  setCustomer(){
    this.adminService.getCustomer(this.userId).subscribe(
      customer=>{this.customer=customer
        this.setForm()
      },
      error=>this.snackBar.open(error, '', {duration: 3000})
    )
  }

  setForm(){
    switch(this.cType){
      case ClientType.Company:
        this.userForm.setValue({email:this.company.email,
          name:this.company.name,
          fee: this.company.fee,
          firstName:null,
          lastName:null,})
          console.log(this.userForm)
        break;
      case ClientType.Customer:
        this.userForm.setValue({email:this.customer.email,
          firstName:this.customer.firstName,
          lastName: this.customer.lastName,
          name:null,
          fee: null,
          password:null})
        break;
    }
  }
}
