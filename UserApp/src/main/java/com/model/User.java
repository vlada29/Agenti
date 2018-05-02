package com.model;

import java.io.Serializable;
import java.util.ArrayList;
@SuppressWarnings("serial")
public class User implements Serializable {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private Host host;
    private ArrayList<String> friends;

    public User() {}

    

    public User(String username, String password, String firstname, String lastname, Host host,
			ArrayList<String> friends) {
		super();
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.host = host;
		this.friends = friends;
	}

    

	public String getFirstname() {
		return firstname;
	}



	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}



	public String getLastname() {
		return lastname;
	}



	public void setLastname(String lastname) {
		this.lastname = lastname;
	}



	public ArrayList<String> getFriends() {
		return friends;
	}



	public void setFriends(ArrayList<String> friends) {
		this.friends = friends;
	}



	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }



	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", firstname=" + firstname + ", lastname="
				+ lastname + ", host=" + host + ", friends=" + friends + "]";
	}
    
    
    
}
