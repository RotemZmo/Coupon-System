import { Component, OnInit, ViewChild, QueryList, ViewChildren } from '@angular/core';
import { Company } from 'src/app/models/company';
import { AdminService } from '../../services/admin.service';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import {animate, state, style, transition, trigger} from '@angular/animations';
import { Customer } from 'src/app/models/customer';
import { ClientType } from 'src/app/models/clientType';
import { Router, ActivatedRoute } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-admin-manage-users',
  templateUrl: './admin-manage-users.component.html',
  styleUrls: ['./admin-manage-users.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed, void', style({ height: '0px', minHeight: '0', display: 'none' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
      transition('expanded <=> void', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)'))
    ]),
  ],
})
export class AdminManageUsersComponent implements OnInit {
  clientType:ClientType[] =
  [
  ClientType.Company,
  ClientType.Customer,
  ClientType.Admin]
  displayedColumnsCompany: string[] = ['id', 'name', 'email', 'fee','active'];
  displayedColumnsCustomer: string[] = ['id', 'firstName', 'lastName', 'email','active'];
  companies:Company[]
  filteredCompanies:Company[]
  dataSourceCompany: MatTableDataSource<Company>
  dataSourceCustomer: MatTableDataSource<Customer>
  expandedElement: Company | Customer| null;

  @ViewChildren(MatPaginator) paginator = new QueryList<MatPaginator>();
  @ViewChildren(MatSort) sort = new QueryList<MatSort>();


  constructor(private adminService:AdminService,private router:Router,private route:ActivatedRoute,
    private snackBar: MatSnackBar) {

   }

   ngOnInit(): void {
    this.setCompanies()
    this.setCustomers()
  }

  applyFilterCompany(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSourceCompany.filter = filterValue.trim().toLowerCase();

    if (this.dataSourceCompany.paginator) {
      this.dataSourceCompany.paginator.firstPage();
    }
  }

  applyFilterCustomer(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSourceCustomer.filter = filterValue.trim().toLowerCase();

    if (this.dataSourceCustomer.paginator) {
      this.dataSourceCustomer.paginator.firstPage();
    }
  }

  setCompanies(){
    this.adminService.getAllCompanies().subscribe(
      companies=> {this.dataSourceCompany = new MatTableDataSource(companies);
      this.dataSourceCompany.paginator = this.paginator.toArray()[0];
      this.dataSourceCompany.sort = this.sort.toArray()[0];
      this.dataSourceCompany.filterPredicate = (data: Company, filter: string) => {
        return data.id === parseInt(filter);
      }
      },
      error=>this.snackBar.open(error, '', {duration: 3000})
    )
  }

  deleteUser(id:number,clientType:ClientType){
    this.adminService.deleteUser(id).subscribe(
      ()=>{this.snackBar.open('User successfully deleted.', '', {duration: 3000})
      switch(clientType){
        case ClientType.Company:
          this.refreshCompanies()
          break;
        case ClientType.Customer:
          this.refreshCustomers()
          break;
          }
        },
        error=>this.snackBar.open(error, '', {duration: 3000})
    )
  }

  deactivateUser(id:number,clientType:ClientType){
    this.adminService.deactivateUser(id).subscribe(
      ()=>{this.snackBar.open('User successfully deactivated.', '', {duration: 3000});
      switch(clientType){
        case ClientType.Company:
          this.refreshCompanies()
          break;
        case ClientType.Customer:
          this.refreshCustomers()
          break;
          }
        },
        error=>this.snackBar.open(error, '', {duration: 3000})
    )

  }

  activateUser(id:number,clientType:ClientType){
    this.adminService.activateUser(id).subscribe(
      ()=>{this.snackBar.open('User successfully activated.', '', {duration: 3000});
      switch(clientType){
      case ClientType.Company:
        this.refreshCompanies()
        break;
      case ClientType.Customer:
        this.refreshCustomers()
        break;
        }
      },
      error=>this.snackBar.open(error, '', {duration: 3000})
    )

  }

  setCustomers(){
    this.adminService.getAllCustomers().subscribe(
      customers=> {this.dataSourceCustomer = new MatTableDataSource(customers);
      this.dataSourceCustomer.paginator = this.paginator.toArray()[1];
      this.dataSourceCustomer.sort = this.sort.toArray()[1];
      this.dataSourceCustomer.filterPredicate = (data: Customer, filter: string) => {
      return data.id === parseInt(filter);
    }
      },
      error=>this.snackBar.open(error, '', {duration: 3000})
    )
  }

  refreshCompanies(){
    this.adminService.getAllCompanies().subscribe(
      companies=> this.dataSourceCompany.data = companies,
      error=>this.snackBar.open(error, '', {duration: 3000})
    )
  }

  refreshCustomers(){
    this.adminService.getAllCustomers().subscribe(
      cutsomers=> this.dataSourceCustomer.data = cutsomers
      ,
      error=>this.snackBar.open(error, '', {duration: 3000})
    )

  }

  updateUser(id:number,clientType:ClientType){
    switch(clientType){
      case ClientType.Company:
        this.router.navigate(['addUser'],{queryParams:{userId:id,cType:clientType},relativeTo:this.route.parent})
        break;
      case ClientType.Customer:
        this.router.navigate(['addUser'],{queryParams:{userId:id,cType:clientType},relativeTo:this.route.parent})
        break;
    }
  }

  addUser(clientType:ClientType){
        this.router.navigate(['addUser'],{queryParams:{userId:undefined,cType:clientType},relativeTo:this.route.parent})
  }
}
