import { Component, OnInit,ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from "@angular/common/http";
import { UserServiceService } from '../user-service.service';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

    constructor(
         private router:Router,
         private http: HttpClient,
         private user_service: UserServiceService) { }

  ngOnInit() {
  }

  @ViewChild('username') username:any;
  @ViewChild('password') password:any;
  @ViewChild('firstname') firstname:any;
  @ViewChild('lastname') lastname:any;

  register(e){
      console.log(this.username.nativeElement.value, this.password.nativeElement.value);
      this.user_service.register(
          this.username.nativeElement.value,
          this.firstname.nativeElement.value,
          this.lastname.nativeElement.value,
          this.password.nativeElement.value,

      );
      return false;
  }
}
