package com.example.questionlist;

import java.util.ArrayList;

import com.example.questionlist.QuestionList.Question;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QuestionListAdapter extends BaseAdapter {
	private final ArrayList<Question> items;
	private final Context mContext;
	
	
	public QuestionListAdapter(Context context){
		mContext = context;
		items = new ArrayList<Question>();
	}
	
	public void setItems(ArrayList<Question> questions){
		items.addAll(questions);
		notifyDataSetChanged();
	}

	public void addItem(Question q){
		items.add(q);
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
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RelativeLayout itemLayout;
		final Question item = (Question) getItem(position);
		if(convertView != null){
			itemLayout = (RelativeLayout)convertView;
		} else {
			itemLayout = (RelativeLayout) View.inflate(mContext,  R.layout.question_list_item, null);
		}
		
		final TextView titleView = (TextView) itemLayout.findViewById(R.id.question_list_item_title);
		titleView.setText(item.getTitle());
		
		itemLayout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent questionViewIntent = new Intent(mContext, QuestionView.class);
				questionViewIntent.putExtra("Title", item.getTitle());
				questionViewIntent.putExtra("Description", item.getDescription());
				questionViewIntent.putParcelableArrayListExtra("Answers", item.getAnswers());
				mContext.startActivity(questionViewIntent);
			}
			
		});
		
		
		return itemLayout;
	}

}
