package state.level;

import java.awt.Graphics2D;

import entity.Player;
import state.GameState;
import state.GameStateManager;
import tileMap.Background;

public class Level1 implements GameState {

	private GameStateManager gsm;

	private Player player;

	private Background background;

	public Level1(GameStateManager gameStateManager) {
		this.gsm = gameStateManager;

		init();
	}

	@Override
	public void init() {

		background = new Background("/backgrounds/WhiteImage.png");

		player = new Player();

		player.setPosition(100, 200);

	}

	@Override
	public void update() {
		background.update();
		player.update();
	}

	@Override
	public void draw(Graphics2D g) {
		background.draw(g);
		player.draw(g);
	}

	@Override
	public void keyPressed(int key) {
		player.keyPressed(key);
	}

	@Override
	public void keyReleased(int key) {
		player.keyReleased(key);
	}

}
