
OracleHomeworkAutograder helps to automate the grading of your student's Oracle queries

Steps:

1- Create a local Oracle database instance with a user/schema called "AutoGrader"

	CREATE USER Autograder identified by Autograder ;
	GRANT CONNECT, RESOURCE, DBA to Autograder ;

2- Create a table called, "HomeworkResults" as per the HomeworkResults.sql DDL file
3- Create the tables that are required for the students in the homework assignment and populate them
4- Open the OracleHomeworkAutograder project in NetBeans
5- Right-click Libraries, choose Add JAR/folder, and navigate to and select the ojdbc7.jar file
6- Open the OracleHomeworkAutograder.java file
   Search for the following lines and update them to correspond with your current connection parameters:

	static final String JDBC_DRIVER = "com.oracle.jdbc.Driver";
	static final String DB_URL = "Autograder@//localhost:1521/xe"; // username@//localhost:1521/xe
	static final String USER = "Autograder"; // This is the username
	static final String PASS = "Autograder"; // This is the password

7- :ocate and update the uNID variable to be set to that of the current student: 

	String uNID = "u9999999";
	
8- Populate the querySolutions String array with the correct queries
9- Populate the querySubmissions String array with the queries submitted by the student
10-Run the program to grade the student's query submissions
11- View the results in the NetBeans Output window, or query the HomeworkResults table

