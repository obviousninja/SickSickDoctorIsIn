package com.example.thedocisin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterUser extends Activity {

	String nickName;
	String email;
	String password;
	String confirmpassword;
	private HTTPServicesTask serviceHelper;
	public static final int RESULT_FAILED = 12;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrationone);
		
		serviceHelper = HTTPServicesTask.getInstance();
		serviceHelper.setRegister(this);
		
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
				
				
				
				//TODO store the informations retrieved above
				//check if user exist, if not add, otherwise tell it like it is...
				
				if(password.equals(confirmpassword)){
					serviceHelper.registerUser(email, password, nickName);
					Toast.makeText(getApplicationContext(), "Attempting to register..", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getApplicationContext(), "Passwords don't match...", Toast.LENGTH_LONG).show();
				}
				
				
				
				
				
//				Toast.makeText(getApplicationContext(), nickName+" "+email+" "+password+" "+confirmpassword, Toast.LENGTH_LONG).show();
			}
		});
		
		
		Button cancel = (Button) findViewById(R.id.cancelregister);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO close the current activity
				setResult(RegisterUser.RESULT_CANCELED);
				finish();
				
			}
		});
	}


	public void verifyRegisterUser(boolean verified) {
		if(verified){
			setResult(RESULT_OK);
		}else{
			setResult(RegisterUser.RESULT_FAILED);
		}
		finish();
	}
}

