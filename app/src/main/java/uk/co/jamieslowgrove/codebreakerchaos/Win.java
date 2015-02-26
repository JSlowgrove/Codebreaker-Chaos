package uk.co.jamieslowgrove.codebreakerchaos;

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

public class Win extends Activity {
	/** Called when the activity is first created. */

	TextView score, scoreTitle, timeTakenH, timeTakenM, timeTakenS,
			timeTakenTitle, win;
	Button bMain;
	ImageView guy;
	int scoreValue;
	long sec, min, hour;
	String[] ScoreData = new String[10];
	int[] loadedScore = new int[10];
	String fileName = "scores_file";
	String loadedScoreCurrent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.win);

		Initalise();

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

		timeTakenH.setText(hour + " Hour(s),");
		timeTakenM.setText(min + " Minute(s),");
		timeTakenS.setText(sec + " Second(s).");
		score.setText(Integer.toString(scoreValue));
		bMain.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private void Initalise() {
		guy = (ImageView) findViewById(R.id.smileGuy);
		win = (TextView) findViewById(R.id.win);
		score = (TextView) findViewById(R.id.score);
		scoreTitle = (TextView) findViewById(R.id.scoreTitle);
		timeTakenH = (TextView) findViewById(R.id.timeTakenH);
		timeTakenM = (TextView) findViewById(R.id.timeTakenM);
		timeTakenS = (TextView) findViewById(R.id.timeTakenS);
		timeTakenTitle = (TextView) findViewById(R.id.timeTakenTitle);
		bMain = (Button) findViewById(R.id.bMain);
		scoreValue = getIntent().getExtras().getInt("score");
		sec = getIntent().getExtras().getLong("sec");
		min = getIntent().getExtras().getLong("min");
		hour = getIntent().getExtras().getLong("hour");
		dataLoad();
		String[] TempData = loadedScoreCurrent.split("#");
		ScoreData[0] = TempData[0];
		ScoreData[1] = TempData[1];
		ScoreData[2] = TempData[2];
		ScoreData[3] = TempData[3];
		ScoreData[4] = TempData[4];
		ScoreData[5] = TempData[5];
		ScoreData[6] = TempData[6];
		ScoreData[7] = TempData[7];
		ScoreData[8] = TempData[8];
		ScoreData[9] = TempData[9];
		dataCheck();
		dataSave();
	}

	private void dataCheck() {
		// TODO Auto-generated method stub
		String dataToSave;
		if (hour == 0 && min != 0) {
			dataToSave = Integer.toString(scoreValue) + ", "
					+ Long.toString(min) + " Minute(s), " + Long.toString(sec)
					+ " Second(s)";
		} else if (min == 0 && hour == 0) {
			dataToSave = Integer.toString(scoreValue) + ", "
					+ Long.toString(sec) + " Second(s)";
		} else {
			dataToSave = Integer.toString(scoreValue) + ", "
					+ Long.toString(hour) + " Hour(s), " + Long.toString(min)
					+ " Minute(s), " + Long.toString(sec) + " Second(s)";
		}
		for (int i = 0; i < 10; i++) {
			String[] TempData = ScoreData[i].split(",");

			try {
				loadedScore[i] = Integer.parseInt(TempData[0]);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				loadedScore[i] = -19000; // imposible score
			}

		}
		if (scoreValue >= loadedScore[0]) {
			ScoreData[9] = ScoreData[8];
			ScoreData[8] = ScoreData[7];
			ScoreData[7] = ScoreData[6];
			ScoreData[6] = ScoreData[5];
			ScoreData[5] = ScoreData[4];
			ScoreData[4] = ScoreData[3];
			ScoreData[3] = ScoreData[2];
			ScoreData[2] = ScoreData[1];
			ScoreData[1] = ScoreData[0];
			ScoreData[0] = dataToSave;

		} else if (scoreValue >= loadedScore[1]) {
			ScoreData[9] = ScoreData[8];
			ScoreData[8] = ScoreData[7];
			ScoreData[7] = ScoreData[6];
			ScoreData[6] = ScoreData[5];
			ScoreData[5] = ScoreData[4];
			ScoreData[4] = ScoreData[3];
			ScoreData[3] = ScoreData[2];
			ScoreData[2] = ScoreData[1];
			ScoreData[1] = dataToSave;

		} else if (scoreValue >= loadedScore[2]) {
			ScoreData[9] = ScoreData[8];
			ScoreData[8] = ScoreData[7];
			ScoreData[7] = ScoreData[6];
			ScoreData[6] = ScoreData[5];
			ScoreData[5] = ScoreData[4];
			ScoreData[4] = ScoreData[3];
			ScoreData[3] = ScoreData[2];
			ScoreData[2] = dataToSave;

		} else if (scoreValue >= loadedScore[3]) {
			ScoreData[9] = ScoreData[8];
			ScoreData[8] = ScoreData[7];
			ScoreData[7] = ScoreData[6];
			ScoreData[6] = ScoreData[5];
			ScoreData[5] = ScoreData[4];
			ScoreData[4] = ScoreData[3];
			ScoreData[3] = dataToSave;

		} else if (scoreValue >= loadedScore[4]) {
			ScoreData[9] = ScoreData[8];
			ScoreData[8] = ScoreData[7];
			ScoreData[7] = ScoreData[6];
			ScoreData[6] = ScoreData[5];
			ScoreData[5] = ScoreData[4];
			ScoreData[4] = dataToSave;

		} else if (scoreValue >= loadedScore[5]) {
			ScoreData[9] = ScoreData[8];
			ScoreData[8] = ScoreData[7];
			ScoreData[7] = ScoreData[6];
			ScoreData[6] = ScoreData[5];
			ScoreData[5] = dataToSave;

		} else if (scoreValue >= loadedScore[6]) {
			ScoreData[9] = ScoreData[8];
			ScoreData[8] = ScoreData[7];
			ScoreData[7] = ScoreData[6];
			ScoreData[6] = dataToSave;

		} else if (scoreValue >= loadedScore[7]) {
			ScoreData[9] = ScoreData[8];
			ScoreData[8] = ScoreData[7];
			ScoreData[7] = dataToSave;

		} else if (scoreValue >= loadedScore[8]) {
			ScoreData[9] = ScoreData[8];
			ScoreData[8] = dataToSave;

		} else if (scoreValue >= loadedScore[9]) {
			ScoreData[9] = dataToSave;

		} else {

		}
	}

	private void dataLoad() {
		// TODO Auto-generated method stub
		int bytenum;
		int current = 0;
		current = current + 0;
		StringBuffer fileContent = new StringBuffer("");
		FileInputStream ScoresIn = null;
		try {
			ScoresIn = openFileInput(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bytenum = (ScoresIn.toString()).length();
		byte[] buffer = new byte[bytenum];
		try {
			while ((current = ScoresIn.read(buffer)) != -1) {
				fileContent.append(new String(buffer));
			}
			String data = new String(fileContent);
			loadedScoreCurrent = data;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ScoresIn.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void dataSave() {
		FileOutputStream Out;
		try {
			Out = openFileOutput(fileName, Context.MODE_PRIVATE);
			try {
				Out.write((ScoreData[0] + "#" + ScoreData[1] + "#"
						+ ScoreData[2] + "#" + ScoreData[3] + "#"
						+ ScoreData[4] + "#" + ScoreData[5] + "#"
						+ ScoreData[6] + "#" + ScoreData[7] + "#"
						+ ScoreData[8] + "#" + ScoreData[9] + "#").getBytes());
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

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}