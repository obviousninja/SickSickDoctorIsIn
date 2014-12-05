package com.example.thedocisin;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Question{
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
	
	public static Question questionFromIntent(Intent i){
		Question q = new Question();
		
		Bundle questionInfo = i.getExtras();
		
		q.setTitle((String) questionInfo.get("Title"));
		q.setDescription((String) questionInfo.get("Description"));
		q.setAnswers(questionInfo.getParcelableArrayList("Answers"));
		return q;
		
	}
	
	public Intent toIntent(){
		Intent i = new Intent();
		i.putExtra("Title", this.title);
		i.putExtra("Description", this.description);
		i.putParcelableArrayListExtra("Answers", answers);
		return i;
	}
	
}
