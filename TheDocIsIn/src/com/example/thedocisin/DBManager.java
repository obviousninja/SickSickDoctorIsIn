package com.example.thedocisin;


import java.util.ArrayList;
import java.util.Random;

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
		
				
		Cursor uC = udatabase.query(UsersDBSim.TABLE_NAME,
				new String[] {UsersDBSim.USER_ID, UsersDBSim.QANS, UsersDBSim.COINS}, 
				UsersDBSim.USER_ID + "=?", 
				new String[] {ansid}, 
				null, null, null, null);

		uC.moveToPosition(0);

		int oldValAns = uC.getInt(1);
		int oldValCoins = uC.getInt(2);

		
		ContentValues values = new ContentValues();
		values.put(UsersDBSim.QANS, oldValAns + 1);
		values.put(UsersDBSim.COINS, oldValCoins + 5);
		
		udatabase.update(UsersDBSim.TABLE_NAME, 
				values, 
				UsersDBSim.USER_ID + "=?", 
				new String[] {ansid});
		
	
		
		values.clear();
		values.put(AnswersDBSim.QID, qid);
		values.put(AnswersDBSim.ANS_ID, ansid);
		values.put(AnswersDBSim.ATXT, atxt);
		values.put(AnswersDBSim.ASCR, 0);
		adatabase.insert(AnswersDBSim.TABLE_NAME, null, values);
		
		result.add(true);
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
			a.setname(getName(aC.getString(2)));
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
			q.setname(getName(qC.getString(1)));
			result.add(q);
		}
		
		return result;
	}

	private ArrayList<Object> askQuestion(String[] split) {
		ArrayList<Object> result = new ArrayList<Object>();

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
				new String[] {UsersDBSim.USER_ID, UsersDBSim.QASK, UsersDBSim.COINS}, 
				UsersDBSim.USER_ID + "=?", 
				new String[] {userid}, 
				null, null, null, null);

		uC.moveToPosition(0);

		int oldValAsk = uC.getInt(1);
		int oldValCoins = uC.getInt(2);
		if(oldValCoins < 5){
			result.add(false);
			return result;
		}
		System.out.println("old value: " + oldValAsk + "\nold coins:" + oldValCoins);
		
		ContentValues values = new ContentValues();
		values.put(UsersDBSim.QASK, oldValAsk + 1);
		values.put(UsersDBSim.COINS, oldValCoins - 5);
		
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
		qdatabase.insert(QuestionsDBSim.TABLE_NAME, null, values);
		
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
		values.put(UsersDBSim.COINS, 75);
		udatabase.insert(UsersDBSim.TABLE_NAME, null, values);
		result.add(true);
		return result;

	}
	
	
	private String getName(String userid){
		System.out.println("TRYING TO GET NAME CORRESPONDING TO " + userid);
		Cursor uC = udatabase.query(UsersDBSim.TABLE_NAME,
				new String[] {UsersDBSim.USER_ID, UsersDBSim.USER_NAME}, 
				UsersDBSim.USER_ID + "=?", 
				new String[] {userid}, 
				null, null, null, null);
		
		uC.moveToNext();
		System.out.println("COLUMN COUNT : " + uC.getColumnCount());
		
		if(uC.getCount() > 0){
			System.out.println("RETURNING " + uC.getString(1));
			return uC.getString(1);
		}
		System.out.println("RETURNING can't find");
		return "can't find";		
	}
	
	
	
	public void populate(){
		
		
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> questions = new ArrayList<String>();
		
		insertUser(list, "user1");
		insertUser(list, "ArcticMonkey");
		insertUser(list, "MailmanJoe");
		insertUser(list, "HippoFred");
		insertUser(list, "xoxoxLipstickQueenxoxox");
		insertUser(list, "SoccerBro");
		insertUser(list, "SurfsUpDude");
		insertUser(list, "GenericUserName");
		insertUser(list, "SportsGuy");
		insertUser(list, "HistoryBuff");
		insertUser(list, "RenaisanceManJim");
		insertUser(list, "UserUser");
		insertUser(list, "CivicsRule");
		
		insertQuestion(list, questions, "How many teams are in the NFL?", "Sports");						//1
		insertQuestion(list, questions, "When is the Maryland Basketball game?", "Sports");					//2
		insertQuestion(list, questions, "How do they decide who makes it into the playoffs?", "Sports");	//3
		insertQuestion(list, questions, "What's the score of the Cowboys game?", "Sports");					//4
		insertQuestion(list, questions, "How many games are left in this NFL season?", "Sports");			//5
		insertQuestion(list, questions, "When does baseball season really start/end?", "Sports");			//6
		insertQuestion(list, questions, "When is the next world cup?", "Sports");							//7
		insertQuestion(list, questions, "What's the best team in the NFL?", "Sports");						//8
		insertQuestion(list, questions, "Was that call really necessary?! (Ravens)", "Sports");				//9
		insertQuestion(list, questions, "How on earth did the ref think that was okay??", "Sports");		//10
		insertQuestion(list, questions, "Tips for practicing soccer by yourself?", "Sports");				//11
		insertQuestion(list, questions, "Was this question already asked?", "Sports");						//12
		insertQuestion(list, questions, "What's the score of the Titans game?", "Sports");					//13
		insertQuestion(list, questions, "How many games are left in this soccer season?", "Sports");		//14
		insertQuestion(list, questions, "When is the College Pro Bowl?", "Sports");							//15
		
		insertQuestion(list, questions, "What's the point of this app?", "Other");							//16
		insertQuestion(list, questions, "Something offenstive.", "Other");									//17
		insertQuestion(list, questions, "What's the meaning of life?", "Other");							//18
		insertQuestion(list, questions, "How is this any different from yahoo answers?", "Other");			//19
		insertQuestion(list, questions, "Is anyone even really reading these?", "Other");					//20
		insertQuestion(list, questions, "This is just an example. What's next?", "Other");					//21
		insertQuestion(list, questions, "Some questions are here.", "Other");								//22
		insertQuestion(list, questions, "Well hello there, how're you?", "Other");							//23
		insertQuestion(list, questions, "I couldn't fit this into any other category but...", "Other");		//24
		insertQuestion(list, questions, "This is stupid... but", "Other");									//25
		insertQuestion(list, questions, "I can't believe it's not butter!", "Other");						//26
		insertQuestion(list, questions, "Why so serious?", "Other");										//27
		insertQuestion(list, questions, "When is the next star wars actually coming out?", "Other");		//28
		insertQuestion(list, questions, "Is family guy even good?", "Other");								//29

		// The following questions are written by Philip Austin
		insertQuestion(list, questions, "Why is your favorite movie Black Swan?", "Entertainment");			//30
		insertQuestion(list, questions, "What is some must hear underground music?", "Entertainment");		//31
		insertQuestion(list, questions, "How old is Jennifer Anniston?", "Entertainment");					//32
		insertQuestion(list, questions, "What's up with Ashton kutcher?", "Entertainment");					//33
		insertQuestion(list, questions, "Game of Thrones - why should I watch?", "Entertainment");			//34
		insertQuestion(list, questions, "Is Orange Is The New Black as good as I hear?", "Entertainment");	//35
		insertQuestion(list, questions, "Is Stephen Colbert actually Conservative?", "Entertainment");		//36
		insertQuestion(list, questions, "What season of Bachelor/Bachelorette is it?", "Entertainment");	//37
		insertQuestion(list, questions, "What has Miley done now?", "Entertainment");						//38
		insertQuestion(list, questions, "What are some recent celebrity marriages?", "Entertainment");		//39
		insertQuestion(list, questions, "Who is the energetic guy on Parks and Rec?", "Entertainment");		//40
		insertQuestion(list, questions, "What network shows South Park?", "Entertainment");					//41
		
		insertQuestion(list, questions, "What meals can I make with onions and rice?", "Food");				//42
		insertQuestion(list, questions, "I can't find the food on instagram anywhere?", "Food");			//43
		insertQuestion(list, questions, "What is Gordon Ramsey's favorite meal to prepare?", "Food");		//44
		insertQuestion(list, questions, "What is Gluten and why are people afraid of it?", "Food");			//45
		insertQuestion(list, questions, "Am I alergic to shellfish?", "Food");								//46
		insertQuestion(list, questions, "Can I eat a blowfish even if it's puffed up?", "Food");			//47
		insertQuestion(list, questions, "Is there proof that McDonalds is bad for you?", "Food");			//48
		insertQuestion(list, questions, "So Arby's...What's up with that?", "Food");						//49
		insertQuestion(list, questions, "Is there an airline that provides good food?", "Food");			//50
		insertQuestion(list, questions, "Easy to make dorm foods?", "Food");								//51
		insertQuestion(list, questions, "Does an apple a day keep the doctor away" +						//52
				" if you like your doctor?", "Food"); 														
		insertQuestion(list, questions, "Are apples or bananas better for you?", "Food");					//53
		insertQuestion(list, questions, "How do you make dough", "Food");									//54
		insertQuestion(list, questions, "What is a good local restaraunt by you?", "Food");					//55
		insertQuestion(list, questions, "Is there a filling but healthy snack out there?", "Food");			//56
		
		insertQuestion(list, questions, "How can I connect with my teenage son?", "Personal");				//57
		insertQuestion(list, questions, "How can I prevent my mother inlaw from moving in?", "Personal");	//58
		insertQuestion(list, questions, "Advise on dealing with a troublesome son inlaw?", "Personal");		//59
		insertQuestion(list, questions, "How can I tell if my 12 y.o. girl is ready for dating",			//60
				"Personal");
		insertQuestion(list, questions, "How to get my teenage son off the couch?", "Personal");			//61
		insertQuestion(list, questions, "Any ideas on wedding themes?", "Personal");						//62
		insertQuestion(list, questions, "How can i get my college roommate to do the dishes?", "Personal");	//63
		insertQuestion(list, questions, "Does Microsoft offer free repairs?", "Personal");					//64
		
		insertQuestion(list, questions, "What if God was one of us?", "Religion");							//65
		insertQuestion(list, questions, "What were the disciples' names?", "Religion");						//66
		insertQuestion(list, questions, "How did Christianity become the most common religion in the world",//67
				"Religion");
		insertQuestion(list, questions, "What does a \"Shin\" mean on a dreidel?", "Religion");				//68
		insertQuestion(list, questions, "How does repenting resolve one of their sins?", "Religion");		//69
		insertQuestion(list, questions, "The Pope is for what religion?", "Religion");						//70
		insertQuestion(list, questions, "Why is there 8 days of Channukkah?", "Religion");					//71
		insertQuestion(list, questions, "What does a bunny have to do with Easter?", "Religion");			//72
		insertQuestion(list, questions, "What are some lesser known holidays for your religion",			//73
				"Religion");
		insertQuestion(list, questions, "Why is church on Sundays?", "Religion");							//74
		insertQuestion(list, questions, "Why can'y some people accept other people like" +					//75
				"other religions?", "Religion");		
		
		insertAnswer(list, questions, 4, "32-16 Titans.");
		insertAnswer(list, questions, -1, "I just need coins!!");
		insertAnswer(list, questions, -1, "I just need coins!!");
		insertAnswer(list, questions, -1, "I just need coins!!");
		insertAnswer(list, questions, -1, "I just need coins!!");
		insertAnswer(list, questions, -1, "I just need coins!!");
		insertAnswer(list, questions, -1, "I just need coins!!");
		insertAnswer(list, questions, -1, "I just need coins!!");
		insertAnswer(list, questions, -1, "I just need coins!!");
		insertAnswer(list, questions, -1, "I just need coins!!");
		insertAnswer(list, questions, -1, "I just need coins!!");
		insertAnswer(list, questions, -1, "I just need coins!!");
		insertAnswer(list, questions, -1, "I just need coins!!");
		insertAnswer(list, questions, -1, "I just need coins!!");
		insertAnswer(list, questions, -1, "I just need coins!!");
		insertAnswer(list, questions, -1, "I just need coins!!");
		insertAnswer(list, questions, -1, "I just need coins!!");
		insertAnswer(list, questions, -1, "I just need coins!!");
		insertAnswer(list, questions, -1, "I just need coins!!");
		insertAnswer(list, questions, -1, "I just need coins!!");

		
	}
	
	private void insertUser(ArrayList<String> list, String userName){
			list.add(userName + "@user.com");
			Random rand = new Random();
		    ContentValues values = new ContentValues();
		    values.put(UsersDBSim.USER_ID, userName + "@user.com");
		    values.put(UsersDBSim.USER_NAME, userName);
		    values.put(UsersDBSim.PASSWORD, "pass");
		    values.put(UsersDBSim.QASK, rand.nextInt(50));
		    values.put(UsersDBSim.QANS, rand.nextInt(50));
		    values.put(UsersDBSim.COINS, 75);
		   
		    udatabase.insert(UsersDBSim.TABLE_NAME, null, values);
	}
	
	private void insertQuestion(ArrayList<String> users, ArrayList<String> questions, String question, String cat){
		ContentValues values = new ContentValues();
		Random rand = new Random();
		questions.add("true");
		values.clear();
		values.put(QuestionsDBSim.ASK_ID, users.get(rand.nextInt(users.size())));
		values.put(QuestionsDBSim.QTXT, question);
		values.put(QuestionsDBSim.CAT, cat);
		qdatabase.insert(QuestionsDBSim.TABLE_NAME, null, values);
	}
	
	private void insertAnswer(ArrayList<String> users, ArrayList<String> questions, int qid, String answer){
		ContentValues values = new ContentValues();
		Random rand = new Random();
	    values.clear();
	    values.put(AnswersDBSim.QID, qid<0? rand.nextInt(questions.size()):qid);
	    values.put(AnswersDBSim.ANS_ID, users.get(rand.nextInt(users.size())));
	    values.put(AnswersDBSim.ATXT, answer);
	    values.put(AnswersDBSim.ASCR, rand.nextInt(100) - (qid < 0? 99:16));
	    adatabase.insert(AnswersDBSim.TABLE_NAME, null, values);
	}
	
	
	
}
