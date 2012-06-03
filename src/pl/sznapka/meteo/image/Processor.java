/**
 * 
 */
package pl.sznapka.meteo.image;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.sznapka.meteo.valueobject.Forecast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;

/**
 * Meteograms processor
 *
 */
public class Processor {
	
	public static final int DIAGRAM_WIDTH = 484;
	public static final int DIAGRAM_HEIGHT = 84;
	public static final int DIAGRAM_X = 28;
	
	public static final int TEMPERATURE_Y = 53;	
	public static final int RAIN_Y = 139;	
	public static final int PRESSURE_Y = 224;	
	public static final int WIND_Y = 310;	
	public static final int VISIBILITY_Y = 432;	
	public static final int CLOUDS_Y = 519;

	public static final int LEGEND_X = 62;
	public static final int LEGEND_Y = 29;
	public static final int LEGEND_WIDTH = 415;
	public static final int LEGEND_HEIGHT = 24;

	
	/**
	 * Extracts chosen diagrams from meteogram fetched from new.meteo.pl/um
	 * For every diagram it adds the legend with time (in CEST)
	 * 
	 * @param forecast
	 * @param types
	 * @return HashMap where keys are types of diagrams and values are files where diagrams are located
	 * @throws ImageProcessingException
	 */
	public HashMap<String, String> extractDiagrams(Forecast forecast, List<String> types) throws ImageProcessingException {
		
		try {
			System.out.println("Extracting diagrams for city: " + forecast.city.name
					+ " path: " + forecast.path);
			Bitmap img = BitmapFactory.decodeFile(forecast.path);
			HashMap<String, Bitmap> diagrams = new HashMap<String, Bitmap>();
			if (types.contains(Forecast.TEMPERATURE)) {
				diagrams.put(Forecast.TEMPERATURE, Bitmap.createBitmap(img, DIAGRAM_X, TEMPERATURE_Y, DIAGRAM_WIDTH, DIAGRAM_HEIGHT));
			}
			if (types.contains(Forecast.RAIN)) {
				diagrams.put(Forecast.RAIN, Bitmap.createBitmap(img, DIAGRAM_X, RAIN_Y, 		  DIAGRAM_WIDTH, DIAGRAM_HEIGHT));
			}
			if (types.contains(Forecast.PRESSURE)) {
				diagrams.put(Forecast.PRESSURE, Bitmap.createBitmap(img, DIAGRAM_X, PRESSURE_Y, 	  DIAGRAM_WIDTH, DIAGRAM_HEIGHT));
			}
			if (types.contains(Forecast.WIND)) {
				diagrams.put(Forecast.WIND, Bitmap.createBitmap(img, DIAGRAM_X, WIND_Y,        DIAGRAM_WIDTH, DIAGRAM_HEIGHT));
			}
			if (types.contains(Forecast.VISIBILITY)) {
				diagrams.put(Forecast.VISIBILITY, Bitmap.createBitmap(img, DIAGRAM_X, VISIBILITY_Y,  DIAGRAM_WIDTH, DIAGRAM_HEIGHT));
			}
			if (types.contains(Forecast.CLOUDS)) {
				diagrams.put(Forecast.CLOUDS, Bitmap.createBitmap(img, DIAGRAM_X, CLOUDS_Y,      DIAGRAM_WIDTH, DIAGRAM_HEIGHT));
			}

			HashMap<String, String> output = new HashMap<String, String> ();
			String prefix = forecast.path.substring(0, forecast.path.length() - 4) + "-";
			for (String key : diagrams.keySet()) {
				String path = prefix + key + ".png";
				FileOutputStream out = new FileOutputStream(path);
				mergeWithLegend(img, diagrams.get(key)).compress(Bitmap.CompressFormat.PNG,	100, out);
				output.put(key, path);
			}

			return output;
		} catch (IOException ex) {
			throw new ImageProcessingException("IOException: " + ex.getMessage());
		}
	}
	
	/**
	 * Extracts 6 diagrams from meteogram fetched from new.meteo.pl/um
	 * For every diagram it adds the legend with time (in CEST)
	 * 
	 * @param forecast
	 * @param types
	 * @return HashMap where keys are types of diagrams and values are files where diagrams are located
	 * @throws ImageProcessingException
	 */	
	public HashMap<String, String> extractDiagrams(Forecast forecast) throws ImageProcessingException {
		
		ArrayList<String> types = new ArrayList<String>();
		types.add(Forecast.TEMPERATURE);
		types.add(Forecast.RAIN);
		types.add(Forecast.PRESSURE);
		types.add(Forecast.WIND);
		types.add(Forecast.VISIBILITY);
		types.add(Forecast.CLOUDS);
		return extractDiagrams(forecast, types);
	}

	/**
	 * Retrieves legend from original file and merges it into input image (on top)
	 * 
	 * @param original
	 * @param input
	 * @return image with legend
	 */
	protected Bitmap mergeWithLegend(Bitmap original, Bitmap input) {

		Bitmap legend = Bitmap.createBitmap(original, LEGEND_X, LEGEND_Y, LEGEND_WIDTH, LEGEND_HEIGHT);
		Bitmap combined = Bitmap.createBitmap(DIAGRAM_WIDTH, LEGEND_HEIGHT + DIAGRAM_HEIGHT, Config.RGB_565);

		Canvas canvas = new Canvas(combined);
		canvas.drawRGB(255, 251, 240);
		canvas.drawBitmap(legend, LEGEND_X - DIAGRAM_X, 0, null);
		canvas.drawBitmap(input, 0, LEGEND_HEIGHT, null);
		
		return combined;
	}
}
