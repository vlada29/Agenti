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

	constructor() {

	}

	ngOnInit() {
	    
	}

	addFriend(){
		this.pronadjen = false;
		this.rezultatPretrage = null;
	}

}
