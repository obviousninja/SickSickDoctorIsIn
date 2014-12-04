package com.example.thedocisin;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * 
 * @author Justin Lain </p>
 * 
 * This class is meant to pull a list of active 
 * questions from a database and display it on the user's screen.
 *
 */
@SuppressWarnings("unused")
public class QuestionList extends ListActivity {
	
	QuestionListAdapter mAdapter;	
	Context mContext;
	String mCategory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getApplicationContext();
		mAdapter = new QuestionListAdapter(this);
		mAdapter.setItems(populateQuestions());
		mCategory = getIntent().getStringExtra("Category");
		
		View header = View.inflate(mContext, R.layout.question_list_header, null);
		View askButton = header.findViewById(R.id.question_list_ask_button);
		
		askButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO This should call the AskQuestion activity.
				Intent i = new Intent(mContext, AskQuestionActivity.class);
				i.putExtra("Category", mCategory);
				QuestionList.this.startActivity(i);
			}
			
		});
		
		getListView().addHeaderView(header);		
		getListView().setAdapter(mAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question_view, menu);
		return true;
	}
	

	/**
	 * Accesses the database and returns the list of questions.
	 * It currently only produces sample questions.
	 */
	public ArrayList<Question> populateQuestions(){
		ArrayList<Question> questions = new ArrayList<Question>();
		
		//TODO: Implement this function such that it accesses the database
		//and populates a List of Questions.
		for(int i = 0; i < 25; i++){
			Question sampleQuestion = new Question();
			sampleQuestion.setTitle("Sample Question Title No. " + (i+1));
			sampleQuestion.setDescription("This is a sample question body.");
			sampleQuestion.addAnswer("I am a sample answerer.");
			sampleQuestion.addAnswer("This is a bad question.");
			questions.add(sampleQuestion);
		}
		
		
		return questions;
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
