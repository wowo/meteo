package pl.sznapka.meteo;

import java.util.ArrayList;
import java.util.HashMap;

import pl.sznapka.meteo.fetcher.ForecastFetcher;
import pl.sznapka.meteo.http.HttpClient;
import pl.sznapka.meteo.image.Processor;
import pl.sznapka.meteo.valueobject.City;
import pl.sznapka.meteo.valueobject.Forecast;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("Start");

		ForecastFetcher fetcher = new ForecastFetcher(new City(797, "Orzesze"), new HttpClient());
		ArrayList<Forecast> result = fetcher.fetch();
		Forecast current = result.get(0);
		System.out.println("Fetched img: " + current.path);

		Processor processor = new Processor();
		HashMap<String, String> diagrams = processor.extractDiagrams(current);
		for (String key : diagrams.keySet()) {
			System.out.println(key + ":\t" + diagrams.get(key));
		}
		System.out.println("End");
	}
}