import { Component, OnInit } from '@angular/core';
import { UserServiceService } from '../user-service.service';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

    public loggedIn = false;
     public username:string;
     constructor(private _userService: UserServiceService) {


     }

  ngOnInit() {
  }
  isLoggedIn() {
          return this._userService.getLoggedIn();
      }

      getUsername(){
          return this._userService.getUsername();
      }
}
