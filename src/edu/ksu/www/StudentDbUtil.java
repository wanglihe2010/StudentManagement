package edu.ksu.www;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


public class StudentDbUtil {
	private DataSource dataSource;
	public StudentDbUtil(DataSource theDataSource){
		dataSource = theDataSource;
	}
	
	public List<Student> getStudents() throws Exception{
		List<Student> students = new ArrayList<>();
		
		Connection myConn = null;
		Statement mystmt = null;
		ResultSet myRs =null; 
		try {
			// get a connection
			myConn = dataSource.getConnection();
			
			//create sql statement
			String sql = "select * from student order by last_name";
			mystmt = myConn.createStatement();
			//execute the query
			myRs = mystmt.executeQuery(sql);
			//process result set
			while (myRs.next()) {
				//retrieve data from result set row
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				//create student object
				Student tempStudent= new Student(id, firstName, lastName, email);
				//add it to list of students
				students.add(tempStudent);
			}
			
			return students;
		} finally {
			//close the jdbc object
			close(myConn,mystmt,myRs);
		}
		
		
		
		
		
	}
	
	public void addStudent(Student theStudent) throws Exception{
		
		Connection myConn = null;
		PreparedStatement mystmt = null;
		try {
			// get a connection
			myConn = dataSource.getConnection();
			
			//create sql statement
			String sql = "insert into student"+"(first_name,last_name,email)" + "values(?,?,?)";
			mystmt = myConn.prepareStatement(sql);
			mystmt.setString(1, theStudent.getFirstName());
			mystmt.setString(2, theStudent.getLastName());
			mystmt.setString(3, theStudent.getEmail());
			//execute the query
			mystmt.execute();
			
			
		} finally {
			//close the jdbc object
			close(myConn,mystmt,null);
		}
	}

	private void close(Connection myConn, Statement mystmt, ResultSet myRs) {
		try {
			if (myConn != null) {
				myConn.close();//return to connection pool
			}
			if (mystmt != null) {
				mystmt.close();
			}
			if (myRs != null) {
				myRs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public Student getStudent(String theStudentId) throws Exception{
		Student student;
		Connection myConn = null;
		PreparedStatement mystmt = null;
		ResultSet myRs =null; 
		int studentId;
		try {
			studentId = Integer.parseInt(theStudentId);
			// get a connection
			myConn = dataSource.getConnection();
			
			//create sql statement
			String sql = "select * from student where id=?";
			mystmt = myConn.prepareStatement(sql);
			mystmt.setInt(1, studentId);
			//execute the query	
			myRs = mystmt.executeQuery();
			//process result set
			if (myRs.next()) {
				//retrieve data from result set row
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				//create student object
				student= new Student(studentId, firstName, lastName, email);
				
			}else {
				throw new Exception("Could not find student id:" + studentId);
			}
			
			return student;
		}finally{
			//close the jdbc object
			close(myConn,mystmt,null);
		}

	}

	public void updateStudent(Student theStudent) throws Exception {
		Connection myConn = null;
		PreparedStatement mystmt = null;
		try {
			// get a connection
			myConn = dataSource.getConnection();
			
			//create sql statement
			String sql = "update  student "
					+ "set first_name = ?,last_name=?,email=? "
					+ "where id=?";
			mystmt = myConn.prepareStatement(sql);
			mystmt.setString(1, theStudent.getFirstName());
			mystmt.setString(2, theStudent.getLastName());
			mystmt.setString(3, theStudent.getEmail());
			mystmt.setInt(4, theStudent.getId());
			//execute the query	
			mystmt.execute();
		}finally{
			//close the jdbc object
			close(myConn,mystmt,null);
		}
		
	}

	public void deleteStudent(String studentId) throws Exception{
		Connection myConn = null;
		PreparedStatement mystmt = null;
		try {
			// get a connection
			myConn = dataSource.getConnection();
			
			//create sql statement
			String sql = "delete from student "
					+ "where id=?";
			mystmt = myConn.prepareStatement(sql);
			mystmt.setInt(1, Integer.parseInt(studentId));
			//execute the query	
			mystmt.execute();
		}finally{
			//close the jdbc object
			close(myConn,mystmt,null);
		}
		
	}

	public List<Student> searchStudent(String studentName) throws Exception {
		List<Student> students = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rSet = null;
		try {
			// get the connection
			conn = dataSource.getConnection();
			// set the sql
			if (studentName != null && studentName.length() > 0) {
				String sql = "select * from student where lower(first_name) like ? or lower(last_name) like ?";
				stmt = conn.prepareStatement(sql);
				String theSearchNameLike ="%" + studentName.toLowerCase() + "%";
				stmt.setString(1, theSearchNameLike);
				stmt.setString(2, theSearchNameLike);
			} else {
				String sql = "select * from student ";
				stmt = conn.prepareStatement(sql);
			}
			
			//excute the sql
			rSet = stmt.executeQuery();
			//get the result
			while (rSet.next()) {
				Student tempStudent = new Student(rSet.getString("first_name"), 
						rSet.getString("last_name"), rSet.getString("email"));
				students.add(tempStudent);
			}
			return students;
			
		} finally{
			//close the jdbc object
			close(conn, stmt, rSet);
		}
		
	}
}	
