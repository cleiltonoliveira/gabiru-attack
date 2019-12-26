package state;

import java.awt.Graphics2D;

import state.level.Level1;

public class GameStateManager implements GameState {

	private GameState[] gameStates;
	public static final int NUMOFSTATES = 2;
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;

	private int currentState;

	public GameStateManager() {
		init();
	}

	@Override
	public void init() {
		currentState = MENUSTATE;
		gameStates = new GameState[NUMOFSTATES];
		loadState(currentState);
	}

	private void loadState(int state) {

		if (state < NUMOFSTATES) {

			if (state == MENUSTATE) {
				gameStates[state] = new Menu(this);

			} else if (state == LEVEL1STATE) {
				gameStates[state] = new Level1(this);

			}
		}
	}

	@Override
	public void update() {
		try {
			gameStates[currentState].update();
		} catch (Exception e) {
		}
	}

	@Override
	public void draw(Graphics2D g) {
		try {
			gameStates[currentState].draw(g);
		} catch (Exception e) {
		}
	}

	@Override
	public void keyPressed(int key) {
		gameStates[currentState].keyPressed(key);
	}

	@Override
	public void keyReleased(int key) {
		gameStates[currentState].keyReleased(key);
	}

	public void setState(int state) {

		int oldState = currentState;

		loadState(state);

		this.currentState = state;

		gameStates[oldState] = null;

	}
}
