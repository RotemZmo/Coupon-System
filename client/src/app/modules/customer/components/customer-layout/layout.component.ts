import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { TokenStorageService } from 'src/app/shared/services/token-storage.service';
import { Customer } from 'src/app/models/customer';
import { CustomerService } from '../../services/customer.service';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class LayoutComponent implements OnInit {

  customer:Customer
  constructor(private router:Router,private tokenService:TokenStorageService,private customerService:CustomerService,private route:ActivatedRoute) { }

  ngOnInit(): void {
    this.customerService.getCustomerDetails().subscribe(
      customer=>{
        this.customer=customer
      },err=>{
        console.log(err)
      }
    )
    this.router.navigate(['allCoupons'],{relativeTo:this.route})
  }

  logout(){
    this.tokenService.logout();
    this.router.navigate(['home'])
  }
}
