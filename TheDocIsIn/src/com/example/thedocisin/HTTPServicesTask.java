package com.example.thedocisin;


import java.util.ArrayList;
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
	private String userID;
	
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
	
	public String getUserID() { return userID; }

	
	public void setCurrentUser(String userID){this.userID = userID;};
	public void setLogger(Logger logger){this.logger = logger;}
	public void setRegister(RegisterUser register){this.register = register;}
	public void setQuestionList(QuestionList qlist){this.qlist = qlist;}
	
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
				for(int i = 0; i < res.size(); i++){
					result.add(res.get(i));
				}
//				System.out.println(res);
//				main.settext();
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
			}
//			else if(result.get(0).equals("askQuestion")){
//				MainActivity.askQuestion((Boolean) result.get(1));
//			}
			else if(result.get(0).equals("getQuestions")){
				qlist.setQuestions(result);
			}
		}

	}


	
}