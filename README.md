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
