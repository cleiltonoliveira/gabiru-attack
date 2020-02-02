package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.xml.namespace.QName;

import audio.AudioPlayer;
import state.GameStateManager;
import tileMap.Background;

public class GameOver implements GameState {

	private GameStateManager gsm;

	private AudioPlayer bgMusic;

	private Background background;
	private Background foreground;

	private String gameOver = "Game Over!";

	private String options[] = { "JOGAR", "AJUDA", "SAIR" };

	private int currentOption = 0;

	private boolean flinching;
	private long flinchTimer;

	public GameOver(GameStateManager gameStateManager) {
		this.gsm = gameStateManager;
		init();
	}

	@Override
	public void init() {
		background = new Background("/res/backgrounds/background_final.gif", 1);

		background.setVector(-0.1, 0);

		foreground = new Background("/res/backgrounds/front_final.gif", 1);

//		bgMusic = new AudioPlayer("/res/music/bt2.mp3");
//		bgMusic.play();
		flinching = true;
		flinchTimer = System.nanoTime();
	}

	@Override
	public void update() {
//		background.update();
//		foreground.update();

		// check done flinching
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 1000) {
				flinching = false;
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
//		background.draw(g);
//		foreground.draw(g);

		// draw options
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		for (int i = 0; i < options.length; i++) {
			if (i == currentOption) {
				g.setColor(Color.RED);
			} else {
				g.setColor(Color.WHITE);
			}

//			g.drawString(options[i], 140, 180 + i * 17);
			g.drawString(options[i], 50, 100 + i * 17);
		}

		// draw player
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed / 100 % 3 == 0) {

				return;
			}

		}

		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.setColor(Color.RED);
		g.drawString(gameOver, 110, 110);
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

//			bgMusic.close();
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
