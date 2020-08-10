import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from 'src/app/shared/services/token-storage.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Company } from 'src/app/models/company';
import { CompanyService } from '../../services/company.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-company-layout',
  templateUrl: './company-layout.component.html',
  styleUrls: ['./company-layout.component.css']
})
export class CompanyLayoutComponent implements OnInit {

  company:Company

  constructor(private router:Router,private tokenService:TokenStorageService,private compService:CompanyService,private route:ActivatedRoute,
    private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.compService.getCompanyDetails().subscribe(
      comp=>{
        this.company=comp
      },
      error=>this.snackBar.open(error, '', {duration: 3000})
    )
    this.router.navigate(["allCoupons"],{relativeTo:this.route})
  }

  logout(){
    this.tokenService.logout();
    this.router.navigate(['home'])
  }

}
