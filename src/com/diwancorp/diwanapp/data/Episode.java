package com.diwancorp.diwanapp.data;


import java.util.ArrayList;

public class Episode {
	
	private String nom;
	private String nomEtendu;
	private String duree;
	private String description;
	private String lien;
	private ArrayList<Acteur> listeActeurs;
	
	public Episode(String n, String ne, String du, String des, String li, ArrayList<Acteur> lis) {
		this.nom = n;
		this.nomEtendu = ne;
		this.duree = du;
		this.description = des;
		this.lien = li;
		this.listeActeurs = lis;
	}
	
	public Episode() {
		listeActeurs = new ArrayList<Acteur>();
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDuree() {
		return duree;
	}
	public void setDuree(String duree) {
		this.duree = duree;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLien() {
		return lien;
	}
	public void setLien(String lien) {
		this.lien = lien;
	}
	public ArrayList<Acteur> getListeActeurs() {
		return listeActeurs;
	}
	
	public void addActeur(Acteur a) {
		this.listeActeurs.add(a);
	}
	
	public String toString() {
		String acteurs = "";
		for (Acteur a : listeActeurs) {
			acteurs = acteurs+a.toString();
		}
		return new String("<html><strong>"+this.nom+"</strong><br />" +
							this.duree+"<br />" +
							"<em>"+this.description+"</em><br />" +
							this.lien+"<br />" +
							acteurs);
	}
	
	public String acteursToString() {
		String acteurs = "";
		for (Acteur a : listeActeurs) {
			acteurs = acteurs+a.toString();
		}
		return acteurs;
	}

	public String getNomEtendu() {
		return nomEtendu;
	}

	public void setNomEtendu(String nomEtendu) {
		this.nomEtendu = nomEtendu;
	}
}
