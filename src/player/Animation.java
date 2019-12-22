package player;

import java.awt.image.BufferedImage;

public class Animation {

	private BufferedImage frames[];

	private int currentFrame;

	private long delay;

	private long startTime;

	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
		currentFrame = 0;
		startTime = System.nanoTime();
	}

	public BufferedImage getFrame() {
		return frames[currentFrame];
	}

	public void update() {
		if (delay == -1) {
			return;
		}

		long elapsed = (System.nanoTime() - startTime) / 1000000;

		if (elapsed > delay) {
			currentFrame++;
			startTime = System.nanoTime();
		}
		if (currentFrame == frames.length) {
			currentFrame = 0;
		}
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

}
