package pl.sznapka.meteo.http;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class HttpClient {
	
	public String makePostRequest(URL url, String postData) throws HttpClientException {
		
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
			throw new HttpClientException("An exception occured while getting content from URL: " + ex.getMessage());
		}	
	}
	
	public String makeGetRequest(URL url) throws HttpClientException {
		
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
			throw new HttpClientException("An exception occured while getting content from URL: " + ex.getMessage());
		}		
	}
	
	public void downloadFile(URL url, String outputPath) throws HttpClientException {
		
		try {
	        URLConnection connection = url.openConnection();
	        connection.connect();
	        int fileLength = connection.getContentLength();
	        System.out.println("Downloading file to output path: " + outputPath + " content length: " + fileLength);
	        InputStream input   = new BufferedInputStream(url.openStream());
	        OutputStream output = new FileOutputStream(outputPath);
	        
	        byte data[] = new byte[1024];
	        long total = 0;
	        int count;
	        while ((count = input.read(data)) != -1) {
                total += count;
                output.write(data, 0, count);
	        }
	        output.flush();
	        output.close();
	        input.close();
		} catch (IOException e) {
			throw new HttpClientException("An IO exception occured while downloading file: " + e.getMessage());
		}
	}
}
