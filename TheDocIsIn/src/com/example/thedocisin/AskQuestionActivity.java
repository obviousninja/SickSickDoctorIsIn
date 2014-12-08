package com.example.thedocisin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AskQuestionActivity extends Activity {

	EditText enterQuestion;
	Spinner categorySpinner;
//	CheckBox askByLocation;
	Button cancelButton, submitButton;
	private HTTPServicesTask serviceHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ask);

		
		serviceHelper = HTTPServicesTask.getInstance();
		
		
		Intent parentIntent = getIntent();
		final String mCategory = parentIntent.getStringExtra("Category");

		enterQuestion = (EditText) findViewById(R.id.askEnterQuestion);
		categorySpinner = (Spinner) findViewById(R.id.askCategorySpinner);
		//askByLocation = (CheckBox) findViewById(R.id.askByLocation);
		cancelButton = (Button) findViewById(R.id.askCancelButton);
		submitButton = (Button) findViewById(R.id.askSubmitButton);


		switch(mCategory){
		case "Sports":
			categorySpinner.setSelection(0);
			break;
		case "Entertainment":
			categorySpinner.setSelection(1);
			break;
		case "Food":
			categorySpinner.setSelection(2);
			break;
		case "Personal":
			categorySpinner.setSelection(3);
			break;
		case "Religion":
			categorySpinner.setSelection(4);
			break;
		case "Other":
			categorySpinner.setSelection(5);
			break;
		default:
			break;
		}
		cancelButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		submitButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (enterQuestion.getText().toString().length() < 10) {
					Toast.makeText(getApplicationContext(), "Question must be at least 15 characters long.",
							Toast.LENGTH_LONG).show();
				} else if (String.valueOf(categorySpinner.getSelectedItem()).equals("Pick A Category")) {
					Toast.makeText(getApplicationContext(), "Please choose a category", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getApplicationContext(), "Submitting question...", Toast.LENGTH_LONG).show();
					
					//TO-DO add question to question list
					String questionText = enterQuestion.getText().toString();
					String category = categorySpinner.getSelectedItem().toString();
					System.out.println("QUESTION TEXT:   " + questionText);
					System.out.println("Category:   " + category);
					
					serviceHelper.askQuestion(serviceHelper.getCurrentUser(), questionText, category);
					
					Intent resultIntent = new Intent();
					resultIntent.putExtra("Category", category);
					setResult(MainActivity.QASK_RESULT, resultIntent);
					finish();
				}
			}
		});
	}
	
	public void logout(){
		setResult(MainActivity.LOGOUT_RESULT);
		finish();
	}
	

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.general_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		if (id == R.id.my_profile) {
			//Go to Profile Page
			Intent profileIntent = new Intent(AskQuestionActivity.this,ProfileActivity.class);
			AskQuestionActivity.this.startActivity(profileIntent);
			return true;
		}
		if(id == R.id.log_out){
			logout();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}