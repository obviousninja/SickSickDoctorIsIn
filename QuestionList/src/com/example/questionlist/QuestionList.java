package com.example.questionlist;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getApplicationContext();
		mAdapter = new QuestionListAdapter(mContext);
		mAdapter.setItems(populateQuestions());
		
		View header = View.inflate(mContext, R.layout.question_list_header, null);
		
		header.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO This should call the AskQuestion activity.
				
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
	
	/**
	 * 
	 * @author Justin Lain </p>
	 * Placeholder for the Question class. Probably will be replaced later.
	 */
	public static class Question{
		private String title;
		private String description;
		private ArrayList<Parcelable> answers;
		
		public static class Answer implements Parcelable {
			private String text;
			private int score;
			public Answer(){
				
			}
			public String getText() {
				return text;
			}
			public void setText(String text) {
				this.text = text;
			}
			public int getScore() {
				return score;
			}
			public void setScore(int score) {
				this.score = score;
			}
		
		    protected Answer(Parcel in) {
		        text = in.readString();
		        score = in.readInt();
		    }
		
		    @Override
		    public int describeContents() {
		        return 0;
		    }
		
		    @Override
		    public void writeToParcel(Parcel dest, int flags) {
		        dest.writeString(text);
		        dest.writeInt(score);
		    }
		
		    public static final Parcelable.Creator<Answer> CREATOR = new Parcelable.Creator<Answer>() {
		        @Override
		        public Answer createFromParcel(Parcel in) {
		            return new Answer(in);
		        }
		
		        @Override
		        public Answer[] newArray(int size) {
		            return new Answer[size];
		        }
		    };
		}
		
		public Question(){
			answers = new ArrayList<Parcelable>();
		}
		
		public void addAnswer(String answerText){
			Answer newAnswer = new Answer();
			newAnswer.setScore(0);
			newAnswer.setText(answerText);
			answers.add(newAnswer);
		}
		
		@SuppressWarnings("unchecked")
		public void setAnswers(ArrayList<Parcelable> a){
			answers = (ArrayList<Parcelable>)a.clone();
		}
		
		
		public ArrayList<Parcelable> getAnswers(){
			return answers;			
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		
	}
}
