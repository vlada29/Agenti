import { Injectable } from '@angular/core';
import { IUser } from './IUser';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Subject, BehaviorSubject } from 'rxjs';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class UserServiceService {
  private user;
  private loggedIn;
  private ws;

  constructor() { }

  setLoggedIn(){
     this.loggedIn = true;
 }

 getLoggedIn(){
     return this.loggedIn;
 }

 logout(){
  this.loggedIn = false;
  }

  getUser(): Observable<IUser> {
    return this.user;
  }
  login(): Observable<IUser>{
      this.ws = new WebSocket('ws://localhost:8080/websocket-example/uservice/login/danilo/pas');
      this.ws.onopen = () => this.ws.send('alohaaa');
      this.ws.onmessage = function(event) {
          console.log(JSON.parse(event.data)['username']);
         this.user = event.data;
         console.log('User logged in as: ', this.user);
     }
     return this.user;
  }
  setUser(data) {
    this.user = data;
    this.loggedIn = true;

    console.log('User logged in: ', this.user.username);
  }

  register(data) {

  }
}
