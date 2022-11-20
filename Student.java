import java.sql.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
class Student extends JFrame implements ActionListener {
    JButton btnAdd, btnBrowse;
    JTextArea tasid, tasname;
    JTextField tfStudId, tfStudName;
    Connection con;
    public Student() {
        btnAdd = new JButton("Add");
        btnBrowse = new JButton("Browse");
        tasid = new JTextArea(20, 20);
        tasname = new JTextArea(20, 20);
        tfStudId = new JTextField(10);
        tfStudName = new JTextField(10);
        setTitle("Student Information");
        setSize(500, 400);
        setVisible(true);
        getContentPane().add(tfStudId);
        getContentPane().add(tfStudName);
        getContentPane().add(tasid);
        getContentPane().add(tasname);
        getContentPane().add(btnAdd);
        getContentPane().add(btnBrowse);
        getContentPane().setLayout(new FlowLayout());
        btnAdd.addActionListener(this);
        btnBrowse.addActionListener(this);
        addWindowListener(new WindowAdapter(){
             public void windowOpened(WindowEvent e){
                 tfStudId.requestFocus();
            }
        });
    }
    public static void main(String args[]) {
        new Student();	
    }
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == btnAdd) {
            try {
	personalConnection();
	Statement st = con.createStatement();
	String sid = tfStudId.getText();
	String sname = tfStudName.getText();
	st.executeUpdate("insert into student values('"+sid+"','"+sname+"')");
                 tfStudId.setText("");
                 tfStudName.setText("");
	st.close();
	con.close();
            }catch(Exception e) { }
        }
        if(ae.getSource() == btnBrowse) {
            try {
	personalConnection();
	Statement st = con.createStatement();
	ResultSet rs = st.executeQuery("select * from student");
	tasid.setText("");
	tasname.setText("");
	while(rs.next()){
	    tasid.append(rs.getString("id") + "\n");
	    tasname.append(rs.getString("name") + "\n");
	}
	st.close();
	con.close();
            }catch(Exception e2) { }
        }
    }
    public void personalConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "tiger");
            System.out.println("Connected " + con);
        }catch(Exception e) { }
    }
}
