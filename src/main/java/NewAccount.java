

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
 * Servlet implementation class NewAccount
 */
@WebServlet("/NewAccount")
public class NewAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewAccount() {
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
		String confirm_password=request.getParameter("cpsw");		
		String amount=request.getParameter("amount");
		String address=request.getParameter("addr");
		String mobileno=request.getParameter("no");
		String status="active";
		
		 try
		 {
			 Class.forName("oracle.jdbc.driver.OracleDriver");
			 Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","ghdb","ghdb");
			 PreparedStatement ps=con.prepareStatement("insert into bankacc values(?,?,?,?,?,?,?)");
			 ps.setString(1, accountnumber);
			 ps.setString(2, name);
			 ps.setString(3, confirm_password);
			 
			 ps.setString(4, amount);
			 ps.setString(5, address);
			 ps.setString(6, mobileno);
			 ps.setString(7, status);
			 int i=ps.executeUpdate();
			 out.print("new account created successfully"+i);
			 con.close();
		 }
		 catch(Exception ex)
		 {
			 out.print(ex);
		 }

	}

}
