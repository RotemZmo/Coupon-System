import { Injectable } from '@angular/core'

import { HttpErrorResponse } from '@angular/common/http'
import { throwError } from 'rxjs'

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  public errorHandler(error: HttpErrorResponse) {
    console.log(error)
    return throwError(error.error)
  }

}
