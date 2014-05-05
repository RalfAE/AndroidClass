package com.example.takephoto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class MainActivity extends ActionBarActivity {

	private static final int REQUEST_CODE = 2266;
	ImageView imageView;
	TextView textView;
	Uri fileUri;

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
		if (id == R.id.PHOTO) {
			Log.d("Hit Menu", "HIT!!!");
			Intent intent = new Intent();
			intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
			
			fileUri = Uri.fromFile(getFile());
			
			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
			// 改用會回傳結果的方法
			// startActivity(intent);
			startActivityForResult(intent, REQUEST_CODE);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		// 鄰斷是由take photo呼叫的result
		if (requestCode == REQUEST_CODE) {

			if (resultCode == RESULT_OK) {
				Log.d("Debug", "OK!");
//				Bitmap bitmap = intent.getParcelableExtra("data");
//				imageView.setImageBitmap(bitmap);
//				save(bitmap);

				textView.setText(fileUri.getPath());
			} else if (resultCode == RESULT_CANCELED) {
				Log.d("Debug", "Cancel!");

				imageView.setImageBitmap(null);
				
			} else {
				Log.d("Debug", "other!");
				
			}

		}

	}
	
	private void save(Bitmap bitmap) {

		File imageFile = getFile();

		try {
			FileOutputStream fos = new FileOutputStream(imageFile);
			bitmap.compress(CompressFormat.PNG, 100, fos);

			fos.flush();
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		textView.setText(imageFile.getAbsolutePath());

	}

	private File getFile() {
		File saveDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

		if (!saveDir.exists()) {
			saveDir.mkdirs();
		}

		File imageFile = new File(saveDir, "photo.png");
		return imageFile;
	}
	
	

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			imageView = (ImageView) rootView.findViewById(R.id.imageView1);
			textView = (TextView) rootView.findViewById(R.id.textView1);
			return rootView;
		}
	}

}