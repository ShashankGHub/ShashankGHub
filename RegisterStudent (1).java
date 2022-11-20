import java.io.*;  
import java.sql.*;  
import javax.servlet.ServletException;  
import javax.servlet.http.*;  
  
public class RegisterStudent extends HttpServlet {  
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException    {  
        response.setContentType("text/html");  
        PrintWriter out = response.getWriter();  
          
        String i=request.getParameter("studId");  
        String n=request.getParameter("studName");  
        String e=request.getParameter("studEmail");  
        String c=request.getParameter("studNumber");
        String s=request.getParameter("studState");  
          
        try{  
            try{
                Class.forName("oracle.jdbc.OracleDriver");  
            }catch(ClassNotFoundException ex1){
                ex1.printStackTrace();    
            }
            conn=(Connection)DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","sit");  
            stmt = conn.createStatement();            
            pstmt=((java.sql.Connection)conn).prepareStatement("insert into registerstud values(?,?,?,?,?)");  
            pstmt.setString(1,i);  
            pstmt.setString(2,n);  
            pstmt.setString(3,e);  
            pstmt.setString(4,c);  
            pstmt.setString(5,s);  
            int j=pstmt.executeUpdate();  
            if(j>0){  
                out.println("<h1>You are successfully registered...<h1>");  
                rs = stmt.executeQuery("select * from registerstud");     
                String id, name, email, contact, state;
                id=name=email=contact=state="";
                while(rs.next()){
                    id = rs.getString("sid");
                    name = rs.getString("sname");
                    email = rs.getString("semail");
                    contact = rs.getString("scontact");
                    state = rs.getString("sstate");
                }
                   out.println("<h1> Student Id:"+id+"<br>");
                   out.println("<h1>Student Name: "+name+"<br>");
                   out.println("<h1> Student Email:"+email+"<br>");
                   out.println("<h1> Student Contact Number:"+contact+"<br>");
                   out.println("<h1>Student State: "+state+"<br>");
            }
            out.close();  
            rs.close();
            stmt.close();
            pstmt.close();
            conn.close();
        }catch (Exception ex2) {
            ex2.printStackTrace();
        }         
    }    
}  