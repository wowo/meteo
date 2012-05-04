import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.junit.Test;

import pl.sznapka.meteo.fetcher.CityFetcher;
import pl.sznapka.meteo.valueobject.City;
import pl.sznapka.meteo.valueobject.State;


public class CityFetcherTest {

	@Test
	public void testCityFetcher() {
		try {
			CityFetcher fetcher = new CityFetcher(new State("śląskie", "SL"));
			assertEquals("SL", fetcher.getState().symbol);
		} catch (MalformedURLException e) {
			fail(e.getClass().toString());
		}
	}

	@Test
	public void testFetch() throws MalformedURLException {
		try {
			CityFetcher fetcher = new CityFetcher(new State("śląskie", "SL"));
			ArrayList<City> cities = fetcher.fetch();
			assertTrue(cities.size() > 0);
		} catch (MalformedURLException e) {
			fail(e.getClass().toString());
		} catch (Exception e) {
			fail(e.getClass().toString());
		}
	}

}
