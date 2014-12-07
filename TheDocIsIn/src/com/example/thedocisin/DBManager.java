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
//	private HTTPServicesTask serviceHelper;
	
	public static DBManager getInstance(Context context){
		if(sInstance == null){
			sInstance = new DBManager(context.getApplicationContext());
		}
		return sInstance;
	}
	
	
	private DBManager(Context context) {		
		mContext = context;

		mUDBSim = UsersDBSim.getInstance(mContext);
		mUDBSim.deleteDatabase();
		mQDBSim = QuestionsDBSim.getInstance(mContext);
		mQDBSim.deleteDatabase();
		mADBSim = AnswersDBSim.getInstance(mContext);
		mADBSim.deleteDatabase();

		
		
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
		mADBSim.getWritableDatabase().delete(AnswersDBSim.TABLE_NAME, null, null);
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
		}else if(input.contains("answerQuestion")){
			result = answerQuestion(split);
		}else if(input.contains("changeScore")){
			result = changeScore(split);
		}else if(input.contains("getUserInfo")){
			result = getUserInfo(split);
		}
		
		System.out.println("printing result");
		for(int i = 0 ; i < result.size(); i++){
			System.out.println(result.get(i));
		}
		return result;
	}
	
	private ArrayList<Object> getUserInfo(String[] split){
		ArrayList<Object> result = new ArrayList<Object>();
		String usr = "";
		
		for(int i = 0 ; i < split.length; i++){
			if(split[i].contains("uid=")){
				usr = split[i].split("=")[1];
			}
		}
		
		Cursor uc = udatabase.query(UsersDBSim.TABLE_NAME,
				UsersDBSim.columns,
				UsersDBSim.USER_ID + "=?",
				new String[] {usr},
				null, null, null, null);
		
		uc.moveToPosition(0);
		
		for(int i = 0 ; i < uc.getColumnCount(); i++){
			result.add(uc.getString(i));
		}
		
		System.out.println("PRINTING RESULT FOR USER INFO " + result);
		return result;
		
	}
	
	
	private ArrayList<Object> changeScore(String[] split){
		ArrayList<Object> result = new ArrayList<Object>();
		String str = "";
		String dir = "";
		for(int i = 0; i < split.length; i++){
			if(split[i].contains("aid=")){
				str = split[i].split("=")[1];
			}else if(split[i].contains("dir=")){
				dir = split[i].split("=")[1];
			}
		}
		
		Cursor aC = adatabase.query(AnswersDBSim.TABLE_NAME,
				new String[] {AnswersDBSim.AID, AnswersDBSim.ASCR}, 
				AnswersDBSim.AID + "=?", 
				new String[] {str}, 
				null, null, null, null);

		aC.moveToPosition(0);

		int oldVal = aC.getInt(1);
		System.out.println("old value: " + oldVal);
		
		ContentValues values = new ContentValues();
		values.put(AnswersDBSim.ASCR, oldVal + ( (dir.equals("up")? 1:-1)));

		adatabase.update(AnswersDBSim.TABLE_NAME, 
				values, 
				AnswersDBSim.AID + "=?", 
				new String[] {str});
		
		result.add(true);
		return result;
	
	}

	
	private ArrayList<Object> answerQuestion(String[] split){
		ArrayList<Object> result = new ArrayList<Object>();
		String str = "";
		String atxt = "";
		String ansid = "";
		int qid;
		
		
		
		for(int i = 0; i < split.length; i++){
			if(split[i].contains("qid=")){
				str = split[i].split("=")[1];
			}else if(split[i].contains("atxt=")){
				atxt = split[i].split("=")[1];
			}else if(split[i].contains("ansid")){
				ansid = split[i].split("=")[1];
			}
		}
		
		qid = Integer.parseInt(str);
		
		System.out.println("ANSWERING QUESTION WITH ID "  + str + " AND TEXT: " + atxt);
		
		ContentValues values = new ContentValues();
		
		values.clear();
		values.put(AnswersDBSim.QID, qid);
		values.put(AnswersDBSim.ANS_ID, ansid);
		values.put(AnswersDBSim.ATXT, atxt);
		values.put(AnswersDBSim.ASCR, 0);
		System.out.println("INSERTING THE answer.........." + adatabase.insert(AnswersDBSim.TABLE_NAME, null, values));
		
		result.add(true);
		System.out.println(" AND THE WINNER ISSSSSS : " + result);
		return result;	
		
	}
	
	
	private ArrayList<Object> getAnswers(String[] split){
		ArrayList<Object> result = new ArrayList<Object>();
		String qid = "";
		
		
		for(int i = 0 ; i < split.length; i++){
			if(split[i].contains("qid=")){
				qid = split[i].split("=")[1];
				break;
			}
		}
		System.out.println("LOOKING FOR QID " + qid);
		
		Cursor aC = adatabase.query(AnswersDBSim.TABLE_NAME,
				AnswersDBSim.columns,
				AnswersDBSim.QID + "=?",
				new String[] {qid},
				null, null, null, null);
		
		System.out.println("FOUND  " + aC.getCount() + "  ANSWERS");
		
		while(aC.moveToNext()){
			Answer a = new Answer(aC.getInt(0), aC.getInt(1), aC.getString(2), aC.getString(3), aC.getInt(4));
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
			System.out.println("DETECTING QID " + qC.getString(0));
			System.out.println("DETECTING ASKID " + qC.getString(1));
			System.out.println("DETECTING QTXT " + qC.getString(2));
			System.out.println("DETECTING CAT " + qC.getString(3));
			
			System.out.println("question: " + q);
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
//		int qid = 1;
//		if(serviceHelper == null){
//			serviceHelper = HTTPServicesTask.getInstance();
//		}
		
//		serviceHelper.registerUser("user1", "pass", "monster");
//		serviceHelper.registerUser("user2@user.com", "pass", "monster");
//		serviceHelper.registerUser("user3@user.com", "pass", "monster");
//		serviceHelper.registerUser("user4@user.com", "pass", "Mailman Joe");
//		serviceHelper.registerUser("user5@user.com", "pass", "Unidentified User");
//
//		
//		serviceHelper.askQuestion("user1", "Who was Julius Caesar?", "Other");
//		serviceHelper.answerQuestion("user5@user.com", qid, "Some guy.");
//		serviceHelper.answerQuestion("user2@user.com", qid, "Inventor of the Caesar Salad");
//		serviceHelper.answerQuestion("user4@user.com", qid, "Main character of a Shakespeare play.");
//		
//		qid++;
//		serviceHelper.askQuestion("user2@user.com", "What's the point of this app?", "Other");
//
//		qid++;
//		serviceHelper.askQuestion("user3@user.com", "Something offenstive.", "Other");
//
//		qid++;
//		serviceHelper.askQuestion("user1", "When is the Maryland Basketball game?", "Sports");
//		serviceHelper.answerQuestion("user2@user.com", qid, "8pm Eastern.");
//		serviceHelper.answerQuestion("user5@user.com", qid, "Who cares? They're going to lose anyways.");
//		
//		qid++;
//		serviceHelper.askQuestion("user1", "How many teams are in the NFL?", "Sports");
//		serviceHelper.answerQuestion("user2@user.com", qid, "32. 16 in the AFC and 16 in the NFC.");
//		serviceHelper.answerQuestion("user5@user.com", qid, "Sounds like something a girl would ask.");
//		serviceHelper.answerQuestion("user4@user.com", qid, "GOOOOO RAVENSSSSS!!!!");
//		
//		qid++;
//		serviceHelper.askQuestion("user4@user.com", "How do they decide who makes it into the playoffs?",  "Sports");
//		serviceHelper.answerQuestion("user5@user.com", qid, "It's a very complicated process. " +
//				"I would suggest going to http://www.nfl.com.");
//		serviceHelper.answerQuestion("user3@user.com", qid, "It's not that hard, it's based on season record.");
//		
//		qid++;
//		serviceHelper.askQuestion("user3@user.com", "What's the score of the Cowboys game?", "Sports");
//		serviceHelper.answerQuestion("user1", qid, "32-17 Cowboys.");
//		serviceHelper.answerQuestion("user1", qid, "Nevermind, 32 - 24 Cowboys.");
//		serviceHelper.answerQuestion("user1", qid, "Oh. The titans are going at it. 32-31");
//		serviceHelper.answerQuestion("user1", qid, "Jesus. 38-32 Titans!");
//		
//		qid++;
//		serviceHelper.askQuestion("user5@user.com", "When does baseball season really start/end?", "Sports");
//		serviceHelper.answerQuestion("user4@user.com", qid, "You're an ass hole, 'unidentified user'.");
//		
//		qid++;
//		serviceHelper.askQuestion("user1", "ughhhhh", "Sports");
		
		
		
		/////////   USER_ID, USER_NAME, PASSWORD, QASK, QANS, ASCR}
	   ContentValues values = new ContentValues();
	   values.put(UsersDBSim.USER_ID, "user1");
	   values.put(UsersDBSim.USER_NAME, "user1name");
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
	   values.put(UsersDBSim.USER_ID, "q");
	   values.put(UsersDBSim.USER_NAME, "q");
	   values.put(UsersDBSim.PASSWORD, "q");
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
	   values.put(AnswersDBSim.QID, 1);
	   values.put(AnswersDBSim.ANS_ID, "idiot@doesntknowit.com");
	   values.put(AnswersDBSim.ATXT, "Inventer of the Caesar salad.");
	   values.put(AnswersDBSim.ASCR, 1);
	   adatabase.insert(AnswersDBSim.TABLE_NAME, null, values);
	   
	   values.clear();
	   values.put(AnswersDBSim.QID, 2);
	   values.put(AnswersDBSim.ANS_ID, "sportsguy@users.us");
	   values.put(AnswersDBSim.ATXT, "8pm Eastern.");
	   values.put(AnswersDBSim.ASCR, 4);
	   adatabase.insert(AnswersDBSim.TABLE_NAME, null, values);
	   
	   values.clear();
	   values.put(AnswersDBSim.QID, 3);
	   values.put(AnswersDBSim.ANS_ID, "sportsguy@users.us");
	   values.put(AnswersDBSim.ATXT, "32. 16 in the AFC and 16 in the NFC.");
	   values.put(AnswersDBSim.ASCR, 5);
	   adatabase.insert(AnswersDBSim.TABLE_NAME, null, values);

	   values.clear();
	   values.put(AnswersDBSim.QID, 3);
	   values.put(AnswersDBSim.ANS_ID, "ass@ho.le");
	   values.put(AnswersDBSim.ATXT, "What're you, some kind of idiot?");
	   values.put(AnswersDBSim.ASCR, 1);
	   adatabase.insert(AnswersDBSim.TABLE_NAME, null, values);
	}
	
	
}
