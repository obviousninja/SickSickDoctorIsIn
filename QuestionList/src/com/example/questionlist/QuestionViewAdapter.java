package com.example.questionlist;

import java.util.ArrayList;

import com.example.questionlist.QuestionList.Question;
import com.example.questionlist.QuestionList.Question.Answer;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionViewAdapter extends BaseAdapter{

	ArrayList<Parcelable> items;
	Context mContext;
	
	public QuestionViewAdapter(Context context){
		mContext = context;
		items = new ArrayList<Parcelable>();
	}
	
	public void addAll(ArrayList<Parcelable> arrayList){
		items.addAll(arrayList);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RelativeLayout itemLayout;
		final Answer item = (Answer) getItem(position);
		if(convertView != null){
			itemLayout = (RelativeLayout)convertView;
		} else {
			itemLayout = (RelativeLayout) View.inflate(mContext,  R.layout.question_view_answer, null);
		}
		
		final TextView scoreView = (TextView) itemLayout.findViewById(R.id.score);
		final TextView answerView = (TextView) itemLayout.findViewById(R.id.answerView);
		final ImageButton upvoteButton = (ImageButton) itemLayout.findViewById(R.id.upvoteButton);
		final ImageButton downvoteButton = (ImageButton) itemLayout.findViewById(R.id.downvoteButton);
		scoreView.setText(Integer.toString(item.getScore()));
		answerView.setText(item.getText());
		answerView.setMovementMethod(new ScrollingMovementMethod());
		
		upvoteButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Implement upvotes.
				
			}
			
		});
		
		downvoteButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Implement downvotes.
				
			}
			
		});
		
		
		return itemLayout;
	}

}
