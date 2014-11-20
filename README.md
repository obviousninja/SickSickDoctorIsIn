SickSickDoctorIsIn
==================

Doctor is in project

PART: Login and Registration

LOGIN: 

Status:

  awaiting for database to verify the username and the password retreived and stored
  
DOCUMENTATION:

Return: 1 for success, 0 for failure
Description: upon success, login will redirect user to the main activity
which will consist of categories of questions. upon failure the login will do
nothing. 1 and 0 will be the check conditions that goes into the database'
user table's status field.
  
  
  
  
REGISTRATION:

Status:

  awaiting for database implementation to verify the NickName, Email. 
  awaiting for database implementation to do user insertions
  
DOCMENTATION:

Return: 1 for success, 0 for failure
Description: upon success, registration will return back to the login page
with all the user's information inserted into the database. upon failure
a Toast will be generated to let the user know the creation failed, along
with what caused the failure.

PART: Ask and Answer




ASK:

Status:

  Waiting for database implementation to send the questions.
  waiting for database implementation to subtract coins appropriately.
  
Documentaion:

  MultiLine EditText that the user can enter his/her question into. Below will be a spinner to choose a category. Other options might include location, age, and others. These will be determined depending how much time is available after the rest of the app is finished.
  
  
  
  
ANSWER:

Status:

  Waiting for database implementation to send the answers.
  Waiting for database implementation to add coins appropriately.
  
Documentation:

  displays the question above a MultiLine EditText. The user can click in the MultiLine EditText to answer the question. Below the EditText there will be a cancel button and a submit button. Clicking cancel will return the user to the question's main page. Clicking submit will send the user to the question's main page, but with the user's answer included.
  
PART: QuestionView and QuestionList 





QUESTIONVIEW: 

Status:

  Awaiting database implementation to track upvotes and downvotes.

Description:

  Displays a question's title and description. Also shows the existing answers to the question and has a button which is meant to lead to the AnswerQuestion activity.
  
  
  
  
QUESTIONLIST:

  Status:

  Awaiting database implementation to obtain questions. It currently creates sample questions on its own.
  Awaiting category/question implementation to create a list of questions based on their category.

Description:

  Displays a list of questions. Tapping a question leads to its QuestionView. The QuestionList View also has a button which is meant to allow users to ask a new question when the AskQuestion activity is implemented.  
  
  
  
  
MainActivity

Status: 

The general organization and layout is complete. However, better looking icons need to be found and chosen. The categories are also not necessarily final, but limiting the mainActivity to 6 categories may be the best option to avoid clutter.

Documentation: 

MainActivity displays 6 categories of questions users can choose from, food, entertainment, personal, sports, religion, and other. The option menu bar will allow the user to ask a question and also check their personal profile page.




Profile

Status:

Pending what should go on the profile page

Documentation:

The profile page will display general statistics related to the user and will also most likely need to be able to show their questions which have been answered and not yet rated. 




HTTPServicesTask

Status:

Methods are mostly named with comments. Still need to figure out what web service to use, and how to do this in such a way.

Documentation:


HTTPServicesTask has public methods like verifyUserInfo and postQuestion that will be called from other classes. These are requests which will use a private AsyncTask to contact a database. Each of these public methods will call the doInBackground() of the AsyncTask. 




Question

The Question class will simply store information about a particular question, such as the name of the poster, the text in the question, the answers, the number of downvotes and upvotes, etc. This will make passing around this data easier. For example, in the HTTPServicesTask, a database request will get the data and populate several “question” objects, which it will then return in an arraylist.

Status:

Methods are named.

Documentation:

This class includes fields like question text, user name, answers, answerers’ user names, answer texts, categories, and answer score.









