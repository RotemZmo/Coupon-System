import { Component, OnInit } from '@angular/core';
import { Coupon } from 'src/app/models/coupon';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UtilityService } from 'src/app/shared/services/utility.service';
import { CompanyService } from '../../services/company.service';

@Component({
  selector: 'app-coupon-details-company',
  templateUrl: './coupon-details-company.component.html',
  styleUrls: ['./coupon-details-company.component.css']
})
export class CouponDetailsCompanyComponent implements OnInit {
  coupon:Coupon

  constructor(private route: ActivatedRoute, private companyService: CompanyService,
    private snackBar:MatSnackBar,private utility:UtilityService, private router:Router) { }

  ngOnInit(): void {
    this.utility.findCoupon(parseInt(this.route.snapshot.paramMap.get('id'))).subscribe(
      coupon=>{
        this.coupon = coupon;
      },
      err=>{
        console.log(err)
      }
    );
  }

  deleteCoupon(){
    this.companyService.deleteCoupon(this.coupon.id).subscribe(
      ()=>{
        this.snackBar.open("Coupon deleted successfuly",'',{duration:3000})
      },err=>{
        this.snackBar.open(err,'',{duration:3000})
      }
    )
  }

  deactivateCoupon(){
    this.companyService.deactivateCoupon(this.coupon.id).subscribe(
      ()=>{
        this.snackBar.open("Coupon deactivated successfuly",'',{duration:3000})
      },err=>{
        this.snackBar.open(err,'',{duration:3000})
      }
    )
  }

  activateCoupon(){
    this.companyService.activateCoupon(this.coupon.id).subscribe(
      ()=>{
        this.snackBar.open("Coupon activated successfuly",'',{duration:3000})
      },err=>{
        this.snackBar.open(err,'',{duration:3000})
      }
    )
  }

  updateCoupon(){
    this.router.navigate(['manageCoupons'],{queryParams:{couponId:this.coupon.id},relativeTo:this.route.parent})
  }
}
