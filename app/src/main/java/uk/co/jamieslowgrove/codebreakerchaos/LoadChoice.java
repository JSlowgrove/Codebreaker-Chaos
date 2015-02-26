package uk.co.jamieslowgrove.codebreakerchaos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class LoadChoice extends Activity {
	/** Called when the activity is first created. */

	Button newGame, old;
	TextView choice;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.loadchoice);
		initalise();
		newGame.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				String fileName = "setup_file";
				String dataToSave = null;
				FileOutputStream Out;
				FileInputStream In;
				int bytenum;
				int current = 0;
				StringBuffer fileContent = new StringBuffer("");
				try {
					In = openFileInput(fileName);
					bytenum = (In.toString()).length();
					byte[] buffer = new byte[bytenum];
					try {
						while ((current = In.read(buffer)) != -1) {
							fileContent.append(new String(buffer));
						}
						String data = new String(fileContent);
						dataToSave = (String.valueOf(data.charAt(0)) + ",0");
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
					e.printStackTrace();
				}
				try {
					Out = openFileOutput(fileName, Context.MODE_PRIVATE);
					try {
						Out.write(dataToSave.getBytes());
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
				Intent openStart = new Intent(
						"uk.co.jamieslowgrove.codebreakerchaos.START");
				startActivity(openStart);
			}
		});
		old.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent openStart = new Intent(
						"uk.co.jamieslowgrove.codebreakerchaos.START");
				startActivity(openStart);
			}
		});
	}

	private void initalise() {
		newGame = (Button) findViewById(R.id.bNew);
		old = (Button) findViewById(R.id.bOld);
		choice = (TextView) findViewById(R.id.tvChoice);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}