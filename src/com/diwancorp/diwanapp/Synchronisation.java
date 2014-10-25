package com.diwancorp.diwanapp;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.diwancorp.diwanapp.data.DiwanCorp;


/**
 * <p>Activit� qui s'affiche lors du chargement des donn�es sur la tablette</p>
 * 
 *
 */

public class Synchronisation extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.synchronisation);

		new DownloadXmlTask().execute();
	}

	private void launchMainActivity() {
		Intent i = new Intent(this, MainActivity.class);
		this.startActivity(i);
	}


	/**
	 * Classe interne utilis�e pour r�cup�rer et parser les documents dans une t�che asynchrone.
	 */
	private class DownloadXmlTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void...voids ) {
			try {
				Log.wtf("Synchronisation.DownloadXmlTask", "Est entr� dans DownloadXMLTask et va lancer l'initialisation");
				DiwanCorp instance = DiwanCorp.getInstance();
				return instance.init();
			} catch (IOException e) {
				Log.wtf("Synchronisation.DownloadWxlTask", e.getMessage());
				return false;
			} catch (XmlPullParserException e) {
				Log.wtf("Synchronisation.DownloadWxlTask", "PullParser : "+e.getMessage());
				return false;
			}
			catch (NullPointerException e) {
				return false;
			}
			catch (RuntimeException e) {
				return true;
			}
			catch (Exception e) {
				return false;
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if(result){
				launchMainActivity();
			}
			else if (!result) {
				// Premier lancement de l'application sans internet
				Toast t = Toast.makeText(App.sContext, R.string.erreur_connexion, Toast.LENGTH_LONG);
				t.show();
				finish();
			}
		}
	}	
}
