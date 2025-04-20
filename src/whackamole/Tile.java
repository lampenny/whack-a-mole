package whackamole;

import javax.swing.*;

public class Tile extends JButton {
	private enum State {
		EMPTY, MOLE, PLANT
	}

	private State currentState = State.EMPTY;

	public Tile() {
		this.setFocusable(false);
	}

	public void setAsMole(ImageIcon moleIcon) {
		this.setIcon(moleIcon);
		this.currentState = State.MOLE;
	}

	public void setAsPlant(ImageIcon plantIcon) {
		this.setIcon(plantIcon);
		this.currentState = State.PLANT;
	}

	public void clear() {
		this.setIcon(null);
		this.currentState = State.EMPTY;
	}

	public boolean isMole() {
		return currentState == State.MOLE;
	}

	public boolean isPlant() {
		return currentState == State.PLANT;
	}

	public boolean isEmpty() {
		return currentState == State.EMPTY;
	}
}
