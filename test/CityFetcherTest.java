import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.junit.Test;

import pl.sznapka.meteo.fetcher.CityFetcher;
import pl.sznapka.meteo.fetcher.HttpClient;
import pl.sznapka.meteo.valueobject.City;
import pl.sznapka.meteo.valueobject.State;


public class CityFetcherTest {

	@Test
	public void testCityFetcher() {
		try {
			CityFetcher fetcher = new CityFetcher(new State("śląskie", "SL"), new HttpClient());
		} catch (MalformedURLException e) {
			fail(e.getClass().toString());
		}
	}

	@Test
	public void testFetch() throws MalformedURLException {
		try {
			CityFetcher fetcher = new CityFetcher(new State("śląskie", "SL"), new HttpClient());
			ArrayList<City> cities = fetcher.fetch();
			assertTrue(cities.size() > 0);
			
			City orzesze = new City(797, "Orzesze, pow. mikołowski");
			boolean hasOrzesze = false;
			for (City city : cities) {
				if (city.id == orzesze.id && city.name.equals(orzesze.name)) {
					hasOrzesze = true;
					break;
				}
			}
			assertTrue(hasOrzesze);
		} catch (MalformedURLException e) {
			fail(e.getClass().toString());
		} catch (Exception e) {
			fail(e.getClass().toString());
		}
	}

}
