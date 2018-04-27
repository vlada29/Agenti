import { Component, OnInit } from '@angular/core';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class RestServiceService implements OnInit {
    ngOnInit(){}
    constructor(private http: HttpClient) {}

    checkRest(){
        this.http.get('/ChatAppEJB/rest/demo/test').subscribe(data => {
            console.log(data);
        })
    }

    // checkWebSocket(){
    //
    //    let ws = new WebSocket('ws://localhost:8080/websocket-example/example1/K1');
    //    ws.onopen = function() {
    //        //appendMessage('info', 'WebSocket connection opened!');
    //        alert('Webscoket conn opened!');
    //    }
    //    ws.onmessage = function(event) {
    //                 //appendMessage('message', event.data);
    //                 document.getElementById('messages').innerHTML += '<div class="' + 'message' + '">' + event.data + '</div>';
    //             }
    //             // ws.onclose = function() {
    //             // appendMessage('warn', 'WebSocket closed');
    //             // }
    //             // ws.onerror = function(err) {
    //             //     appendMessage(err);
    //             // }
    // }
    //
    // // appendMessage(type, text) {
    // //            document.getElementById('messages').innerHTML += '<div class="' + type + '">' + text + '</div>';
    // //        }
    //        sendMessageToKey() {
    //            let messageToKey = document.getElementById('messageToKey').value;
    //            this.ws.send(this.messageToKey);
    //            this.appendMessage('message', 'you > ' + 'K1' + ': "' + this.messageToKey + '"');
    //        }
           // onFormSubmit() {
           //     let messageToOther = document.getElementById('messageToOtherKey').value;
           //     let otherKey = document.getElementById('otherKey').value;
           //     this.appendMessage('message', 'you > ' + 'K241' + ': "' + messageToOther + '"');
           // }
}
