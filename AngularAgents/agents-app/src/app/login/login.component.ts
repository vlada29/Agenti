import { Component, OnInit,ViewChild } from '@angular/core';
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
  @ViewChild('username') username:any;
  @ViewChild('password') password:any;

  login(e){
      console.log(this.username.nativeElement.value, this.password.nativeElement.value);

      this.user_service.login(this.username.nativeElement.value, this.password.nativeElement.value);
      // if(this.user_service.getLoggedIn()){
      //     this.router.navigate(['chatroom']);
      // } else {
      //     alert('Wrong username or password!');
      // }
      return false;
  }
}
