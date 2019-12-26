package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.xml.namespace.QName;

import state.GameStateManager;
import tileMap.Background;

public class Menu implements GameState {

	private GameStateManager gsm;

	private Background background;
	private Background foreground;

	private String options[] = { "JOGAR", "AJUDA", "SAIR" };

	private int currentOption = 0;

	public Menu(GameStateManager gameStateManager) {
		this.gsm = gameStateManager;
		init();
	}

	@Override
	public void init() {
		background = new Background("/backgrounds/background_final.gif", 1);

		background.setVector(-0.1, 0);

		foreground = new Background("/backgrounds/front_final.gif");

	}

	@Override
	public void update() {
		background.update();
		foreground.update();
	}

	@Override
	public void draw(Graphics2D g) {
		background.draw(g);
		foreground.draw(g);

		// draw options
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		for (int i = 0; i < options.length; i++) {
			if (i == currentOption) {
				g.setColor(Color.WHITE);
			} else {
				g.setColor(Color.RED);
			}
			g.drawString(options[i], 140, 110 + i * 17);
		}
	}

	@Override
	public void keyPressed(int key) {

		if (key == KeyEvent.VK_ENTER) {
			select();
		}

		if (key == KeyEvent.VK_UP) {
			currentOption--;
			if (currentOption == -1) {
				currentOption = options.length - 1;
			}

		}
		if (key == KeyEvent.VK_DOWN) {
			currentOption++;
			if (currentOption == options.length) {
				currentOption = 0;
			}
		}

	}

	@Override
	public void keyReleased(int key) {
	}

	private void select() {

		if (currentOption == 0) {
			// start
			gsm.setState(GameStateManager.LEVEL1STATE);

		} else if (currentOption == 1) {
			// help
		} else if (currentOption == 2) {
			// exit
			System.exit(0);
		}
	}

}
