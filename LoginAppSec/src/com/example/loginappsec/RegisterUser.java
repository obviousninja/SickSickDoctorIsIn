package com.example.loginappsec;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterUser extends Activity {

	String nickName;
	String email;
	String password;
	String confirmpassword;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrationone);
		
		Button submit = (Button) findViewById(R.id.submitregister);
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Put all information given in the edittext field into the database
				EditText nicknameblock = (EditText) findViewById(R.id.nickname);
				nickName = nicknameblock.getText().toString();
				EditText emailblock = (EditText) findViewById(R.id.email);
				email = emailblock.getText().toString();
				EditText passwordblock = (EditText) findViewById(R.id.regpassword1);
				password = passwordblock.getText().toString();
				EditText confirmpasswordblock = (EditText) findViewById(R.id.regpassword2);
				confirmpassword = confirmpasswordblock.getText().toString();
				
				//TODO store the informations retreved above
			}
		});
		
		
		Button cancel = (Button) findViewById(R.id.cancelregister);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO close the current activity
				finish();
				
			}
		});
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
