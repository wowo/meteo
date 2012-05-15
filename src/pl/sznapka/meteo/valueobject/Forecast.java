package pl.sznapka.meteo.valueobject;

public class Forecast {
	public String url;
	public City city;
	public String path;
	
	public static final String TEMPERATURE = "temperature";
	public static final String RAIN        = "rain";
	public static final String PRESSURE    = "pressure";
	public static final String WIND        = "wind";
	public static final String VISIBILITY  = "visibility";
	public static final String CLOUDS      = "clouds";
	
	public Forecast(String url, City city, String path) {
		
		this.url = url;
		this.city = city;
		this.path = path;
	}
}