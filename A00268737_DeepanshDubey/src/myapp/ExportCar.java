package myapp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExportCar extends GUI {
	Connection con;

	public ExportCar() throws Exception {
		super();

	}

	public static int export() throws ClassNotFoundException, SQLException, IOException {
		Class.forName("org.hsqldb.jdbcDriver");
		Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/OneDB", "SA", "");

		PreparedStatement st ;
		ResultSet rs ;

		String sql = "SELECT * FROM car";
		st = con.prepareStatement(sql);
		rs = st.executeQuery();

		ArrayList id = new ArrayList();
		ArrayList name = new ArrayList();
		ArrayList numb = new ArrayList();
		ArrayList area = new ArrayList();

		while (rs.next()) {

			int a = rs.getInt("id");
			String d = rs.getString("name");
			String e = rs.getString("numb");
			int f = rs.getInt("area");

			id.add(a);
			name.add(d);
			numb.add(e);
			area.add(f);

		}

		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("Cars.csv")));
		for (int i = 0; i < id.size(); i++) {

			writer.write(" ID: " + id.get(i));
			writer.newLine();
			writer.write(" Name: " + name.get(i));
			writer.newLine();
			writer.write(" Car Number  : " + numb.get(i));
			writer.newLine();

			writer.write("Area Parked " + area.get(i));
			writer.newLine();
			writer.newLine();
		}
		writer.close();

		return 1;

	}

}
























