package pl.sznapka.meteo.fetcher;

import java.util.List;

public interface IFetcher {
	@SuppressWarnings("rawtypes")
	public List fetch() throws Exception;
}