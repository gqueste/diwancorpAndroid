package com.diwancorp.diwanapp;


import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.diwancorp.diwanapp.data.DiwanCorp;
import com.diwancorp.diwanapp.data.Episode;
import com.diwancorp.diwanapp.data.Saga;

public class PresentationSaga extends Activity {

	private String Nom_Saga_Selectionnee;
	private ArrayList<Episode> listeEpisodes;
	private Saga sagaSelectionnee;

	private Button buttonLecteur;
	
	private MediaPlayer mp;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.presentation_saga);

		App.isPlaying = false;
		App.lectureStarted = false;

		Intent intent = getIntent();
		Nom_Saga_Selectionnee = intent.getStringExtra(MainActivity.NOM_SAGA);
		setTitle(Nom_Saga_Selectionnee);

		sagaSelectionnee = DiwanCorp.getInstance().getSagaByName(Nom_Saga_Selectionnee);
		listeEpisodes = sagaSelectionnee.getListeEpisodes();

		TextView nomSagaView = (TextView) findViewById(R.id.view_description_episode_titre_saga);
		nomSagaView.setText(sagaSelectionnee.getNom());

		TextView descriptionSagaView = (TextView) findViewById(R.id.view_description_episode_resume_episode);
		descriptionSagaView.setText(sagaSelectionnee.getDescription());
		
		buttonLecteur = (Button) findViewById(R.id.button_description_episode_lecteur);
		buttonLecteur.setVisibility(View.INVISIBLE);

		WebView acteursView = (WebView) findViewById(R.id.view_description_episode_acteurs);
		
		acteursView.loadData("", "text/html", null);
		acteursView.setBackgroundColor(android.R.drawable.btn_default);


		this.initiateList();

	}


	private void initiateList() {

		ListView listView = (ListView) findViewById(R.id.listview_liste_episodes);

		String[] contentText = new String[listeEpisodes.size()];
		for(int i=0; i< listeEpisodes.size(); i++) {
			contentText[i] = listeEpisodes.get(i).getNom();
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getBaseContext(), R.layout.menu_saga_item, R.id.menu_saga_textview, contentText);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> av, View v, int val, long l) {

				Episode episodeSelected = listeEpisodes.get((int)l);


				try {
					
					if(App.isPlaying) {
						mp.stop();
						App.isPlaying = false;
					}
					
					if(mp !=null) {
						mp.release();
						App.lectureStarted = false;
					}

					mp = new MediaPlayer();
					App.mp = mp;
					mp.setDataSource(episodeSelected.getLien());
				}
				catch(IOException e) {
					System.out.println(e.getMessage());
				}


				TextView nomSagaView = (TextView) findViewById(R.id.view_description_episode_titre_saga);
				nomSagaView.setText(sagaSelectionnee.getNom());

				TextView nomEpisodeView = (TextView) findViewById(R.id.view_description_episode_titreetendu_episode);
				nomEpisodeView.setText(episodeSelected.getNomEtendu());

				TextView descriptionEpisodeView = (TextView) findViewById(R.id.view_description_episode_resume_episode);
				descriptionEpisodeView.setText(episodeSelected.getDescription());

				TextView dureeEpisodeView = (TextView) findViewById(R.id.view_description_episode_duree_episode);
				dureeEpisodeView.setText(episodeSelected.getDuree());

				
				buttonLecteur = (Button) findViewById(R.id.button_description_episode_lecteur);
				buttonLecteur.setVisibility(View.VISIBLE);
				buttonLecteur.setText("Lancer l'épisode");
				buttonLecteur.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						if(App.isPlaying) {
							mp.pause();
							buttonLecteur.setText("Relancer");
							App.isPlaying = false;
						}
						else {
							try {
								if(App.lectureStarted) {
									mp.start();
								}
								else {
									mp.prepare();
									mp.start();
									App.lectureStarted = true;
								}
								
								buttonLecteur.setText("Pause");
								App.isPlaying = true;
							}
							catch (IOException e) {
								System.out.println(e.getMessage());
							}
							
						}
					}
				});



				WebView acteursView = (WebView) findViewById(R.id.view_description_episode_acteurs);
				acteursView.loadDataWithBaseURL("", episodeSelected.acteursToString(), "text/html", "UTF-8", "");				
			}
		});
	}
	
	@Override
	public void onBackPressed(){
		if(App.isPlaying) {
			App.mp.stop();
			App.isPlaying = false;
		}
		if(App.mp !=null) {
			App.mp.release();
			App.lectureStarted = false;
		}
		super.onBackPressed();
	}
}
