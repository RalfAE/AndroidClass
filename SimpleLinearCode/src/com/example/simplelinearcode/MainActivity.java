package com.example.simplelinearcode;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}
		EditText toField = null;
		EditText subjectField = null;
		EditText messageField = null;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// View rootView = inflater.inflate(R.layout.fragment_main,
			// container, false);
			LinearLayout ll = new LinearLayout(getActivity());
			ll.setPadding(20, 20, 20, 20); // 設定邊界
			ll.setOrientation(LinearLayout.VERTICAL);
			toField = new EditText(getActivity());
			toField.setHint("To");
			subjectField = new EditText(getActivity());
			subjectField.setHint("Subject");
			messageField = new EditText(getActivity());
			messageField.setHint("Message");
			messageField.setGravity(Gravity.TOP);

			LinearLayout llw = new LinearLayout(getActivity());
			llw.setOrientation(LinearLayout.HORIZONTAL);
			llw.setGravity(Gravity.RIGHT);
			Button cancelButton = new Button(getActivity());
			Button sendButton = new Button(getActivity());
			llw.addView(cancelButton);
			cancelButton.setText("Cancel");
			llw.addView(sendButton);
			sendButton.setText("Send");

			ll.addView(toField);
			ll.addView(subjectField);
			ll.addView(messageField);
			ll.addView(llw);

			LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) sendButton
					.getLayoutParams();
			params1.weight = LinearLayout.LayoutParams.WRAP_CONTENT;
			LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) cancelButton
					.getLayoutParams();
			params2.weight = LinearLayout.LayoutParams.WRAP_CONTENT;
			
			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) messageField
					.getLayoutParams();
			params.weight = 1;


			cancelButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					toField.setText("");
					subjectField.setText("");
					messageField.setText("");
				}
			});
			
			sendButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Toast.makeText(getActivity(), "Sending " + toField.getText(), Toast.LENGTH_SHORT).show();
					
				}
			});

			return ll;
		}
	}

}
