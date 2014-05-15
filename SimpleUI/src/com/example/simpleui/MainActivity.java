package com.example.simpleui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.PushService;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		Parse.initialize(this, "wj1uKrCQB65p4ohKMgUF8olZnegyh4EgK20Qnfy6",
				"afAqZ39F3NL0qDlIbFdzSwfmaybehimetEcAIgDu");
		PushService.setDefaultPushCallback(this, MainActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		// When users indicate they are Giants fans, we subscribe them to that
		// channel.
		PushService.subscribe(this, "ChannelALL", MainActivity.class);
		PushService.subscribe(this, "ID" + getDeviceId(), MainActivity.class);
		register();
	}

	public String getDeviceId() {
		return Secure.getString(getContentResolver(), Secure.ANDROID_ID);
	}

	public void register() {
		ParseObject obj = new ParseObject("DEVICE_ID");
		obj.add("DEVICE_ID", getDeviceId());
		obj.saveInBackground();
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		private void sendMsg() {
			String text = textField.getText().toString();
			if (checkBox.isChecked()) {
				text = "****";
			}
			Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();

			String deviceId = (String) spinner.getSelectedItem();

			ParsePush push = new ParsePush();
			// push.setChannel("ChannelALL");
			push.setChannel("ID" + deviceId);
			push.setMessage(text);
			push.sendInBackground();

			Intent intent = new Intent();
			intent.setClass(getActivity(), MessageActivity.class);
			intent.putExtra("TEXT", text);
			intent.putExtra("CHECKED", checkBox.isChecked());
			getActivity().startActivity(intent);

			textField.getText().clear();

		}

		Button btn;
		EditText textField;
		CheckBox checkBox;

		SharedPreferences sp;
		Editor editor;

		Spinner spinner;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			btn = (Button) rootView.findViewById(R.id.button2);
			textField = (EditText) rootView.findViewById(R.id.editText1);
			checkBox = (CheckBox) rootView.findViewById(R.id.checkBox1);

			sp = getActivity().getSharedPreferences("settings",
					Context.MODE_PRIVATE);
			editor = sp.edit();

			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					sendMsg();
				}
			});

			checkBox.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					editor.putBoolean("checked", checkBox.isChecked());
					editor.commit();
				}
			});

			textField.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					Log.d("androidClass", "keyCode:" + keyCode + ", event: "
							+ event.getAction());

					if (event.getAction() == KeyEvent.ACTION_DOWN
							&& keyCode == KeyEvent.KEYCODE_ENTER) {
						sendMsg();
						return true;
					} else {

						editor.putString("text", textField.getText().toString());
						editor.commit();

					}
					return false;
				}
			});

			textField.setText(sp.getString("text", "Hello World"));
			checkBox.setChecked(sp.getBoolean("checked", false));

			spinner = (Spinner) rootView.findViewById(R.id.spinner1);

			return rootView;
		}

		private void loadData() {

			ParseQuery<ParseObject> query = ParseQuery.getQuery("DEVICE_ID");
			query.findInBackground(new FindCallback<ParseObject>() {
				public void done(List<ParseObject> msgList, ParseException e) {
					if (e == null) {
						List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

						String[] strArray = new String[data.size()];

						for (int i = 0; i < strArray.length; i++) {
							strArray[i] = data.get(i).toString();
						}

						ArrayAdapter<String> adapter = new ArrayAdapter<String>(
								getActivity(),
								android.R.layout.simple_spinner_item, strArray);

						spinner.setAdapter(adapter);
					} else {
						e.printStackTrace();
					}
				}
			});
		}
	}

	public void btnClick(View view) {
		Log.d("androidClass", "clicked");
	}
}
