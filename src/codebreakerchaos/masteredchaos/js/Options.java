package codebreakerchaos.masteredchaos.js;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Options extends Activity {
	/** Called when the activity is first created. */

	Button creds, help, dif;
	TextView title, difTitle;
	int difficulty, savedData;
	String filename = "setup_file";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.options);
		initalise();
		dif.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (difficulty) {
				case 0:
					difficulty = 1;
					dif.setText("Normal");
					dif.setTextColor(Color.YELLOW);
					break;
				case 1:
					difficulty = 2;
					dif.setText("Hard");
					dif.setTextColor(Color.RED);
					break;
				case 2:
					difficulty = 0;
					dif.setText("Easy");
					dif.setTextColor(Color.GREEN);
					break;
				}
				difChange();
				final ProgressDialog saving = new ProgressDialog(Options.this);
				saving.setMessage("Saving. Please wait...");
				saving.setCancelable(false);
				saving.show();
				Thread Timer = new Thread() {
					public void run() {
						try {
							sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} finally {
							saving.dismiss();
						}
					}
				};
				Timer.start();
			}
		});
		help.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent openScores = new Intent(
						"codebreakerchaos.masteredchaos.js.HELP");
				startActivity(openScores);
			}
		});
		creds.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent openCredits = new Intent(
						"codebreakerchaos.masteredchaos.js.CREDITS");
				startActivity(openCredits);
			}
		});

	}

	private void difChange() {
		String dataToSave = Integer.toString(difficulty) + ","
				+ Integer.toString(savedData);
		FileOutputStream outputStream;
		try {
			outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
			outputStream.write(dataToSave.getBytes());
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initalise() {
		creds = (Button) findViewById(R.id.bCredits);
		help = (Button) findViewById(R.id.bHelp);
		dif = (Button) findViewById(R.id.bCurentDif);
		title = (TextView) findViewById(R.id.tvOptions);
		difTitle = (TextView) findViewById(R.id.tvDifTitle);
		load();
		switch (difficulty) {
		case 0:
			dif.setText("Easy");
			dif.setTextColor(Color.GREEN);
			break;
		case 1:
			dif.setText("Normal");
			dif.setTextColor(Color.YELLOW);
			break;
		case 2:
			dif.setText("Hard");
			dif.setTextColor(Color.RED);
			break;
		}

	}

	private void load() { // loads the data from the file
		int bytenum;
		int current = 0;
		current = current + 0;
		StringBuffer fileContent = new StringBuffer("");
		FileInputStream In = null;
		try {
			In = openFileInput(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bytenum = (In.toString()).length();
		byte[] buffer = new byte[bytenum];
		try {
			while ((current = In.read(buffer)) != -1) {
				fileContent.append(new String(buffer));
			}
			String data = new String(fileContent);
			difficulty = Integer.parseInt(Character.toString(data.charAt(0)));
			savedData = Integer.parseInt(Character.toString(data.charAt(2)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			In.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
}