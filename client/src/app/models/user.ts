import { ClientType } from './clientType'

export class User {
    id?: number
    password?: string
    email?: string
    clientType?: ClientType
    enabled:boolean
}

