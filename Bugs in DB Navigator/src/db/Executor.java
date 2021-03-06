package db;

//java SQL
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
//java utilities
import java.util.ArrayList;
import java.util.Date;

//STEP 1. Import required packages
import java.sql.*;

public class Executor 
{
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/eclipse_platform_ui";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "";
   

   
   public static void main(String[] args) 
   {
	   Connection conn = null;
	   Statement stmt = null;
	   BugList buglist = new BugList();
	   
	   try 
	   {
		System.out.println("Number of bugs:"+buglist.rows_number("bug_and_files"));
	} catch (ApplicationException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	   
   
   try
   {
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);

      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      String sql;
      // ---- table bug_and_files
      sql = "SELECT * FROM bug_and_files";
      ResultSet rs = stmt.executeQuery(sql);

      //STEP 5: Extract data from result set
   
      Bug temp ;
      System.out.print(">>Query to retrieve bugs being executed... ");
      while(rs.next())
      {
    	  temp = new Bug();
          if (rs.getObject("bug_id") != null) temp.bug_id = new Integer(rs.getInt("bug_id"));
          else temp.bug_id = -1;
          if (rs.getString("summary") != null) temp.summary = new String(rs.getString("summary"));
          else temp.summary = new String("N/A");
          if (rs.getString("description") != null) temp.description = new String(rs.getString("description"));
          else temp.description = new String("N/A");
          if (rs.getDate("report_time") != null) temp.report_time = rs.getDate("report_time");
          else temp.report_time = new java.sql.Date(0);
          if (rs.getString("report_time") != null) temp.report_time_2 = rs.getString("report_time");
          else temp.report_time_2 = new String("N/A");
          if (rs.getObject("report_timestamp") != null) temp.report_timestamp = new Double(rs.getDouble("report_timestamp"));
          else temp.report_timestamp = new Double(0);
          if (rs.getObject("commit_timestamp") != null) temp.commit_timestamp = new Double(rs.getDouble("commit_timestamp"));
          else temp.commit_timestamp = new Double(0);
          if (rs.getString("files") != null) temp.files = new String(rs.getString("files"));
          else temp.files = new String("N/A");
          // Adding to ArrayList
          buglist.bugs.add(temp);
      }
      System.out.println("done");
   // ---- table bug_and_files
      sql = "SELECT * FROM file_history";
      rs = stmt.executeQuery(sql);

      //STEP 5: Extract data from result set
   
      BugFile tempFile ;
      System.out.print(">>Query to retrieve files being executed... ");
      while(rs.next())
      {
    	  tempFile = new BugFile();
          if (rs.getObject("doc_id") != null) tempFile.doc_id = new Integer(rs.getInt("doc_id"));
          else tempFile.doc_id = -1;
          if (rs.getString("file_name") != null) tempFile.file_name = new String(rs.getString("file_name"));
          else tempFile.file_name = new String("N/A");
          if (rs.getString("bug_id") != null) tempFile.bug_id = new String(rs.getString("bug_id"));
          else tempFile.bug_id = new String("N/A");
          // Adding to ArrayList     
          buglist.files.add(tempFile);
      }
      System.out.println("done");   
     	
      
      //STEP 6: Clean-up environment
      rs.close();
      stmt.close();
      conn.close();
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }// nothing we can do
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
   
   buglist.assignBugsToFiles();
   buglist.printData();
   buglist.export_files_data();
   
   
   
   System.out.println("End Of Main!");
}//end main
}//end FirstExample
