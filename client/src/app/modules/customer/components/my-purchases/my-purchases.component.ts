import { Component, OnInit } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { Coupon } from 'src/app/models/coupon';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { CouponCategory } from 'src/app/models/coupon-category';
import { MatSnackBar } from '@angular/material/snack-bar';



@Component({
  selector: 'app-my-purchases',
  templateUrl: './my-purchases.component.html',
  styleUrls: ['./my-purchases.component.css']
})
export class MyPurchasesComponent implements OnInit {

  myCoupons:Coupon[]
  filtered:boolean = false
  filterByPriceBool:boolean = false
  filterByCategoryBool:boolean = false
  myFilteredCoupons:Coupon[]
  categories: CouponCategory[]=[
    CouponCategory.Electronics,
    CouponCategory.Food,
    CouponCategory.Spa,
    CouponCategory.Sport,
    CouponCategory.Vacation
  ]
  filterForm:FormGroup
  maxPrice= new FormControl(1)
  type = new FormControl(CouponCategory.Electronics,Validators.required)


  constructor(private customerService:CustomerService,private fb:FormBuilder, private snackBar:MatSnackBar) { }

  ngOnInit(): void {
    this.getMyPurchases()
    this.filterForm = this.fb.group({
      type:this.type,
      maxPriceSelected: this.maxPrice,
    })
    this.filterForm.valueChanges.subscribe(console.log)

  }

  getMyPurchases(){
    this.customerService.getAllCustomerCoupons().subscribe(
      myPurchases=>this.myCoupons=myPurchases,
      error=>this.snackBar.open(error, '', {duration: 3000})
    )
  }

  filterByPrice(){
    this.filterByPriceBool = true;
    this.filterByCategoryBool = false
  }

  filterByCategory(){
    this.filterByCategoryBool = true
    this.filterByPriceBool = false
  }

  getCouponsByCategory(){
    this.filtered=true
    this.customerService.getCustomerCouponsByCategory(this.filterForm.get('type').value).subscribe(
      newCoupons=>{this.myFilteredCoupons=newCoupons;
      console.log(newCoupons)},
      error=>this.snackBar.open(error, '', {duration: 3000})
    )

  }

  getCouponsByMaxPrice(){
    this.filtered=true
    console.log(this.maxPrice)
    console.log(this.filterForm.get('maxPriceSelected').value)
    this.customerService.getCustomerCouponsByPrice(this.filterForm.get('maxPriceSelected').value).subscribe(
      newCoupons=>{this.myFilteredCoupons=newCoupons},
      error=>this.snackBar.open(error, '', {duration: 3000})
    )
  }

  filter(){
    this.filtered=true;
  }

  unfilter(){
    this.filtered=false
    this.filterByPriceBool = false
    this.filterByCategoryBool = false
  }

}
