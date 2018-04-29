import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-userprofile',
  templateUrl: './userprofile.component.html',
  styleUrls: ['./userprofile.component.css']
})
export class UserprofileComponent implements OnInit {

	public user = null;
	public groups = null;
	public pronadjen = true;
	public rezultatPretrage = null;
	public searchType = 'username';
	private ws;

	constructor() {

	}

	ngOnInit() {
	    this.pronadjen = false;
	}

	addFriend(){
		this.pronadjen = false;
	}

	setSearchType(value){
	    this.searchType = value;
	}

	searchFriend(value){
	    console.log(this.searchType + value);

	    this.ws = new WebSocket('ws://localhost:8080/websocket-example/findFriend/'+this.searchType+'/'+value);
	    this.ws.onopen = () => this.ws.send('alohaaa');
	    this.ws.onmessage = function(event) {
	    	this.rezultatPretrage = event.data;
	    	this.pronadjen = true;

	    	console.log(this.pronadjen);

	    }

	    console.log(this.pronadjen);

	}



}
