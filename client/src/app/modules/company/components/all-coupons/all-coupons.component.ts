import { Component, OnInit, Input } from '@angular/core';
import { CompanyService } from '../../services/company.service';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { Coupon } from 'src/app/models/coupon';
import { CouponCategory } from 'src/app/models/coupon-category';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-all-coupons',
  templateUrl: './all-coupons.component.html',
  styleUrls: ['./all-coupons.component.css']
})
export class AllCouponsComponent implements OnInit {

  companyCoupons:Coupon[]
  companyCouponsDisplay:Coupon[]
  filtered:boolean = false
  filterByPriceBool:boolean = false
  filterByCategoryBool:boolean = false
  companyFilteredCoupons:Coupon[]
  companyFilteredCouponsDisplay:Coupon[]
  categories: CouponCategory[]=[
    CouponCategory.Electronics,
    CouponCategory.Food,
    CouponCategory.Spa,
    CouponCategory.Sport,
    CouponCategory.Vacation
  ]
  filterForm:FormGroup
  type = new FormControl(CouponCategory.Electronics,Validators.required)
  maxPrice= new FormControl(1)
  selectCategory = new FormControl('')
  showInactive = false;

  constructor(private companyService:CompanyService,private fb:FormBuilder,
    private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.getCompanyCoupons()
    this.filterForm = this.fb.group({
      type:this.type,
      maxPriceSelected: this.maxPrice,
    })
  }

  getCompanyCoupons(){
    this.companyService.getAllCompanyCoupons().subscribe(
      coupons=>{
        this.companyCoupons = coupons
        this.companyCouponsDisplay =[]
      for(let coupon of this.companyCoupons){
        if(coupon.active === true)
        this.companyCouponsDisplay.push(coupon)
      }
    },error=>this.snackBar.open(error, '', {duration: 3000})
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
    this.companyService.getCouponsByCategory(this.filterForm.get('type').value).subscribe(
      newCoupons=>{this.companyFilteredCoupons=newCoupons
        if(!this.showInactive){
          this.companyFilteredCouponsDisplay = []
          for(let coupon of this.companyFilteredCoupons){
            if(coupon.active === true)
            this.companyFilteredCouponsDisplay.push(coupon)
          }
        } else{
          this.companyFilteredCouponsDisplay = this.companyFilteredCouponsDisplay
        }

      },
      error=>this.snackBar.open(error, '', {duration: 3000})
    )

  }

  getCouponsByMaxPrice(){
    this.filtered=true
    console.log(this.maxPrice)
    console.log(this.filterForm.get('maxPriceSelected').value)
    this.companyService.getCouponsByMaxPrice(this.filterForm.get('maxPriceSelected').value).subscribe(
      newCoupons=>{this.companyFilteredCoupons=newCoupons
        if(!this.showInactive){
          this.companyFilteredCouponsDisplay = []
          for(let coupon of this.companyFilteredCoupons){
            if(coupon.active === true)
            this.companyFilteredCouponsDisplay.push(coupon)
          }
        } else{
          this.companyFilteredCouponsDisplay = this.companyFilteredCouponsDisplay
        }

      },
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

  onChange(value){
    if (value.checked === true) {
      this.companyCouponsDisplay = this.companyCoupons
      this.companyFilteredCouponsDisplay = this.companyFilteredCoupons
    } else {
      if(this.filtered){
        this.companyFilteredCouponsDisplay = []
        for(let coupon of this.companyFilteredCoupons){
          if(coupon.active === true)
          this.companyFilteredCouponsDisplay.push(coupon)
        }
      } else{
      this.companyCouponsDisplay = []
      for(let coupon of this.companyCoupons){
        if(coupon.active === true)
        this.companyCouponsDisplay.push(coupon)
        }
      }
    }
  }
}
