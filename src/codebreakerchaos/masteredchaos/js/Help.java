package codebreakerchaos.masteredchaos.js;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class Help extends Activity {
	/** Called when the activity is first created. */

	TextView helpTitle;
	ImageView helpImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.help);
		initalise();
	}

	private void initalise() {
		helpTitle = (TextView) findViewById(R.id.help);
		helpImage = (ImageView) findViewById(R.id.ivHelp);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}