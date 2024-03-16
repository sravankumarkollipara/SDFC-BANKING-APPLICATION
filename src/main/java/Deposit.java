

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
 * Servlet implementation class Deposit
 */
@WebServlet("/Deposit")
public class Deposit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Deposit() {
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
		int deposit_amount=Integer.parseInt(request.getParameter("depositamount"));
		String status="active";
		
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			 Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","ghdb","ghdb");
			 PreparedStatement ps=con.prepareStatement("select amount from bankacc where accountnumber=? and name=? and password=?");
			 ps.setString(1, accountnumber);
			 ps.setString(2, name);
			 ps.setString(3, password);
			
			 ResultSet rs=ps.executeQuery();
			 int oldbalance=0,amount=0;
			 if(rs.next())
			 {
				oldbalance=rs.getInt(1);
			 }
			 out.print("my accountnumber="+accountnumber+"<br>");
			 out.print("my balance="+oldbalance+"<br>");
			 out.print("deposit amount="+deposit_amount+"<br>");
			 if(status=="active")
			 {
			 amount=deposit_amount+oldbalance;
			 }
			 else
			 {
				 out.print("account is not active");
			 }
			 PreparedStatement ps2=con.prepareStatement("update bankacc set amount=? where accountnumber=? and name=? and password=?");
			 ps2.setInt(1, amount);
			 ps2.setString(2, accountnumber);
			 ps2.setString(3, name);
			 ps2.setString(4, password);
			 int i=ps2.executeUpdate();
			 out.print(i+"updated <br>");
			 out.print("after deposit the account balance="+amount+i);
			 con.close();
		}
		
		catch(Exception ex)
		{
			out.print(ex);
		}
			 
		
		
	}

}
