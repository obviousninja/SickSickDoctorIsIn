package com.example.thedocisin;

import java.util.ArrayList;

//import com.example.thedocisin.Answer.Answer;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AnswerViewAdapter extends BaseAdapter{

	ArrayList<Answer> items;
	Context mContext;
	private HTTPServicesTask serviceHelper;
	
	public AnswerViewAdapter(Context context){
		mContext = context;
		items = new ArrayList<Answer>();
		serviceHelper = HTTPServicesTask.getInstance();
	}
	
	public void addAll(ArrayList<Answer> arrayList){
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
		scoreView.setText(Integer.toString(item.getaScr()));
		answerView.setText(item.getname() + "\n" + item.getaTxt());
		answerView.setMovementMethod(new ScrollingMovementMethod());
		
		upvoteButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Implement upvotes.
				serviceHelper.changeScore(item.getaid(), "up");
				item.setaScr(item.getaScr() + 1);
				scoreView.setText(Integer.toString(item.getaScr()));
				upvoteButton.setOnClickListener(null);
				downvoteButton.setOnClickListener(null);
			}
			
		});
		
		downvoteButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Implement downvotes.
				serviceHelper.changeScore(item.getaid(), "dn");		
				item.setaScr(item.getaScr() - 1);
				scoreView.setText(Integer.toString(item.getaScr()));
				upvoteButton.setOnClickListener(null);
				downvoteButton.setOnClickListener(null);
			}
			
		});
		
		
		return itemLayout;
	}

	public void clear() {
		items.clear();
	}

}