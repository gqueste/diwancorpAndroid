package com.diwancorp.diwanapp;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;

/**
 * 
 * 
 *
 */

public class App extends Application {
	
	public static Context sContext;
	public static boolean isPlaying = false;;
	public static boolean lectureStarted = false;
	public static MediaPlayer mp = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		sContext = getApplicationContext();
	}

}
