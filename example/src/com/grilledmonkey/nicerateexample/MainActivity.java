package com.grilledmonkey.nicerateexample;

import android.app.Activity;
import android.os.Bundle;

import com.grilledmonkey.nicerate.Rater;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	/*
	 * This is a very basic usage of NiceRate - just call it from onBackPressed()
	 * and close activity if function returns true.
	 */
	/*@Override
	public void onBackPressed() {
		if(Rater.isClosable(this)) {
			finish();
		}
	}*/

	/*
	 * This is an example of how to change settings
	 */
	/*@Override
	public void onBackPressed() {
		Rater.Settings settings = new Rater.Settings();
		settings.setDialogMessage("Rate us NOW!");
		settings.setRateTrigger(2);
		if(Rater.isClosable(this, settings)) {
			finish();
		}
	}*/

	/*
	 * You can also pass strings/res ids for quick call.
	 */
	@Override
	public void onBackPressed() {
		if(Rater.isClosable(this, "Title", "Message", "Yes", "No")) {
			finish();
		}
	}

}
