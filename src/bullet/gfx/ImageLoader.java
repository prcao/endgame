package bullet.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

//loads images

public class ImageLoader {

	public static BufferedImage loadImage(String path){
		
		try {
			return ImageIO.read(ImageLoader.class.getResource(path));	//getresource gets a file | syntax YourClass.class.getResource(string of path)
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);	//exits program if cannot load image
		}
		
		return null;
	}
}
