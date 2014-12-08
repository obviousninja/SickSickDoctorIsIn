package com.example.thedocisin;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	final String TAG = "TheDocIsIn";
	final static int REQUEST_CODE = 10;
	final static int LOGOUT_RESULT = 11;
	final static int QASK_RESULT = 12;
	private HTTPServicesTask serviceHelper;



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

		
		serviceHelper = HTTPServicesTask.getInstance();
		serviceHelper.setMain(this);

		food_view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Go to food question list
				Intent i = new Intent(getApplicationContext(), QuestionList.class);
				i.putExtra("Category", "Food");
				MainActivity.this.startActivityForResult(i, REQUEST_CODE);
			}
		});
		
		entertainment_view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Go to entertainment question list
				Intent i = new Intent(getApplicationContext(), QuestionList.class);
				i.putExtra("Category", "Entertainment");;
				MainActivity.this.startActivityForResult(i, REQUEST_CODE);
			}
		});
		
		personal_view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Go to personal question list 	
				Intent i = new Intent(getApplicationContext(), QuestionList.class);
				i.putExtra("Category", "Personal");
				MainActivity.this.startActivityForResult(i, REQUEST_CODE);
			}
		});
		
		sports_view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Go to sports question list 
				Intent i = new Intent(getApplicationContext(), QuestionList.class);
				i.putExtra("Category", "Sports");
				MainActivity.this.startActivityForResult(i, REQUEST_CODE);
			}
		});
		
		religion_view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Go to religion question list 
				Intent i = new Intent(getApplicationContext(), QuestionList.class);
				i.putExtra("Category", "Religion");
				MainActivity.this.startActivityForResult(i, REQUEST_CODE);
			}
		});
		
		other_view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Go to other question list
				Intent i = new Intent(getApplicationContext(), QuestionList.class);
				i.putExtra("Category", "Other");
				MainActivity.this.startActivityForResult(i, REQUEST_CODE);
			}
		});
		
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == MainActivity.REQUEST_CODE){
			if(resultCode == MainActivity.LOGOUT_RESULT){
				logout();
			} else if(resultCode == MainActivity.QASK_RESULT){
				String cat = data.getStringExtra("Category");
				Intent i = new Intent(getApplicationContext(), QuestionList.class);
				i.putExtra("Category", cat);
				MainActivity.this.startActivityForResult(i, MainActivity.REQUEST_CODE);
			}
		}
	}
	
	public void logout(){
		setResult(MainActivity.LOGOUT_RESULT);
		finish();
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
			MainActivity.this.startActivity(profileIntent);
			return true;
		}
		if(id == R.id.ask_a_question){
			Log.i(TAG, "Before creating intent");
			Intent askIntent = new Intent(MainActivity.this,AskQuestionActivity.class);
			Log.i(TAG, "After creating intent");
			askIntent.putExtra("Category", "Other");;
			MainActivity.this.startActivityForResult(askIntent, REQUEST_CODE);
			Log.i(TAG, "After starting intent");
			return true;
		}
		if(id == R.id.log_out){
			logout();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void askedQuestion(boolean b) {
		Toast.makeText(getApplicationContext(), "Not enough coins.", Toast.LENGTH_SHORT).show();		
	}
	

	
}
