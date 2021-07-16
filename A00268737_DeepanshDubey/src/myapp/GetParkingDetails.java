package myapp;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class GetParkingDetails {

	public static StringBuilder Details() throws Exception {
	

		CloseableHttpResponse response = null;
		try {
			URI uri = new URIBuilder()
					.setScheme("http")
					.setHost("localhost")
					.setPort(8080)
					.setPath("/A00268737_DeepanshDubey/myapp/ParkingService/items").build();
			
			System.out.println(uri.toString());
			
			HttpGet httpGet = new HttpGet(uri);
			httpGet.setHeader("Accept", "application/xml");
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			response = httpClient.execute(httpGet);
			
			HttpEntity entity = response.getEntity();
			String text = EntityUtils.toString(entity);
			System.out.println(text);
			
			List<Car> bookList = new parseCar().doParseBooks(text);
			System.out.println("-----------------------------");
			StringBuilder s = new StringBuilder();
			for(Car book : bookList) {
				s.append("ID:"+book.getId()+"   Name:"+book.getName()+"   Car-Number:"+book.getNumb()+"   Area-parked:"+book.getArea()+"\n");
			}
			return s;
		} 
	
		finally {
			try {
				response.close();
			} catch (IOException e) {
			
				e.printStackTrace();
			}
		}
	}

}
