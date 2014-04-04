package codebreakerchaos.masteredchaos.js;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StartingPoint extends Activity {
	/** Called when the activity is first created. */

	Button Start, Scores, Options;
	TextView MCSite;
	ImageView MCLogo, CCLogo;
	boolean savedGame;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		filecheck();
		setContentView(R.layout.main);
		initalise();
		Start.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent openStart = new Intent(
						"codebreakerchaos.masteredchaos.js.START");

				if (savedGame == true) {
					openStart = new Intent(
							"codebreakerchaos.masteredchaos.js.LOADCHOICE");
				}

				startActivity(openStart);
			}
		});
		Scores.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent openScores = new Intent(
						"codebreakerchaos.masteredchaos.js.SCORES");
				startActivity(openScores);
			}
		});
		Options.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent openCredits = new Intent(
						"codebreakerchaos.masteredchaos.js.OPTIONS");
				startActivity(openCredits);
			}
		});

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		filecheck();
	}

	private void initalise() {
		Start = (Button) findViewById(R.id.bStart);
		Scores = (Button) findViewById(R.id.bScores);
		Options = (Button) findViewById(R.id.bOptions);
		MCSite = (TextView) findViewById(R.id.tvMCsite);
		MCLogo = (ImageView) findViewById(R.id.ivMClogo);
		CCLogo = (ImageView) findViewById(R.id.ivCClogo);
	}

	private void filecheck() { // checks if the score and setup file exists, if
								// not then
								// it creates a new default file if so it checks
								// for a saved file
		String filename = "setup_file";
		String defaultSetup = "1,0";
		FileOutputStream Out;
		FileInputStream In;
		int bytenum;
		int current = 0;
		StringBuffer fileContent = new StringBuffer("");
		try {
			In = openFileInput(filename);
			bytenum = (In.toString()).length();
			byte[] buffer = new byte[bytenum];
			try {
				while ((current = In.read(buffer)) != -1) {
					fileContent.append(new String(buffer));
				}
				String data = new String(fileContent);
				if (data.charAt(2) == "0".charAt(0)) {
					savedGame = false;
				} else if (data.charAt(2) == "1".charAt(0)) {
					savedGame = true;
				} else {
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				In.close();
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			savedGame = false;
			try {
				Out = openFileOutput(filename, Context.MODE_PRIVATE);
				try {
					Out.write(defaultSetup.getBytes());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					Out.close();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			} catch (FileNotFoundException er) {
				// TODO Auto-generated catch block
				er.printStackTrace();
			}
		}
		filename = "scores_file";
		defaultSetup = "-#-#-#-#-#-#-#-#-#-";
		try {
			In = openFileInput(filename);
			try {
				In.close();
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			try {
				Out = openFileOutput(filename, Context.MODE_PRIVATE);
				try {
					Out.write(defaultSetup.getBytes());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					Out.close();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			} catch (FileNotFoundException er) {
				// TODO Auto-generated catch block
				er.printStackTrace();
			}
		}
	}
	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setMessage(
						"Are you sure you want to leave?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								finish();
							}
						}).setNegativeButton("No", null).show();
	}
}