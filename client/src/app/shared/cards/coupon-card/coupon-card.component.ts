import { Component, OnInit, Input } from '@angular/core';
import { Coupon } from 'src/app/models/coupon';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-coupon-card',
  templateUrl: './coupon-card.component.html',
  styleUrls: ['./coupon-card.component.css']
})
export class CouponCardComponent implements OnInit {

  @Input() coupon:Coupon
  @Input() isOwned:boolean
  constructor( private router:Router,private route:ActivatedRoute) { }

  ngOnInit(): void {
    console.log(this.isOwned)
  }

  couponDetails(){
    this.router.navigate(['couponDetails',this.coupon.id],{queryParams:{isOwned:this.isOwned},relativeTo:this.route.parent})

  }

}
