package com.example.activityintro;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Activity1 extends Activity {
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("debug", "onActivityResult");
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onApplyThemeResource(Theme theme, int resid, boolean first) {
		Log.d("debug", "onApplyThemeResource");
		super.onApplyThemeResource(theme, resid, first);
	}

	@Override
	protected void onChildTitleChanged(Activity childActivity,
			CharSequence title) {
		Log.d("debug", "onChildTitleChanged");
		super.onChildTitleChanged(childActivity, title);
	}

	@Override
	protected void onDestroy() {
		Log.d("debug", "onDestroy");
		super.onDestroy();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		Log.d("debug", "onNewIntent");
		super.onNewIntent(intent);
	}

	@Override
	protected void onPause() {
		Log.d("debug", "onPause");
		super.onPause();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		Log.d("debug", "onPostCreate");
		super.onPostCreate(savedInstanceState);
	}

	@Override
	protected void onPostResume() {
		Log.d("debug", "onPostResume");
		super.onPostResume();
	}

	@Override
	protected void onRestart() {
		Log.d("debug", "onRestart");
		super.onRestart();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.d("debug", "onRestoreInstanceState");
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onResume() {
		Log.d("debug", "onResume");
		super.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Log.d("debug", "onSaveInstanceState");
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onStart() {
		Log.d("debug", "onStart");
		super.onStart();
	}

	@Override
	protected void onStop() {
		Log.d("debug", "onStop");
		super.onStop();
	}

	@Override
	protected void onTitleChanged(CharSequence title, int color) {
		Log.d("debug", "onTitleChanged");
		super.onTitleChanged(title, color);
	}

	private static final int RESULT_CODE_DONE1 = 5566;
	private static final int RESULT_CODE_DONE2 = 7788;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("debug", "onCreate");
		setContentView(R.layout.activity1);
		super.onCreate(savedInstanceState);
		setResult(RESULT_CODE_DONE1);
	}

	public void finish(View view) {
		Intent intent = new Intent();
		switch (view.getId()) {
		case R.id.button1:
			intent.putExtra("WHICHBUTTON", "BTN1");
			// setResult(RESULT_CODE_DONE1);
			setResult(RESULT_CODE_DONE1, intent);
			break;
		case R.id.button2:
			intent.putExtra("WHICHBUTTON", "BTN2");
			setResult(RESULT_CODE_DONE2, intent);
			break;
		}
		// 結束現在的activity
		finish();
	}
}
