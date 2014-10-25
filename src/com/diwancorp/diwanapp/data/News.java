package com.diwancorp.diwanapp.data;

public class News {
	
	private String date;
	
	private String contenu;
	
	public News () {}
	
	public News (String d, String c) {
		this.date = d;
		this.contenu = c;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	

}
