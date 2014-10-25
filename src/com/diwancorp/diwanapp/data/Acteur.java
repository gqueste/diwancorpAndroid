package com.diwancorp.diwanapp.data;


import java.util.ArrayList;

public class Acteur {
	
	private String nom;
	private ArrayList<String> listeRoles;
	private String lienSite;
	
	public Acteur(String n, ArrayList<String> list, String lien) {
		this.nom = n;
		this.listeRoles = list;
		this.lienSite = lien;
	}
	
	public Acteur() {
		listeRoles = new ArrayList<String>();
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getLienSite() {
		return lienSite;
	}
	public void setLienSite(String lienSite) {
		this.lienSite = lienSite;
	}
	public ArrayList<String> getListeRoles() {
		return listeRoles;
	}
	public void addRole(String r) {
		this.listeRoles.add(r);
	}
	
	public String toString() {
		
		String roles = "";
		for (String s : listeRoles) {
			roles = roles + s + ", ";
		}
		
		return new String("<html><strong>"+this.nom+"</strong> : "+roles+"<a href=\""+this.lienSite+"\">Son site</a><br /><br /></html>");
	}
}
