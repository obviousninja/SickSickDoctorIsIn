package com.example.thedocisin;


import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.widget.SimpleCursorAdapter;

public class DBManager {

	private UsersDBSim mUDBSim;
	private QuestionsDBSim mQDBSim;
	private AnswersDBSim mADBSim;
	private SQLiteDatabase udatabase;
	private SQLiteDatabase qdatabase;
	private SQLiteDatabase adatabase;
	private Context mContext;
	private static DBManager sInstance;
	
	public static DBManager getInstance(Context context){
		if(sInstance == null){
			sInstance = new DBManager(context.getApplicationContext());
		}
		return sInstance;
	}
	
	
	private DBManager(Context context) {		
		mContext = context;

		mUDBSim = UsersDBSim.getInstance(mContext);
		udatabase = mUDBSim.getWritableDatabase();

		mQDBSim = QuestionsDBSim.getInstance(mContext);
		qdatabase = mQDBSim.getWritableDatabase();
		
		mADBSim = AnswersDBSim.getInstance(mContext);
		adatabase = mADBSim.getWritableDatabase();
		
		clearAll();
		populate();
	}
	
	private void clearAll() {
		mUDBSim.getWritableDatabase().delete(UsersDBSim.TABLE_NAME, null, null);
		mQDBSim.getWritableDatabase().delete(QuestionsDBSim.TABLE_NAME, null, null);
	}
	
	public Cursor getData(String databaseName){
		if(databaseName.equals("UserInfo")){
			return mUDBSim.readDB(udatabase);
		}else if(databaseName.equals("QuestionsInfo")){
			return mQDBSim.readDB(qdatabase);
		}else{
			System.out.println("invalid db name");
			return null;
		}
	}
	
	
	
	

	public ArrayList<Object> request(String input) {
		ArrayList<Object> result = null;
		System.out.println("request:    " + input );
		input = (String) input.subSequence(19, input.length());
		
		String[] split = input.split("&");

		if(input.contains("verifyUserPass")){
			result = verifyUserPass(split);
		}else if(input.contains("registerUser")){
			result = registerUser(split);
		}else if(input.contains("askQuestion")){
			result = askQuestion(split);
		}else if(input.contains("getQuestions")){
			result = getQuestions(split);
		}else if(input.contains("getAnswers")){
			result = getAnswers(split);
		}
		
		System.out.println("printing result");
		for(int i = 0 ; i < result.size(); i++){
			System.out.println(result.get(i));
		}
		return result;
	}

	
	private ArrayList<Object> getAnswers(String[] split){
		ArrayList<Object> result = new ArrayList<Object>();
		int qid;
		String str = "";
		
		
		for(int i = 0 ; i < split.length; i++){
			if(split[i].contains("qid=")){
				str = split[i].split("=")[1];
				break;
			}
		}
				
		qid = Integer.parseInt(str);
		
		Cursor aC = adatabase.query(AnswersDBSim.TABLE_NAME,
				AnswersDBSim.columns,
				AnswersDBSim.QID + "=?",
				new String[] {str},
				null, null, null, null);
		
		while(aC.moveToNext()){
			Answer a = new Answer(aC.getInt(0), aC.getString(1), aC.getString(2), aC.getInt(3));
			result.add(a);
		}
		
		return result;
		
	}

	private ArrayList<Object> getQuestions(String[] split) {
		ArrayList<Object> result = new ArrayList<Object>();
		String cat = "";
		
		for(int i = 0; i < split.length; i++){
			if(split[i].contains("cat=")){
				cat = split[i].split("=")[1];
				System.out.println("detecting category:  " +  cat);
			}
		}
		
		Cursor qC = qdatabase.query(QuestionsDBSim.TABLE_NAME,
				QuestionsDBSim.columns, 
				QuestionsDBSim.CAT + "=?", 
				new String[] {cat}, 
				null, null, null, null);
		
		
//		int qid, String askid, String qtxt, String ansid, String atxt, int aScr, String category
		while(qC.moveToNext()){
			Question q = new Question(qC.getInt(0), qC.getString(1), qC.getString(2), qC.getString(3));
			result.add(q);
		}
		
		return result;
	}

	private ArrayList<Object> askQuestion(String[] split) {
		String userid = "";
		String qtxt = "";
		String cat = "";

		for(int i = 0; i < split.length; i++){
			if(split[i].contains("usr=")){
				userid = split[i].split("=")[1];
			}else if(split[i].contains("qtxt=")){
				qtxt = split[i].split("=")[1];
			}else if(split[i].contains("cat=")){
				cat = split[i].split("=")[1];
			}
		}
		
		Cursor uC = udatabase.query(UsersDBSim.TABLE_NAME,
				new String[] {UsersDBSim.USER_ID, UsersDBSim.QASK}, 
				UsersDBSim.USER_ID + "=?", 
				new String[] {userid}, 
				null, null, null, null);

		uC.moveToPosition(0);

		int oldVal = uC.getInt(1);
		System.out.println("old value: " + oldVal);
		
		ContentValues values = new ContentValues();
		values.put(UsersDBSim.QASK, oldVal + 1);
		
		udatabase.update(UsersDBSim.TABLE_NAME, 
				values, 
				UsersDBSim.USER_ID + "=?", 
				new String[] {userid});
		
		
		uC = udatabase.query(UsersDBSim.TABLE_NAME,
				new String[] {UsersDBSim.USER_ID, UsersDBSim.QASK}, 
				UsersDBSim.USER_ID + "=?", 
				new String[] {userid}, 
				null, null, null, null);

		uC.moveToPosition(0);

		int newVal = uC.getInt(1);
		System.out.println("new value: " + newVal);
		
		values.clear();
		values.clear();
		values.put(QuestionsDBSim.ASK_ID, userid);
		values.put(QuestionsDBSim.QTXT, qtxt);
		values.put(QuestionsDBSim.CAT, cat);
//		values.put(QuestionsDBSim.ANS_ID, ".");
//		values.put(QuestionsDBSim.ASCR, 0);
//		values.put(QuestionsDBSim.ATXT, "...");
		System.out.println("INSERTING THE QUESTION.........." + qdatabase.insert(QuestionsDBSim.TABLE_NAME, null, values));
		
		ArrayList<Object> result = new ArrayList<Object>();
		result.add(true);
		return result;
	}

	private ArrayList<Object> verifyUserPass(String[] split) {
		ArrayList<Object> result = new ArrayList<Object>();
		String userid = "";
		String password = "";

		for(int i = 0; i < split.length; i++){
			if(split[i].contains("usr=")){
				userid = split[i].split("=")[1];
			}else if(split[i].contains("pas=")){
				password = split[i].split("=")[1];
			}
		}

		Cursor c = udatabase.query(
				UsersDBSim.TABLE_NAME, 
				new String[] {UsersDBSim.USER_ID, UsersDBSim.PASSWORD},
				UsersDBSim.USER_ID + "=?",
				new String[] {userid},
				null, null, null, null);
		
		c.moveToPosition(0);
		
		if(c.getCount() == 0){
			result.add(false);
			return result;
		}

		if(c.getString(1).equals(password)){
			result.add(true);
		}else{
			result.add(false);
		}	
		return result;
	}
	

	private ArrayList<Object> registerUser(String[] split) {
		ArrayList<Object> result = new ArrayList<Object>();
		String userid = "";
		String password = "";
		String username = "";

		for(int i = 0; i < split.length; i++){
			if(split[i].contains("usr=")){
				userid = split[i].split("=")[1];
			}else if(split[i].contains("pas=")){
				password = split[i].split("=")[1];
			}else if(split[i].contains("nam=")){
				username = split[i].split("=")[1];
			}
		}

		Cursor c = udatabase.query(
				UsersDBSim.TABLE_NAME, 
				new String[] {UsersDBSim.USER_ID},
				UsersDBSim.USER_ID + "=?",
				new String[] {userid},
				null, null, null, null);
		
		c.moveToPosition(0);
		
		if(c.getCount() != 0){
			result.add(false);
			return result;
		}

		
		
		ContentValues values = new ContentValues();
		values.put(UsersDBSim.USER_ID, userid);
		values.put(UsersDBSim.USER_NAME, username);
		values.put(UsersDBSim.PASSWORD, password);
		values.put(UsersDBSim.QASK, 0);
		values.put(UsersDBSim.QANS, 0);
		values.put(UsersDBSim.ASCR, 0);
		udatabase.insert(UsersDBSim.TABLE_NAME, null, values);
		result.add(true);
		return result;

	}
	
	
	
	
	
	public void populate(){
		/////////   USER_ID, USER_NAME, PASSWORD, QASK, QANS, ASCR}
	   ContentValues values = new ContentValues();
	   values.put(UsersDBSim.USER_ID, "user1");
	   values.put(UsersDBSim.USER_NAME, "user1");
	   values.put(UsersDBSim.PASSWORD, "pass");
	   values.put(UsersDBSim.QASK, 5);
	   values.put(UsersDBSim.QANS, 6);
	   values.put(UsersDBSim.ASCR, 5);
	   
	   udatabase.insert(UsersDBSim.TABLE_NAME, null, values);

	   values.clear();
	   values.put(UsersDBSim.USER_ID, "user2@user.u");
	   values.put(UsersDBSim.USER_NAME, "user2");
	   values.put(UsersDBSim.PASSWORD, "pass");
	   values.put(UsersDBSim.QASK, 14);
	   values.put(UsersDBSim.QANS, 3);
	   values.put(UsersDBSim.ASCR, 12);

	   udatabase.insert(UsersDBSim.TABLE_NAME, null, values);
	   
	   
	   values.clear();
	   values.put(QuestionsDBSim.ASK_ID, "user1@user.u");
	   values.put(QuestionsDBSim.QTXT, "Who was Julius Caesar?");
	   values.put(QuestionsDBSim.CAT, "Other");
	   System.out.println(qdatabase.insert(QuestionsDBSim.TABLE_NAME, null, values));
	   
	   values.clear();
	   values.put(QuestionsDBSim.ASK_ID, "user1@user.u");
	   values.put(QuestionsDBSim.QTXT, "What time is the Maryland Basketball game?");
	   values.put(QuestionsDBSim.CAT, "Sports");
	   qdatabase.insert(QuestionsDBSim.TABLE_NAME, null, values);
	   
	   
	   values.clear();
	   values.put(QuestionsDBSim.ASK_ID, "user1@user.u");
	   values.put(QuestionsDBSim.QTXT, "How many teams are in the NFL?");
	   values.put(QuestionsDBSim.CAT, "Sports");
	   qdatabase.insert(QuestionsDBSim.TABLE_NAME, null, values);
	   
	   values.clear();
	   values.put(AnswersDBSim.QID, 0);
	   values.put(AnswersDBSim.ANS_ID, "idiot@doesntknowit.com");
	   values.put(AnswersDBSim.ATXT, "Inventer of the Caesar salad.");
	   values.put(AnswersDBSim.ASCR, 1);
	   
	   values.clear();
	   values.put(AnswersDBSim.QID, 1);
	   values.put(AnswersDBSim.ANS_ID, "sportsguy@users.us");
	   values.put(AnswersDBSim.ATXT, "8pm Eastern.");
	   values.put(AnswersDBSim.ASCR, 4);
	   
	   values.clear();
	   values.put(AnswersDBSim.QID, 2);
	   values.put(AnswersDBSim.ANS_ID, "sportsguy@users.us");
	   values.put(AnswersDBSim.ATXT, "32. 16 in the AFC and 16 in the NFC.");
	   values.put(AnswersDBSim.ASCR, 5);
	}
	
	
}
