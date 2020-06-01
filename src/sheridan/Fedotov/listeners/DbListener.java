package sheridan.Fedotov.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import sheridan.Fedotov.db.DaoEvaluations;

/**
 * Application Lifecycle Listener implementation class DbListener
 *
 */
@WebListener
public class DbListener implements ServletContextListener, HttpSessionListener {

    /**
     * Default constructor. 
     */
    public DbListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
    	
         sce.getServletContext().removeAttribute("DAO");
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
    
         String dbUrl = sce.getServletContext().getInitParameter("dbUrl").toString();
         String dbName = sce.getServletContext().getInitParameter("dbName").toString();
         String user = sce.getServletContext().getInitParameter("user").toString();
         String password = sce.getServletContext().getInitParameter("password").toString();
         DaoEvaluations dao = new DaoEvaluations(dbUrl, dbName, user, password);
         sce.getServletContext().setAttribute("DAO", dao);
         dao = null;
    }
    
	
}
