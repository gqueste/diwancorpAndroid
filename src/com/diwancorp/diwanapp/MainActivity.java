package com.diwancorp.diwanapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.diwancorp.diwanapp.data.DiwanCorp;
import com.diwancorp.diwanapp.data.Saga;

public class MainActivity extends Activity {
	
	public static String NOM_SAGA = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(App.isPlaying) {
			App.mp.stop();
			App.isPlaying = false;
		}
		
		if(App.mp !=null) {
			App.mp.release();
			App.lectureStarted = false;
		}
		
		LinearLayout layoutSagas = ( LinearLayout ) findViewById(R.id.layout_buttons_sagas );
				
		ScrollView sv = new ScrollView(this);
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.setGravity(Gravity.CENTER);
		
		//Initialisation de Diwancorp
		DiwanCorp contenu = DiwanCorp.getInstance();
		
		for (Saga s : contenu.getListeSagas()) {
			Button button = new Button(this);
			button.setText(s.getNom());
			button.setId(s.getId());
			LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			
			button.setOnClickListener(new View.OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	
		        	DiwanCorp contenu = DiwanCorp.getInstance();
		        	Saga s = contenu.getSagaById(v.getId());
		        	
		        	LinearLayout layoutDescription = (LinearLayout) findViewById(R.id.layout_description_sagas);
		        	layoutDescription.removeAllViewsInLayout();
		        	
		        	ScrollView sv = new ScrollView(getApplicationContext());
		        	LinearLayout ll = new LinearLayout(getApplicationContext());
		        	ll.setOrientation(LinearLayout.VERTICAL);
		        	
		        	TextView descriptionText = new TextView(getApplicationContext());
		        	descriptionText.setText(s.getDescription());
		        	
		        	Button button = new Button(getApplicationContext());
		        	button.setText(s.getNom());
		        	
		        	button.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							DiwanCorp contenu = DiwanCorp.getInstance();
							Button b = (Button) v;
				        	Saga s = contenu.getSagaByName((String)b.getText());
							
							Intent intent = new Intent(MainActivity.this, PresentationSaga.class);
							
							intent.putExtra(MainActivity.NOM_SAGA, s.getNom());
							startActivity(intent);
						}
					});
		        	
		        	
		        	
		        	LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		        	button.setLayoutParams(lp);
		        	
		        	ll.addView(descriptionText);
		        	ll.addView(button);
		        	
		        	sv.addView(ll);
		        	layoutDescription.addView(sv);
					
		        }
		    });
			
			
			ll.addView(button, lp);
		}
		sv.addView(ll);
		layoutSagas.addView(sv);
		
		LinearLayout newsLayout = (LinearLayout) findViewById(R.id.layout_description_sagas);
		
		TextView titreNews = new TextView(getApplicationContext());
		titreNews.setText("Nouveau : " + contenu.getNews().getDate());
		titreNews.setTextSize(20);
		
		TextView contenuNews = new TextView(getApplicationContext());
		contenuNews.setText(contenu.getNews().getContenu());
		contenuNews.setPadding(10, 5, 10, 10);
		
		newsLayout.addView(titreNews);
		newsLayout.addView(contenuNews);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
