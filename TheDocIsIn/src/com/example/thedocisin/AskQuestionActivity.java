package com.example.thedocisin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AskQuestionActivity extends Activity {

	EditText enterQuestion;
	Spinner categorySpinner;
	CheckBox askByLocation;
	Button cancelButton, submitButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ask);

		enterQuestion = (EditText) findViewById(R.id.askEnterQuestion);
		categorySpinner = (Spinner) findViewById(R.id.askCategorySpinner);
		askByLocation = (CheckBox) findViewById(R.id.askByLocation);
		cancelButton = (Button) findViewById(R.id.askCancelButton);
		submitButton = (Button) findViewById(R.id.askSubmitButton);

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
					Intent intent = new Intent(AskQuestionActivity.this, AnswerQuestionActivity.class);
					intent.putExtra("QUESTION", enterQuestion.getText().toString());
					intent.putExtra("LOCATION", askByLocation.isChecked());
					intent.putExtra("CATEGORY", String.valueOf(categorySpinner.getSelectedItem()));
					startActivity(intent);
				}
			}
		});
	}
}