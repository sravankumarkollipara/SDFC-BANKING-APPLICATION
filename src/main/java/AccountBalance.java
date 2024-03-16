

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AccountBalance
 */
@WebServlet("/AccountBalance")
public class AccountBalance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountBalance() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @param accountbalance 
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
			 PreparedStatement ps=con.prepareStatement("select amount from bankacc where accountnumber=? and password=? and name=?");
			 ps.setString(1, accountnumber); 
			 ps.setString(2, password);
			 ps.setString(3, name);
			 ResultSet rs=ps.executeQuery();
			 out.print("my account number="+accountnumber+"<br>");
			 out.print("account holdername="+name+"<br>");
			
			 if(rs.next())
			 {
				 out.print("my accountbalance="+rs.getInt(1));
			 }
			 else
			 {
				 out.print("check your details");
			 }
			 
			 
			 con.close();
		}
		catch(Exception ex)
		{
			out.print(ex);
		}
		
		
	}

}
