package com.grilledmonkey.nicerate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

public class Rater {

	public static boolean isClosable(Context context, int titleId, int messageId, int positiveId, int negativeId) {
		Settings settings = new Settings();
		settings.setDialogTitleId(titleId);
		settings.setDialogMessageId(messageId);
		settings.setDialogPositiveId(positiveId);
		settings.setDialogNegativeId(negativeId);
		return(isClosable(context, settings));
	}

	public static boolean isClosable(Context context, String title, String message, String positive, String negative) {
		Settings settings = new Settings();
		settings.setDialogTitle(title);
		settings.setDialogMessage(message);
		settings.setDialogPositive(positive);
		settings.setDialogNegative(negative);
		return(isClosable(context, settings));
	}

	public static boolean isClosable(Context context) {
		return(isClosable(context, null));
	}

	public static boolean isClosable(Context context, Rater.Settings settings) {
		if(settings == null) {
			settings = new Settings();
		}

		boolean doUpdate = false, doClose = true;
		SharedPreferences preferences = context.getSharedPreferences(settings.getSharedPreferenceName(), settings.getSharedPreferenceMode());
		int count = preferences.getInt(settings.getPreferenceCounterName(), 0);

		if(count == settings.getRateTrigger()) {
			showDialog(context, settings);
			count++;
			doUpdate = true;
			doClose = false;
		}
		else if(count < settings.getRateTrigger()) {
			count++;
			doUpdate = true;
		}

		if(doUpdate) {
			SharedPreferences.Editor editor = preferences.edit();
			editor.putInt(settings.getPreferenceCounterName(), count);
			editor.commit();
		}

		return(doClose);
	}

	protected static void showDialog(Context context, Rater.Settings settings) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		ClickListener listener = new ClickListener(context, settings);

		if(settings.getDialogIconId() > 0) {
			builder.setIcon(settings.getDialogIconId());
		}

		if(settings.getDialogTitleId() > 0) {
			builder.setTitle(settings.getDialogTitleId());
		}
		else {
			builder.setTitle(settings.getDialogTitle());
		}

		if(settings.getDialogMessageId() > 0) {
			builder.setMessage(settings.getDialogMessageId());
		}
		else {
			builder.setMessage(settings.getDialogMessage());
		}

		if(settings.getDialogPositiveId() > 0) {
			builder.setPositiveButton(settings.getDialogPositiveId(), listener);
		}
		else {
			builder.setPositiveButton(settings.getDialogPositive(), listener);
		}

		if(settings.getDialogNegativeId() > 0) {
			builder.setNegativeButton(settings.getDialogNegativeId(), listener);
		}
		else {
			builder.setNegativeButton(settings.getDialogNegative(), listener);
		}

		builder.show();
	}

	public static class Settings {
		protected String sharedPreferenceName = "preferences";
		protected int sharedPreferenceMode = Context.MODE_PRIVATE;
		protected String preferenceCounterName = "niceRateCounter";
		protected int rateTrigger = 5;

		protected int dialogIconId = 0;
		protected int dialogTitleId = 0;
		protected int dialogMessageId = 0;
		protected int dialogPositiveId = 0;
		protected int dialogNegativeId = 0;

		protected String dialogTitle = "Rate application";
		protected String dialogMessage = "Please rate our application.";
		protected String dialogPositive = "OK";
		protected String dialogNegative = "Cancel";

		protected OnRateListener rateListener = null;

		public OnRateListener getRateListener() {
			return rateListener;
		}

		public void setRateListener(OnRateListener rateListener) {
			this.rateListener = rateListener;
		}

		public int getRateTrigger() {
			return rateTrigger;
		}

		public void setRateTrigger(int rateTrigger) {
			this.rateTrigger = rateTrigger;
		}

		public String getPreferenceCounterName() {
			return preferenceCounterName;
		}

		public void setPreferenceCounterName(String preferenceCounterName) {
			this.preferenceCounterName = preferenceCounterName;
		}

		public int getSharedPreferenceMode() {
			return sharedPreferenceMode;
		}

		public void setSharedPreferenceMode(int sharedPreferenceMode) {
			this.sharedPreferenceMode = sharedPreferenceMode;
		}

		public String getSharedPreferenceName() {
			return sharedPreferenceName;
		}

		public void setSharedPreferenceName(String sharedPreferenceName) {
			this.sharedPreferenceName = sharedPreferenceName;
		}

		public int getDialogIconId() {
			return dialogIconId;
		}

		public void setDialogIconId(int dialogIconId) {
			this.dialogIconId = dialogIconId;
		}

		public int getDialogTitleId() {
			return dialogTitleId;
		}

		public void setDialogTitleId(int dialogTitleId) {
			this.dialogTitleId = dialogTitleId;
		}

		public int getDialogMessageId() {
			return dialogMessageId;
		}

		public void setDialogMessageId(int dialogMessageId) {
			this.dialogMessageId = dialogMessageId;
		}

		public int getDialogPositiveId() {
			return dialogPositiveId;
		}

		public void setDialogPositiveId(int dialogPositiveId) {
			this.dialogPositiveId = dialogPositiveId;
		}

		public int getDialogNegativeId() {
			return dialogNegativeId;
		}

		public void setDialogNegativeId(int dialogNegativeId) {
			this.dialogNegativeId = dialogNegativeId;
		}

		public String getDialogTitle() {
			return dialogTitle;
		}

		public void setDialogTitle(String dialogTitle) {
			this.dialogTitle = dialogTitle;
		}

		public String getDialogMessage() {
			return dialogMessage;
		}

		public void setDialogMessage(String dialogMessage) {
			this.dialogMessage = dialogMessage;
		}

		public String getDialogPositive() {
			return dialogPositive;
		}

		public void setDialogPositive(String dialogPositive) {
			this.dialogPositive = dialogPositive;
		}

		public String getDialogNegative() {
			return dialogNegative;
		}

		public void setDialogNegative(String dialogNegative) {
			this.dialogNegative = dialogNegative;
		}
	}

	public static interface OnRateListener {
		public void onRate();
	}

	protected static class ClickListener implements DialogInterface.OnClickListener {
		private final static String MARKET_URL_BASE = "https://play.google.com/store/apps/details?id=";

		private final Context context;
		private final Settings settings;

		public ClickListener(Context context, Settings settings) {
			this.context = context;
			this.settings = settings;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch(which) {
				case DialogInterface.BUTTON_POSITIVE:
					if(settings.getRateListener() != null) {
						settings.getRateListener().onRate();
					}
					else {
						context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(MARKET_URL_BASE + context.getPackageName())));
					}
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					break;
			}
		}

	}

}
