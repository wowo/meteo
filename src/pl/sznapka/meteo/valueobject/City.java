package pl.sznapka.meteo.valueobject;

import java.io.Serializable;

public class City implements Serializable{

	private static final long serialVersionUID = 6753507669186921935L;
	public int id;
	public String name;
	public State state;
	
	public City(int id, String name, State state) {
		
		this.id = id;
		this.name = name;
		this.state = state;
	}
}
