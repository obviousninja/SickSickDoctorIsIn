package com.example.thedocisin;

import java.util.ArrayList;

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
import android.widget.Toast;

public class QuestionView extends Activity {

	Context mContext;
	HTTPServicesTask serviceHelper;
	AnswerViewAdapter answersAdapter;
	private int qid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_view);
		mContext = getApplicationContext();
		Intent i = getIntent();
		final Question theQuestion = new Question(i);
		
		final TextView titleView = (TextView) findViewById(R.id.question_view_title);
		final TextView descriptionView = (TextView) findViewById(R.id.question_view_description);
		final ListView answersView = (ListView) findViewById(R.id.question_view_answers);
		answersAdapter = new AnswerViewAdapter(mContext);
		
		this.qid = theQuestion.getqid();
		serviceHelper = HTTPServicesTask.getInstance();
		serviceHelper.setQuestionView(this);
//		serviceHelper.getAnswers(qid);
		/*
		String title = theQuestion.getqTxt();
		if(title.length() > 31){
			title = title.substring(0, 28);
			title += "...";
		}
		*/
		//title += "\n" + theQuestion.getname() ;
		String title = "Question by: "+ theQuestion.getname();
		titleView.setText(title);
		descriptionView.setText(theQuestion.getqTxt());
		descriptionView.setMovementMethod(new ScrollingMovementMethod());
		
		View header = View.inflate(getApplicationContext(), R.layout.question_view_header, null);
		View answerButton = header.findViewById(R.id.question_view_answer_button);
		answerButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				if(!theQuestion.getaskID().equals(serviceHelper.getCurrentUser())){
					Intent answerViewIntent = new Intent(getApplicationContext(),AnswerQuestionActivity.class);
					answerViewIntent.putExtra("qid", theQuestion.getqid());
					answerViewIntent.putExtra("qTxt", theQuestion.getqTxt());
					QuestionView.this.startActivityForResult(answerViewIntent,MainActivity.REQUEST_CODE);
				} else {
					Toast.makeText(mContext, "You cannot answer your own question.", Toast.LENGTH_LONG).show();
				}
			}
			
		});
		
		answersView.addHeaderView(header);
		// Access database to add answers here.
		
		
		
//		answersAdapter.addAll(theQuestion.getAnswers());
		answersView.setAdapter(answersAdapter);
	}
	
	public void logout(){
		setResult(MainActivity.LOGOUT_RESULT);
		finish();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == MainActivity.REQUEST_CODE){
			if(resultCode == MainActivity.LOGOUT_RESULT){
				logout();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.general_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.log_out) {
			logout();
			return true;
		}
		
		if(id == R.id.my_profile){
			Intent profileIntent = new Intent(QuestionView.this,ProfileActivity.class);
			QuestionView.this.startActivity(profileIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	@Override
	public void onResume(){
		answersAdapter.clear();
		if(serviceHelper != null){
			serviceHelper.getAnswers(this.qid);
		}

		super.onResume();
	}
	
	public void setAnswers(ArrayList<Object> list){
		System.out.println("Setting answers  " + list.size());
		ArrayList<Answer> newlist = new ArrayList<Answer>();
		for(int i = 1 ; i < list.size(); i++){
			newlist.add((Answer) list.get(i));
			System.out.println("ANSWER  NUMBER  " + i + "   " + newlist.get(newlist.size() - 1 ));
		}
		answersAdapter.addAll(newlist);
//		answersAdapter.(newlist);
	}
	
}
