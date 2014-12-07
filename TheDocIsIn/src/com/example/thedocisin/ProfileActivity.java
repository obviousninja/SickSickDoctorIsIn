package com.example.thedocisin;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class ProfileActivity extends Activity{
	//These are the text fields which should be edited according to profile data
	TextView coin_edit, asked_edit, answered_edit;
	
	//A list of all the user's currently open questions. The user should be able to
	//click on the question and check if it has been answered or not.
	ListView open_question_list;
	private HTTPServicesTask serviceHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_layout);
		
		serviceHelper = HTTPServicesTask.getInstance();
		serviceHelper.setProfileAct(this);
		serviceHelper.getUserInfo(serviceHelper.getCurrentUser());
		
		coin_edit = (TextView) findViewById(R.id.coin_edit);
		asked_edit = (TextView) findViewById(R.id.asked_edit);
		answered_edit = (TextView) findViewById(R.id.answered_edit);
		open_question_list = (ListView) findViewById(R.id.open_question_list);
		
	}
	
	public void setUserInfo(HashMap<String, String> map){
		this.coin_edit.setText(map.get(UsersDBSim.COINS));
		this.asked_edit.setText(map.get(UsersDBSim.QASK));
		this.answered_edit.setText(map.get(UsersDBSim.QANS));
	}
	
	
}
