package myapp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class UpdateItem {

	public void updateCarData(String id, String name, String numb, String area) {

		try {
			URI uri = new URIBuilder().setScheme("http").setHost("localhost").setPort(8080)
					.setPath("/A00268737_DeepanshDubey/myapp/ParkingService/" + id + "").build();

			HttpPut httpPut = new HttpPut(uri);
			httpPut.setHeader("Accept", "text/html");
			CloseableHttpClient client = HttpClients.createDefault();
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("name", name));
			nameValuePairs.add(new BasicNameValuePair("numb", numb));
			nameValuePairs.add(new BasicNameValuePair("area", area));
			httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			System.out.println("sending update  response");
//			JOptionPane.showMessageDialog(null, "Member Updated sucessfully", "Information",
//					JOptionPane.INFORMATION_MESSAGE);
		//	JOptionPane.showInputDialog(this, "Member Updated");
			
			CloseableHttpResponse response = client.execute(httpPut);
			System.out.println("Updated Successfully...");
		} catch (URISyntaxException | IOException ex) {
			ex.printStackTrace();
		}
	}
}
