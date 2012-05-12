/**
 * 
 */
package pl.sznapka.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

	public static final int LEGEND_X = 62;
	public static final int LEGEND_Y = 29;
	public static final int LEGEND_WIDTH = 415;
	public static final int LEGEND_HEIGHT = 24;

	
	/**
	 * Extracts 6 diagrams from meteogram fetched from new.meteo.pl/um
	 * For every diagram it adds the legend with time (in CEST)
	 * 
	 * @param source
	 * @param destinationPrefix
	 * @return HashMap where keys are types of diagrams and values are files where diagrams are located
	 * @throws ImageProcessingException
	 */
	public HashMap<String, File> extractDiagrams(String source, String destinationPrefix) throws ImageProcessingException {
		
		try {
			BufferedImage img = ImageIO.read(new File(source));
			HashMap<String, BufferedImage> diagrams = new HashMap<String, BufferedImage>();
			diagrams.put("temperature", img.getSubimage(DIAGRAM_X, TEMPERATURE_Y, DIAGRAM_WIDTH, DIAGRAM_HEIGHT));
			diagrams.put("rain", 		img.getSubimage(DIAGRAM_X, RAIN_Y, 		  DIAGRAM_WIDTH, DIAGRAM_HEIGHT));
			diagrams.put("pressure",	img.getSubimage(DIAGRAM_X, PRESSURE_Y, 	  DIAGRAM_WIDTH, DIAGRAM_HEIGHT));
			diagrams.put("wind", 		img.getSubimage(DIAGRAM_X, WIND_Y,        DIAGRAM_WIDTH, DIAGRAM_HEIGHT));
			diagrams.put("visibility",  img.getSubimage(DIAGRAM_X, VISIBILITY_Y,  DIAGRAM_WIDTH, DIAGRAM_HEIGHT));
			diagrams.put("clouds", 	    img.getSubimage(DIAGRAM_X, CLOUDS_Y,      DIAGRAM_WIDTH, DIAGRAM_HEIGHT));

			HashMap<String, File> output = new HashMap<String, File> ();
			for (String key : diagrams.keySet()) {
				File file = new File(destinationPrefix + key + ".png");
				ImageIO.write(mergeWithLegend(img, diagrams.get(key)), "png", file);
				output.put(key, file);
			}

			return output;
		} catch (IOException ex) {
			throw new ImageProcessingException("IOException: " + ex.getMessage());
		}
	}

	/**
	 * Retrieves legend from original file and merges it into input image (on top)
	 * 
	 * @param original
	 * @param input
	 * @return image with legend
	 */
	protected BufferedImage mergeWithLegend(BufferedImage original, BufferedImage input) {

		BufferedImage legend = original.getSubimage(LEGEND_X, LEGEND_Y, LEGEND_WIDTH, LEGEND_HEIGHT);
		BufferedImage combined = new BufferedImage(DIAGRAM_WIDTH, LEGEND_HEIGHT + DIAGRAM_HEIGHT, BufferedImage.TYPE_INT_RGB);

		Graphics g = combined.getGraphics();
		g.setColor(new Color(255, 251, 240));
		g.fillRect(0, 0, combined.getWidth(), combined.getHeight());
		g.drawImage(legend, LEGEND_X - DIAGRAM_X, 0, null);
		g.drawImage(input, 0, LEGEND_HEIGHT, null);

		return combined;
	}
}
