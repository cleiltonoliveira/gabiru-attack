package state.level;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import entity.Enemy;
import entity.Player;
import entity.enemies.Bird;
import entity.enemies.Slugger;
import init.GamePanel;
import state.GameState;
import state.GameStateManager;
import tileMap.Background;
import tileMap.TileMap;

public class Level1 implements GameState {

	private TileMap tileMap;

	private Background background;

	private Player player;

	private ArrayList<Enemy> enemies;

	private GameStateManager gsm;

	public Level1(GameStateManager gameStateManager) {
		this.gsm = gameStateManager;

		init();
	}

	@Override
	public void init() {

		tileMap = new TileMap(30);

		tileMap.loadTiles("/res/tileset/grasstileset.gif");
		tileMap.loadMap("/res/maps/level1-1.map");

		tileMap.setPosition(0, 0);

		tileMap.setTween(1);

		background = new Background("/res/backgrounds/bg_lv1_alpha1_final.gif", 0.1);

		player = new Player(tileMap);
		player.setPosition(100, 100);

		populateEnemies();

	}

	private void populateEnemies() {
		enemies = new ArrayList<Enemy>();

		Slugger s;
		Point[] points = new Point[] { new Point(200, 100), new Point(830, 200), new Point(930, 200), new Point(1480, 200),
				new Point(1720, 200) };

		Bird bird;

		for (int i = 0; i < points.length; i++) {

			s = new Slugger(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}

		points = new Point[] { new Point(680, 40), new Point(2150, 40) };

		for (int i = 0; i < points.length; i++) {

			bird = new Bird(tileMap);
			bird.setPosition(points[i].x, points[i].y);
			enemies.add(bird);
		}

	}

	@Override
	public void update() {
		// update player
		player.update();

		// update tilemap position
		tileMap.setPosition(GamePanel.WIDTH / 2 - player.getX(), GamePanel.HEIGHT / 2 - player.getY());

		// set background
		background.setPosition(tileMap.getX(), tileMap.getY());

		// update all enemies
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);

			e.update();
			if (e.isDead()) {
				enemies.remove(i);
				i--;

//				explosions.add(new Explosion(e.getX(), e.getY()));
			}
		}

	}

	@Override
	public void draw(Graphics2D g) {
		background.draw(g);

		// draw tilemap
		tileMap.draw(g);

		// draw player
		player.draw(g);

		// draw enemies
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
	}

	@Override
	public void keyPressed(int k) {

		if (k == KeyEvent.VK_LEFT)
			player.setLeft(true);
		if (k == KeyEvent.VK_RIGHT)
			player.setRight(true);
		if (k == KeyEvent.VK_UP)
			player.setUp(true);
		if (k == KeyEvent.VK_DOWN)
			player.setDown(true);
		if (k == KeyEvent.VK_W)
			player.setJumping(true);

	}

	@Override
	public void keyReleased(int k) {
		if (k == KeyEvent.VK_LEFT)
			player.setLeft(false);
		if (k == KeyEvent.VK_RIGHT)
			player.setRight(false);
		if (k == KeyEvent.VK_UP)
			player.setUp(false);
		if (k == KeyEvent.VK_DOWN)
			player.setDown(false);
		if (k == KeyEvent.VK_W)
			player.setJumping(false);
	}
}
