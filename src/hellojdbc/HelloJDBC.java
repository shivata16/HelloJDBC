package hellojdbc;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.Properties;

public class HelloJDBC {
	
	final private static String MySQLJDBCDriver = "org.gjt.mm.mysql.Driver";
	final private static String DBNAME = "hellojdbc"; // Database Name
	final private static String USER = "root"; // user name for DB.
	final private static String PASS = ""; // password for DB.
	final private static String JDBCDriver = MySQLJDBCDriver;
	final private static String DBURL = "jdbc:mysql://localhost/" + DBNAME; 
	
	private static Statement stmt = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws SQLException {
		
		// Connection
		try {
			connect( USER, PASS );

			ResultSet rs = execute( "select * from commodity" );
			DecimalFormat dformat = new DecimalFormat ("#,###円");
			
			while(rs.next()){
				int t = rs.getInt("type");
		        	String n = rs.getString("name");
		        	int p = rs.getInt("price");
		        	System.out.println(t + " " + n + " " + dformat.format( p ) );
				}

				rs.close();
				stmt.close();
				disconnect();
			}
		catch (Exception e) {
			// Error Message and Error Code
			System.out.print(e.toString());
			if (e instanceof SQLException) {
				System.out.println("Error Code:" + ((SQLException)e).getErrorCode());
			}
			// Print Stack Trace
			e.printStackTrace();
			if (conn != null) {
				conn.rollback();
				conn.close();
			}
		}	
	}
	
	/*
	 *  Service Functions
	 *  ここから先は，決まり文句を関数化したもの．
	 */
	
	static Connection conn = null;
	//
	// database open
	//
	public static void connect( String user, String pass ) 
	throws SQLException, ClassNotFoundException {
		try {
			// JDBC Driver Loading
			Class.forName(JDBCDriver).newInstance();
			System.setProperty("jdbc.driver",JDBCDriver);
		}
		catch (Exception e) {
			// Error Message and Error Code
			System.out.print(e.toString());
			if (e instanceof SQLException) {
				System.out.println("Error Code:" + ((SQLException)e).getErrorCode());
			}
			// Print Stack Trace
			e.printStackTrace();
		}
		try {
			// Connection
			if ( user.isEmpty() && pass.isEmpty() ) {
				conn = DriverManager.getConnection(DBURL);			
			}
			else {
				Properties prop = new Properties();
				prop.put("user", user);
				prop.put("password", pass);
				conn = DriverManager.getConnection(DBURL,prop);	
			}
		}
		catch (Exception e) {
			// Error Message and Error Code
			System.out.print(e.toString());
			if (e instanceof SQLException) {
				System.out.println("Error Code:" + ((SQLException)e).getErrorCode());
			}
			// Print Stack Trace
			e.printStackTrace();
			if (conn != null) {
				conn.rollback();
				conn.close();
			}
		}
	}
	//
	// database close
	//
	public static void disconnect()
	    throws SQLException {
		try {
			conn.close();
		}
		catch (Exception e) {
			// Error Message and Error Code
			System.out.print(e.toString());
			if (e instanceof SQLException) {
				System.out.println("Error Code:" + ((SQLException)e).getErrorCode());
			}
			// Print Stack Trace
			e.printStackTrace();
			if (conn != null) {
				conn.rollback();
				conn.close();
			}
		}
	}
	//
	// execute
	//
	public static ResultSet execute( String sql )
	    throws SQLException {
		try {	
			conn.setReadOnly(true);
			// Execute 'commit' Automatically after each SQL
			// conn.setAutoCommit(true);		 
			// Query Exection 		 
			stmt = conn.createStatement();
			return stmt.executeQuery(sql);
		}
		catch (Exception e) {
			// Error Message and Error Code
			System.out.print(e.toString());
			if (e instanceof SQLException) {
				System.out.println("Error Code:" + ((SQLException)e).getErrorCode());
			}
			// Print Stack Trace
			e.printStackTrace();
			if (conn != null) {
				conn.rollback();
				conn.close();
			}
			return null;
		}
	}
}
