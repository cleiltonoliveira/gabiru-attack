package init;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entity.Player;
import state.GameStateManager;

public class GamePanel extends JPanel implements Runnable, KeyListener {

	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int IMGSCALE = 2;

	private Graphics2D g;
	private BufferedImage image;
	private Thread thread;

//	Player player;

	private GameStateManager gsm;

	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH * IMGSCALE, HEIGHT * IMGSCALE));
		setFocusable(true);
		requestFocus();
	}

	@Override
	public void run() {

		init();

		while (true) {

			update();
			draw();
			drawToScreen();

			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void drawToScreen() {
		/*
		 * graphics g draws to a clean image 320x240. graphics g2 draws to the panel
		 */

		Graphics g2 = getGraphics();

		g2.drawImage(image, 0, 0, WIDTH * IMGSCALE, HEIGHT * IMGSCALE, null);
		g2.dispose();
	}

	private void init() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

		g = (Graphics2D) image.getGraphics();

		gsm = new GameStateManager();

	}

	private void draw() {

		gsm.draw(g);

	}

	private void update() {
		gsm.update();

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		gsm.keyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		gsm.keyReleased(e.getKeyCode());
	}

	@Override
	public void addNotify() {

		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
}
