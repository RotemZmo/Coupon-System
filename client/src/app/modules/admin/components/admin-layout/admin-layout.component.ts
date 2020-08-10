import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { TokenStorageService } from 'src/app/shared/services/token-storage.service';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-admin-layout',
  templateUrl: './admin-layout.component.html',
  styleUrls: ['./admin-layout.component.css']
})
export class AdminLayoutComponent implements OnInit {

  constructor(private router:Router,private tokenService:TokenStorageService,private route:ActivatedRoute) { }

  ngOnInit(): void {
    this.router.navigate(['home'],{relativeTo:this.route})
  }


  logout(){
    this.tokenService.logout();
    this.router.navigate(['home'])
  }

}
