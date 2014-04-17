package com.example.simplerelative;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

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

		private Spinner spinnerMonth = null;
		private Spinner spinnerDate = null;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			spinnerMonth = (Spinner) rootView.findViewById(R.id.monthSpinner);
			spinnerDate = (Spinner) rootView.findViewById(R.id.dateSpinner);
			
			String[] monthList = getResources().getStringArray(R.array.Month);
			
			ArrayAdapter<String> saMonth = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, monthList);

			spinnerMonth.setAdapter(saMonth);
			
//			String[] dateList = new String[31];
//			for (int i = 1; i <= 31; i++) {
//				dateList[i] = String.valueOf(i);
//			}
//			
//			ArrayAdapter<String> saDate = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, dateList);
//
//			spinnerDate.setAdapter(saDate);
			
			return rootView;
		}
	}

}
