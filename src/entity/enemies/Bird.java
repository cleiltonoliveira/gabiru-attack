package entity.enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import entity.Animation;
import entity.Enemy;
import tileMap.TileMap;

public class Bird extends Enemy {

	private BufferedImage[] sprites;

	public Bird(TileMap tm) {
		super(tm);

		moveSpeed = 0.6;
		maxSpeed = 0.6;
//		fallSpeed = 0.2;
//		maxFallSpeed = 10.0;

		width = 32;
		height = 32;
		cwidth = 28;
		cheight = 28;

		health = maxHealth = 2;
		damage = 1;

		// load sprites

		try {

			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/res/sprites/enemies/carcara.png"));

			sprites = new BufferedImage[4];

			for (int i = 0; i < sprites.length; i++) {

				if (i == sprites.length - 1)
					// getting the second sprite and putting it at the end for complete the bird
					// movement
					sprites[3] = sprites[1];
				else
					sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);

			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(300);

		right = true;
		facingRight = true;
	}

	private void getNextPosition() {
		// movement
		if (left) {
			dx -= moveSpeed;
			if (dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		} else if (right) {
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
			}
		}

		// falling
//		if (falling) {
//			dy += fallSpeed;
//		}
	}

	public void update() {
		// update position
		getNextPosition();
		checkTileMapCollision();
		falling = false;

		setPosition(xtemp, ytemp);

		// check flinching
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 400) {
				flinching = false;
			}
		}

		// if it hits a wall, go other direction
		if (right && dx == 0) {
			right = false;
			left = true;
			facingRight = false;
		} else if (left && dx == 0) {
			right = true;
			left = false;
			facingRight = true;
		}
		// animation update
		animation.update();
	}

	@Override
	public void draw(Graphics2D g) {
//		if (notOnScreen()) {
//			return;
//		}

		setMapPosition();
		if (facingRight) {

			g.drawImage(animation.getImage(), (int) (x + xmap - width / 2 + width), (int) (y + ymap - height / 2), -width, height,
					null);
		} else {
			g.drawImage(animation.getImage(), (int) (x + xmap - width / 2), (int) (y + ymap - height / 2), null);

		}
	}
}
