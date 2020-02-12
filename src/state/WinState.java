package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import audio.AudioPlayer;
import tileMap.Background;

public class WinState implements GameState {

	private GameStateManager gsm;

	private AudioPlayer bgMusic;

	private Background background;
	private Background foreground;

	private String gameWin = "Nível Concluído!";

	private String options[] = { "JOGAR", "MENU", "SAIR" };

	private int currentOption = 0;

	public WinState(GameStateManager gameStateManager) {
		this.gsm = gameStateManager;
		init();
	}

	@Override
	public void init() {
		background = new Background("/res/backgrounds/background_final.gif", 1);

		background.setVector(-0.1, 0);

		foreground = new Background("/res/backgrounds/front_final.gif", 1);

		bgMusic = new AudioPlayer("/res/music/win.mp3");
		bgMusic.play();

	}

	@Override
	public void update() {
//		background.update();
//		foreground.update();
	}

	@Override
	public void draw(Graphics2D g) {
//		background.draw(g);
//		foreground.draw(g);

		// draw options
		g.setFont(new Font("Sans Serif", Font.BOLD, 12));
		for (int i = 0; i < options.length; i++) {
			if (i == currentOption) {
				g.setColor(Color.WHITE);
			} else {
				g.setColor(Color.YELLOW);
			}

//			g.drawString(options[i], 140, 180 + i * 17);
			g.drawString(options[i], 50, 100 + i * 17);
		}

		g.setFont(new Font("Sans Serif", Font.BOLD, 20));
		g.setColor(Color.RED);
		g.drawString(gameWin, 100, 110);
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

			bgMusic.close();
			// start
			gsm.setState(GameStateManager.LEVEL1STATE);
		} else if (currentOption == 1) {

			bgMusic.close();
			gsm.setState(GameStateManager.MENUSTATE);

		} else if (currentOption == 2) {

			// exit
			System.exit(0);
		}
	}

}
