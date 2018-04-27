import { Component, OnInit } from '@angular/core';
import { RestServiceService } from './rest-service.service'
import { Http, Response, Headers } from '@angular/http';
import { Router} from '@angular/router'
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'app';
  constructor(private rest_service: RestServiceService){}
  ngOnInit(){
      //this.rest_service.checkRest();
      //this.rest_service.checkWebSocket();
  }
}
