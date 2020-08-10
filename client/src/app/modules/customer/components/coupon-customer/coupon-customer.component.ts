import { Component, OnInit, Input } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CustomerService } from '../../services/customer.service';
import { Coupon } from 'src/app/models/coupon';

@Component({
  selector: 'app-coupon-customer',
  templateUrl: './coupon-customer.component.html',
  styleUrls: ['./coupon-customer.component.css']
})
export class CouponCustomerComponent implements OnInit {
  @Input() coupon:Coupon

  constructor( private customerService: CustomerService,
    private snackBar:MatSnackBar) { }

  ngOnInit(): void {
  }

  buyCoupon(){
    this.customerService.buyCoupon(this.coupon.id).subscribe(()=>{
      this.snackBar.open("Coupon purchased successfully",'',{duration:3000})
    },error=>this.snackBar.open(error, '', {duration: 3000})

   )
  }

}
