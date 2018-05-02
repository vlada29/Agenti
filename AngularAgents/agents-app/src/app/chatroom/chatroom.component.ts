import { Component, OnInit } from '@angular/core';
import { UserServiceService } from '../user-service.service';

@Component({
  selector: 'app-chatroom',
  templateUrl: './chatroom.component.html',
  styleUrls: ['./chatroom.component.css']
})
export class ChatroomComponent implements OnInit {

	public user = null;
	public groups = null;
	public invited_friends_ids = [];
	
	private ws;
	private w3;
	constructor(private user_service: UserServiceService) { }

	ngOnInit() {
		this.user = this.user_service.getUser();
		this.w3 = new WebSocket('ws://localhost:8080/websocket-example/findGroups/'+this.user.username);
		this.w3.onopen = () => this.w3.send('ok');
		this.w3.onmessage = (event) => {console.log(event.data); this.groups = JSON.parse(event.data)}; 
		
		this.ws = new WebSocket('ws://localhost:8080/websocket-example/chat/'+this.user.username);
		
	}
	//
//
//	checkWebSocket(){
//
//
//		this.ws.onopen = function() {
//		}
//
//		this.ws.onmessage = function(event) {
//			let currentChat = "Jon";
//			document.getElementById('messagesDiv').innerHTML += '<div class="' + 'message' + '">' + currentChat +': ' + event.data + '</div>';
//		}
//
//	}


  appendMessage(type, text) {
      document.getElementById('messagesDiv').innerHTML += '<div class="' + type + '">' + text + '</div>';
  }


  sendMessageToKey() {
             let messageToKey = (<HTMLInputElement>document.getElementById('messageToKey')).value;
             //this.ws.send(messageToKey);
             this.appendMessage('message', 'you > ' + 'K1' + ': "' + messageToKey + '"');
  }



  sendMsg(event,text){
	    if(event.keyCode == 13) {
	    	console.log(text);
	    	//this.ws.send(text);
	    	this.appendMessage('message','You:' + text);
	    }
  }

  openChat(event){
	  console.log('otvoren');
	  console.log(event.srcElement.firstChild.data);
	  var grupa = this.groups.find(function (obj) { return obj.ime === event.srcElement.firstChild.data; });
	  console.log('grupa');
	  console.log(grupa);
	  
	  document.getElementById('messagesDiv').innerHTML="";
	  
	  //document.getElementById('messagesDiv').innerHTML += '<div class="' + 'message' + '">'
	  
	  
	  for (var poruka in grupa.poruke) {
		    var obj = grupa.poruke[poruka];
		    document.getElementById('messagesDiv').innerHTML += '<div class="' + 'message' + '">' + obj.from.username +':' + obj.content + '</div>';
		    
		}
	  
	  
  }
  
  
  createGroup(nazivGrupe){
	  console.log('prijatelji');
	  console.log(this.invited_friends_ids);
	  console.log('nazivGrupe');
	  console.log(nazivGrupe);
  }
  
  changed(){

	  
	  this.invited_friends_ids = [];
	  
	  
	    this.user.friends.forEach(item=>{
	      console.log(item);
	      if(item['checked']){
	        this.invited_friends_ids.push(item['username']);
	      }  
	    })
  }
  
  
  
  

}














