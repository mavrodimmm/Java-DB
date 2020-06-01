package sheridan.Fedotov.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sheridan.Fedotov.db.DaoEvaluations;
import sheridan.jollymor.assigns.Course;
import sheridan.jollymor.assigns.Evaluation;

/**
 * Servlet implementation class AddEvaluation
 */
@WebServlet("/AddEvaluation")
public class AddEvaluation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddEvaluation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Create a session object
		
		HttpSession session = request.getSession();
		
		//remove all the session attributes
		
		session.removeAttribute("courseCode");
		session.removeAttribute("evaluationName");
		session.removeAttribute("year");
		session.removeAttribute("month");
		session.removeAttribute("day");
		session.removeAttribute("submit");
		
		// Getting DAO object from application attributes
		
		ServletContext sc = getServletContext();
		DaoEvaluations dao = (DaoEvaluations)sc.getAttribute("DAO");
		
		//getting parameters from the index.jsp
		
		String courseCode = request.getParameter("course");
		String name = request.getParameter("evaluationName");
		String year1 = request.getParameter("year");
		int month = Integer.parseInt(request.getParameter("month"));
		int day = Integer.parseInt(request.getParameter("day"));
		String submit = request.getParameter("submit");
		
		//error string will store all the errors
		
		String error = "";
		
		//validating all the data inputs by user
		
		if (submit == null || submit.isEmpty())
			
			error += "<p>Select submitted";
		
		if (name == null || name.isEmpty())
			
			error += "<p>Evaluuation name can not be empty,</p>";
		
		if (year1 == null || year1.isEmpty())
			
			error += "<p>Year can not be empty.</p>";
		
		else if (!isNumeric(year1))
			
			error += "<p>Year should be a valid number.</p>";
		
		sc.setAttribute("errorMessage", error);
		
		//if there are no errors proceed to converting user inputs
		
		if (error == null || error.isEmpty()) {
			
			int year = Integer.parseInt(year1);
			LocalDate date = LocalDate.of(year, month, day);
			boolean submitted = Boolean.parseBoolean(submit);
			Course course;
			
			//Using DAO to create new record to the table
			
			try {
				
				course = dao.getCourse(courseCode);
				Evaluation eva = new Evaluation(course, name, date, submitted);
				dao.setEvaluation(eva, date, submitted);
				
				//clearing all the session attributes 
				
				session.removeAttribute("courseCode");
				session.removeAttribute("evaluationName");
				session.removeAttribute("year");
				session.removeAttribute("month");
				session.removeAttribute("day");
				session.removeAttribute("submit");
				
				//redirect to List Evaluations Servlet
				
				response.sendRedirect("ListEvaluations");
				
			} catch (SQLException e) {
				
				throw new ServletException(e.getMessage());
				
			}
			
		}
		
		//if there are some errors captured
		
		else {
			
			//set sessionAttrubutes to user inputs
			
			session.setAttribute("courseCode", courseCode);
			session.setAttribute("evaluationName", name);
			session.setAttribute("year", year1);
			session.setAttribute("month", month);
			session.setAttribute("day", day);
			session.setAttribute("submit", submit);
			
			//use error page mechanism
			
			throw new ServletException(error);
			
		}
			
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public static boolean isNumeric(String str) {
		
		  try {
			  
		    Integer.parseInt(str);  
		    return true;
		    
		  } catch(NumberFormatException e){
			  
		    return false;  
		  }  
		  
	}

}
