package pl.sznapka.meteo.valueobject;

import java.io.Serializable;

public class State implements Serializable {

	private static final long serialVersionUID = -3885313250040639137L;
	public String name;
	public String symbol;
	
	public State(String name, String symbol) {
		this.name = name;
		this.symbol = symbol;
	}
}
