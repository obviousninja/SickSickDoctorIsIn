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