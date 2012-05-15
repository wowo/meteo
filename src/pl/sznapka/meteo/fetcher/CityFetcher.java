package pl.sznapka.meteo.fetcher;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.sznapka.meteo.http.HttpClient;
import pl.sznapka.meteo.http.HttpClientException;
import pl.sznapka.meteo.valueobject.City;
import pl.sznapka.meteo.valueobject.State;

public class CityFetcher implements IFetcher {
	
	protected State state;
	protected HttpClient client;
	
	public CityFetcher(State state, HttpClient client) {
		
		this.state = state;
		this.client = client;
	}
	
	@Override
	public ArrayList<City> fetch() throws FetcherException {
		
		try {
			return parseCities(client.makePostRequest(new URL("http://new.meteo.pl/um/php/gpp/next.php"), "litera=&woj=" + state.symbol));
		} catch (MalformedURLException e) {
			throw new FetcherException("Malformed url");
		} catch (HttpClientException e) {
			throw new FetcherException("HTTP problem occured: " + e.getMessage());
		}
	}
	
	protected ArrayList<City> parseCities(String content) {
		
		Matcher matcher = Pattern.compile("show_mgram\\(([0-9]+)\\)'>([^<]+)<").matcher(content);
		ArrayList<City> cities = new ArrayList<City>();
		while (matcher.find()) {
			cities.add(new City(Integer.parseInt(matcher.group(1)), matcher.group(2)));
		}
		return cities;
	}
}