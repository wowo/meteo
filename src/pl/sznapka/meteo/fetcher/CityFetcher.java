package pl.sznapka.meteo.fetcher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.sznapka.meteo.valueobject.City;
import pl.sznapka.meteo.valueobject.State;

public class CityFetcher implements IFetcher {
	
	protected State state;
	protected URL url;
	
	public static final String CITIES_REGEX = "show_mgram\\(([0-9]+)\\)'>([^<]+)<";
	
	public CityFetcher(State state) throws MalformedURLException {
		
		this.state = state;
		this.url = new URL("http://new.meteo.pl/um/php/gpp/next.php");
	}
	
	public State getState() {
		return state;
	}
	
	@Override
	public ArrayList<City> fetch() throws Exception {
		
		return parseCities(getContent());

	}
	
	protected String getContent() throws Exception {
		
		try {
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
			writer.write("litera=&woj=" + state.symbol);
			writer.flush();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "ISO8859_2"));
			String line;
			String content = "";
			while ((line = reader.readLine()) != null) {
				content += line;
			}
			writer.close();
			reader.close();
			return content;
		} catch (java.lang.Exception ex) {
			throw new Exception("An exception occured while getting content from URL");
		}	
	}
	
	protected ArrayList<City> parseCities(String content) {
		
		Matcher matcher = Pattern.compile(CITIES_REGEX).matcher(content);
		ArrayList<City> cities = new ArrayList<City>();
		while (matcher.find()) {
			cities.add(new City(Integer.parseInt(matcher.group(1)), matcher.group(2)));
		}
		return cities;
	}
}