import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from "@angular/common/http";
import { UserServiceService } from '../user-service.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

    constructor(
        private router:Router,
        private http: HttpClient,
        private user_service: UserServiceService) { }

  ngOnInit(){}

  login(e){
      console.log('yes');
      this.user_service.login();
      return false;
  }
}
