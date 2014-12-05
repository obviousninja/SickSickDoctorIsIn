package com.example.thedocisin;



import android.content.Intent;
import android.os.Bundle;

public class Question {
	
	private final String QID_KEY = "QID";
	private final String ASK_ID_KEY = "ASK_ID";
	private final String Q_TEXT_KEY = "Q_TEXT";
	private final String CAT_KEY = "CAT";
	
	private int qid;
	private String askID;
	private String qTxt;
	private String category;
	
	public Question(int qid, String askid, String qtxt, String category){
		this.setqid(qid);
		this.setaskID(askid);
		this.setqTxt(qtxt);
		this.setcategory(category);
	}
	
	public Question(Intent intent){
		Bundle bundle = intent.getExtras();
		this.qid = bundle.getInt(QID_KEY, qid);
		this.askID = bundle.getString(ASK_ID_KEY, askID);
		this.qTxt = bundle.getString(Q_TEXT_KEY, qTxt);
		this.category = bundle.getString(CAT_KEY, category);
	}
	
	
	public Intent packageToIntent(){
		Intent intent = new Intent();
		intent.putExtra(QID_KEY, qid);
		intent.putExtra(ASK_ID_KEY, askID);
		intent.putExtra(Q_TEXT_KEY, qTxt);
		intent.putExtra(CAT_KEY, category);
		return intent;
	}

	public int getqid() {
		return qid;
	}

	public void setqid(int qid) {
		this.qid = qid;
	}

	public String getaskID() {
		return askID;
	}

	public void setaskID(String askID) {
		this.askID = askID;
	}

	public String getqTxt() {
		return qTxt;
	}

	public void setqTxt(String qTxt) {
		this.qTxt = qTxt;
	}

	public String getcategory() {
		return category;
	}

	public void setcategory(String category) {
		this.category = category;
	}
	
	public String toString(){
		String result = this.qid +"..."+ this.askID +"..."+ this.qTxt +"..."+  this.category;
		return result;
	}
	
}














// OLD STUFF COMMENTED OUT FOR REFERENCE
//
//import java.util.ArrayList;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Parcel;
//import android.os.Parcelable;
//
//public class Question{
//	private String title;
//	private String description;
//	private ArrayList<Parcelable> answers;
//	
//	public static class Answer implements Parcelable {
//		private String text;
//		private int score;
//		public Answer(){
//			
//		}
//		public String getText() {
//			return text;
//		}
//		public void setText(String text) {
//			this.text = text;
//		}
//		public int getScore() {
//			return score;
//		}
//		public void setScore(int score) {
//			this.score = score;
//		}
//	
//	    protected Answer(Parcel in) {
//	        text = in.readString();
//	        score = in.readInt();
//	    }
//	
//	    @Override
//	    public int describeContents() {
//	        return 0;
//	    }
//	
//	    @Override
//	    public void writeToParcel(Parcel dest, int flags) {
//	        dest.writeString(text);
//	        dest.writeInt(score);
//	    }
//	
//	    public static final Parcelable.Creator<Answer> CREATOR = new Parcelable.Creator<Answer>() {
//	        @Override
//	        public Answer createFromParcel(Parcel in) {
//	            return new Answer(in);
//	        }
//	
//	        @Override
//	        public Answer[] newArray(int size) {
//	            return new Answer[size];
//	        }
//	    };
//	}
//	
//	public Question(){
//		answers = new ArrayList<Parcelable>();
//	}
//	
//	public void addAnswer(String answerText){
//		Answer newAnswer = new Answer();
//		newAnswer.setScore(0);
//		newAnswer.setText(answerText);
//		answers.add(newAnswer);
//	}
//	
//	@SuppressWarnings("unchecked")
//	public void setAnswers(ArrayList<Parcelable> a){
//		answers = (ArrayList<Parcelable>)a.clone();
//	}
//	
//	
//	public ArrayList<Parcelable> getAnswers(){
//		return answers;			
//	}
//
//	public String getTitle() {
//		return title;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}
//
//	public String getDescription() {
//		return description;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
//	}
//	
//	public static Question questionFromIntent(Intent i){
//		Question q = new Question();
//		
//		Bundle questionInfo = i.getExtras();
//		
//		q.setTitle((String) questionInfo.get("Title"));
//		q.setDescription((String) questionInfo.get("Description"));
//		q.setAnswers(questionInfo.getParcelableArrayList("Answers"));
//		return q;
//		
//	}
//	
//	public Intent toIntent(){
//		Intent i = new Intent();
//		i.putExtra("Title", this.title);
//		i.putExtra("Description", this.description);
//		i.putParcelableArrayListExtra("Answers", answers);
//		return i;
//	}
//	
//}
