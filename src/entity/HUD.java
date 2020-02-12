package entity;
/**
 * @author cleilton
 * File: HUD.java - Date: Dec 16, 2019
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class HUD {
	private Player player;
	private BufferedImage image;
	private Font font;

	public HUD(Player p) {

		player = p;

		try {
//			image = ImageIO.read(getClass().getResourceAsStream("/res/hud/hud.gif"));
			image = ImageIO.read(getClass().getResourceAsStream("/res/hud/menubg8final.gif"));
			font = new Font("Arial", Font.PLAIN, 14);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g) {
		g.drawImage(image, 0, 0, null);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(player.getHealth() + "/" + player.getMaxHealth(), 30, 29);
		g.drawString(player.getHurricane() + "/" + player.getMaxHurricane(), 30, 50);

	}
}
