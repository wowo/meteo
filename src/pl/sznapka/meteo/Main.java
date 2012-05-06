package pl.sznapka.meteo;

import java.util.ArrayList;

import pl.sznapka.meteo.fetcher.CityFetcher;
import pl.sznapka.meteo.fetcher.ForecastFetcher;
import pl.sznapka.meteo.fetcher.HttpClient;
import pl.sznapka.meteo.valueobject.City;
import pl.sznapka.meteo.valueobject.State;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("Start ąśęłóżźćń");
		ForecastFetcher fetcher = new ForecastFetcher(new City(797, "Orzesze"), new HttpClient());
		fetcher.fetch();
		System.out.println("End");
	}
}