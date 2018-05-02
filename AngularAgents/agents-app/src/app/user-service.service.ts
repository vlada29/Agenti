import { Injectable } from '@angular/core';
import { IUser } from './IUser';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Subject, BehaviorSubject } from 'rxjs';
import {Observable} from 'rxjs/Observable';
import { Router } from '@angular/router';
@Injectable()
export class UserServiceService {
  private user;
  private loggedIn;
  private ws;

  constructor(private router:Router) { }

  setLoggedIn(){
     this.loggedIn = true;
 }
 getUsername(){
     return this.user.username;
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

  login(username, password): Observable<IUser>{
      this.ws = new WebSocket('ws://localhost:8080/websocket-example/login/'+username+'/'+password);
      this.ws.onopen = () => this.ws.send('alohaaa');
      this.ws.onmessage = (event) => {
          console.log(event.data);
          if(event.data!='ERROR'){
              console.log(JSON.parse(event.data)['username']);
              this.user = JSON.parse(event.data);
              this.loggedIn = true;
              console.log('User logged in as: ', this.user);
              this.router.navigate(['chatroom']);
              return "loggedIn";
          } else {
              alert('Wrong username or password!');
          }
      }
     return this.user;
  }
  setUser(data) {
    this.user = data;
    this.loggedIn = true;

    console.log('User logged in: ', this.user.username);
  }

  sendMessageForRefresh(){
	  this.ws.send('alohaaa');
  }
  
  register(username, firstname, lastname, password) {
      this.ws = new WebSocket('ws://localhost:8080/websocket-example/register/'+username+'/'+password+'/'+firstname+'/'+lastname);
      this.ws.onopen = () => this.ws.send('');
      this.ws.onmessage = (event) => {
          console.log(event.data);
          if(event.data!='ERROR'){
              this.router.navigate(['login']);
          } else {
              alert('Error!');
          }
      }
  }
}
