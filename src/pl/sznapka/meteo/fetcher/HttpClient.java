package pl.sznapka.meteo.fetcher;

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
		
        File file = new File(outputPath);
        
        /* Open a connection to that URL. */
        URLConnection ucon = url.openConnection();

        /*
         * Define InputStreams to read from the URLConnection.
         */
        java.io.InputStream is = ucon.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);

        /*
         * Read bytes to the Buffer until there is nothing more to read(-1).
         */
        ByteArrayBuffer baf = new ByteArrayBuffer(50);
        int current = 0;
        while ((current = bis.read()) != -1) {
                baf.append((byte) current);
        }

        /* Convert the Bytes read to a String. */
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(baf.toByteArray());
        fos.close();
	}
}
