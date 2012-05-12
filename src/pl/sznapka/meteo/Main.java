package pl.sznapka.meteo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pl.sznapka.image.Processor;
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
		System.out.println("Start");
		Processor processor = new Processor();
		ArrayList<File> result = processor.extractDiagrams("/tmp/meteo-797-2012051206.png", System.getProperty("java.io.tmpdir") + "/meteo-extracted-");
		for (File file : result) {
			System.out.println("Result: " + file);
		}
		/*
		ForecastFetcher fetcher = new ForecastFetcher(new City(797, "Orzesze"), new HttpClient());
		List result = fetcher.fetch();
		System.out.println("Fetched img: " + result.get(0));
		*/
		System.out.println("End");
	}
}