package myapp;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class parseCar {

	boolean inParkingModels = false;
	boolean inCar = false;
	boolean inId = false;
	boolean inName = false;
	boolean inNumb = false;
	boolean inArea = false;

	Car currentCar;

	List<Car> currentCarList;

	public List<Car> doParseBooks(String s) throws Exception {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		XmlPullParser pullParser = factory.newPullParser();
		pullParser.setInput(new StringReader(s));
		processDocument(pullParser);
		return currentCarList;
	}

	public void processDocument(XmlPullParser pullParser) throws XmlPullParserException, IOException {
		int eventType = pullParser.getEventType();
		do {
			if (eventType == XmlPullParser.START_DOCUMENT) {
				// System.out.println("Start Document");
			} else if (eventType == XmlPullParser.END_DOCUMENT) {
				// System.out.println("End Document");
			} else if (eventType == XmlPullParser.START_TAG) {
				processStartElement(pullParser);
			} else if (eventType == XmlPullParser.END_TAG) {
				processEndElement(pullParser);
			} else if (eventType == XmlPullParser.TEXT) {
				processText(pullParser);
			}
			eventType = pullParser.next();
		} while (eventType != XmlPullParser.END_DOCUMENT);
	}

	public void processStartElement(XmlPullParser event) {
		String name = event.getName();
		if (name.equals("parkingModels")) {
			inParkingModels = true;
			currentCarList = new ArrayList<Car>();
		} else if (name.equals("Car")) {
			inCar = true;
			currentCar = new Car();
		} else if (name.equals("id")) {
			inId = true;
		} else if (name.equals("name")) {
			inName = true;
		} else if (name.equals("numb")) {
			inNumb = true;
		} else if (name.equals("area")) {
			inArea = true;
		}
	}

	public void processEndElement(XmlPullParser event) {
		String name = event.getName();
		if (name.equals("parkingModels")) {
			inParkingModels = false;
		} else if (name.equals("Car")) {
			inCar = false;
			currentCarList.add(currentCar);
		} else if (name.equals("id")) {
			inId = false;
		} else if (name.equals("name")) {
			inName = false;
		} else if (name.equals("numb")) {
			inNumb = false;
		} else if (name.equals("area")) {
			inArea = false;
		}
	}

	public void processText(XmlPullParser event) throws XmlPullParserException {
		if (inId) {
			String s = event.getText();
			currentCar.setId(Integer.parseInt(s));
			// System.out.println(""+Integer.parseInt(s));
		}
		if (inName) {
			String s = event.getText();
			currentCar.setName(s);

			// System.out.println(s);
		}
		if (inNumb) {
			String s = event.getText();
			currentCar.setNumb(s);
			// System.out.println(s);

		}
		if (inArea) {
			String s = event.getText();
			currentCar.setArea(Integer.parseInt(s));
			// System.out.println(""+Integer.parseInt(s));

		}
	}
}
