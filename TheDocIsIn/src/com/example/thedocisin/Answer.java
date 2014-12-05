package com.example.thedocisin;


import android.content.Intent;
import android.os.Bundle;

public class Answer {
	
	private final String QID_KEY = "QID";
	private final String ANS_ID_KEY = "ANS_ID";
	private final String A_TEXT_KEY = "A_TEXT";
	private final String A_SCORE_KEY = "A_SCORE";
	
	private int qid;
	private String ansID;
	private String aTxt;
	private int aScr;
	
	public Answer(int qid, String ansid, String atxt, int aScr){
		this.setqid(qid);
		this.setansID(ansid);
		this.setaTxt(atxt);
		this.setaScr(aScr);
	}
	
	public Answer(Intent intent){
		Bundle bundle = intent.getExtras();
		this.qid = bundle.getInt(QID_KEY, qid);
		this.ansID = bundle.getString(ANS_ID_KEY, ansID);
		this.aTxt = bundle.getString(A_TEXT_KEY, aTxt);
		this.aScr = bundle.getInt(A_SCORE_KEY, aScr);
	}
	
	
	public Intent packageToIntent(){
		Intent intent = new Intent();
		intent.putExtra(QID_KEY, qid);
		intent.putExtra(ANS_ID_KEY, ansID);
		intent.putExtra(A_TEXT_KEY, aTxt);
		intent.putExtra(A_SCORE_KEY, aScr);
		return intent;
	}
	

	public int getqid() {
		return qid;
	}

	public void setqid(int qid) {
		this.qid = qid;
	}

	public String getansID() {
		return ansID;
	}

	public void setansID(String ansID) {
		this.ansID = ansID;
	}

	public String getaTxt() {
		return aTxt;
	}

	public void setaTxt(String aTxt) {
		this.aTxt = aTxt;
	}

	public int getaScr() {
		return aScr;
	}

	public void setaScr(int aScr) {
		this.aScr = aScr;
	}

	public String toString(){
		String result = this.qid +"..."+  
	this.ansID +"..."+ this.aTxt +"..."+ this.aScr +"...";
		return result;
	}	
}
