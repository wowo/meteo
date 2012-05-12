package pl.sznapka.meteo.fetcher;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.sznapka.meteo.valueobject.City;

public class ForecastFetcher implements IFetcher {
	
	protected City city;
	protected HttpClient client;
	
	public ForecastFetcher(City city, HttpClient client) {
		
		this.city = city;
		this.client = client;
	}
	
	@Override
	public List fetch() throws FetcherException {
		
		try {
			String content = client.makeGetRequest(new URL("http://new.meteo.pl/um/php/meteorogram_id_um.php?ntype=0u&id=" + city.id));
			String date = getMeteogramParam(content, "fcstdate");
			String url = "http://new.meteo.pl/um/metco/mgram_pict.php?ntype=0u&lang=pl&fdate=" + date;
			url += "&row=" + getMeteogramParam(content, "act_y") + "&col="  + getMeteogramParam(content, "act_x");
			System.out.println("Meteogram url: " + url);

			ArrayList<String> result= new ArrayList();
			result.add(System.getProperty("java.io.tmpdir") + "/meteo-" + city.id + "-" + date + ".png");
			client.downloadFile(new URL(url), result.get(0));

			return result;
		} catch (MalformedURLException e) {
			throw new FetcherException("Malformed url");
		} catch (IOException e) {
			throw new FetcherException("IOException: " + e.getMessage());
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