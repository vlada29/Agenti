
import { Component, OnInit } from '@angular/core';
import { IUser } from '../IUser';
import { UserServiceService } from '../user-service.service';

@Component({
  selector: 'app-userprofile',
  templateUrl: './userprofile.component.html',
  styleUrls: ['./userprofile.component.css']
})
export class UserprofileComponent implements OnInit {
	public user = null;
	public groups = null;
	public pronadjen = false;
	public rezultatPretrage = null;
	public searchType = 'username';
	private ws;
	private ws2;
	private w3;
	constructor(private user_service: UserServiceService) {

	}

	ngOnInit() {
		
		this.user = this.user_service.getUser();
		
		this.w3 = new WebSocket('ws://localhost:8080/websocket-example/findGroups/'+this.user.username);
		this.w3.onopen = () => this.w3.send('ok');
		this.w3.onmessage = (event) => {console.log(event.data); this.groups = JSON.parse(event.data)}
	}

	addFriend(){
		console.log(this.rezultatPretrage);

		this.ws2 = new WebSocket('ws://localhost:8080/websocket-example/addFriend/'+this.user.username+'/'+this.rezultatPretrage.username);
		this.ws2.onopen = () => this.ws2.send('ok');
		this.ws2.onmessage = (event) => {this.user_service.sendMessageForRefresh();this.user = this.user_service.getUser();}

	}

	setSearchType(value){
	    this.searchType = value;
	}

	searchFriend(value){
	    console.log(this.searchType + value);

	    this.ws = new WebSocket('ws://localhost:8080/websocket-example/findFriend/'+this.searchType+'/'+value);
	    this.ws.onopen = () => this.ws.send('alohaaa');
	    this.ws.onmessage = (event) => {this.pronadjen = true; this.rezultatPretrage = JSON.parse(event.data);}



	}


	namesti(data){
		this.rezultatPretrage = data;
	}





}
