package com.example.thedoctorisin;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_layout2);
		
		coin_edit = (TextView) findViewById(R.id.coin_edit);
		asked_edit = (TextView) findViewById(R.id.asked_edit);
		answered_edit = (TextView) findViewById(R.id.answered_edit);
		open_question_list = (ListView) findViewById(R.id.open_question_list);
	}
}
