package codebreakerchaos.masteredchaos.js;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Scores extends Activity {
	/** Called when the activity is first created. */

	TextView scoresTitle, firstPlaceScore, secondPlaceScore, thirdPlaceScore,
			fourthPlaceScore, fithPlaceScore, sixthPlaceScore,
			seventhPlaceScore, eighthPlaceScore, ninethPlaceScore,
			tenthPlaceScore;
	String fileName, loadedScoreCurrent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.scores);
		Initalise();

	}

	private void Initalise() {
		scoresTitle = (TextView) findViewById(R.id.scores);
		firstPlaceScore = (TextView) findViewById(R.id.firstplacescore);
		secondPlaceScore = (TextView) findViewById(R.id.secondplacescore);
		thirdPlaceScore = (TextView) findViewById(R.id.thirdplacescore);
		fourthPlaceScore = (TextView) findViewById(R.id.fourthplacescore);
		fithPlaceScore = (TextView) findViewById(R.id.fithplacescore);
		sixthPlaceScore = (TextView) findViewById(R.id.sixthplacescore);
		seventhPlaceScore = (TextView) findViewById(R.id.seventhplacescore);
		eighthPlaceScore = (TextView) findViewById(R.id.eighthplacescore);
		ninethPlaceScore = (TextView) findViewById(R.id.ninethplacescore);
		tenthPlaceScore = (TextView) findViewById(R.id.tenthplacescore);
		fileName = "scores_file"; 
		dataLoad();
		String[] ScoreData = loadedScoreCurrent.split("#");
		firstPlaceScore.setText(ScoreData[0]);
		secondPlaceScore.setText(ScoreData[1]);
		thirdPlaceScore.setText(ScoreData[2]);
		fourthPlaceScore.setText(ScoreData[3]);
		fithPlaceScore.setText(ScoreData[4]);
		sixthPlaceScore.setText(ScoreData[5]);
		seventhPlaceScore.setText(ScoreData[6]);
		eighthPlaceScore.setText(ScoreData[7]);
		ninethPlaceScore.setText(ScoreData[8]);
		tenthPlaceScore.setText(ScoreData[9]);
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
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}