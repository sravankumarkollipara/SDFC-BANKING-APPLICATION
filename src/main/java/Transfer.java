

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
 * Servlet implementation class Transfer
 */
@WebServlet("/Transfer")
public class Transfer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Transfer() {
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
		
		int transfer_amount=Integer.parseInt(request.getParameter("tamount"));
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			 Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","ghdb","ghdb");
			 PreparedStatement ps=con.prepareStatement("select amount from bankacc where accountnumber=? and name=? and password=?");
			 ps.setString(1, accountnumber);
			 ps.setString(2, name);
			 ps.setString(3, password);
			 ResultSet rs=ps.executeQuery();
			 double balance=0.00, amount=0;
			 if(rs.next())
			 {
				 balance=rs.getDouble(1);
			 }
			 amount=balance-transfer_amount;
			 
			 PreparedStatement ps2=con.prepareStatement("update bankacc set amount=? where accountnumber=? and name=? and password=?");
			 ps2.setDouble(1, amount);
			 ps2.setString(2, accountnumber);
			 ps2.setString(3, name);
			 ps2.setString(4, password);
			 int i=ps2.executeUpdate();
			 out.print(i+"updatedfirst <br>");
			 out.print("my accountno="+accountnumber+"<br>");
			 String target_account=request.getParameter("targetaccno");
			 
			 PreparedStatement ps3=con.prepareStatement("select amount from bankacc where accountnumber=?");
			 ps3.setString(1, target_account);
			 ResultSet rs2=ps3.executeQuery();
			 double target_oldbalance=0.00,targetamount=0;
			 if(rs2.next())
			 {
				 target_oldbalance=rs2.getDouble(1);
			 }
			 
			 targetamount=target_oldbalance+transfer_amount;
			 
		
			 PreparedStatement ps4=con.prepareStatement("update bankacc set amount=? where accountnumber=?");
			 ps4.setDouble(1, targetamount);
			 ps4.setString(2, target_account);
			 int j=ps4.executeUpdate();
			 out.print(j+"transaction successfully");
			
			 
			 out.print("transfer account="+target_account+"<br>");
			 out.print("targetacc balance="+target_oldbalance+"<br>");
			 out.print("transfer amount="+transfer_amount+"<br>");
			 
			 out.print("the accountbalance="+targetamount+"<br>");
			 
			 
			
			 
			 con.close();
		}
		catch(Exception ex)
		{
			out.print(ex);
		}
			 
		
		
	}

}
