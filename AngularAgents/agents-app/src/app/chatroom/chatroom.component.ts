import { Component, OnInit, ViewChild } from '@angular/core';
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
	public added_to_group = [];
    public friends_array_obj : any;
	private otvorenaGrupa = null;
    private activeChat;

	private primljenaPoruka;


	private ws;
	private w3;
    private w_create_group;

	constructor(private user_service: UserServiceService) { }

	ngOnInit() {
		this.user = this.user_service.getUser();
		this.w3 = new WebSocket('ws://localhost:8080/websocket-example/findGroups/'+this.user.username);
		this.w3.onopen = () => this.w3.send('ok');
		this.w3.onmessage = (event) => {console.log(event.data); this.groups = JSON.parse(event.data)};




        this.friends_array_obj = [];
        this.user.friends.forEach(item=>{
          this.friends_array_obj.push({"username":item,"checked_to_group":false})
      })

	}
  sendMsg(event,text){
	    if(event.keyCode == 13) {
	    	console.log(text);

	    	var m = {
	    			from: this.user.username,
	    			to: this.otvorenaGrupa.clanovi,
	    			date: new Date(),
	    			subject: this.otvorenaGrupa.ime,
	    			content: text
	    	}



	    	this.ws.send(JSON.stringify(m));


	    	document.getElementById('messagesDiv').innerHTML += '<div class="' + 'message' + '">' + this.user.username +':' + text + '</div>';

	    }
  }
  private primljene = [];
  openChat(event){
	  console.log('otvoren');
	  console.log(event.srcElement.firstChild.data);
	  var grupa = this.groups.find(function (obj) { return obj.ime === event.srcElement.firstChild.data; });
	  this.otvorenaGrupa = grupa;
	  console.log('grupa');
	  console.log(grupa);

	  document.getElementById('messagesDiv').innerHTML="";

	  //document.getElementById('messagesDiv').innerHTML += '<div class="' + 'message' + '">'


	  for (var poruka in grupa.poruke) {
		    var obj = grupa.poruke[poruka];
		    document.getElementById('messagesDiv').innerHTML += '<div class="' + 'message' + '">' + obj.from +':' + obj.content + '</div>';

		}
        if(document.getElementById(this.activeChat)!=null){
            document.getElementById(this.activeChat).classList.remove('btn', 'btn-success');
            document.getElementById(this.activeChat).classList.add('btn', 'btn-warning');
        }
        this.activeChat = event.target.id;
        document.getElementById(event.target.id).classList.remove('btn', 'btn-warning');
        document.getElementById(event.target.id).classList.add('btn', 'btn-success');




        this.ws = new WebSocket('ws://localhost:8080/websocket-example/chat/'+this.user.username);
		this.ws.onopen = () => {};
		this.ws.onmessage = (event) => {
			this.primljenaPoruka = JSON.parse(event.data);
            console.log(this.primljenaPoruka);
                if(this.primljene.indexOf(this.primljenaPoruka.content) == -1){
			         if(this.primljenaPoruka.subject === this.otvorenaGrupa.ime) {
				            document.getElementById('messagesDiv').innerHTML += '<div class="' + 'message' + '">' + this.primljenaPoruka.from +':' + this.primljenaPoruka.content + '</div>';
			         }
                }
                this.primljene.push(this.primljenaPoruka.content);
			//Dodavanje poruke u odgovarajucu grupu
			for (var g in this.groups) {
				var obj = this.groups[g];
				if(obj.ime === this.primljenaPoruka.subject) {
					obj.poruke.push(this.primljenaPoruka);
				}
			}

		}

  }


  // createGroup(nazivGrupe){
	//   console.log('prijatelji');
	//   console.log(this.invited_friends_ids);
	//   console.log('nazivGrupe');
	//   console.log(nazivGrupe);
  // }

  changed(){


	  this.invited_friends_ids = [];


	    this.user.friends.forEach(item=>{
	      console.log(item);
	      if(item['checked']){
	        this.invited_friends_ids.push(item['username']);
	      }
	    })
  }

  changed_added_to_group(){
	  this.added_to_group = [];

	    this.friends_array_obj.forEach(item=>{

	      console.log(item);
	      if(item['checked_to_group']){
              console.log('yes');
	        this.added_to_group.push(item['username']);
	      }
	    })
        console.log('trenutrni clanovi:',this.added_to_group )
  }
@ViewChild('groupName') groupName:any;
  createGroup(ime){
      this.added_to_group.push(this.user.username);
      var m = {
              ime: this.groupName.nativeElement.value,
              osnivac: this.user.username,
              clanovi: this.added_to_group
      }
      this.added_to_group=[];
      console.log('Creating new group: ', m);

      this.w_create_group =  new WebSocket('ws://localhost:8080/websocket-example/createGroup');
      this.w_create_group.onopen = () => this.w_create_group.send(JSON.stringify(m));
      this.w_create_group.onmessage = (event) => {
          this.getGroups();
      }
  }

  getGroups(){
      this.w3 = new WebSocket('ws://localhost:8080/websocket-example/findGroups/'+this.user.username);
      this.w3.onopen = () => this.w3.send('ok');
      this.w3.onmessage = (event) => {console.log('Groups are: ', event.data); this.groups = JSON.parse(event.data)};
  }

  private deleteGroupWS;
  deleteGroup(event){
  console.log('deleting group: ', event.target.name);
       this.deleteGroupWS =  new WebSocket('ws://localhost:8080/websocket-example/deleteGroup/'+event.target.name);
       this.deleteGroupWS.onopen = () => this.deleteGroupWS.send('ok');
       this.deleteGroupWS.onmessage = (event) => {
           this.getGroups();
       };
  }

  private addWS;
  addNewToGroup(event){
      this.addWS =  new WebSocket('ws://localhost:8080/websocket-example/addToGroup/'+event.target.name+'/'+event.target.id);
      this.addWS.onopen = () => this.addWS.send('ok');
      this.addWS.onmessage = (event) => {
          this.getGroups();
      };
  }

  private removeWS;
  removeFromGroup(event){
      this.removeWS =  new WebSocket('ws://localhost:8080/websocket-example/removeFromGroup/'+event.target.name+'/'+event.target.id);
      this.removeWS.onopen = () => this.removeWS.send('ok');
      this.removeWS.onmessage = (event) => {
          this.getGroups();
      };
  }

  private leaveGroupWS;
  leaveGroup(event){
      this.leaveGroupWS =  new WebSocket('ws://localhost:8080/websocket-example/leaveGroup/'+event.target.name+'/'+event.target.id);
      this.leaveGroupWS.onopen = () => this.leaveGroupWS.send('ok');
      this.leaveGroupWS.onmessage = (event) => {
          this.getGroups();
      };
  }



}
