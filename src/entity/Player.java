package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import tileMap.TileMap;

public class Player extends MapObject {

	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;

	// player stuff
	private int health;
	private int maxHealth;
	private boolean dead;

	private boolean flinching;
	private long flinchTimer;

	// animations
	private BufferedImage[] sprites;

	public Player(TileMap tileMap) {
		super(tileMap);

		width = 32;
		height = 32;

		// collision box;
		cwidth = 28;
		cheight = 28;

		moveSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;

		facingRight = false;

		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/res/sprites/player/gabiru.png"));

			sprites = new BufferedImage[3];

			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}

			animation = new Animation();
			currentAction = IDLE;
			animation.setFrames(new BufferedImage[] { sprites[IDLE] });
			animation.setDelay(400000);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public int getHealth() {
		return health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	private void hit(int damage) {
		if (flinching)
			return;
		health -= damage;
		if (health < 0)
			health = 0;
		if (health == 0)
			dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
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
		} else {
			if (dx > 0) {

				dx -= stopSpeed;
				if (dx < 0) {
					dx = 0;
				}
			} else if (dx < 0) {
				dx += stopSpeed;
				if (dx > 0) {
					dx = 0;
				}
			}
		}

		// jumping
		if (jumping && !falling) {

			dy = jumpStart; // jumpStart = -4.8;
			falling = true;
		}

		// falling
		if (falling) {
//			if (dy > 0 && gliding) {
//				dy += fallSpeed * 0.1;
//			} else {
//				dy += fallSpeed; // fallSpeed = 0.15;
//			}
			dy += fallSpeed;
			if (dy > 0) {
				jumping = false;
			}
//			if (dy < 0 && !jumping) {
//				dy += stopJumpSpeed;
//			}
			if (dy > maxFallSpeed) {
				dy = maxFallSpeed;
			}
		}
	}

	public void update() {

		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);

		// check done flinching
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 1000) {
				flinching = false;
			}
		}

		if (dy < 0) {

			if (currentAction != JUMPING) {

				currentAction = JUMPING;
//				animation.setFrames(sprites.get(JUMPING));
				animation.setFrames(sprites);
				animation.setDelay(-1);
				width = 32;
			}
		} else if (left || right) {

			if (currentAction != WALKING) {
				currentAction = WALKING;
//				animation.setFrames(sprites.get(WALKING));
				animation.setFrames(sprites);
				animation.setDelay(40);
				width = 32;
			}
		} else {
			if (currentAction != IDLE) {
				currentAction = IDLE;

//				animation.setFrames(sprites.get(IDLE));
				animation.setFrames(new BufferedImage[] { sprites[IDLE] });

				animation.setDelay(400);
				width = 32;
			}
		}

		animation.update();

		if (right)
			facingRight = false;
		if (left)
			facingRight = true;

		System.out.println("x : " + x + "y : " + y);

	}

	public void draw(Graphics2D g) {

		setMapPosition();

		// draw player
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed / 100 % 2 == 0) {
				return;
			}
		}
		super.draw(g);
	}

}
