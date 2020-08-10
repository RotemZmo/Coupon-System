import { Component, OnInit, Input } from '@angular/core';
import { Coupon } from 'src/app/models/coupon';
import { Company } from 'src/app/models/company';
import { CompanyService } from '../../services/company.service';
import { FormBuilder, FormControl, Validators, FormGroup } from '@angular/forms';
import { CouponCategory } from 'src/app/models/coupon-category';
import { ActivatedRoute } from '@angular/router';
import { UtilityService } from 'src/app/shared/services/utility.service';
import { MatSnackBar } from '@angular/material/snack-bar';



@Component({
  selector: 'app-coupon-managment',
  templateUrl: './coupon-managment.component.html',
  styleUrls: ['./coupon-managment.component.css']
})
export class CouponManagmentComponent implements OnInit {
   coupon:Coupon
   company:Company
    couponId:number
    catTest: CouponCategory[] =[
      CouponCategory.Electronics,
      CouponCategory.Food,
      CouponCategory.Spa,
      CouponCategory.Sport,
      CouponCategory.Vacation
    ]


    type = new FormControl(CouponCategory.Electronics,Validators.required)
    couponForm:FormGroup
    title = new FormControl('',Validators.required)
    description = new FormControl('',Validators.required)
    price = new FormControl(1,Validators.required)
    amount = new FormControl(1,Validators.required)
    category = new FormControl('',Validators.required)
    endDate = new FormControl(Date.now,Validators.required)
    image = new FormControl('',Validators.required)

  constructor(private companyService:CompanyService,private fb:FormBuilder,private route:ActivatedRoute, private sharedService:UtilityService,
    private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.couponForm = this.fb.group({
      title: this.title,
      description: this.description,
      price: this.price,
      amount: this.amount,
      type:this.type,
      endDate:this.endDate,
      image: this.image
    })
    if(this.route.snapshot.queryParamMap.get("couponId")!=undefined){
    this.couponId = parseInt(this.route.snapshot.queryParamMap.get("couponId"));
    this.getCoupon()
  }
    this.setCompany()
  }


  addCoupon(){
    if (this.coupon==undefined){
    this.coupon = new Coupon(null,this.couponForm.get("title").value,
    this.couponForm.get("description").value,
    null,
    this.couponForm.get("amount").value,
    this.couponForm.get("price").value,
    new Date(),
    this.couponForm.get("endDate").value,
    this.couponForm.get("type").value,
    this.couponForm.get("image").value,true)

  } else {
    this.coupon.title = this.couponForm.get("title").value
    this.coupon.description = this.couponForm.get("description").value
    this.coupon.amount = this.couponForm.get("amount").value
    this.coupon.price = this.couponForm.get("price").value
    this.coupon.endDate = this.couponForm.get("endDate").value
    this.coupon.type = this.couponForm.get("type").value
    this.coupon.image = this.couponForm.get("image").value
  }
  this.couponForm.disable
    this.companyService.addCoupon(this.coupon).subscribe(
      ()=>this.snackBar.open('Coupon successfully added', '', {duration: 3000}),
      error=>this.snackBar.open(error, '', {duration: 3000})
    )
  }

  updateCoupon(){
    this.couponForm.disable
    this.coupon.title = this.couponForm.get("title").value
    this.coupon.description = this.couponForm.get("description").value
    this.coupon.amount = this.couponForm.get("amount").value
    this.coupon.price = this.couponForm.get("price").value
    this.coupon.endDate = this.couponForm.get("endDate").value
    this.coupon.type = this.couponForm.get("type").value
    this.coupon.image = this.couponForm.get("image").value
    this.companyService.updateCoupon(this.coupon).subscribe(
      ()=>this.snackBar.open('Coupon successfully updated', '', {duration: 3000}),
      error=>this.snackBar.open(error, '', {duration: 3000})
    )
  }

  getCoupon(){
    this.sharedService.findCoupon(this.couponId).subscribe(
      coupon=> {this.coupon=coupon
        this.setForm()},
        error=>this.snackBar.open(error, '', {duration: 3000})
    )
  }

  setForm(){
    this.couponForm.setValue({title:this.coupon.title,
      description:this.coupon.description,
      amount: this.coupon.amount,
      price: this.coupon.price,
      endDate:this.coupon.endDate,
      type: this.coupon.type,
      image:this.coupon.image
    })
  }

  setCompany(){
    this.companyService.getCompanyDetails().subscribe(
      company=>{
        this.company=company
      },error=>this.snackBar.open(error, '', {duration: 3000})
    )
  }

}
