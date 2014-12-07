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
	private ArrayList<Question> questions;
	private HTTPServicesTask serviceHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getApplicationContext();
		mAdapter = new QuestionListAdapter(this);
		mCategory = getIntent().getStringExtra("Category");
		
		serviceHelper = HTTPServicesTask.getInstance();
		serviceHelper.setQuestionList(this);
		serviceHelper.getQuestions(mCategory);
		
		
		
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

	public void setQuestions(ArrayList<Object> result) {
		ArrayList<Question> questions = new ArrayList<Question>();
		
		for(int i = 1; i < result.size(); i++){
			questions.add((Question) result.get(i));
		}
		this.questions = questions;
		mAdapter.setItems(this.questions);
	
	}
}
