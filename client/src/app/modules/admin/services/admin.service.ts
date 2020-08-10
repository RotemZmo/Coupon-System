import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from 'src/app/models/user';
import { NewUser } from 'src/app/models/new-user';
import { Company } from 'src/app/models/company';
import { Customer } from 'src/app/models/customer';
import { ErrorService } from 'src/app/shared/services/error.service';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private httpClient: HttpClient,
    private errorService: ErrorService) { }

  public addUser(user:NewUser){
    return this.httpClient.post<String>("http://localhost:8080/admin/addUser/user/",user).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public deleteUser(userId:number){
    return this.httpClient.delete<Object>("http://localhost:8080/admin/delete/" + userId).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public deactivateUser(userId:number){
    return this.httpClient.put<Object>("http://localhost:8080/admin/deactivate/" + userId,"").pipe(
      catchError(err => this.errorService.errorHandler(err)))

  }

  public activateUser(userId:number){
    return this.httpClient.put<Object>("http://localhost:8080/admin/activate/" + userId,"").pipe(
      catchError(err => this.errorService.errorHandler(err)))

  }

  public getAllCompanies(){
    return this.httpClient.get<Company[]>("http://localhost:8080/admin/get/companies").pipe(
      catchError(err => this.errorService.errorHandler(err)))

  }

  public getAllCustomers(){
    return this.httpClient.get<Customer[]>("http://localhost:8080/admin/get/customers").pipe(
      catchError(err => this.errorService.errorHandler(err)))

  }

  public getCustomer(customerId:number){
    return this.httpClient.get<Customer>("http://localhost:8080/admin/get/customer/" + customerId).pipe(
      catchError(err => this.errorService.errorHandler(err)))

  }

  public getCompany(companyId:number){
    return this.httpClient.get<Company>("http://localhost:8080/admin/get/company/" + companyId).pipe(
      catchError(err => this.errorService.errorHandler(err)))

  }

  public getAllUsers(){
    return this.httpClient.get<User[]>("http://localhost:8080/admin/get/users").pipe(
      catchError(err => this.errorService.errorHandler(err)))

  }

  public getUser(userId:number){
    return this.httpClient.get<User>("http://localhost:8080/admin/get/user/" + userId).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public updateCustomer(customer:Customer){
    return this.httpClient.put<Object>("http://localhost:8080/admin/update/customer",customer).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public updateCompany(company:Company){
    return this.httpClient.put<Object>("http://localhost:8080/admin/update/company",company).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public updateUser(user:User){
    return this.httpClient.put<Object>("http://localhost:8080/admin/update/user",user).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public setFee(newFee:number,companyId:number){
    return this.httpClient.put<Object>("http://localhost:8080/admin/setFee/" + companyId + "&" + newFee,"").pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }


  // public addCompany(){}

  //public addCustomer(){}

  //public deleteCompany(){}

  //public deleteCustomer(){}

  //public deactivateCompany(){}

  //public deactivateCustomer(){}

  //public activateCompany(){}

  //public activateCustomer(){}

}
