package com.baydar.kombiapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	static int layoutLevel = 0;
	static ArrayList<Kombi> kombi = new ArrayList<Kombi>();
	static int chosenKombi = 0;
	static int chosenModel = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			loadFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		layoutLevel = 0;
		LinearLayout ll = (LinearLayout) findViewById(R.id.layout1);
		ll.removeAllViews();
		for (int size = 1; size < kombi.size(); size++) {
			ll = (LinearLayout) findViewById(R.id.layout1);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			Button btn = new Button(this);
			btn.setId(size);
			final int id_ = btn.getId();
			btn.setText(kombi.get(size).name);
			ll.addView(btn, params);
			Button btn1 = ((Button) findViewById(id_));
			btn1.setOnClickListener(new MyOnClickListener(size));
		}
	}

	public void setKombiPage() {
		layoutLevel = 0;
		LinearLayout ll = (LinearLayout) findViewById(R.id.layout1);
		ll.removeAllViews();
		for (int size = 1; size < kombi.size(); size++) {
			ll = (LinearLayout) findViewById(R.id.layout1);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			Button btn = new Button(this);
			btn.setId(size);
			final int id_ = btn.getId();
			btn.setText(kombi.get(size).name);
			ll.addView(btn, params);
			Button btn1 = ((Button) findViewById(id_));
			btn1.setOnClickListener(new MyOnClickListener(size));
		}
	}

	public void loadFile() throws IOException {
		File folder = new File(
				Environment.getExternalStorageDirectory() + File.separator + "KombiApp" + File.separator + "kombi.txt");
		BufferedReader br = null;
		if (folder.exists()) {
			File sdcard = Environment.getExternalStorageDirectory();
			File file = new File(sdcard, "KombiApp" + File.separator + "kombi.txt");
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1252"));
		} else {
			br = new BufferedReader(new InputStreamReader(getAssets().open("kombi.txt"), "Cp1252"));
		}

		String text = null;

		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(getAssets().open("kombi.txt"), "Cp1252"));
		// BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			text = sb.toString();
		} finally {
			br.close();
		}

		String[] temp;
		String delimiter = "~~~";
		temp = text.split(delimiter);
		// System.out.println(temp[1]);
		for (int i = 0; i < temp.length; i++) {
			String[] tempModel;
			String delimiterModel = "##";
			tempModel = temp[i].split(delimiterModel);
			// System.out.print(tempModel[0]);
			Kombi komb = new Kombi();
			komb.name = tempModel[0];
			kombi.add(komb);
			for (int j = 1; j < tempModel.length; j++) {
				String[] tempError;
				String delimiterError = ">";
				tempError = tempModel[j].split(delimiterError);
				Model m = new Model();
				m.name = tempError[0];
				komb.models.add(m);
				// System.out.print(tempError[0]);
				for (int k = 1; k < tempError.length; k++) {
					String[] error = tempError[k].split("\n", 2);
					m.errorCodes.add(error[0]);
					m.errorExps.add(error[1]);
				}

			}
		}
	}

	public void setKombiModelPage(int id) {
		layoutLevel = 1;
		chosenKombi = id;
		LinearLayout ll = (LinearLayout) findViewById(R.id.layout1);
		ll.removeAllViews();
		for (int size = 0; size < kombi.get(id).models.size(); size++) {
			ll = (LinearLayout) findViewById(R.id.layout1);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			Button btn = new Button(this);
			btn.setId(size);
			final int id_ = btn.getId();
			btn.setText(kombi.get(id).models.get(size).name);
			ll.addView(btn, params);
			Button btn1 = ((Button) findViewById(id_));
			btn1.setOnClickListener(new MyOnClickListener2(size));
		}
	}

	public void setKombiErrorCode(int id) {
		layoutLevel = 2;
		chosenModel = id;
		LinearLayout ll = (LinearLayout) findViewById(R.id.layout1);
		ll.removeAllViews();
		for (int size = 0; size < kombi.get(chosenKombi).models.get(id).errorCodes.size(); size++) {
			ll = (LinearLayout) findViewById(R.id.layout1);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			Button btn = new Button(this);
			btn.setId(size);
			final int id_ = btn.getId();
			btn.setText(kombi.get(chosenKombi).models.get(id).errorCodes.get(size));
			ll.addView(btn, params);
			Button btn1 = ((Button) findViewById(id_));
			btn1.setOnClickListener(new MyOnClickListener3(size));
		}
	}

	public void setKombiErrorExp(int id) {
		layoutLevel = 3;
		LinearLayout ll = (LinearLayout) findViewById(R.id.layout1);
		ll.removeAllViews();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		TextView tv = new TextView(this);
		tv.setText(kombi.get(chosenKombi).models.get(chosenModel).errorExps.get(id));
		ll.addView(tv, params);
	}

	public class MyOnClickListener implements OnClickListener {

		int myVariable;

		public MyOnClickListener(int myVariable) {
			this.myVariable = myVariable;
		}

		public void onClick(View v) {
			setKombiModelPage(myVariable);
		}
	};

	public class MyOnClickListener2 implements OnClickListener {

		int myVariable;

		public MyOnClickListener2(int myVariable) {
			this.myVariable = myVariable;
		}

		public void onClick(View v) {
			setKombiErrorCode(myVariable);
		}
	};

	public class MyOnClickListener3 implements OnClickListener {

		int myVariable;

		public MyOnClickListener3(int myVariable) {
			this.myVariable = myVariable;
		}

		public void onClick(View v) {
			setKombiErrorExp(myVariable);
		}
	};

	public void onBackPressed() {
		if (layoutLevel == 3) {
			setKombiErrorCode(chosenModel);
		} else if (layoutLevel == 2) {
			setKombiModelPage(chosenKombi);
		} else if (layoutLevel == 1) {
			setKombiPage();
		} else {
			super.onBackPressed();
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
}
