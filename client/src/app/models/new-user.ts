import { ClientType } from './clientType'

export class NewUser {
  password: string
  email: string
  clientType: ClientType
  name?:string
  fee?:number
  firstName?:string
  lastName?:string
}
