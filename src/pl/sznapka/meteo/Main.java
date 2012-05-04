package pl.sznapka.meteo;

import java.util.ArrayList;

import pl.sznapka.meteo.fetcher.CityFetcher;
import pl.sznapka.meteo.valueobject.City;
import pl.sznapka.meteo.valueobject.State;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("Hello ąśęłóżźćń");
		CityFetcher fetcher = new CityFetcher(new State("śląskie", "SL"));
		ArrayList<City> cities = fetcher.fetch();
		for (City city : cities) {
			System.out.println("City: " + city.name + " id: " + city.id);
		}

	}

}