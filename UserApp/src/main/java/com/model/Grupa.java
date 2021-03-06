package com.model;

import java.util.ArrayList;

public class Grupa {
	private int id;
	private String ime;
	private String osnivac;
	public String getOsnivac() {
		return osnivac;
	}
	public void setOsnivac(String osnivac) {
		this.osnivac = osnivac;
	}
	private ArrayList<String> clanovi;
	private ArrayList<Message> poruke;
	public Grupa(){}
	public Grupa(int id, String osnivac, String ime, ArrayList<String> clanovi, ArrayList<Message> poruke) {
		super();
		this.id = id;
		this.ime = ime;
		this.clanovi = clanovi;
		this.poruke = poruke;
		this.osnivac = osnivac;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public ArrayList<String> getClanovi() {
		return clanovi;
	}
	public void setClanovi(ArrayList<String> clanovi) {
		this.clanovi = clanovi;
	}
	public ArrayList<Message> getPoruke() {
		return poruke;
	}
	public void setPoruke(ArrayList<Message> poruke) {
		this.poruke = poruke;
	}
}
