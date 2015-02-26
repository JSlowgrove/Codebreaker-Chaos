package uk.co.jamieslowgrove.codebreakerchaos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Start extends Activity {
	/** Called when the activity is first created. */

	// GLOBAL VARIABLES//
	long startTime, endTime, sec, min, hour;
	long loadedTime = 0;
	TextView codeToBreak, codeCurrentBroke, letters;
	int count, score, difficulty, isSave;
	int[] numKey = new int[26];
	char[] codeKey = new char[27];
	char[] codeKeysUsed = new char[27];
	Button checkButton, saveButton;
	String[] inBox = new String[26];
	EditText[] editTextId = new EditText[26];
	boolean loading = false;
	boolean setup = true;
	String userCurrent = "";
	String usedLetters = "";
	String codeStart = "";
	String message = "";

	// GLOBAL VARIABLES//

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.start);
		initialise();
		if (isSave == 0) {
			setMessage();
			randomNumKey();
		} else if (isSave == 1) {
			loadPrevious();
		} else {
		}
		setCode();
		if (isSave == 1) {
			fillInBoxes();
		} else {
		}

		codeToBreak.setText(codeStart);
		checkButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				userCurrent();
				setCode();
				if (userCurrent.equals(message)) {
					Intent win = new Intent(
							"uk.co.jamieslowgrove.codebreakerchaos.WIN");
					codeCurrentBroke.setText(userCurrent);
					endTime = System.nanoTime();
					endTime = (endTime - startTime) + loadedTime;
					sec = Math.round(endTime / 1000000000);
					switch (difficulty) {
					case (0):
						score = 300 - ((int) sec);
						break;
					case (1):
						score = 600 - ((int) sec);
						break;
					case (2):
						score = 900 - ((int) sec);
						break;
					}
					min = Math.round(sec / 60);
					sec = sec % 60;
					hour = Math.round(min / 60);
					min = min % 60;
					Bundle secValues = new Bundle();
					secValues.putLong("sec", sec);
					Bundle minValues = new Bundle();
					secValues.putLong("min", min);
					Bundle hourValues = new Bundle();
					hourValues.putLong("hour", hour);
					Bundle scoreValues = new Bundle();
					scoreValues.putInt("score", score);
					win.putExtras(scoreValues);
					win.putExtras(secValues);
					win.putExtras(minValues);
					win.putExtras(hourValues);
					startActivity(win);
					finish();
				} else {
					codeCurrentBroke.setText(userCurrent);
				}
			}
		});

		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				String dataToSave = Integer.toString(difficulty) + "," + "1";
				String filename = "setup_file";
				FileOutputStream outputStream;
				try {
					outputStream = openFileOutput(filename,
							Context.MODE_PRIVATE);
					outputStream.write(dataToSave.getBytes());
					outputStream.flush();
					outputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				saveGame();
				final ProgressDialog saving = new ProgressDialog(Start.this);
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
	}
	

	private void initialise() {
		// TODO Auto-generated method stub
		startTime = System.nanoTime();
		checkButton = (Button) findViewById(R.id.bCheck);
		saveButton = (Button) findViewById(R.id.bSave);
		codeToBreak = (TextView) findViewById(R.id.codetobreak);
		codeCurrentBroke = (TextView) findViewById(R.id.codecurrentbroke);
		letters = (TextView) findViewById(R.id.letters);
		int[] idInt = {R.id.exclamationchoice,R.id.speechmarkchoice, R.id.hashchoice,
				R.id.dollarchoice, R.id.percentchoice, R.id.andchoice,
				R.id.apostraphychoice, R.id.openbracketchoice,
				R.id.closebracketchoice, R.id.starchoice, R.id.pluschoice,
				R.id.commachoice, R.id.dashchoice, R.id.dotchoice,
				R.id.slashchoice, R.id.zerochoice, R.id.onechoice,
				R.id.twochoice, R.id.threechoice, R.id.fourchoice,
				R.id.fivechoice, R.id.sixchoice, R.id.sevenchoice,
				R.id.eightchoice, R.id.ninechoice, R.id.colonchoice };
		for (int i = 0; i < 26; i++) {
			editTextId[i] = (EditText) findViewById(idInt[i]);
		}
		String filename = "setup_file";
		int bytenum;
		int current = 0;
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
			difficulty = Integer.parseInt(String.valueOf(data.charAt(0)));
			isSave = Integer.parseInt(String.valueOf(data.charAt(2)));
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

	private void saveGame() {
		// TODO Auto-generated method stub
		endTime = System.nanoTime();
		userCurrent();
		setCode();
		String dataToSave = message + "~" + codeStart + "~" + userCurrent + "~"
				+ usedLetters + "~" + Long.toString(endTime - startTime) + "~";
		for (int i = 0; i < 27; i++) {
			dataToSave = dataToSave + String.valueOf(codeKey[i]);
		}
		dataToSave = dataToSave + "~";
		for (int i = 0; i < 27; i++) {
			dataToSave = dataToSave + String.valueOf(codeKeysUsed[i]);
		}
		String filename = "game_file";
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

	private void loadPrevious() {
		// TODO Auto-generated method stub
		loading = true;
		int bytenum;
		int current = 0;
		String loaded = "";
		String fileName = "game_file";
		StringBuffer fileContent = new StringBuffer("");
		FileInputStream In = null;
		try {
			In = openFileInput(fileName);
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
			loaded = data;
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
		String[] messages = loaded.split("~");
		message = messages[0];
		codeStart = messages[1];
		userCurrent = messages[2];
		usedLetters = messages[3];
		loadedTime = Long.parseLong(messages[4]);
		for (int i = 0; i < 27; i++) {
			codeKey[i] = messages[5].charAt(i);
			codeKeysUsed[i] = messages[6].charAt(i);
		}
		letters.setText(usedLetters);
	}

	private void fillInBoxes() {
		// TODO Auto-generated method stub
		int messageLength = userCurrent.length();
		char aASCII = 0;
		boolean inMessage = false;
		for (int i = 0; i < 26; i++) {
			for (int i2 = 0; i2 < messageLength; i2++) {
				if (userCurrent.charAt(i2) == (char) (i + 97)) {
					inMessage = true;
					aASCII = codeStart.charAt(i2);
				}
			}
			if (inMessage == true) {
				editTextId[((int) aASCII) - 33].setText(String
						.valueOf((char) (i + 97)));
			}
			inMessage = false;
		}
	}

	private void setCode() {
		StringBuilder combo;
		int assciValue = 0;
		String coded = "";
		int length = message.length(); // finds value for length of message
		for (int i = 0; i < length; i++) { // 0 to length-1
			char LetterChar = message.charAt(i); // collect char for the letter
													// i in the message
			assciValue = ((int) LetterChar); // converts the char to the assci
												// value
			if (assciValue < 90) { // if capital letter - 65 for char place in
									// alphabet (0-26)
				assciValue = assciValue - 65;
				if (assciValue == -33) { // checks for if it is a space, if so
											// it sets it so
					assciValue = 26;
				} else {
				}
			} else { // if lowercase letter - 97 for char place in alphabet
						// (0-26)
				assciValue = assciValue - 97;
			}
			if (setup == true) {
				combo = new StringBuilder(coded).append(codeKey[assciValue]); // compiles
																				// chars
																				// coded
																				// version
																				// into
																				// a
																				// string
				coded = combo.toString();
			} else {
				combo = new StringBuilder(coded)
						.append(codeKeysUsed[assciValue]); // compiles chars
															// coded version
															// into a string
				coded = combo.toString();
			}
		}
		if (setup == true) {
			codeStart = coded;
			if (loading == false) {
				userCurrent = coded;
			} else {
				loading = false;
			}
			codeCurrentBroke.setText(userCurrent);
			displaySetup();
		} else {
			userCurrent = coded;
		}
		setup = false;
	}

	private void userCurrent() {
		for (int i = 0; i < 26; i++) {
			inBox[i] = " " + editTextId[i].getText().toString() + " ";
		}
		codeKeysUsed[26] = codeKey[26];
		int a = 0;
		for (int i = 0; i < 26; i++) {
			while (codeKey[a] != (char) (i + 33)) {
				a++;
			}
			if ((inBox[i].charAt(1)) == " ".charAt(0)) {
				codeKeysUsed[a] = codeKey[a];
			} else {
				if (inBox[i].charAt(1) == "I".charAt(0)) {
					codeKeysUsed[a] = "i".charAt(0);
				} else {
					codeKeysUsed[a] = inBox[i].charAt(1);
				}
			}
			a = 0;
		}
	}

	private void displaySetup() {
		TextView[] textId = new TextView[26];
		int[] idInt = { R.id.exclamation, R.id.speechmark, R.id.hash,
				R.id.dollar, R.id.percent, R.id.and, R.id.apostraphy,
				R.id.openbracket, R.id.closebracket, R.id.star, R.id.plus,
				R.id.comma, R.id.dash, R.id.dot, R.id.slash, R.id.zero,
				R.id.one, R.id.two, R.id.three, R.id.four, R.id.five, R.id.six,
				R.id.seven, R.id.eight, R.id.nine, R.id.colon };
		for (int i = 0; i < 26; i++) {
			textId[i] = (TextView) findViewById(idInt[i]);
		}
		for (int i = 0; i < 26; i++) {
			editTextId[i].setVisibility(View.GONE);
			textId[i].setVisibility(View.GONE);
		}
		DisplayMetrics metrics = new DisplayMetrics(); // find out the
		// screens with and
		// height and saves
		// them as variables
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int screenwidth = metrics.widthPixels;
		int a = 260;
		int b = -5;
		int length = message.length(); // finds value for length of message
		boolean check = false;
		for (int i = 0; i < length; i++) {
			for (int i2 = 0; i2 < 26; i2++) {
				if (codeStart.charAt(i) == (char) (i2 + 33) && check == false) {
					a = a - 5;
					b = b + 5;
					if (a == b) {
						a = a - 5;
						b = b + 5;
					} else {

					}
					editTextId[i2].setVisibility(View.VISIBLE);
					editTextId[i2].setTextColor(Color.rgb(0, a, 0));
					editTextId[i2].setBackgroundColor(Color.rgb(0, b, 0));
					editTextId[i2].setWidth(screenwidth / 10);
					textId[i2].setVisibility(View.VISIBLE);
					textId[i2].setTextColor(Color.rgb(0, b, 0));
					textId[i2].setBackgroundColor(Color.rgb(0, a, 0));
					textId[i2].setWidth(screenwidth / 10);
					check = true;
				} else {
				}
			}
			check = false;
		}
	}

	private void setMessage() {
		Random num = new Random();
		int a = 0;
		int rand = num.nextInt(6);
		switch (rand) {
		case 0:
			message = "you";
			break;
		case 1:
			message = "they";
			break;
		case 2:
			message = "clouds";
			break;
		case 3:
			message = "fish";
			break;
		case 4:
			message = "penguins";
			break;
		case 5:
			message = "socks";
			break;
		}
		if (difficulty == 2 || difficulty == 1) {
			num = new Random();
			rand = num.nextInt(6);
			switch (rand) {
			case 0:
				message = message + "   " + "are";
				break;
			case 1:
				message = message + "   " + "eat";
				break;
			case 2:
				message = message + "   " + "like";
				a = 1;
				break;
			case 3:
				message = message + "   " + "sell";
				break;
			case 4:
				message = message + "   " + "dance";
				break;
			case 5:
				message = message + "   " + "fish";
				break;
			}
		}
		if (difficulty == 2) {
			num = new Random();
			rand = num.nextInt(4);
			switch (rand) {
			case 0:
				message = message + "   " + "like";
				break;
			case 1:
				message = message + "   " + "with";
				break;
			case 2:
				if (a == 0) {
					message = message + "   " + "on";
				} else {
					message = message + "   " + "with";
				}
				break;
			case 3:
				message = message + "   " + "at";
				break;
			}
		}
		if (difficulty == 2 || difficulty == 1) {
			num = new Random();
			rand = num.nextInt(6);
			switch (rand) {
			case 0:
				message = message + "   " + "grapes";
				break;
			case 1:
				message = message + "   " + "cakes";
				break;
			case 2:
				message = message + "   " + "software";
				break;
			case 3:
				message = message + "   " + "shops";
				break;
			case 4:
				message = message + "   " + "hats";
				break;
			case 5:
				message = message + "   " + "pie";
				break;
			}
		}
		StringBuilder tempUsed;
		int messageLength = message.length();
		boolean inMessage = false;
		for (int i = 0; i < 26; i++) {
			for (int i2 = 0; i2 < messageLength; i2++) {
				if (message.charAt(i2) == (char) (i + 97)) {
					inMessage = true;
				}
			}
			if (inMessage == true) {
				tempUsed = new StringBuilder(usedLetters)
						.append((char) (i + 97) + ",");
				usedLetters = tempUsed.toString();
				tempUsed = new StringBuilder(usedLetters).append(",");
			}
			inMessage = false;
		}
		letters.setText(usedLetters);
	}

	private void randomNumKey() {
		// TODO Auto-generated method stub -Randomly sets a symbol to an array
		for (int i = 0; i < 26; i++) {
			count = 0;
			Random num = new Random();
			numKey[i] = num.nextInt(26);
			while (count <= i - 1) {// -checks if there are multiple of a number
									// in the array, if so it randomly chooses a
									// number until it has one that is not used
				if (numKey[i] == numKey[count]) {
					numKey[i] = num.nextInt(26);
					count = 0;
				} else {
					count++;
				}
			}

		}

		for (int i = 0; i < 26; i++) {
			codeKey[numKey[i]] = (char) (i + 33);
		}
		codeKey[26] = (char) (32);
	}


	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setMessage(
						"Are you sure you want to exit? Any unsaved progress will be lost.")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								finish();
							}
						}).setNegativeButton("No", null).show();
	}
}
