package tileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background {

	private BufferedImage image;

	public Background(String backgroundLocation) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(backgroundLocation));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
	}

	public void draw(Graphics2D g) {
		g.drawImage(image, 0, 0, null);
	}

}
