package myapp;

import java.sql.*;

import javax.swing.JOptionPane;

public class CreateDB {
	private Connection con = null;

	public Connection getConnection() {

		try {
			if (con == null) {
				Class.forName("org.hsqldb.jdbcDriver");
				con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/OneDB", "SA", "");
				Statement stmt = con.createStatement();
				stmt.executeUpdate(
						"CREATE TABLE IF NOT EXISTS CAR (id INTEGER identity,name VARCHAR(32),numb VARCHAR(32),area INTEGER)");
				
				stmt.close();
				JOptionPane.showMessageDialog(null, "Table Created sucessfully", "Information",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error in Creating the Table", "Warning",
					JOptionPane.WARNING_MESSAGE);
			
		}

		return con;
	}
}
