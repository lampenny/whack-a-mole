package whackamole;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.function.Consumer;

public class GameBoard extends JPanel {
	private final Tile[] tiles = new Tile[9];
	private Tile currMoleTile = null;
	private Tile currPlantTile = null;
	private final Random random = new Random();

	private final ImageIcon moleIcon;
	private final ImageIcon plantIcon;

	public GameBoard(ImageIcon moleIcon, ImageIcon plantIcon, Consumer<Tile> onTileClick) {
		this.moleIcon = moleIcon;
		this.plantIcon = plantIcon;

		this.setLayout(new GridLayout(3, 3, 10, 10));
		this.setBackground(Color.YELLOW);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		for (int i = 0; i < 9; i++) {
			Tile tile = new Tile();
			tiles[i] = tile;
			this.add(tile);

			tile.addActionListener(e -> onTileClick.accept(tile));
		}
	}

	public void setMoleAtRandomTile() {
		if (currMoleTile != null) {
			currMoleTile.clear();
			currMoleTile = null;
		}

		int index = random.nextInt(9);
		Tile selectedTile = tiles[index];

		if (selectedTile == currPlantTile)
			return;

		selectedTile.setAsMole(moleIcon);
		currMoleTile = selectedTile;
	}

	public void setPlantAtRandomTile() {
		if (currPlantTile != null) {
			currPlantTile.clear();
			currPlantTile = null;
		}

		int index = random.nextInt(9);
		Tile selectedTile = tiles[index];

		if (selectedTile == currMoleTile)
			return;

		selectedTile.setAsPlant(plantIcon);
		currPlantTile = selectedTile;
	}

	public Tile[] getTiles() {
		return tiles;
	}

	public Tile getMoleTile() {
		return currMoleTile;
	}

	public Tile getPlantTile() {
		return currPlantTile;
	}

	public void disableAllTiles() {
		for (Tile tile : tiles) {
			tile.setEnabled(false);
		}
	}

	public void clearMole() {
		if (currMoleTile != null) {
			currMoleTile.setIcon(null);
			currMoleTile = null;
		}
	}

	public void clearPlant() {
		if (currPlantTile != null) {
			currPlantTile.setIcon(null);
			currPlantTile = null;
		}
	}

	public void enableAllTiles() {
		for (Tile tile : tiles) {
			tile.setEnabled(true);
		}
	}
}
