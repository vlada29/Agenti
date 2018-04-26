package beans;

import javax.ws.rs.FormParam;

public class Student {
	@FormParam("ime")
	private String ime;
	@FormParam("prezime")
	private String prezime;
	
	public Student() {
		
	}
	
	public Student(String ime, String prezime) {
		this();
		this.ime = ime;
		this.prezime = prezime;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	@Override
	public String toString() {
		return "Student [ime=" + ime + ", prezime=" + prezime + "]";
	}
}
