package edu.ksu.www;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private StudentDbUtil studentDbUtil;
    
    @Resource(name = "jdbc/web_student_tracker")
    private DataSource dataSource;
    
    
    
	@Override
	public void init() throws ServletException {
		//called by Tomcat when the serve firstly initialized;
		super.init();	
		//create studentDbUtil and pass in the datasource
		try {
			studentDbUtil = new StudentDbUtil(dataSource);
		} catch (Exception e) {
			throw new ServletException(e);
		}	
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//read the command parameter
			String theCommand = request.getParameter("command");
			if(theCommand == null){
				theCommand ="List";
			}
			//route to the appropriate method
			switch (theCommand) {
			case "List":
				listStudents(request,response);
				break;
			case "ADD":
				addStudnt(request,response);
				break;
			case "LOAD":
				loadStudent(request,response);
				break;
			case "UPDATE":
				updateStudent(request,response);
				break;
			case "DELETE":
				deleteStudent(request,response);
				break;
			case "SEARCH":
				searchStudent(request,response);
				break;
			default:
				listStudents(request,response);
				break;
			}

			
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}

	private void searchStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//read student name from form data	
		String studentName = request.getParameter("SearchName");
		//find student in the database
		List<Student> students = studentDbUtil.searchStudent(studentName);
		//display the student
		request.setAttribute("STUDENTS_LIST", students);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list_students.jsp");
		dispatcher.forward(request, response);
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//read student info from form data
		String thestudentId = request.getParameter("studentId");
		// delete student to database
		studentDbUtil.deleteStudent(thestudentId);
		// send back to main page
		listStudents(request, response);
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//read student info from form data
		int studentId = Integer.parseInt(request.getParameter("stdeuntId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		// create student object
		Student theStudent = new Student(studentId, firstName, lastName, email);
		// add student to database
		studentDbUtil.updateStudent(theStudent);
		// send back to main page
		listStudents(request, response);
		
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//read student id from form data
		String theStudentId = request.getParameter("studentId");
		// get student from database
		Student theStudent = studentDbUtil.getStudent(theStudentId);
		//place student in the request attribute
		request.setAttribute("THE_STUDENT", theStudent);
		//send to jsp Page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
	}

	private void addStudnt(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//read student info from form data
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		// create student object
		Student theStudent = new Student(firstName, lastName, email);
		// add student to database
		studentDbUtil.addStudent(theStudent);
		// send back to main page
		listStudents(request, response);
		
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response)  throws Exception{
		// get students from studentDbUtil
		List<Student> students = studentDbUtil.getStudents();
		// add students to the request
		request.setAttribute("STUDENTS_LIST", students);
		
		// send to JSP page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list_students.jsp");
		dispatcher.forward(request, response);
		
	}

	

}
