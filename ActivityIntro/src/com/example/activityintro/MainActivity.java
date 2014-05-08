package com.example.activityintro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.activityintro.fragment.PlaceholderFragment;

public class MainActivity extends ActionBarActivity {

	private static final int MAIN_ACTIVITY_CODE = 1;
	private static final int ACT2_REQUEST_CODE = 2;
	private static final int ACT3_REQUEST_CODE = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}



	public void changeToActivity(View view) {
		Intent intent = new Intent();
		switch (view.getId()) {
		case R.id.button1:
			intent.setClass(this, Activity1.class);
			// startActivity(intent);

			intent.putExtra("MESSAGE", "HI1");
			startActivityForResult(intent, MAIN_ACTIVITY_CODE);
			break;
		case R.id.button2:
			intent.setClass(this, Activity2.class);
			// startActivity(intent);

			intent.putExtra("MESSAGE", "HI2");
			startActivityForResult(intent, ACT2_REQUEST_CODE);
			break;
		case R.id.button3:
			intent.setClass(this, Activity3.class);
			// startActivity(intent);

			intent.putExtra("MESSAGE", "HI3");
			startActivityForResult(intent, ACT3_REQUEST_CODE);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == MAIN_ACTIVITY_CODE) {
			Log.d("debug", "requestCode:" + requestCode + ", resultCode"
					+ resultCode);
			if (intent != null) {
				Log.d("debug", intent.getStringExtra("WHICHBUTTON"));
			}
			Toast.makeText(this, "ACT1", Toast.LENGTH_SHORT).show();
		} else if (requestCode == ACT2_REQUEST_CODE) {
			Toast.makeText(this, "ACT2", Toast.LENGTH_SHORT).show();
		} else if (requestCode == ACT3_REQUEST_CODE) {
			Toast.makeText(this, "ACT3", Toast.LENGTH_SHORT).show();

		}
	}

}
