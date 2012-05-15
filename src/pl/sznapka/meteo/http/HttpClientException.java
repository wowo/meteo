package pl.sznapka.meteo.http;

public class HttpClientException extends java.lang.Exception {

	private static final long serialVersionUID = -9222865420385247062L;

	public HttpClientException (String message) {
		super(message);
	}
}
