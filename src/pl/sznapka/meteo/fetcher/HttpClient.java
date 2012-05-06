package pl.sznapka.meteo.fetcher;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;

import pl.sznapka.meteo.valueobject.State;

public class HttpClient {
	
	public String makePostRequest(URL url, String postData) throws FetcherException {
		
		try {
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
			writer.write(postData);
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
			throw new FetcherException("An exception occured while getting content from URL: " + ex.getMessage());
		}	
	}
	
	public String makeGetRequest(URL url) throws FetcherException {
		
		try {
		    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			String content = "";
			while ((line = reader.readLine()) != null) {
				content += line;
			}
			reader.close();
			return content;
		} catch (java.lang.Exception ex) {
			throw new FetcherException("An exception occured while getting content from URL: " + ex.getMessage());
		}		
	}
	
	public void downloadFile(URL url, String outputPath) throws IOException {
		
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		FileOutputStream fos = new FileOutputStream(outputPath);
		fos.getChannel().transferFrom(rbc, 0, 1 << 24);
	}
}
