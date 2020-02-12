package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import audio.AudioPlayer;
import tileMap.TileMap;

public class Player extends MapObject {

	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int SCRATCHING = 3;
	private static final int FIREBALL = 5;
	// player stuff
	private int health;
	private int maxHealth;
	private int hurricane;
	private int maxHurricane;
	private boolean dead;

	private boolean flinching;
	private long flinchTimer;

	// fireball
	private boolean hurricanning;
	private int hurricaneCost;
	private int hurricaneBallDamage;
	private ArrayList<Hurricane> hurricanes;
//	private long hurricaneTime = 0;

	// scratch
	private boolean scratching;
	private int scratchDamage;
	private int scratchRange;

	// animations
	private ArrayList<BufferedImage[]> sprites;

	// num of frames for each action
	private final int[] numFrames = { 1, 4, 1, 3

	};

	private HashMap<String, AudioPlayer> sfx;

	public Player(TileMap tileMap) {
		super(tileMap);

		width = 32;
		height = 32;

		// collision box;
		cwidth = 26;
		cheight = 26;

		moveSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;

		facingRight = true;

		health = maxHealth = 5;

		hurricane = maxHurricane = 10;

		hurricaneCost = 1;
		hurricaneBallDamage = 5;

		hurricanes = new ArrayList<Hurricane>();

		scratchDamage = 8;
		scratchRange = 40;

		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/res/sprites/player/gabiru.png"));

			sprites = new ArrayList<BufferedImage[]>();

			for (int i = 0; i < numFrames.length; i++) {

				BufferedImage[] bi = new BufferedImage[numFrames[i]];

				// change to attack spritesheet
				if (i == SCRATCHING - 1) {
					spritesheet = ImageIO.read(getClass().getResourceAsStream("/res/sprites/player/gabiru_attacking.png"));
				}

				for (int j = 0; j < numFrames[i]; j++) {

					int n = (j == 3 ? width : j * width);

					bi[j] = spritesheet.getSubimage(n, 0, width, height);

				}
				sprites.add(bi);
			}

			animation = new Animation();
			currentAction = IDLE;
			animation.setFrames(sprites.get(IDLE));
			animation.setDelay(400000);

			sfx = new HashMap<String, AudioPlayer>();
			sfx.put("jump", new AudioPlayer("/res/sfx/jump.mp3"));
			sfx.put("scratch", new AudioPlayer("/res/sfx/scratch.mp3"));

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

	public int getHurricane() {
		return hurricane;
	}

	public int getMaxHurricane() {
		return maxHurricane;
	}

	public void setHurricanning() {
		hurricanning = true;
	}

	public boolean isDead() {
		return dead;
	}

	public void setScratching() {
		this.scratching = true;
	}

	public void setFlinching(boolean flinching) {
		this.flinching = flinching;

		flinchTimer = System.nanoTime();
	}

	public void checkAttack(ArrayList<Enemy> enemies) {

		// loop through enemies
		for (int i = 0; i < enemies.size(); i++) {

			Enemy e = enemies.get(i);

			// check scratch attack
			if (scratching) {
				if (facingRight) {
					if (e.getX() > x && e.getX() < x + scratchRange && e.getY() > y - height / 2 && e.getY() < y + height / 2) {
						e.hit(scratchDamage);
					}
				} else {
					if (e.getX() < x && e.getX() > x - (scratchDamage + width) && e.getY() > y - height / 2
							&& e.getY() < y + height / 2) {
						e.hit(scratchDamage);
					}
				}
			}

			// hurricanes
			for (int j = 0; j < hurricanes.size(); j++) {

				if (hurricanes.get(j).intersects(e)) {

					e.hit(hurricaneBallDamage);
					hurricanes.get(j).setHit();
					break;
				}
			}

			// check for enemy colisions
			if (intersects(e)) {
				hit(e.getDamage());
			}
		}
	}

	private void hit(int damage) {
		if (flinching)
			return;
		health -= damage;

		if (health < 0)
			health = 0;
		if (health == 0)
			dead = true;
		else {
			flinching = true;
			flinchTimer = System.nanoTime();
		}
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

		// cannot move while attacking, except in air
		if ((currentAction == SCRATCHING || currentAction == FIREBALL) && !(jumping || falling)) {
			dx = 0;
		}

		// jumping
		if (jumping && !falling) {

			sfx.get("jump").play();

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

		if (notOnScreen()) {

			flinching = false;
			hit(1);

			if (isDead() == false) {

				tileMap.setPosition(0, 0);
				setPosition(100, 100);
				setFlinching(true);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);

		// check attack has stopped

		if (currentAction == SCRATCHING) {

			if (animation.hasPlayedOnce()) {
				scratching = false;
			}
		}

		if (currentAction == FIREBALL) {
			if (animation.hasPlayedOnce()) {
				hurricanning = false;
			}
		}

		// hurricane attack

//		long elapsed1 = (System.nanoTime() - hurricaneTime) / 1000000;
//		if (elapsed1 > 2000) {
//			hurricane += 1;
//
//		}

		if (hurricane > maxHurricane) {
			hurricane = maxHurricane;
		}

		if (hurricanning && currentAction != FIREBALL) {
			if (hurricane >= hurricaneCost) {
				hurricane -= hurricaneCost;

//				hurricaneTime = System.nanoTime();

				Hurricane fb = new Hurricane(tileMap, facingRight);
				fb.setPosition(x, y);
				hurricanes.add(fb);
			}
		}

		// update hurricanes
		for (int i = 0; i < hurricanes.size(); i++) {
			hurricanes.get(i).update();

			if (hurricanes.get(i).shouldRemove()) {
				hurricanes.remove(i);
				i--;
			}
		}

		// check done flinching
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 2000) {
				flinching = false;
			}
		}
		// set animation
		if (scratching) {
			if (currentAction != SCRATCHING) {

				sfx.get("scratch").play();
				currentAction = SCRATCHING;
				animation.setFrames(sprites.get(SCRATCHING));
				animation.setDelay(50);
//				width = 60;
			}

		} else if (hurricanning) {
			if (currentAction != FIREBALL) {
				currentAction = FIREBALL;
//					animation.setFrames(sprites.get(FIREBALL));
				animation.setFrames(sprites.get(SCRATCHING));
				animation.setDelay(100);
//					width = 30;
			}

		} else if (dy < 0) {

			if (currentAction != JUMPING) {

				currentAction = JUMPING;
//				animation.setFrames(sprites.get(JUMPING));
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				width = 32;
			}
		} else if (left || right) {

			if (currentAction != WALKING) {

				currentAction = WALKING;
//				animation.setFrames(sprites.get(WALKING));
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(40);
				width = 32;
			}
		} else {
			if (currentAction != IDLE) {
				currentAction = IDLE;

//				animation.setFrames(sprites.get(IDLE));
				animation.setFrames(sprites.get(IDLE));

				animation.setDelay(400);
				width = 32;
			}
		}

		animation.update();

		// set direction
		if (currentAction != SCRATCHING && currentAction != FIREBALL) {
			if (right)
				facingRight = true;
			if (left)
				facingRight = false;

		}
	}

	@Override
	public void draw(Graphics2D g) {

		setMapPosition();

		// draw fireballs
		for (int i = 0; i < hurricanes.size(); i++) {
			hurricanes.get(i).draw(g);
		}

		// draw player
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed / 100 % 2 == 0) {
				return;
			}
		}
		if (facingRight) {

			g.drawImage(animation.getImage(), (int) (x + xmap - width / 2 + width), (int) (y + ymap - height / 2), -width, height,
					null);
		} else {
			g.drawImage(animation.getImage(), (int) (x + xmap - width / 2), (int) (y + ymap - height / 2), null);

		}
	}
}