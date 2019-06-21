package edu.ksu.www;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

//import com.sun.jndi.toolkit.ctx.StringHeadTail;

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//Define datasource/connection pool for Resource Injection
	@Resource(name ="jdbc/web_student_tracker")
	private DataSource dataSource;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Step 1: set up the printwriter
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		
		//Step 2: Get a connection to database
		Connection myConn = null;
		Statement mystmt = null;
		ResultSet myRs =null; 
		
		try{
			myConn = dataSource.getConnection();
			//Step 3: Create a SQL statements
			String sql = "select * from student";
			mystmt = myConn.createStatement();
			//Step 4: Execute SQL query
			myRs = mystmt.executeQuery(sql);
			//step 5: Process the result set
			while (myRs.next()) {
				String email = myRs.getString("email");
				out.println(email);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
