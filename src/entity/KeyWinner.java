package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import tileMap.TileMap;

public class KeyWinner extends MapObject {

	private BufferedImage image;

	public KeyWinner(TileMap tm) {
		super(tm);
		width = 32;
		height = 32;
		cwidth = 28;
		cheight = 28;

		try {
			image = ImageIO.read(getClass().getResourceAsStream("/res/levelkey/key_final_1.gif"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		animation = new Animation();
		animation.setFrames(new BufferedImage[] { image });
		animation.setDelay(300);

	}

	public void draw(Graphics2D g) {

		setMapPosition();

		super.draw(g);
	}

}
