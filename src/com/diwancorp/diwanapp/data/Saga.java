package com.diwancorp.diwanapp.data;


import java.util.ArrayList;

public class Saga {
	
	private String nom;
	private String description;
	private int id;
	private ArrayList<Episode> listeEpisodes;
	
	public Saga(int id, String n, String des, ArrayList<Episode> l) {
		this.id = id;
		this.nom = n;
		this.description = des;
		this.listeEpisodes = l;
	}
	
	public Saga() {
		listeEpisodes = new ArrayList<Episode>();
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<Episode> getListeEpisodes() {
		return listeEpisodes;
	}
	
	public void addEpisode(Episode e) {
		listeEpisodes.add(e);
	}

}
