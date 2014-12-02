package com.example.thedocisin;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ImageView food_view = (ImageView) findViewById(R.id.food_view);
		ImageView entertainment_view = (ImageView) findViewById(R.id.entertainment_view);
		ImageView personal_view = (ImageView) findViewById(R.id.personal_view);
		ImageView sports_view = (ImageView) findViewById(R.id.sports_view);
		ImageView religion_view = (ImageView) findViewById(R.id.religion_view);
		ImageView other_view = (ImageView) findViewById(R.id.other_view);
		
		food_view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Go to food question list
			}
		});
		
		entertainment_view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Go to entertainment question list
			}
		});
		
		personal_view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Go to personal question list 	
			}
		});
		
		sports_view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Go to sports question list 
			}
		});
		
		religion_view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Go to religion question list 
			}
		});
		
		other_view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Go to other question list 
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.my_profile) {
			//Go to Profile Page
			Intent profileIntent = new Intent(MainActivity.this,ProfileActivity.class);
			startActivity(profileIntent);
			return true;
		}
		if(id == R.id.ask_a_question){
			//Go to ask a question page
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
