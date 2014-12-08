package com.example.thedocisin;


import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.os.AsyncTask;

public class HTTPServicesTask {

	private static Context mContext;
	private DBManager dbmanager;
	private final String url = "http://www.url.com/";
	private static HTTPServicesTask sInstance;
	private Logger logger;
	private RegisterUser register;
	private QuestionList qlist;
	private QuestionView qview;
	private ProfileActivity profile;
	private String userID;
	private MainActivity main;
	
	public static HTTPServicesTask getInstance(Context context){
		if(sInstance == null && mContext == null){
			sInstance = new HTTPServicesTask(context.getApplicationContext());
		}else if(sInstance == null){
			sInstance = new HTTPServicesTask(context.getApplicationContext());

		}
		return sInstance;
	}
	
	public static HTTPServicesTask getInstance(){
		if(sInstance == null){
			sInstance = new HTTPServicesTask();
		}
		return sInstance;
	}
	
	private HTTPServicesTask(){
		dbmanager = DBManager.getInstance(mContext);
	}
	
	private HTTPServicesTask(Context context){
		HTTPServicesTask.mContext = context;
		dbmanager = DBManager.getInstance(mContext);
	}
	
	public String getCurrentUser() { return userID; }

	public void setMain(MainActivity main){this.main = main;}
	public void setProfileAct(ProfileActivity profile){this.profile = profile;}
	public void setCurrentUser(String userID){this.userID = userID;};
	public void setLogger(Logger logger){this.logger = logger;}
	public void setRegister(RegisterUser register){this.register = register;}
	public void setQuestionList(QuestionList qlist){this.qlist = qlist;}
	public void setQuestionView(QuestionView qview){this.qview = qview;}
	
	public void verifyUserPass(String userid, String password) {
		new DatabaseAccessor().execute(new String[] {"verifyUserPass", userid, password});
	}
	
	
	public void registerUser(String userid, String password, String username) {
		new DatabaseAccessor().execute(new String[] {"registerUser", userid, password, username});
	}
	
	public void askQuestion(String userid, String qtxt, String category){
		new DatabaseAccessor().execute(new String[] {"askQuestion", userid, qtxt, category});
	}

	public void getQuestions(String category) {
		new DatabaseAccessor().execute(new String[] {"getQuestions", category});
	}
	
	public void getAnswers(int qid){
		new DatabaseAccessor().execute(new String[] {"getAnswers", qid + ""});
	}
	
	public void answerQuestion(String userid, int qid, String atxt){
		new DatabaseAccessor().execute(new String[] {"answerQuestion", qid + "", getCurrentUser(), atxt});
	}
	
	public void changeScore(int aid, String updown){
		new DatabaseAccessor().execute(new String[] {"changeScore", aid + "", updown});
	}
	
	public void getUserInfo(String userID){
		new DatabaseAccessor().execute(new String[] {"getUserInfo", userID});
	}


	private class DatabaseAccessor extends AsyncTask<String, Void, ArrayList<Object>>{


		@Override
		protected ArrayList<Object> doInBackground(String... params) {
			ArrayList<Object> result = new ArrayList<Object>();
			result.add(params[0]);
			
			
			for(int i = 0; i < params.length; i++){
				System.out.println(params[i]);
			}
			
			
			if(params[0].equals("verifyUserPass")){
				ArrayList<Object> res = dbmanager.request(url + "&req=" + params[0] + "&usr=" + 
									params[1] + "&pas=" + params[2]);
				System.out.println(res.toString());
				result.add((Boolean) res.get(0));
				System.out.println(result.toString());

			}else if(params[0].equals("registerUser")){
				String reqString = url + "&req=" + params[0] + "&usr=" + params[1] + "&pas=" + params[2] + 
						"&nam=" + params[3];
				ArrayList<Object> res =  dbmanager.request(reqString);
				result.add(res.get(0));
			}else if(params[0].equals("askQuestion")){
				String reqString = url + "&req=" + params[0] + "&usr=" + params[1] + "&qtxt=" + 
						params[2] + "&cat=" + params[3];
				ArrayList<Object> res = dbmanager.request(reqString);
				result.add(res.get(0));
			}else if(params[0].equals("getQuestions")){
				String reqString = url + "&req=" + params[0] + "&cat=" + params[1];
				ArrayList<Object> res = dbmanager.request(reqString);
				result.addAll(res);
			}else if(params[0].equals("getAnswers")){
				String reqString = url + "&req=" + params[0] + "&qid=" + params[1];
				ArrayList<Object> res = dbmanager.request(reqString);
				result.addAll(res);
			}else if(params[0].equals("answerQuestion")){
				String reqString = url + "&req=" + params[0] + "&qid=" + params[1] + 
						"&ansid=" + params[2] + "&atxt=" + params[3];
				ArrayList<Object> res = dbmanager.request(reqString);
				result.add(res.get(0));
			}else if(params[0].equals("changeScore")){
				String reqString = url + "&req=" + params[0] + "&aid=" + params[1] + "&dir=" + params[2];
				ArrayList<Object> res = dbmanager.request(reqString);
				result.add(res.get(0));
			}else if(params[0].equals("getUserInfo")){
				String reqString = url + "&req=" + params[0] + "&uid=" + params[1];
				ArrayList<Object> res = dbmanager.request(reqString);
				result.addAll(res);
			}
			
			
			return result;
		}
		
		@Override
		protected void onPostExecute(ArrayList<Object> result){
			if( ((String) result.get(0)).equals("verifyUserPass")){
				// This should probably be done using a broadcast receiver
				logger.verifyUserPass((Boolean) result.get(1));
			}else if(result.get(0).equals("registerUser")){
				register.verifyRegisterUser((Boolean) result.get(1));
			}else if(result.get(0).equals("getQuestions")){
				qlist.setQuestions(result);
			}else if(result.get(0).equals("getAnswers")){
				qview.setAnswers(result);
			}else if(result.get(0).equals("getUserInfo")){
				profile.setUserInfo(sendToHash(result));
			}else if(result.get(0).equals("askQuestion")){
				if(((Boolean) result.get(1)) == false){
					main.askedQuestion(false);
				}
			}
		}
		
	}
	
	private HashMap<String, String> sendToHash(ArrayList<Object> result){
		HashMap<String, String> map = new HashMap<String, String>();
		
		for(int i = 1; i < result.size(); i++){
			switch(i){
			case 1:
				map.put(UsersDBSim.USER_ID, (String) result.get(i));
				break;
			case 2:
				map.put(UsersDBSim.USER_NAME, (String) result.get(i));
				break;
			case 4:
				map.put(UsersDBSim.QASK, (String) result.get(i));
				break;
			case 5:
				map.put(UsersDBSim.QANS, (String) result.get(i));
				break;
			case 6:
				map.put(UsersDBSim.COINS, (String) result.get(i));
				break;
			}
		}
		
		
		return map;
		
		
	}

	
}
