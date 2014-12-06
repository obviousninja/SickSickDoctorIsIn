package com.example.thedocisin;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuestionsDBSim extends SQLiteOpenHelper{
	
	final static String TABLE_NAME = "QuestionInfo";
	final static String QID = "_id";
	final static String ASK_ID = "asker";
	final static String QTXT = "q_text";
	final static String CAT = "category";
	final static String[] columns = {QID, ASK_ID, QTXT, CAT};	
	
	
	final private static String CREATE_CMD =

	"CREATE TABLE " + TABLE_NAME +  " (" + QID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT not null, "
			+ ASK_ID + " TEXT NOT NULL, "
			+ QTXT + " TEXT NOT NULL, "
			+ CAT + " TEXT NOT NULL)";

	final private static String NAME = "question_db";
	final private static Integer VERSION = 1;
	final private Context mContext;
	private static QuestionsDBSim sInstance;
	
	public static QuestionsDBSim getInstance(Context context){
		if(sInstance == null){
			sInstance = new QuestionsDBSim(context.getApplicationContext());
		}
		return sInstance;
	}
	
	private QuestionsDBSim(Context context) {
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
		sInstance = null;
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
