import { Component, OnInit } from '@angular/core';
import { Company } from 'src/app/models/company';
import { CompanyService } from '../../services/company.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-company-details',
  templateUrl: './company-details.component.html',
  styleUrls: ['./company-details.component.css']
})
export class CompanyDetailsComponent implements OnInit {
  company:Company
  constructor(private companyService:CompanyService,
    private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.companyService.getCompanyDetails().subscribe(
      company=>this.company=company,
      error=>this.snackBar.open(error, '', {duration: 3000})
    )
  }

}
