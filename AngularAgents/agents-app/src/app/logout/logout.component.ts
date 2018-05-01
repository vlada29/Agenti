import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {HttpClient} from "@angular/common/http";
import { UserServiceService } from '../user-service.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

    constructor(
      private router:Router,
      private http: HttpClient,
      private _userService: UserServiceService) { }

  ngOnInit() {
      this._userService.logout();
     this.router.navigate(['login']);
  }

}
