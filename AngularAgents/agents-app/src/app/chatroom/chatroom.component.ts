import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-chatroom',
  templateUrl: './chatroom.component.html',
  styleUrls: ['./chatroom.component.css']
})
export class ChatroomComponent implements OnInit {



  constructor() { }

  ngOnInit() {
      this.checkWebSocket();
  }
  ws = new WebSocket('ws://localhost:8080/websocket-example/example1/K1');

  checkWebSocket(){


     this.ws.onopen = function() {
         //appendMessage('info', 'WebSocket connection opened!');
        // alert('Webscoket conn opened!');
     }

     this.ws.onmessage = function(event) {
         let currentChat = "Jon";
                  document.getElementById('messagesDiv').innerHTML += '<div class="' + 'message' + '">' + currentChat +': ' + event.data + '</div>';
     }

  }


  appendMessage(type, text) {
      document.getElementById('messagesDiv').innerHTML += '<div class="' + type + '">' + text + '</div>';
  }


  sendMessageToKey() {
             let messageToKey = (<HTMLInputElement>document.getElementById('messageToKey')).value;
             this.ws.send(messageToKey);
             this.appendMessage('message', 'you > ' + 'K1' + ': "' + messageToKey + '"');
  }



  sendMsg(event,text){
	    if(event.keyCode == 13) {
	    	console.log(text);
	    	this.ws.send(text);
	    	this.appendMessage('message','You:' + text);
	    }
  }



}
