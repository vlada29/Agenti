import { Component, OnInit } from '@angular/core';
import { IUser } from '../IUser';

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
	constructor() {

	}

	ngOnInit() {
	}

	addFriend(){
		console.log(this.rezultatPretrage);
	}

	setSearchType(value){
	    this.searchType = value;
	}

	searchFriend(value){
	    console.log(this.searchType + value);

	    this.ws = new WebSocket('ws://localhost:8080/websocket-example/findFriend/'+this.searchType+'/'+value);
	    this.ws.onopen = () => this.ws.send('alohaaa');
	    this.ws.onmessage = (event) => {this.pronadjen = true; this.rezultatPretrage = JSON.parse(event.data); console.log(this.rezultatPretrage)}

	    

	}
	
	
	namesti(data){
		this.rezultatPretrage = data;
	}



}
