package com.example.activityintro;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Activity2 extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("debug", "act2 create");
	}
}
