package sheridan.Fedotov.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sheridan.Fedotov.db.DaoEvaluations;
import sheridan.jollymor.assigns.Evaluation;

/**
 * Servlet implementation class ListEvaluations
 */
@WebServlet("/ListEvaluations")
public class ListEvaluations extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListEvaluations() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Getting DAO object from application attributes
		
		ServletContext sc = getServletContext();
		DaoEvaluations dao = (DaoEvaluations)sc.getAttribute("DAO");
		
		try {
			
			//Use DAO to get all the evaluations from the dataBase
			
			ArrayList<Evaluation> evaList = dao.getEvaluations();
			
			//Create session object
			
			HttpSession session = request.getSession();
			
			//clear all session attributes written by user on iindex.jsp
			
			session.removeAttribute("courseCode");
			session.removeAttribute("evaluationName");
			session.removeAttribute("year");
			session.removeAttribute("month");
			session.removeAttribute("day");
			session.removeAttribute("submit");
			
			//set session attribute to store evaluations from the database
			
			session.setAttribute("evals", evaList);
			
			//redirect to listEvals.jsp
			
			response.sendRedirect("listEvals.jsp");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
