import { Component, OnInit } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { Coupon } from 'src/app/models/coupon';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-all-coupons',
  templateUrl: './all-coupons.component.html',
  styleUrls: ['./all-coupons.component.css']
})
export class AllCouponsComponent implements OnInit {

  coupons:Coupon[];

  constructor(private customerService: CustomerService, private router:Router,
    private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.getCoupons();
  }

  getCoupons(){
    this.customerService.getAllAvaiableCoupons().subscribe(
      newCoupons=>this.coupons = newCoupons,
      error=>this.snackBar.open(error, '', {duration: 3000})
    )
  }


    navigate(coupon:Coupon){
      console.log("clicked");
      this.router.navigate(['/customer/couponDetails'],{state:coupon})
    }

}
