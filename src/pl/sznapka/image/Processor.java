/**
 * 
 */
package pl.sznapka.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

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

	
	public ArrayList<File> extractDiagrams(String source, String destinationPrefix) throws ImageProcessingException, IOException {
		
		BufferedImage img = readSource(source);
		HashMap<String, BufferedImage> diagrams = new HashMap<String, BufferedImage>();
		diagrams.put("temperature", img.getSubimage(DIAGRAM_X, TEMPERATURE_Y, DIAGRAM_WIDTH, DIAGRAM_HEIGHT));
		diagrams.put("rain", 		img.getSubimage(DIAGRAM_X, RAIN_Y, 		  DIAGRAM_WIDTH, DIAGRAM_HEIGHT));
		diagrams.put("pressure",	img.getSubimage(DIAGRAM_X, PRESSURE_Y, 	  DIAGRAM_WIDTH, DIAGRAM_HEIGHT));
		diagrams.put("wind", 		img.getSubimage(DIAGRAM_X, WIND_Y,        DIAGRAM_WIDTH, DIAGRAM_HEIGHT));
		diagrams.put("visibility",  img.getSubimage(DIAGRAM_X, VISIBILITY_Y,  DIAGRAM_WIDTH, DIAGRAM_HEIGHT));
		diagrams.put("clouds", 	    img.getSubimage(DIAGRAM_X, CLOUDS_Y,      DIAGRAM_WIDTH, DIAGRAM_HEIGHT));

		ArrayList<File> output = new ArrayList<File>();
		for (String key : diagrams.keySet()) {
			File path = new File(destinationPrefix + key + ".png");
			ImageIO.write(diagrams.get(key), "png", path); 
			output.add(path);
			
		}
		
		return output;
	}
	
	protected BufferedImage readSource(String source) throws ImageProcessingException {
		
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(source));
			System.out.println("Image Read. Image Dimension: " + img.getWidth()
			+ "w X " + img.getHeight() + "h");
			return img;
		} catch (IOException e) {
			throw new ImageProcessingException();
		}
	}
}
