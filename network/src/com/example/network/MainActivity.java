package com.example.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		// MainActivity禁止作INTERNET CONNECTION, 這邊為了上課方便才在此開放所有權限(極度不建議此作法)
		// StrictMode.ThreadPolicy policy = new
		// StrictMode.ThreadPolicy.Builder()
		// .permitAll().build();
		// StrictMode.setThreadPolicy(policy);

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

		TextView textView;
		EditText editText;
		Button button;
		ProgressDialog dialog; // 未測完

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			textView = (TextView) rootView.findViewById(R.id.textView1);
			editText = (EditText) rootView.findViewById(R.id.editText1);
			button = (Button) rootView.findViewById(R.id.button1);
			dialog = new ProgressDialog(getActivity());

			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// 連網不能在main thread, 改用AsyncTask
					// search(editText.getText().toString());

					AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

						// doInBackgroud前執行
						@Override
						protected void onPreExecute() {
							super.onPreExecute();
							dialog.show();
						}

						@Override
						protected String doInBackground(Void... params) {
							return search(editText.getText().toString());
						}

						// doInBackground後執行(UI THREAD)
						@Override
						protected void onPostExecute(String result) {
							super.onPostExecute(result);
							Toast.makeText(getActivity(), result,
									Toast.LENGTH_SHORT).show();
							dialog.dismiss();
						}
					};

					task.execute();
				}
			});

			// String content = fetch2();
			// textView.setText(content);
			return rootView;
		}

		public String search(String address) {

			String urlString = "http://maps.googleapis.com/maps/api/geocode/json?address="
					+ address + "&sensor=false";
			DefaultHttpClient client = new DefaultHttpClient();

			HttpGet get = new HttpGet(urlString);

			ResponseHandler<String> handler = new BasicResponseHandler();

			try {
				String result = client.execute(get, handler);
				Log.d("debug", result);

				JSONObject json = new JSONObject(result);
				JSONArray jsonArrayResult = (JSONArray) json.get("results");
				String realAddress = "";

				for (int i = 0; i < jsonArrayResult.length(); i++) {
					jsonArrayResult.getJSONObject(0).getString(
							"formatted_address");
				}
				Log.d("debug", realAddress);

				return realAddress;
				// UI操作一定要在MAINTHREAD
				// Toast.makeText(getActivity(), realAddress,
				// Toast.LENGTH_SHORT)
				// .show();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		private String fetch2() {

			String urlString = "http://www.ntu.edu.tw/";
			DefaultHttpClient client = new DefaultHttpClient();

			HttpGet get = new HttpGet(urlString);

			ResponseHandler<String> handler = new BasicResponseHandler();

			try {
				String result = client.execute(get, handler);
				return "2\r\n" + result;
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

		private String fetch() {
			String urlString = "http://www.ntu.edu.tw/";
			try {
				URL url = new URL(urlString);
				URLConnection con = url.openConnection();

				InputStream is = con.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));

				String line = "";
				StringBuffer sb = new StringBuffer();
				while ((line = reader.readLine()) != null) {
					sb.append(line).append("");
				}

				return "1\r\n" + sb.toString();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

}
