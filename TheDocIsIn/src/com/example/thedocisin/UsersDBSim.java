package com.example.thedocisin;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UsersDBSim extends SQLiteOpenHelper{
	
	final static String TABLE_NAME = "UserInfo";
	final static String USER_NAME = "name";
	final static String USER_ID = "_id";
	final static String PASSWORD = "password";
	final static String QASK = "Q_Ask";
	final static String QANS = "Q_Ans";
	final static String COINS = "coins";
	final static String[] columns = { USER_ID, USER_NAME, PASSWORD, QASK, QANS, COINS};
	
	
	
	final private static String CREATE_CMD =

	"CREATE TABLE " + TABLE_NAME +  " (" + USER_ID
			+ " TEXT UNIQUE NOT NULL, "
			+ USER_NAME + " TEXT NOT NULL, "
			+ PASSWORD + " TEXT NOT NULL, "
			+ QASK + " INTEGER , "
			+ QANS +" INTEGER , "
			+ COINS + " INTEGER )";

	final private static String NAME = "user_db";
	final private static Integer VERSION = 1;
	final private Context mContext;
	private static UsersDBSim sInstance;
	
	public static UsersDBSim getInstance(Context context){
		if(sInstance == null){
			sInstance = new UsersDBSim(context.getApplicationContext());
		}
		return sInstance;
	}
	
	
	private UsersDBSim(Context context) {
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
