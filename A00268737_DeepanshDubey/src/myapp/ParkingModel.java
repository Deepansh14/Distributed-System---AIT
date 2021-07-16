package myapp;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "car")
@XmlType(propOrder = { "id", "name", "numb", "area" })
public class ParkingModel {

	int id, area;
	String name, numb;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int price) {
		this.area = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumb() {
		return numb;
	}

	public void setNumb(String description) {
		this.numb = description;
	}

}
