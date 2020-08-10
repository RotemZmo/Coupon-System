import { Component, OnInit, Inject } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { Customer } from 'src/app/models/customer';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-my-details',
  templateUrl: './my-details.component.html',
  styleUrls: ['./my-details.component.css']
})
export class MyDetailsComponent implements OnInit {

  customer:Customer

  constructor(private customerService:CustomerService, private snackBar:MatSnackBar) { }

  ngOnInit(): void {
    this.getCustomerDetails()
  }

  getCustomerDetails(){
    this.customerService.getCustomerDetails().subscribe(
      newCustomer=>this.customer = newCustomer,
      error=>this.snackBar.open(error, '', {duration: 3000})
    )
  }

}
