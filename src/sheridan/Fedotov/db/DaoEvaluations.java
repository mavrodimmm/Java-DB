package sheridan.Fedotov.db;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;



import sheridan.jollymor.assigns.Course;
import sheridan.jollymor.assigns.Evaluation;


public class DaoEvaluations {
	private String dbUrl;
	private String dbName;
	private String user;
	private String pass;
	
	public DaoEvaluations() {
		dbUrl="jdbc:mysql://localhost:3306/";;
		dbName= "tracker";
		user = "guest";
		pass = "980829Nf";
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		} catch(ClassNotFoundException ex) {
			
			ex.printStackTrace();
		}
	}
	
	public DaoEvaluations(String dbUrl, String dbName, String user, String pass){
		this.dbUrl = dbUrl;
		this.dbName = dbName;
		this.user = user;
		this.pass = pass;
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		} catch(ClassNotFoundException ex) {
			
			ex.printStackTrace();
			
		}
	}
	
	private Connection getConnection() throws SQLException{
		Connection dbConn = DriverManager.getConnection(dbUrl + dbName, user, pass);
		return dbConn;
	}
	
	
	//get course by course code
	public Course getCourse(String courseCode) throws SQLException{
		Connection dbConn = getConnection();
		
		PreparedStatement stmt = 
				dbConn.prepareStatement("Select * from courses where code like ?");
		
		stmt.setString(1, courseCode);
		ResultSet records = stmt.executeQuery();
		Course course = new Course();
		while(records.next()) {
			
			course.setCode(records.getString("code"));
			course.setTitle(records.getString("title"));
			
		}
		dbConn.close();
		return course;
	}
	
	
	//add evaluation record to the table
	public int setEvaluation(Evaluation e, LocalDate localDate, boolean submit) throws SQLException{
		Connection dbConn = getConnection();
		
		PreparedStatement stmt = 
				dbConn.prepareStatement(
						"Insert into evaluations (course_code, eval_name, due_date, submitted)" 
				+ "values (?, ?, ?, ?)");
		
		stmt.setString(1, e.getCourse().getCode());
		stmt.setString(2, e.getEvalName());
		stmt.setDate(3, Date.valueOf(localDate));
		stmt.setBoolean(4, submit);
		int c = stmt.executeUpdate();
		dbConn.close();
		return c;
	}
	
	
	//get all the evaluations sorted by date
	public ArrayList<Evaluation> getEvaluations() throws SQLException{
		Connection dbConn = getConnection();
		Statement stmt = dbConn.createStatement();
		
		ResultSet records = stmt.executeQuery("Select evaluations.*, courses.title "
				+ "from evaluations INNER JOIN courses ON evaluations.course_code = courses.code "
				+ "order by due_date");
		
		ArrayList<Evaluation> evaList = new ArrayList<Evaluation>();
		
		while (records.next()) {
			
			Course course = getCourse(records.getString("course_code"));
			Evaluation eva = new Evaluation (records.getInt("id"), course, 
					records.getString("eval_name"), records.getDate("due_date").toLocalDate(),
					records.getBoolean("submitted"));
			
			evaList.add(eva);
		}
		dbConn.close();
		return evaList;
	}
	
	
	
}

