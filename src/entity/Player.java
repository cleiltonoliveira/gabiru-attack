package entity;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player {

	private static final int STOPPED = 0;
	private static final int WALKING = 1;

	private BufferedImage[] sprites;

	private boolean facingRight;

	private double movespeed;

	private boolean jumping;
	private double jumpSpeed;

	private double maxJumpSize;

	private boolean falling;
	private double fallSpeed;

	private double x;
	private double y;
	private double dx;
	private double dy;

	private boolean left;
	private boolean right;

	private int tileWidth;
	private int tileHeight;

	private Animation animation;

	private int currentAction;

	public Player() {
		movespeed = 0.4;
		tileHeight = 32;
		tileWidth = 32;
		jumpSpeed = 4.0;
		maxJumpSize = 40;
		fallSpeed = 0.3;

		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/playersprites/gabiru.png"));

			sprites = new BufferedImage[3];

			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * tileWidth, 0, tileWidth, tileHeight);
			}

			animation = new Animation();
			animation.setFrames(new BufferedImage[] { sprites[0] });
			animation.setDelay(100);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void draw(Graphics2D g) {

		if (facingRight) {
//			g.drawImage(sprites[0], (int) (x + dx), (int) (y + dy), -tileWidth, tileHeight, null);
			g.drawImage(animation.getFrame(), (int) (x + dx), (int) (y + dy), -tileWidth, tileHeight, null);
		} else {
//			g.drawImage(sprites[0], (int) (x + dx - tileWidth), (int) (y + dy), null);
			g.drawImage(animation.getFrame(), (int) (x + dx - tileWidth), (int) (y + dy), null);
		}
	}

	public void update() {
		updatePosition();
		animation.update();
	}

	private void updatePosition() {

		if (left || right) {
			if (currentAction != Player.WALKING) {

				currentAction = Player.WALKING;
				animation.setFrames(sprites);
			}

			if (left) {
				dx -= movespeed;
				facingRight = false;
			} else if (right) {
				facingRight = true;
				dx += movespeed;
			}

		} else {
			if (currentAction != Player.STOPPED) {

				currentAction = Player.STOPPED;
				animation.setFrames(new BufferedImage[] { sprites[0] });
			}
		}

		if (jumping && !falling) {

			if (dy <= -maxJumpSize) {
				jumping = false;
				falling = true;
			}
			dy -= jumpSpeed;
		}

		if (falling) {

			if (dy >= 0) {

				falling = false;
			}

			dy += fallSpeed;
		}

	}

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void keyPressed(int key) {

		if (key == KeyEvent.VK_LEFT)
			left = true;
		if (key == KeyEvent.VK_RIGHT)
			right = true;
		if (key == KeyEvent.VK_UP)
			jumping = true;

	}

	public void keyReleased(int key) {
		if (key == KeyEvent.VK_LEFT)
			left = false;
		if (key == KeyEvent.VK_RIGHT)
			right = false;
		if (key == KeyEvent.VK_UP) {
			jumping = false;
			falling = true;
		}
	}
}
