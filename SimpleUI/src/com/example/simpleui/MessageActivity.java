package com.example.simpleui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MessageActivity extends Activity {

	private static final String FILE_NAME = "text.txt";
	TextView textView = null;			
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_message);
		
		Intent intent = getIntent();
		String text = intent.getStringExtra("TEXT");
		textView = (TextView) findViewById(R.id.textView1);
		writeFile(text);
		textView.setText(readFile());
	}

	private CharSequence readFile() {
		try {
			StringBuffer sb = new StringBuffer();
			FileInputStream fis = openFileInput(FILE_NAME);
			byte[] buffer = new byte[1024];
			while(fis.read(buffer) >= 0) {
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
//			text = text + "\r\n";
			
			FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_APPEND);
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
