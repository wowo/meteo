package pl.sznapka.meteo.fetcher;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.sznapka.meteo.http.HttpClient;
import pl.sznapka.meteo.http.HttpClientException;
import pl.sznapka.meteo.valueobject.City;
import pl.sznapka.meteo.valueobject.Forecast;

public class ForecastFetcher implements IFetcher {
	
	protected City city;
	protected HttpClient client;
	
	public ForecastFetcher(City city, HttpClient client) {
		
		this.city = city;
		this.client = client;
	}
	
	@Override
	public ArrayList<Forecast> fetch() throws FetcherException {

		try {
			URL url = getMeteogramUrl();
			String path = System.getProperty("java.io.tmpdir") + "/meteo-" + city.id + ".png";
			client.downloadFile(url, path);

			ArrayList<Forecast> result = new ArrayList<Forecast>();
			result.add(new Forecast(url.toString(), city, path));
			
			return result;
		} catch (HttpClientException e) {
			throw new FetcherException("HTTP problem occured: " + e.getMessage());
		}
	}

	protected URL getMeteogramUrl() throws FetcherException {

		try {
			String content = client.makeGetRequest(new URL("http://new.meteo.pl/um/php/meteorogram_id_um.php?ntype=0u&id=" + city.id));
			String date = getMeteogramParam(content, "fcstdate");
			String url = "http://new.meteo.pl/um/metco/mgram_pict.php?ntype=0u&lang=pl&fdate=" + date;
			url += "&row=" + getMeteogramParam(content, "act_y") + "&col="  + getMeteogramParam(content, "act_x");
			System.out.println("Meteogram url: " + url);
			
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new FetcherException("Malformed url:" + e.getMessage());
		} catch (HttpClientException e) {
			throw new FetcherException("HTTP problem occured: " + e.getMessage());
		}
	}
	
	protected String getMeteogramParam(String content, String name) {
		
		Matcher matcher = Pattern.compile(name + " = \"?([^\";]+)\"?;").matcher(content);
		while (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}
}