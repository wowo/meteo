package pl.sznapka.http.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import org.apache.http.util.ByteArrayBuffer;

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
	        java.io.InputStream is = url.openConnection().getInputStream();
	        BufferedInputStream bis = new BufferedInputStream(is);
	        ByteArrayBuffer baf = new ByteArrayBuffer(50);
	        int current = 0;
	        while ((current = bis.read()) != -1) {
	                baf.append((byte) current);
	        }	
	        FileOutputStream fos = new FileOutputStream(new File(outputPath));
	        fos.write(baf.toByteArray());
	        fos.close();
		} catch (IOException e) {
			throw new HttpClientException("An IO exception occured while downloading file: " + e.getMessage());
		}
	}
}
