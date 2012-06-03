package pl.sznapka.meteo.valueobject;

import java.io.Serializable;

public class City implements Serializable{

	private static final long serialVersionUID = 6753507669186921935L;
	public int id;
	public String name;
	
	public City(int id, String name) {
		
		this.id = id;
		this.name = name;
	}
}
