import { Component, OnInit, Input } from '@angular/core';
import { Coupon } from 'src/app/models/coupon';
import { ActivatedRoute } from '@angular/router';
import { CustomerService } from 'src/app/modules/customer/services/customer.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpResponse } from '@angular/common/http';
import { UtilityService } from '../services/utility.service';


@Component({
  selector: 'app-coupon',
  templateUrl: './coupon.component.html',
  styleUrls: ['./coupon.component.css']
})
export class CouponComponent implements OnInit {
  coupon:Coupon
  isOwned:boolean
  constructor(public route: ActivatedRoute, private customerService: CustomerService,
    private snackBar:MatSnackBar,private utility:UtilityService) {
  }

  ngOnInit() {
    this.isOwned = this.route.snapshot.queryParamMap.get("isOwned")=="true";
    console.log(this.isOwned)
   this.utility.findCoupon(parseInt(this.route.snapshot.paramMap.get('id'))).subscribe(
      coupon=>{
        this.coupon = coupon;
      },
      error=>this.snackBar.open(error, '', {duration: 3000})
    );

  }

  buyCoupon(){
    this.customerService.buyCoupon(this.coupon.id).subscribe(()=>{
      this.snackBar.open("Coupon purchased successfully",'',{duration:3000})
    },error=>this.snackBar.open(error, '', {duration: 3000})

   )
  }

}
