package com.example.loginappsec;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Logger extends Activity {

	static final int REGISTRATION_REQUEST = 1; //flag for registration for result intent
	Context mContext;
	String userInputName;
	String userInputPass;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logger);
		
		mContext = getApplicationContext();

		Button login = (Button) findViewById(R.id.loginButton);
		login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO check with database with valid username and password
				EditText usern = (EditText) findViewById(R.id.username);
				userInputName = usern.getText().toString();
				EditText userp = (EditText) findViewById(R.id.password);
				userInputPass = userp.getText().toString();
				
				
				//TODO checking if the users exist or not
				
				//TODO if user exist, start the intent, otherwise display a toast
				//saying user does not exist
				Toast.makeText(mContext, userInputName + " "+userInputPass, Toast.LENGTH_SHORT).show();
				
				Intent loggedIntent = new Intent(mContext, /*class to start*/);
				startActivity(loggedIntent);		
			}
		});
		Button register = (Button) findViewById(R.id.register);
	
		register.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO start register activity
				Intent registerIntent = new Intent(mContext, RegisterUser.class);
				startActivityForResult(registerIntent, REGISTRATION_REQUEST);
				
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REGISTRATION_REQUEST){
			if(resultCode == RESULT_OK){
				//TODO do something with the intent data from registerUser activity if there are any
				
			}
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.logger, menu);
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
