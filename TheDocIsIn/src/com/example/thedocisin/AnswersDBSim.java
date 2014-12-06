package com.example.thedocisin;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AnswersDBSim extends SQLiteOpenHelper{
	
	final static String TABLE_NAME = "AnswerInfo";
	final static String QID = "_id";
	final static String ANS_ID = "a_id";
	final static String ATXT = "a_text";
	final static String ASCR = "a_scr";
	final static String[] columns = { QID, ANS_ID, ATXT, ASCR};
	
	
	
	final private static String CREATE_CMD =

	"CREATE TABLE " + TABLE_NAME +  " (" + QID
			+ " INTEGER , "
			+ ANS_ID + " TEXT , "
			+ ATXT +" TEXT  , "
			+ ASCR + " INTEGER )";

	final private static String NAME = "answer_db";
	final private static Integer VERSION = 1;
	final private Context mContext;
	private static AnswersDBSim sInstance;
	
	
	public static AnswersDBSim getInstance(Context context){
		if(sInstance == null){
			sInstance = new AnswersDBSim(context.getApplicationContext());
		}
		return sInstance;
	}	
	
	
	public AnswersDBSim(Context context) {
		super(context, NAME, null, VERSION);
		this.mContext = context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_CMD);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// N/A
	}

	void deleteDatabase() {
		mContext.deleteDatabase(NAME);
	}
	
	
	public Cursor readDB(SQLiteDatabase db) {
		return db.query(TABLE_NAME,
				columns, null, new String[] {}, null, null,
				null);
	}
	
	
	public boolean getData(String data){

		return false;	
	}

}
