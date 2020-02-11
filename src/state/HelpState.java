package state;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import audio.AudioPlayer;
import tileMap.Background;

public class HelpState implements GameState {

	private GameStateManager gsm;

	private AudioPlayer bgMusic;

	private Background background;
	private Background foreground;

	private String options[] = { "MENU", "SAIR" };

	private int currentOption = 0;

	public HelpState(GameStateManager gameStateManager) {
		this.gsm = gameStateManager;
		init();
	}

	@Override
	public void init() {
		background = new Background("/res/backgrounds/background_final.gif", 1);

		background.setVector(-0.1, 0);

		foreground = new Background("/res/backgrounds/front_final.gif", 1);

		bgMusic = new AudioPlayer("/res/music/menu.mp3");
		bgMusic.play();
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

		AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);

		g.setComposite(alcom);

		g.setPaint(Color.black);

		g.fillRoundRect(10, 10, 300, 220, 10, 10);

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		// draw options
		g.setFont(new Font("Sans Serif", Font.BOLD, 12));

		g.setColor(Color.WHITE);

		g.drawString("CONTROLES DO JOGO", 90, 40);

		g.setFont(new Font("Arial", Font.PLAIN, 9));

		String controls[] = { "SETA PARA CIMA --- PULAR",

				"SETA ESQUERDA --- ANDAR PARA ESQUERDA", "SETA DIREITA --- ANDAR PARA DIREITA" };

		for (int i = 0; i < controls.length; i++) {
			g.drawString(controls[i], 40, 80 + i * 10);
		}

		g.setFont(new Font("Sans Serif", Font.BOLD, 12));

		for (int i = 0; i < options.length; i++) {
			if (i == currentOption) {
				g.setColor(Color.YELLOW);
			} else {
				g.setColor(Color.WHITE);
			}
			g.drawString(options[i], 240, 110 + i * 17);
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

//		if (currentOption == 0) {
//
//			bgMusic.close();
//			// start
//			gsm.setState(GameStateManager.LEVEL1STATE);
//		} else 

		if (currentOption == 0) {
			bgMusic.close();
			gsm.setState(GameStateManager.MENUSTATE);
			// help
		} else if (currentOption == 1) {
			// exit
			System.exit(0);
		}
	}

}
