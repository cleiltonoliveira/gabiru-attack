package tileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import init.GamePanel;

public class Background {

	private BufferedImage image;

	private double x;
	private double y;
	private double dx;
	private double dy;

	private double moveScale;

	public Background(String backgroundLocation) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(backgroundLocation));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Background(String backgroundLocation, double moveScale) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(backgroundLocation));

			this.moveScale = moveScale;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setPosition(double x, double y) {
		this.x = (x * moveScale) % GamePanel.WIDTH;
		this.y = (y * moveScale) % GamePanel.HEIGHT;
	}

	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public void update() {
		x += dx;
		y += dy;
	}

	public void draw(Graphics2D g) {
		g.drawImage(image, (int) x, (int) y, null);

		if (x < 0) {
			g.drawImage(image, (int) x + GamePanel.WIDTH, (int) y, null);
		}
		if (x > 0) {
			g.drawImage(image, (int) x - GamePanel.WIDTH, (int) y, null);
		}

		// for background on movement
		if ((int) x == -GamePanel.WIDTH) {
			x = GamePanel.WIDTH;

		}

	}

}
