package com.example.thedocisin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class QuestionView extends Activity {

	Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_view);
		mContext = getApplicationContext();
		Intent i = getIntent();
		final Question theQuestion = Question.questionFromIntent(i);
		
		final TextView titleView = (TextView) findViewById(R.id.question_view_title);
		final TextView descriptionView = (TextView) findViewById(R.id.question_view_description);
		final ListView answersView = (ListView) findViewById(R.id.question_view_answers);
		QuestionViewAdapter answersAdapter = new QuestionViewAdapter(mContext);
		
		titleView.setText(theQuestion.getTitle());
		descriptionView.setText(theQuestion.getDescription());
		descriptionView.setMovementMethod(new ScrollingMovementMethod());
		
		View header = View.inflate(getApplicationContext(), R.layout.question_view_header, null);
		View answerButton = header.findViewById(R.id.question_view_answer_button);
		answerButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO This should start the AnswerQuestion activity.
				// The question to be answered is theQuestion.
				Intent answerViewIntent = new Intent(getApplicationContext(),AnswerQuestionActivity.class);
				answerViewIntent.fillIn(theQuestion.toIntent(), 0);
				QuestionView.this.startActivity(answerViewIntent);
				
			}
			
		});
		
		answersView.addHeaderView(header);
		answersAdapter.addAll(theQuestion.getAnswers());
		answersView.setAdapter(answersAdapter);
	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question_view, menu);
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
