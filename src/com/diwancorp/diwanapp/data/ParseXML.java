package com.diwancorp.diwanapp.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

/**
 * <h3>Classe implémentant le parseur XML pour Tabcom</h3>
 * <p>S'utilise en créant une nouvelle instance de ParseXML et en appelant parse(InputStream in)</p>
 * Prends en paramètre un InputStream qui devra etre récupérer depuis une autre classe.
 * 
 * Cette classe doit s'éxécuter dans une AsyncTask
 * 
 * @author Tabcom Groupe C
 * @version 1.0
 *
 */
public class ParseXML {

	private static final String ns = null;
	private DiwanCorp diwancorp;

	/**
	 * Parse un inputStream contenant un XML et le transforme en une instance de Domaine
	 * @param InputStream contenant le XML
	 * @return Domaine créé avec le XML
	 * @throws XmlPullParserException Si le XML est impossible à parser (Syntaxe invalide par exemple)
	 * @throws IOException si l'InputStream est null 
	 */
	public void parse(InputStream in) throws XmlPullParserException, IOException {
		try {
			Log.wtf("Parser", "Lance le parser");
			this.diwancorp = DiwanCorp.getInstance();
			this.diwancorp.setListeSagas(new ArrayList<Saga>());
			
			XmlPullParser parser = Xml.newPullParser();
			
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			
			parser.setInput(in, null);
			
			parser.nextTag();
			
			Log.wtf("PARSER", "va faire un readFeed"); 
			readFeed(parser);
		} finally {
			in.close();
		}
	}

	private void readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.wtf("Parser", "Est entré dans le readFeed");
		parser.require(XmlPullParser.START_TAG, ns, "feed");
		
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if(name.equals("news")){
				Log.wtf("PARSER", "va faire un readNews");
				this.diwancorp.setNews(readNews(parser));
			}
			else if(name.equals("saga")){
				this.diwancorp.addSaga(readSaga(parser));
			}
			else {
				skip(parser);
			}
		}		
	}
	
	
	private News readNews(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.wtf("Parser", "Est entré dans le readNews");
		
		parser.require(XmlPullParser.START_TAG, ns, "news");
		News n = new News();
		
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if(name.equals("date")){
				n.setDate(readDate(parser));
			}
			else if(name.equals("contenu")){
				n.setContenu(readContenu(parser));
			}
			else {
				skip(parser);
			}
		}
		return n;
	}
	
	
private Saga readSaga(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.wtf("Parser", "Est entré dans le readSaga");
		
		parser.require(XmlPullParser.START_TAG, ns, "saga");
		Saga s = new Saga();
		
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if(name.equals("id")){
				s.setId(readId(parser));
			}
			else if(name.equals("nom")){
				s.setNom(readNom(parser));
			}
			else if(name.equals("description")){
				s.setDescription(readDescription(parser));
			}
			else if(name.equals("episode")){
				s.addEpisode(readEpisode(parser));
			}
			else {
				skip(parser);
			}
		}
		return s;
	}


private Episode readEpisode(XmlPullParser parser) throws XmlPullParserException, IOException {
	
	parser.require(XmlPullParser.START_TAG, ns, "episode");
	Episode e = new Episode();
	
	while (parser.next() != XmlPullParser.END_TAG) {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			continue;
		}
		String name = parser.getName();
		if(name.equals("nom")){
			e.setNom(readNom(parser));
		}
		else if(name.equals("nom_etendu")){
			e.setNomEtendu(readNomEtendu(parser));
		}
		else if(name.equals("duree")){
			e.setDuree(readDuree(parser));
		}
		else if(name.equals("description")){
			e.setDescription(readDescription(parser));
		}
		else if(name.equals("lien")){
			e.setLien(readLien(parser));
		}
		else if(name.equals("acteur")){
			e.addActeur(readActeur(parser));
		}
		else {
			skip(parser);
		}
	}
	return e;
}


private Acteur readActeur(XmlPullParser parser) throws XmlPullParserException, IOException {
	
	parser.require(XmlPullParser.START_TAG, ns, "acteur");
	Acteur a = new Acteur();
	
	while (parser.next() != XmlPullParser.END_TAG) {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			continue;
		}
		String name = parser.getName();
		if(name.equals("nom")){
			a.setNom(readNom(parser));
		}
		else if(name.equals("role")){
			a.addRole(readRole(parser));
		}
		else if(name.equals("site")){
			a.setLienSite(readSite(parser));
		}
		else {
			skip(parser);
		}
	}
	return a;
}

	

	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
	        throw new IllegalStateException();
	    }
	    int depth = 1;
	    while (depth != 0) {
	        switch (parser.next()) {
	        case XmlPullParser.END_TAG:
	            depth--;
	            break;
	        case XmlPullParser.START_TAG:
	            depth++;
	            break;
	        }
	    }

	}

	private int readId(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "id");
		String summary = readText(parser);
		int ret = Integer.parseInt(summary);
		parser.require(XmlPullParser.END_TAG, ns, "id");
		return ret;
	}

	
	private String readDate(XmlPullParser parser) throws IOException, XmlPullParserException {
		Log.wtf("Parser", "Est entré dans le readDate");
		parser.require(XmlPullParser.START_TAG, ns, "date");
		String summary = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "date");
		return summary;
	}

	private String readContenu(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "contenu");
		String summary = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "contenu");
		return summary;
	}
	
	private String readDescription(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "description");
		String summary = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "description");
		return summary;
	}
	
	private String readNom(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "nom");
		String summary = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "nom");
		return summary;
	}
	
	private String readNomEtendu(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "nom_etendu");
		String summary = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "nom_etendu");
		return summary;
	}
	
	private String readDuree(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "duree");
		String summary = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "duree");
		return summary;
	}
	
	private String readLien(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "lien");
		String summary = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "lien");
		return summary;
	}
	
	private String readRole(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "role");
		String summary = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "role");
		return summary;
	}
	
	private String readSite(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "site");
		String summary = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "site");
		return summary;
	}
	

	private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
		String result = "";
		Log.wtf("PARSER", "va faire un readText");
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}
}
