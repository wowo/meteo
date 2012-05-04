import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import pl.sznapka.meteo.fetcher.StateFetcher;
import pl.sznapka.meteo.valueobject.State;



public class StateFetcherTest {

	@Test
	public void testFetch() {
		StateFetcher fetcher = new StateFetcher();
		ArrayList<State> states = fetcher.fetch();
		assertNotNull(states);
		assertEquals(16, states.size());
	}
}