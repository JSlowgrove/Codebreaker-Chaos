package codebreakerchaos.masteredchaos.js;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Credits extends Activity {
	/** Called when the activity is first created. */

	TextView CredMCL, CredCreate, CredNotes, CredTitle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.credits);
		Initalise();
	}

	private void Initalise() {
		CredMCL = (TextView) findViewById(R.id.creditsmclogo);
		CredCreate = (TextView) findViewById(R.id.creditscreator);
		CredNotes = (TextView) findViewById(R.id.creditsnotes);
		CredTitle = (TextView) findViewById(R.id.credits);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}