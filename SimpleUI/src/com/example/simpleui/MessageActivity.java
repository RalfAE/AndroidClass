package com.example.simpleui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

@SuppressLint("NewApi")
public class MessageActivity extends Activity {

	private static final String FILE_NAME = "text.txt";
	// TextView textView = null;
	// ProgressBar progressBar = null;
	ProgressDialog progressDialog = null;
	ListView listView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_message);

		Intent intent = getIntent();
		String text = intent.getStringExtra("TEXT");
		boolean isChecked = intent.getBooleanExtra("CHECKED", false);
		// textView = (TextView) findViewById(R.id.textView1);
		// progressBar = (ProgressBar) findViewById(R.id.progressBar1);

		listView = (ListView) findViewById(R.id.listView1);

		progressDialog = new ProgressDialog(this);
		progressDialog.setTitle("SimpleUI");
		progressDialog.setMessage("加載中, 請稍候...");
		progressDialog.setCancelable(false); // 是否允許讓使用者在點擊別處理取消
		progressDialog.show();
		// writeFile(text);
		// writeFileSD(text);
		// textView.setText(readFile());

		ParseObject testObject = new ParseObject("MessageText");
		testObject.put("TEXT", text);
		testObject.put("CHECKED", isChecked);

		saveData(testObject);
		// 呼叫此方法會等server端真正智存完畢才會繼續往下執行
		// try {
		// testObject.save();
		// } catch (ParseException e1) {
		// e1.printStackTrace();
		// }

	}

	private void loadData() {
		// Document http://goo.gl/XdoZL, sample
		ParseQuery<ParseObject> query = ParseQuery.getQuery("MessageText");
		// query.whereEqualTo("playerName", "Dan Stemkoski"); // no use
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> msgList, ParseException e) {
				if (e == null) {
					// StringBuffer sb = new StringBuffer();
					// for (ParseObject msg : msgList) {
					// sb.append(msg.get("TEXT")).append("\r\n");
					// }

					List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

					for (ParseObject msg : msgList) {
						Map<String, Object> dataMap = new HashMap<String, Object>();
						dataMap.put("TEXT", msg.getString("TEXT"));
						dataMap.put("isChecked", msg.getBoolean("isChecked"));
						data.add(dataMap);
					}
					String[] from = new String[] { "TEXT", "isChecked" };
					int[] to = new int[] { android.R.id.text1,
							android.R.id.text2 };
					SimpleAdapter adapter = new SimpleAdapter(
							MessageActivity.this, data,
							android.R.layout.simple_list_item_2, from, to);

					listView.setAdapter(adapter);

					// GONE: 看不見也不占位子; INVISIBLE: 會占位子
					// progressBar.setVisibility(View.GONE);
					progressDialog.dismiss();
					// textView.setText(sb.toString());

				} else {
					e.printStackTrace();
				}
			}
		});
	}

	private void saveData(ParseObject testObject) {
		testObject.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				if (e == null) { // save success
					Log.d("DEBUG", "OK");
					loadData();
				} else {
					e.printStackTrace();
				}
				saveFinish();
			}
		});
		Log.d("DEBUG", "after saveInBackground.");
	}

	private void saveFinish() {
		Toast.makeText(this, "Finished.", Toast.LENGTH_SHORT).show();
	}

	private CharSequence readFile() {
		try {
			StringBuffer sb = new StringBuffer();
			FileInputStream fis = openFileInput(FILE_NAME);
			byte[] buffer = new byte[1024];
			while (fis.read(buffer) >= 0) {
				sb.append(new String(buffer));
			}
			fis.close();
			return sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void writeFile(String text) {
		try {
			// text = text + "\r\n";

			FileOutputStream fos = openFileOutput(FILE_NAME,
					Context.MODE_APPEND);
			fos.write(text.getBytes());
			fos.write("\r\n".getBytes());
			fos.flush();
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeFileSD(String text) {
		try {
			// text = text + "\r\n";
			File sdDir = Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
			if (sdDir.exists()) {
				sdDir.mkdirs();
			}
			File docFile = new File(sdDir, FILE_NAME);

			FileOutputStream fos = new FileOutputStream(docFile);
			fos.write(text.getBytes());
			fos.write("\r\n".getBytes());
			fos.flush();
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
