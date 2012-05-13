package pl.sznapka.meteo;

import java.util.ArrayList;
import java.util.HashMap;

import pl.sznapka.image.Processor;
import pl.sznapka.meteo.fetcher.ForecastFetcher;
import pl.sznapka.meteo.fetcher.HttpClient;
import pl.sznapka.meteo.valueobject.City;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("Start");

		ForecastFetcher fetcher = new ForecastFetcher(new City(797, "Orzesze"), new HttpClient());
		@SuppressWarnings("unchecked")
		ArrayList<String> result = (ArrayList<String>)fetcher.fetch();
		String path = result.get(0);
		System.out.println("Fetched img: " + path);

		Processor processor = new Processor();
		HashMap<String, String> diagrams = processor.extractDiagrams(path, path.substring(0, path.length() - 4) + "-");
		for (String key : diagrams.keySet()) {
			System.out.println(key + ":\t" + diagrams.get(key));
		}
		System.out.println("End");
	}
}