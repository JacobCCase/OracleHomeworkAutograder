/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oraclehomeworkautograder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Jacob Case
 */
public class OracleHomeworkAutograder
{ 
  // JDBC driver name and database URL
  static final String JDBC_DRIVER = "com.oracle.jdbc.Driver";  
  // static final String DB_URL = "jdbc:oracle://localhost:1521";
  static final String DB_URL = "AutoGrader@//localhost:1521/xe";
  
  // Database credentials
  static final String USER = "Autograder";
  static final String PASS = "Autograder";
  
  public static void main( String[] args ) throws SQLException, ClassNotFoundException
  {
    Class.forName( "oracle.jdbc.driver.OracleDriver" );
    Connection connection = DriverManager.getConnection( "jdbc:oracle:thin:Autograder@//localhost:1521/xe", "Autograder", "Autograder" );
    
    int assignmentNumber = 1;
    String uNID = "u0935050";
    
    String[] querySolutions = 
    { "select * from product where product_price > (select avg(product_price) from product);",
      "select product_id, sum(quantity) as Total_Quantity from orderline group by product_id order by product_id;",
      "select product_id, sum(quantity) as Total_Quantity from orderline group by product_id order by sum(quantity);",
      "select orderline.product_id, product_name, sum(quantity) as Total_Quantity from orderline, product where orderline.product_id=product.product_id group by orderline.product_id, product_name order by orderline.product_id;",
      "select distinct cust_name from customer, ordertable where customer.cust_id = ordertable.cust_id and ordertable.order_date > to_date('23-OCT-2008','dd-mon-yyyy') order by cust_name;",
      "Select cust_name from customer where cust_id in (select cust_id from ordertable where order_date > to_date('23-OCT-2008','dd-mon-yyyy') ) order by cust_name;",
      "select city, count(distinct customer.cust_id) from customer, ordertable where customer.cust_id=ordertable.cust_id group by city order by city;",
      "select city, count(distinct cust_id) from customer natural join ordertable group by city order by city;",
      "select city, count(cust_id) from customer where cust_id in (select cust_id from ordertable) group by city order by city;",
      "Select product_id from product Minus Select product_id from orderline natural join ordertable where order_date > to_date('28-OCT-2008', 'dd-mon-yyyy') order by product_id;",
      "Select cust_id from customer where state='AZ' Intersect Select cust_id from ordertable where order_date > to_date('28-OCT-2008','dd-mon-yyyy') Order by cust_id;",
      "Select cust_id from customer where state='AZ' and cust_id in ( Select cust_id from ordertable where order_date > to_date('28-OCT-2008','dd-mon-yyyy') ) Order by cust_id;",
      "Select cust_id from customer where state='CA' Union  Select cust_id from ordertable where order_date > to_date('28-OCT-2008','dd-mon-yyyy') Order by cust_id;",
      "Select product_id, product_name, sum(quantity) as Total_Quantity from orderline natural join product group by product_id, product_name having sum(quantity)>10;",
      "Select product.product_id, product_name, sum(quantity) as Total_Quantity from orderline, product, customer, ordertable where orderline.product_id=product.product_id and ordertable.order_id=orderline.order_id and ordertable.cust_id=customer.cust_id and state='UT' group by product.product_id, product_name having sum(quantity)>6;"
    };
    
    String[] logicCriteriaItems = 
    { "product_price >",
      " ",
      " ",
      " ",
      "cust_id =",
      " IN",
      "cust_id =",
      " NATURAL JOIN ",
      "cust_id IN",
      " MINUS ",
      " INTERSECT ",
      "cust_id IN",
      " UNION SELECT ",
      "> 10",
      "state = 'UT'"
    };
    
    String[] querySubmissions = 
    { "select * from PRODUCT  Where PRODUCT_PRICE > (SELECT AVG (PRODUCT_PRICE) From PRODUCT);",
"select PRODUCT_ID, SUM (QUANTITY)  AS TOTAL_QUANTITY FROM ORDERLINE GROUP BY PRODUCT_ID ORDER BY PRODUCT_ID;",
"select PRODUCT_ID, SUM (QUANTITY)  AS TOTAL_QUANTITY FROM ORDERLINE GROUP BY PRODUCT_ID ORDER BY TOTAL_QUANTITY;",
"SELECT P.PRODUCT_ID, P.PRODUCT_NAME,PA.TOTAL_QUANTITY FROM PRODUCT P JOIN (select PRODUCT_ID , SUM (QUANTITY)  AS TOTAL_QUANTITY FROM ORDERLINE GROUP BY PRODUCT_ID ORDER BY PRODUCT_id) PA ON P.PRODUCT_ID = PA.PRODUCT_ID;",
"select distinct C.CUST_NAME FROM CUSTOMER C, ORDERTABLE O WHERE C.CUST_ID = O.CUST_ID AND ORDER_DATE> TO_DATE('2008-10-23', 'YYYY-MM-DD') order by C.CUST_NAME;",
"select distinct C.CUST_NAME FROM CUSTOMER C WHERE CUST_ID IN (SELECT CUST_ID  FROM ORDERTABLE WHERE ORDER_DATE> TO_DATE('2008-10-23', 'YYYY-MM-DD')) order by C.CUST_NAME;",
"SELECT DISTINCT C. CITY , count (DISTINCT C.CUST_ID) FROM CUSTOMER C,ORDERTABLE WHERE C.CUST_ID = ORDERTABLE.CUST_ID GROUP BY C.CITY ORDER BY C.CITY;",
"SELECT  CITY, count(DISTINCT CUST_ID) FROM CUSTOMER  Natural join ORDERTABLE GROUP BY CITY ORDER BY CITY;",
"SELECT DISTINCT C.CITY , count (DISTINCT C.CUST_ID) FROM CUSTOMER C WHERE CUST_ID IN (SELECT CUST_ID FROM ORDERTABLE) GROUP BY C.CITY ORDER BY C.CITY;",
"SELECT PRODUCT_id FROM PRODUCT MINUS SELECT DISTINCT OL.PRODUCT_ID FROM ORDERTABLE OT JOIN ORDERLINE OL ON OT.ORDER_ID = OL.ORDER_ID WHERE ORDER_DATE > to_date('2008-10-28', 'YYYY-MM-DD' );",
"SELECT CUST_ID FROM ORDERTABLE WHERE  ORDER_DATE > TO_DATE ('2008-10-28', 'YYYY,MM-DD') INTERSECT SELECT CUST_ID FROM CUSTOMER WHERE STATE = 'AZ' ORDER BY CUST_ID;",
"SELECT CUST_ID FROM ORDERTABLE WHERE ORDER_DATE > TO_DATE ('2008-10-28', 'YYYY-MM-DD') AND CUST_ID IN (SELECT CUST_ID FROM CUSTOMER C WHERE C.STATE = 'AZ');",
"SELECT CUST_ID FROM CUSTOMER WHERE STATE='CA' UNION SELECT CUST_ID FROM ORDERTABLE WHERE ORDER_DATE > TO_DATE ('2008-10-28','YYYY-MM-DD') ORDER BY CUST_ID;",
"SELECT P.PRODUCT_ID,P.PRODUCT_NAME, SUM(O.QUANTITY) AS TOTAL_QUANTITY FROM PRODUCT P JOIN ORDERLINE O ON P.PRODUCT_ID= O.PRODUCT_ID GROUP BY  P.PRODUCT_ID, P.PRODUCT_NAME HAVING SUM(O.QUANTITY)  > 10;",
"SELECT P.PRODUCT_ID, P.PRODUCT_NAME, SUM(OL.QUANTITY) AS TOTALQUANTITYORDERED FROM ORDERTABLE O  INNER JOIN ORDERLINE OL ON O.ORDER_ID = OL.ORDER_ID INNER JOIN CUSTOMER C ON O.CUST_ID = C.CUST_ID INNER JOIN PRODUCT P ON OL.PRODUCT_ID = P.PRODUCT_ID  WHERE C.STATE = 'UT' GROUP BY P.PRODUCT_ID, P.PRODUCT_NAME HAVING SUM(OL.QUANTITY) >6;"

            
            
    };
    
    String[] rawQuerySubmissions = { "", "", "", "", "", "" , "", "", "", "", "", "", "", "", "" };
    System.arraycopy( querySubmissions, 0, rawQuerySubmissions, 0, querySubmissions.length );
    
    // Capitalize all query strings from the solution & strip out semicolons
    for( int i = 0; i < querySolutions.length; i++ )
    { querySolutions[i] = querySolutions[i].toUpperCase();
      querySolutions[i] = querySolutions[i].replace( ";", "" );
    }
    
    // Capitalize all query strings from the student & strip out semicolons
    for( int i = 0; i < querySubmissions.length; i++ )
    { querySubmissions[i] = querySubmissions[i].toUpperCase();
      querySubmissions[i] = querySubmissions[i].replace( ";", "" );
    }
    
    // Capitalize all query strings from the criteria items & strip out semicolons
    for( int i = 0; i < logicCriteriaItems.length; i++ )
    { logicCriteriaItems[i] = logicCriteriaItems[i].toUpperCase();
      logicCriteriaItems[i] = logicCriteriaItems[i].replace( ";", "" );
    }
    
    String solutionString = "";
    String rawSubmittedString = "";
    String submittedString = "";
    
    for( int i = 0; i < querySolutions.length; i++ )
    { solutionString = querySolutions[ i ];
      rawSubmittedString = rawQuerySubmissions[ i ];
      submittedString = querySubmissions[ i ];
      System.out.println( rawSubmittedString );
      evaluateHomeworkQuestion( uNID, assignmentNumber, i+1, submittedString, solutionString, connection, logicCriteriaItems );
      // System.out.println("");
    }
    
  }
  
  
  public static void evaluateHomeworkQuestion( String uNID,
                                               int assignmentNumber,
                                               int questionNumber,
                                               String submittedQuery,
                                               String solutionQuery,
                                               Connection connection,
                                               String[] logicCriteriaItems 
                                             ) throws SQLException
  {
    String logicCriteria = logicCriteriaItems[ questionNumber - 1 ];
    
    float score = 0.000000f;
    float meetsLogicCriteriaScore = 0.00f;
    float queryExecutesScore = 0.00f;
    float resultSetsMatchScore = 0.00f;
    String meetsLogicCriteria = ( submittedQuery.indexOf( logicCriteria ) > 0 ) ? "Y" : "N";
    meetsLogicCriteriaScore = ( meetsLogicCriteria == "Y" ) ? 2.0f : 0;
    String queryExecutes = "Y";
    String resultSetsMatch = "N";
    
    Statement stmt = connection.createStatement();
    
    try
    { // Execute the query
      ResultSet solutionResultSet = stmt.executeQuery( solutionQuery );
      int solutionColumnCount = solutionResultSet.getMetaData().getColumnCount();
      ArrayList<String> solutionArray = new ArrayList<String>();
      
      while( solutionResultSet.next() )
      { String currentRow = "";
        for( int i = 1; i <= solutionColumnCount; i++ )  
        { if( i > 1 ) { currentRow += " | "; }
          currentRow += solutionResultSet.getString( i );
        }
        solutionArray.add( currentRow );
      }
      
      ResultSet submittedResultSet = stmt.executeQuery( submittedQuery );
      int submittedColumnCount = submittedResultSet.getMetaData().getColumnCount();
      ArrayList<String> submittedArray = new ArrayList<String>();
      
      while( submittedResultSet.next() )
      { String currentRow = "";
        for( int i = 1; i <= submittedColumnCount; i++ )  
        { if( i > 1 ) { currentRow += " | "; }
          currentRow += submittedResultSet.getString( i );
        }
        submittedArray.add( currentRow );
      }
      
      resultSetsMatch = ( submittedArray.equals( solutionArray ) ) ? "Y" : "N";
      queryExecutesScore = ( queryExecutes == "Y" ) ? 2.0f : 0;
      resultSetsMatchScore = ( resultSetsMatch == "Y" ) ? 2.666666f : 0;
      score += ( meetsLogicCriteriaScore + queryExecutesScore + resultSetsMatchScore );
    }
      
    catch( SQLException se )
    { //Handle errors for JDBC
      // se.printStackTrace();
      // System.out.println( "In SQL Exception block" );
      queryExecutes = "N";
    }
      
    catch( Exception e )
    { //Handle errors for Class.forName
      e.printStackTrace();
    }
    finally
    { // Record the results in the database
      
      System.out.println( "Question #: " + questionNumber 
              + ", Executes: " + queryExecutes 
              + ", Meets Logic Criteria: " + meetsLogicCriteria 
              + ", Resultsets Match: " + resultSetsMatch 
              + ", Score: " + score );
      
      String finalQueryString = "MERGE INTO HomeworkResults R " + 
  "USING ( SELECT '" + uNID + "' AS v_uNID, " +
                 assignmentNumber + " AS v_AssignmentNumber, " +
                 questionNumber + " AS v_QuestionNumber, '" +
                 queryExecutes + "' AS v_Executes, '" +
                 meetsLogicCriteria + "' AS v_MeetsLogicCriteria, '" +
                 resultSetsMatch + "' AS v_ResultSetsMatch, " +
                 score + " AS v_Score" +
          " FROM DUAL " +
       " ) S " +
  "ON ( R.uNID = S.v_uNID AND R.AssignmentNumber = S.v_AssignmentNumber AND R.QuestionNumber = S.v_QuestionNumber ) " +
  "WHEN MATCHED THEN UPDATE " +
    "SET R.Executes = S.v_Executes, " +
        "R.MeetsLogicCriteria = S.v_MeetsLogicCriteria, " +
        "R.ResultSetsMatch = S.v_ResultSetsMatch, " +
        "R.Score = S.v_Score, " +
        "R.UpdatedTimestamp = CURRENT_TIMESTAMP(0) " +
  "WHEN NOT MATCHED THEN " +
    "INSERT ( uNID, " +
             "AssignmentNumber, " +
             "QuestionNumber, " +
             "Executes, " +
             "MeetsLogicCriteria, " +
             "ResultSetsMatch, " +
             "Score " +
           ") " +
    "VALUES ( S.v_uNID, " +
             "S.v_AssignmentNumber, " +
             "S.v_QuestionNumber, " +
             "S.v_Executes, " +
             "S.v_MeetsLogicCriteria, " +
             "S.v_ResultSetsMatch, " +
             "S.v_Score " +
           ")";
      
      // System.out.println( finalQueryString );
      stmt.executeQuery( finalQueryString );
      //finally block used to close resources
      /*
      try
      { if( stmt != null )
        stmt.close();
      }
      catch( SQLException se2 )
      { // nothing we can do
      }
      try
      { if( connection != null )
          connection.close();
      } 
      catch( SQLException se )
      { se.printStackTrace();
      } //end finally try
      */
    } //end try
  }
    
}
