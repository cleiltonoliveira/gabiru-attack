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

	private String options[] = { "JOGAR", "AJUDA", "SAIR" };

	private int currentOption = 0;

	public Menu(GameStateManager gameStateManager) {
		this.gsm = gameStateManager;
		init();
	}

	@Override
	public void init() {
		background = new Background("/backgrounds/WhiteImage.png");
	}

	@Override
	public void update() {
		background.update();
	}

	@Override
	public void draw(Graphics2D g) {
		background.draw(g);
		// draw title
		g.setColor(new Color(128, 0, 0));
		g.setFont(new Font("Century Gothic", Font.PLAIN, 28));
		g.drawString("Gabiru Attack", 80, 70);

		// draw options
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		for (int i = 0; i < options.length; i++) {
			if (i == currentOption) {
				g.setColor(Color.BLACK);
			} else {
				g.setColor(Color.RED);
			}
			g.drawString(options[i], 150, 140 + i * 15);
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
