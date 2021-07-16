package myapp;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParserException;

import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;

public class GUI {

	private DefaultTableModel model;
	Connection con;

	private JFrame frmValetParkingSystem;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JButton btnPark, btnDeliver, btnUpdate;
	private JButton btnCreateDB;
	private JButton btnNewButton;
	private JTable table;
	private JButton btnExit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmValetParkingSystem.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();

				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws Exception
	 */
	public GUI() throws Exception {
		initialize();
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/OneDB", "SA", "");

		} catch (Exception e) {
			e.printStackTrace();
		}

//	fillData();	
	}

	public void clearallfield() {

		textField.setText("");
		textField_1.setText("");
		textField_2.setText("");
		textField_3.setText("");

	}

	public void postThreeCar() throws Exception {
		String[] name = { "Albert" };
		String[] carnumb = { "IRE-2020" };
		String[] area = { "1" };

		int count = 0;

		try {
			URI uri = new URIBuilder().setScheme("http").setHost("localhost").setPort(8080)
					.setPath("/A00268737_DeepanshDubey/myapp/ParkingService/carthree").build();
			System.out.println(uri.toString());

			HttpPost httpPost = new HttpPost(uri);
			httpPost.setHeader("Accept", "application/xml");
			CloseableHttpClient client = HttpClients.createDefault();

			for (int i = 0; i < name.length; i++) {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("name", name[i]));
				nameValuePairs.add(new BasicNameValuePair("carnumber", carnumb[i]));
				nameValuePairs.add(new BasicNameValuePair("area", area[i]));

				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				System.out.println("Sending POST request...");
				JOptionPane.showMessageDialog(null, "Data Filled into table sucessfully , Please tap Show Button ",
						"Information", JOptionPane.INFORMATION_MESSAGE);
				CloseableHttpResponse response = client.execute(httpPost);
				String result = EntityUtils.toString(response.getEntity());
				if (result.equals("Data inserted  Successfully")) {

					if (++count == name.length) {
						filltables(false);
						JOptionPane.showMessageDialog(null, result, "Information", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}

		} catch (URISyntaxException | IOException ex) {
			ex.printStackTrace();
		}
	}

	private void filltables(boolean exportresponse) throws Exception {

		ArrayList<Car> cartabledata = new ArrayList<Car>();
		try {
			URI uri = new URIBuilder().setScheme("http").setHost("localhost").setPort(8080)
					.setPath("/A00268737_DeepanshDubey/myapp/ParkingService").build();

			HttpGet httpReq = new HttpGet(uri);
			httpReq.setHeader("Accept", "application/xml");

			CloseableHttpResponse httpResponse = HttpClients.createDefault().execute(httpReq);

			String result = EntityUtils.toString(httpResponse.getEntity());

			parseCar parseBook = new parseCar();
			model = (DefaultTableModel) table.getModel();
			model.setRowCount(0);
			cartabledata = (ArrayList<Car>) parseBook.doParseBooks(result);

			if (cartabledata.isEmpty()) {
			} else {
				for (Car car : cartabledata) {

					model.addRow(new Object[] { car.getId(), car.getName(), car.getNumb(), car.getArea() });

				}

				if (exportresponse) {

				}
			}
		} catch (URISyntaxException | IOException | XmlPullParserException ex) {
			ex.printStackTrace();
		}

	}

	public TableModel ShowData() {

		DefaultTableModel model1 = new DefaultTableModel(new String[] { "Id", "Name", "Car Number", "Area" }, 0);
		// String sql1 = "SELECT * FROM storemanagement.member";
		Statement ps;
		try {

			ps = con.createStatement();
			ResultSet rs1 = ps.executeQuery("SELECT * FROM car");

			while (rs1.next()) {
				String Id = rs1.getString(1);
				String Name = rs1.getString(2);
				String numb = rs1.getString(3);
				String area = rs1.getString(4);

				model1.addRow(new Object[] { Id, Name, numb, area });
			}

			table.setModel(model1);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return model1;

		// table.setModel(model);

	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws Exception
	 */
	private void initialize() throws Exception {
		frmValetParkingSystem = new JFrame();
		frmValetParkingSystem.getContentPane().setBackground(new Color(102, 205, 170));
		frmValetParkingSystem.setTitle(" Parking System");
		frmValetParkingSystem.setBounds(100, 100, 1047, 858);
		frmValetParkingSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmValetParkingSystem.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("  ID");
		lblNewLabel.setIcon(new ImageIcon(
				"C:\\Users\\Deepansh\\eclipse-workspace\\ProjectServer\\default package\\billnumber.png"));
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 17));
		lblNewLabel.setBounds(217, 95, 135, 32);
		frmValetParkingSystem.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setIcon(new ImageIcon(
				"C:\\Users\\Deepansh\\eclipse-workspace\\ProjectServer\\default package\\membericon.png"));
		lblNewLabel_1.setFont(new Font("Arial Black", Font.BOLD, 17));
		lblNewLabel_1.setBounds(217, 138, 162, 64);
		frmValetParkingSystem.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Car Number");
		lblNewLabel_2.setIcon(
				new ImageIcon("C:\\Users\\Deepansh\\eclipse-workspace\\ProjectServer\\default package\\info.png"));
		lblNewLabel_2.setFont(new Font("Arial Black", Font.BOLD, 17));
		lblNewLabel_2.setBounds(206, 213, 175, 38);
		frmValetParkingSystem.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Area Parked");
		lblNewLabel_3.setIcon(new ImageIcon(
				"C:\\Users\\Deepansh\\eclipse-workspace\\ProjectServer\\default package\\expirydate.png"));
		lblNewLabel_3.setFont(new Font("Arial Black", Font.BOLD, 17));
		lblNewLabel_3.setBounds(206, 273, 177, 38);
		frmValetParkingSystem.getContentPane().add(lblNewLabel_3);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setToolTipText("gfhvb");
		textField.setBounds(414, 101, 170, 25);
		frmValetParkingSystem.getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_1.setBounds(414, 160, 170, 28);
		frmValetParkingSystem.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_2.setBounds(414, 216, 170, 25);
		frmValetParkingSystem.getContentPane().add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_3.setBounds(414, 275, 170, 25);
		frmValetParkingSystem.getContentPane().add(textField_3);
		textField_3.setColumns(10);

		btnPark = new JButton("PARK/INSERT");
		btnPark.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textField_1.getText().equals("") || textField_2.getText().equals("")
						|| textField_3.getText().equals("")) {
					clearallfield();
					JOptionPane.showMessageDialog(null,
							"Name,car number and area Field is Missng , please fill all the details", "Warning",
							JOptionPane.WARNING_MESSAGE);

				} else {
					InsertClient client = new InsertClient();
					client.insert(textField_1.getText(), textField_2.getText(), textField_3.getText());
					// textArea.setText("");
					// fillData();
					// filltables(exportresponse);
					ShowData();
				}
			}
		});
		btnPark.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnPark.setBounds(48, 352, 141, 32);
		frmValetParkingSystem.getContentPane().add(btnPark);

		btnDeliver = new JButton("DELETE ALL");
		btnDeliver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					DeleteItem deleteItem = new DeleteItem();
					deleteItem.deleteCarinfo(textField.getText());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					// textArea.setText("");
					// fillData();
					ShowData();
				}
			}
		});
		btnDeliver.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDeliver.setBounds(653, 352, 129, 32);
		frmValetParkingSystem.getContentPane().add(btnDeliver);

		btnUpdate = new JButton("UPDATE BY ID");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().equals("")) {

					clearallfield();

					JOptionPane.showMessageDialog(null, "Please fill the ID field , You can update all  with ID ",
							"Warning", JOptionPane.WARNING_MESSAGE);

				} else {
					UpdateItem item = new UpdateItem();
					item.updateCarData(textField.getText(), textField_1.getText(), textField_2.getText(),
							textField_3.getText());
					JOptionPane.showMessageDialog(null, "Member Updated sucessfully", "Information",
							JOptionPane.INFORMATION_MESSAGE);
					// textArea.setText("");
					// fillData();
					ShowData();
				}
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnUpdate.setBounds(246, 352, 149, 32);
		frmValetParkingSystem.getContentPane().add(btnUpdate);

		btnCreateDB = new JButton("Create DB");
		btnCreateDB.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCreateDB.setBounds(455, 372, 129, 25);

		JLabel lblNewLabel_4 = new JLabel("CAR PARKING SYSTEM");
		lblNewLabel_4.setForeground(new Color(255, 140, 0));
		lblNewLabel_4.setFont(new Font("Arial Black", Font.BOLD, 30));
		lblNewLabel_4.setBounds(252, 5, 498, 38);
		frmValetParkingSystem.getContentPane().add(lblNewLabel_4);

		JButton btnNewButton_1 = new JButton("CREATE TABLE");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				CreateDB cd = new CreateDB();
				cd.getConnection();

			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_1.setBounds(734, 427, 175, 38);
		frmValetParkingSystem.getContentPane().add(btnNewButton_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(88, 427, 600, 264);
		frmValetParkingSystem.getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setBackground(Color.yellow);

		JButton btnCl = new JButton("CLEAR");
		btnCl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
				textField_3.setText("");

			}
		});

		btnCl.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCl.setBounds(834, 352, 129, 32);
		frmValetParkingSystem.getContentPane().add(btnCl);

		btnExit = new JButton("EXIT");
		btnExit.setIcon(
				new ImageIcon("C:\\Users\\Deepansh\\eclipse-workspace\\ProjectServer\\default package\\cancel.png"));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnExit.setBounds(834, 11, 135, 38);
		frmValetParkingSystem.getContentPane().add(btnExit);

		JButton btnNewButton_1_1 = new JButton("EXPORT ");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					int n = ExportCar.export();
					if (n == 1) {
						JOptionPane.showMessageDialog(null, "Data exported to the  csv file", "Information",
								JOptionPane.INFORMATION_MESSAGE);
						// JOptionPane.showInputDialog(this, "Data exported to the csv file");
					} else {
						JOptionPane.showMessageDialog(null, "Error in exporting the csv file", "Warning",
								JOptionPane.WARNING_MESSAGE);
						// JOptionPane.showInputDialog(this, "Error in exporting the csv file");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_1_1.setBounds(734, 653, 175, 38);
		frmValetParkingSystem.getContentPane().add(btnNewButton_1_1);

		JButton btnDeleteById = new JButton("DELETE BY ID");
		btnDeleteById.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (textField.getText().equals("")) {
					clearallfield();

					JOptionPane.showMessageDialog(null,
							"Please fill the ID field, You can delete the particular member with ID", "Warning",
							JOptionPane.WARNING_MESSAGE);
				}

				else {

					try {
						DeleteItem item = new DeleteItem();
						item.deletebyID(textField.getText());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						// textArea.setText("");
						// fillData();
						ShowData();
					}

				}
			}
		});
		btnDeleteById.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDeleteById.setBounds(448, 352, 149, 32);
		frmValetParkingSystem.getContentPane().add(btnDeleteById);

		JButton btnNewButton_1_2 = new JButton("FILL TABLE");
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					postThreeCar();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// fillData();

			}
		});
		btnNewButton_1_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_1_2.setBounds(734, 503, 175, 38);
		frmValetParkingSystem.getContentPane().add(btnNewButton_1_2);

		JButton btnNewButton_1_3 = new JButton("SHOW ");
		btnNewButton_1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ShowData();

			}
		});
		btnNewButton_1_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_1_3.setBounds(734, 584, 175, 38);
		frmValetParkingSystem.getContentPane().add(btnNewButton_1_3);

	}
}
