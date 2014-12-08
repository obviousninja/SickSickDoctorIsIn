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
import android.widget.TextView;
import android.widget.Toast;

public class AnswerQuestionActivity extends Activity {

	TextView questionText;
	EditText answer;
	Button cancel, submit;
	private int qid;
	private HTTPServicesTask serviceHelper;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_answer);
		
		Intent intent = getIntent();
		
		serviceHelper = HTTPServicesTask.getInstance();
		qid = intent.getIntExtra("qid", -1);
		
		
		questionText = (TextView) findViewById(R.id.answerQuestionText);
		answer = (EditText) findViewById(R.id.answerAnswerEditText);
		cancel = (Button) findViewById(R.id.answerCancelButton);
		submit = (Button) findViewById(R.id.answerSubmitButton);
		
		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		
		submit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (answer.getText().toString().length() < 2) {
					Toast.makeText(getApplicationContext(), "Answer needs to be longer", Toast.LENGTH_LONG).show();
				} else {
					//Add answer to wherever it needs to go
					System.out.println("QID " + qid);
					System.out.println("CURRUS " + serviceHelper.getCurrentUser());
					System.out.println("Answer " + answer.getText().toString());
					serviceHelper.answerQuestion(serviceHelper.getCurrentUser(), qid, answer.getText().toString());
					
					finish();
				}
			}
		});
	}

	
	public void logOut(){
		setResult(MainActivity.LOGOUT_RESULT);
		finish();
	}
	

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.logout_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.log_out) {
			logOut();
		}
		return super.onOptionsItemSelected(item);
	}
}