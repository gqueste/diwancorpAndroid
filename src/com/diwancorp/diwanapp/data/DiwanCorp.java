package com.diwancorp.diwanapp.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.util.Log;

import com.diwancorp.diwanapp.App;

public class DiwanCorp {

	private String mLienFlux = "http://diwancorp.fr/fluxAndroid.xml";

	private News news;
	private ArrayList<Saga> listeSagas;
	private static DiwanCorp instance = null;
	
	private final File mFile = new File("data/data/com.diwancorp.diwanapp/flux.xml");

	private boolean mSynchronized;

	public static DiwanCorp getInstance() {
		if (instance == null) {
			instance = new DiwanCorp();			
		}
		return instance;
	}

	/**
	 * Builds the differents sagas and all the information included
	 * @throws IOException 
	 * @throws XmlPullParserException 
	 */
	private DiwanCorp() {

		listeSagas = new ArrayList<Saga>();
		news  = new News();
		this.mSynchronized = false;
	}

	public ArrayList<Saga> getListeSagas() {
		return listeSagas;
	}
	
	public void setListeSagas(ArrayList<Saga> liste) {
		this.listeSagas = new ArrayList<Saga>();
	}

	public void addSaga(Saga s) {
		this.listeSagas.add(s);
	}

	public Saga getSagaById(int id) {
		Saga ret = null;
		for (Saga s : listeSagas) {
			if(s.getId() == id) {
				ret = s;
				break;
			}
		}
		return ret;
	}

	public Saga getSagaByName(String name) {
		Saga ret = null;
		for(Saga s : listeSagas) {
			if(s.getNom().equals(name)) {
				ret = s;
				break;
			}
		}
		return ret;
	}

	public News getNews() {
		return this.news;
	}
	
	public void setNews(News n) {
		this.news = n;
	}

	public boolean init() throws XmlPullParserException, IOException {
		boolean ret = false;
		String connexion = loadXmlFromNetwork();
		this.mSynchronized = true;
		
		if(connexion.equals("TRUE")) {
			ret = true;
		}
		return ret;
	}

	private String loadXmlFromNetwork() throws XmlPullParserException, IOException {
		InputStream stream = null;
		Looper.prepare();
		ConnectivityManager  cm = (ConnectivityManager) App.sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		Log.wtf("Connexion", "Connexion : "+(activeNetwork!=null));

		//Si présence d'une connexion
		if(activeNetwork != null){
			try {
				Log.wtf("Connexion", "Connexion disponible");
				
				//connexion au lien
				this.downloadUrl(mLienFlux, mFile); //récupération du flux
				stream = (InputStream) new FileInputStream(mFile);
				new ParseXML().parse(stream);
				stream.close();

				return "TRUE";
			}
			finally{
				stream.close();
			}
		}
		
		else {
			return "FALSE";
		}
	}
	
	
	/**
	 * Récupère les données depuis une adresse URL dans un InputStream
	 * et sauvegarde les fichiers récupérés dans un fichier à l'emplacement indiqué
	 * @param urlString
	 * @param file
	 * @throws IOException
	 */
	private void downloadUrl(String urlString, File file) throws IOException {
		Log.wtf("IUT.downloadUrl", "Téléchargmeent de : " + urlString);
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setDoInput(true);

		conn.connect();
		InputStream stream = conn.getInputStream();
		Log.wtf("IUT.downloadUrl", "Connection done");

		byte[] buffer = new byte[1024];
		int bufferLength = 0;

		FileOutputStream fos = new FileOutputStream(file);
		while ( (bufferLength = stream.read(buffer)) > 0 ) {
			fos.write(buffer, 0, bufferLength);
		}
		Log.wtf("IUT.downloadUrl", "Ecriture faite");
		fos.close();
	}
	
	/**
	 * Renvoie le boolean vérifiant l'état de la synchronisation
	 * @return boolean
	 */
	public boolean isSynchronized() {
		return this.mSynchronized;
	}	
}


