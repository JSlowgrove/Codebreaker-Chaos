package uk.co.jamieslowgrove.codebreakerchaos;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Splash extends Activity {

	MediaPlayer SplashSound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.splash);

		SplashSound = MediaPlayer.create(Splash.this, R.raw.splashsound);
		SplashSound.start();

		Thread Timer = new Thread() {
			public void run() {
				try {
					sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent opentStartingPoint = new Intent(
							"uk.co.jamieslowgrove.codebreakerchaos.STARTINGPOINT");
					startActivity(opentStartingPoint);
				}
			}
		};
		Timer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		SplashSound.release();
		finish();
	}

}
