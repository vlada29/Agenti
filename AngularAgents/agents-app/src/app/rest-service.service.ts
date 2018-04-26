import { Component, OnInit } from '@angular/core';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class RestServiceService implements OnInit {
    ngOnInit(){}
    constructor(private http: HttpClient) {}

    checkRest(){
        this.http.get('/RESTApp/rest/demo/test').subscribe(data => {
            console.log(data);
        })
    }
}
