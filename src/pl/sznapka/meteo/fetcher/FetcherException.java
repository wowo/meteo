package pl.sznapka.meteo.fetcher;

public class FetcherException extends java.lang.Exception {

	private static final long serialVersionUID = -4770056067167700216L;

	public FetcherException (String message) {
		super(message);
	}
}