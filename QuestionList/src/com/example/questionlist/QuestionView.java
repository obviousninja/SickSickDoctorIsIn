package com.example.questionlist;

import java.util.ArrayList;

import com.example.questionlist.QuestionList.Question;
import com.example.questionlist.QuestionList.Question.Answer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionView extends Activity {

	Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_view);
		mContext = getApplicationContext();
		Intent i = getIntent();
		Question theQuestion = questionFromInfo(i.getExtras());
		
		final TextView titleView = (TextView) findViewById(R.id.question_view_title);
		final TextView descriptionView = (TextView) findViewById(R.id.question_view_description);
		final ListView answersView = (ListView) findViewById(R.id.question_view_answers);
		QuestionViewAdapter answersAdapter = new QuestionViewAdapter(mContext);
		
		titleView.setText(theQuestion.getTitle());
		descriptionView.setText(theQuestion.getDescription());
		descriptionView.setMovementMethod(new ScrollingMovementMethod());
		
		View header = View.inflate(getApplicationContext(), R.layout.question_view_header, null);
		
		header.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO This should start the AnswerQuestion activity.
				// The question to be answered is theQuestion.
				
				/* Something like this:
				Intent i = new Intent(getApplicationContext(), AnswerQuestion.class);
				i.putExtra(question stuff);
				*/
				
			}
			
		});
		
		answersView.addHeaderView(header);
		answersAdapter.addAll(theQuestion.getAnswers());
		answersView.setAdapter(answersAdapter);
	}
	
	private Question questionFromInfo(Bundle questionInfo){
		Question q = new Question();
		
		q.setTitle((String) questionInfo.get("Title"));
		q.setDescription((String) questionInfo.get("Description"));
		q.setAnswers(questionInfo.getParcelableArrayList("Answers"));
		return q;
		
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
