

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CloseAcc
 */
@WebServlet("/CloseAcc")
public class CloseAcc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CloseAcc() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		String accountnumber=request.getParameter("accno");
		String name=request.getParameter("name");
		String password=request.getParameter("psw");
		String status="active";
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			 Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","ghdb","ghdb");
			 PreparedStatement ps=con.prepareStatement("update bankacc set status=? where accountnumber=? and name=? and password=?");
			 ps.setString(1, status);
			 ps.setString(2, accountnumber);
			 ps.setString(3, name);
			 ps.setString(4, password);
			 int i=ps.executeUpdate();
			 out.print(i+"updated"+"<br>");
			 out.print("account status "+status);
			 con.close();
		}
		catch(Exception ex)
		{
			out.print(ex);
		}
		
	
			 
	}

}
