package com.example.thedocisin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Logger extends Activity {

	static final int REGISTRATION_REQUEST = 1; //flag for registration for result intent
	Context mContext;
	String userInputName;
	String userInputPass;
	private static String USER_NAME = 	"username";
	
	//SharePreferences curPreference = Activity.getPreferences(MODE_PRIVATE);
	//SharePreferenceEditor = SharePreferences.edit();
	
	private HTTPServicesTask serviceHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logger);
		
		final SharedPreferences sharePrf = getPreferences(MODE_PRIVATE);
		
		
		mContext = getApplicationContext();
		serviceHelper = HTTPServicesTask.getInstance(mContext);
		serviceHelper.setLogger(this);
		
		//if sharepref is not a null, then use that on the edit text field of nickname
		EditText logger = (EditText) findViewById(R.id.username);
		if(sharePrf != null){
		if(sharePrf.getString(USER_NAME, "").isEmpty() == false){
			logger.setText(sharePrf.getString(USER_NAME, ""));
		}
		}
		
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
				serviceHelper.verifyUserPass(userInputName, userInputPass);
				
				//change the current user_name to something different, otherwise keep it as it is
					if(sharePrf == null || sharePrf.getString(USER_NAME, "").isEmpty() || sharePrf.getString(USER_NAME, "").compareToIgnoreCase(userInputName) != 0){
				SharedPreferences.Editor editor = sharePrf.edit();
				editor.putString(USER_NAME, userInputName);
				editor.commit();
				}
				
				//TODO if user exist, start the intent, otherwise display a toast
				//saying user does not exist
				
				/************* 	CODE MOVED **************/
//				Toast.makeText(mContext, userInputName + " "+userInputPass, Toast.LENGTH_SHORT).show();
//				
//				Intent loggedIntent = new Intent(mContext, MainActivity.class);
//				startActivity(loggedIntent);
				/***************************************/
				
			}
		});
		Button register = (Button) findViewById(R.id.register);
	
		register.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO start register activity
				Intent registerIntent = new Intent(mContext, RegisterUser.class);
				Logger.this.startActivityForResult(registerIntent, REGISTRATION_REQUEST);
				
			}
		});
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REGISTRATION_REQUEST){
			if(resultCode == RESULT_OK){
				//TODO do something with the intent data from registerUser activity if there are any
				Toast.makeText(mContext, "Registration Complete. Please Login", Toast.LENGTH_SHORT).show();
			}else if(resultCode == RegisterUser.RESULT_FAILED){
				Toast.makeText(mContext, "Username Already Exists", Toast.LENGTH_SHORT).show();	
			}else if(resultCode == RESULT_CANCELED){
				Toast.makeText(mContext, "Registration Cancelled...", Toast.LENGTH_SHORT).show();	
			}
		}
		
		if(requestCode == MainActivity.REQUEST_CODE){
			if(resultCode == MainActivity.LOGOUT_RESULT){
				serviceHelper.logout();
			}
		}
	}
	
	
	public void verifyUserPass(boolean verified){
		
		if(verified){
			Toast.makeText(mContext, "Logging in " + userInputName, Toast.LENGTH_SHORT).show();
			serviceHelper.setCurrentUser(userInputName);
			
		
			
			
			Intent loggedIntent = new Intent(mContext, MainActivity.class);
			Logger.this.startActivity(loggedIntent);	
		}else{
			Toast.makeText(mContext, "Invalid user/password combination", Toast.LENGTH_SHORT).show();
		}
	}
	

	
	
}