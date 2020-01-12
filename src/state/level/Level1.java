package state.level;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entity.Player;
import init.GamePanel;
import state.GameState;
import state.GameStateManager;
import tileMap.Background;
import tileMap.TileMap;

public class Level1 implements GameState {

	private TileMap tileMap;

	private Background background;

	private Player player;

	private GameStateManager gsm;

	public Level1(GameStateManager gameStateManager) {
		this.gsm = gameStateManager;

		init();
	}

	@Override
	public void init() {

		tileMap = new TileMap(30);

		tileMap.loadTiles("/tileset/grasstileset.gif");
		tileMap.loadMap("/maps/level1-1.map");

		tileMap.setPosition(0, 0);

		tileMap.setTween(1);

		background = new Background("/backgrounds/bg_lv1_alpha1_final.gif", 0.1);

		player = new Player(tileMap);
		player.setPosition(100, 100);

	}

	@Override
	public void update() {
		// update player
		player.update();

		// update tilemap position
		tileMap.setPosition(GamePanel.WIDTH / 2 - player.getX(), GamePanel.HEIGHT / 2 - player.getY());

		// set background
		background.setPosition(tileMap.getX(), tileMap.getY());

	}

	@Override
	public void draw(Graphics2D g) {
		background.draw(g);

		// draw tilemap
		tileMap.draw(g);

		// draw player
		player.draw(g);

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
