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
  static final String DB_URL = "Lab3@//localhost:1521/xe";
  
  // Database credentials
  static final String USER = "Lab3";
  static final String PASS = "Lab3";
  
  public static void main( String[] args ) throws SQLException, ClassNotFoundException
  {
    Class.forName( "oracle.jdbc.driver.OracleDriver" );
    Connection connection = DriverManager.getConnection( "jdbc:oracle:thin:Lab3@//localhost:1521/xe", "Lab3", "Lab3" );
    
    String uNID = "u9999999";
    
    String[] querySolutions = 
    { "select * from product where product_price > (select avg(product_price) from product)",
      "select product_id, sum(quantity) as Total_Quantity from orderline group by product_id order by product_id",
      "select product_id, sum(quantity) as Total_Quantity from orderline group by product_id order by sum(quantity)",
      "select orderline.product_id, product_name, sum(quantity) as Total_Quantity from orderline, product where orderline.product_id=product.product_id group by orderline.product_id, product_name order by orderline.product_id",
      "select distinct cust_name from customer, ordertable where customer.cust_id = ordertable.cust_id and ordertable.order_date > to_date('23-OCT-2008','dd-mon-yyyy') order by cust_name",
      "Select cust_name from customer where cust_id in (select cust_id from ordertable where order_date > to_date('23-OCT-2008','dd-mon-yyyy') ) order by cust_name",
      "select city, count(distinct customer.cust_id) from customer, ordertable where customer.cust_id=ordertable.cust_id group by city order by city",
      "select city, count(distinct cust_id) from customer natural join ordertable group by city order by city",
      "select city, count(cust_id) from customer where cust_id in (select cust_id from ordertable) group by city order by city",
      "Select product_id from product Minus Select product_id from orderline natural join ordertable where order_date > to_date('28-OCT-2008', 'dd-mon-yyyy') order by product_id",
      "Select cust_id from customer where state='AZ' Intersect Select cust_id from ordertable where order_date > to_date('28-OCT-2008','dd-mon-yyyy') Order by cust_id",
      "Select cust_id from customer where state='AZ' and cust_id in ( Select cust_id from ordertable where order_date > to_date('28-OCT-2008','dd-mon-yyyy') ) Order by cust_id",
      "Select cust_id from customer where state='CA' Union  Select cust_id from ordertable where order_date > to_date('28-OCT-2008','dd-mon-yyyy') Order by cust_id",
      "Select product_id, product_name, sum(quantity) as Total_Quantity from orderline natural join product group by product_id, product_name having sum(quantity)>10",
      "Select product.product_id, product_name, sum(quantity) as Total_Quantity from orderline, product, customer, ordertable where orderline.product_id=product.product_id and ordertable.order_id=orderline.order_id and ordertable.cust_id=customer.cust_id and state='UT' group by product.product_id, product_name having sum(quantity)>6"
    };
    
    String[] logicCriteriaItems = 
    { ">",
      " ",
      " ",
      " ",
      "=",
      " IN",
      "=",
      " NATURAL JOIN ",
      " IN",
      " MINUS ",
      " INTERSECT ",
      " IN",
      " UNION ",
      ">",
      "UT"
    };
    
    String[] querySubmissions = 
    { "SELECT product_id, product_name, product_price FROM product WHERE product_price > (SELECT AVG(product_price) FROM product)",
"SELECT product_id, SUM(quantity) AS total_quantity FROM orderline GROUP BY product_id ORDER BY product_id",
"SELECT product_id, SUM(quantity) AS total_quantity FROM orderline GROUP BY product_id ORDER BY SUM(quantity)",
"SELECT product_id, product_name, SUM(quantity) AS total_quantity FROM orderline, product WHERE orderline.product_id = product.product_id GROUP BY product_id, product_name ORDER BY product_id",
"SELECT DISTINCT cust_name FROM customer, ordertable WHERE customer.cust_id = ordertable.cust_id AND order_date > to_date('23-OCT-2008', 'dd-mon-yyyy') ORDER BY cust_name",
"SELECT cust_name FROM customer WHERE cust_id IN( SELECT cust_id FROM ordertable WHERE order_date > to_date('23-OCT-2008', 'dd-mon-yyyy')) ORDER BY cust_name",
"SELECT city, COUNT(DISTINCT customer.cust_id) FROM customer, ordertable WHERE customer.cust_id = ordertable.cust_id GROUP BY city ORDER BY city",
"SELECT city, COUNT(DISTINCT cust_id) FROM customer NATURAL JOIN ordertable GROUP BY city ORDER BY city",
"SELECT city, COUNT(DISTINCT cust_id) FROM customer WHERE cust_id IN (SELECT cust_id FROM ordertable) GROUP BY city ORDER BY city",
"SELECT product_id FROM product MINUS SELECT product_id FROM orderline NATURAL JOIN ordertable WHERE order_date > to_date('28-OCT-2008', 'dd-mon-yyyy') ORDER BY product_id",
"SELECT cust_id FROM customer WHERE state='AZ' INTERSECT SELECT cust_id FROM ordertable WHERE order_date > to_date('28-OCT-2008', 'dd-mon-yyyy') ORDER BY cust_id",
"SELECT cust_id FROM customer WHERE state='AZ' INTERSECT AND cust_id IN( SELECT cust_id FROM ordertable WHERE order_date > to_date('28-OCT-2008', 'dd-mon-yyyy')) ORDER BY cust_id",
"SELECT cust_id FROM customer WHERE state='CA' UNION SELECT cust_id FROM ordertable WHERE order_date > to_date('28-OCT-2008', 'dd-mon-yyyy') ORDER BY cust_id",
"SELECT product_id, product_name, SUM(quantity) AS total_quantity FROM orderline NATURAL JOIN product GROUP BY product_id, product_name HAVING SUM(quantity) > 10",
"SELECT product.product_id, product_name, SUM(quantity) AS total_quantity FROM orderline, product, customer, ordertable WHERE orderline.product_id = product.product_id AND ordertable.order_id = orderline.order_id AND ordertable.cust_id = customer.cust_id AND state='UT' GROUP BY product.product_id, product_name HAVING SUM(quantity) > 6"
      
    };
    
    // Capitalize all query strings from the student
    for( int i = 0; i < querySubmissions.length; i++ )
    { querySubmissions[i] = querySubmissions[i].toUpperCase();
    }
    
    String solutionString = "";
    String submittedString = "";
    
    for( int i = 0; i < querySolutions.length; i++ )
    { solutionString = querySolutions[ i ];
      submittedString = querySubmissions[ i ];
      evaluateHomeworkQuestion( uNID, i+1, submittedString, solutionString, connection, logicCriteriaItems );
    }
    
  }
  
  
  public static void evaluateHomeworkQuestion( String uNID,
                                               int questionNumber,
                                               String submittedQuery,
                                               String solutionQuery,
                                               Connection connection,
                                               String[] logicCriteriaItems 
                                             ) throws SQLException
  {
    String logicCriteria = logicCriteriaItems[ questionNumber - 1 ];
    
    float score = 0.666666f;
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
      resultSetsMatchScore = ( resultSetsMatch == "Y" ) ? 2.0f : 0;
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
      
      String finalQueryString = "MERGE INTO Homework4Results R " + 
  "USING ( SELECT '" + uNID + "' AS v_uNID," +
                 questionNumber + " AS v_QuestionNumber, '" +
                 queryExecutes + "' AS v_Executes, '" +
                 meetsLogicCriteria + "' AS v_MeetsLogicCriteria, '" +
                 resultSetsMatch + "' AS v_ResulsetsMatch, " +
                 score + " AS v_Score" +
          " FROM DUAL " +
       " ) S " +
  "ON ( R.uNID = S.v_uNID AND R.QuestionNumber = S.v_QuestionNumber ) " +
  "WHEN MATCHED THEN UPDATE " +
    "SET R.Executes = S.v_Executes, " +
        "R.MeetsLogicCriteria = S.v_MeetsLogicCriteria, " +
        "R.ResulsetsMatch = S.v_ResulsetsMatch, " +
        "R.Score = S.v_Score, " +
        "R.UpdatedTimestamp = CURRENT_TIMESTAMP(0) " +
  "WHEN NOT MATCHED THEN " +
    "INSERT ( uNID, " +
             "QuestionNumber, " +
             "Executes, " +
             "MeetsLogicCriteria, " +
             "ResulsetsMatch, " +
             "Score " +
           ") " +
    "VALUES ( S.v_uNID, " +
             "S.v_QuestionNumber, " +
             "S.v_Executes, " +
             "S.v_MeetsLogicCriteria, " +
             "S.v_ResulsetsMatch, " +
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
