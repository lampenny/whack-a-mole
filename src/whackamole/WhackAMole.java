package whackamole;

import javax.swing.*;
import java.awt.*;

public class WhackAMole {
	private GameBoard gameBoard;
	private JButton resetButton = new JButton("Restart");

	int boardWidth = 600;
	int boardHeight = 650;

	JFrame frame = new JFrame("Whack-a-mole");
	JLabel scoreLabel = new JLabel();
	JPanel scorePanel = new JPanel();
	JLabel hiScoreLabel = new JLabel();
	JPanel hiScorePanel = new JPanel();
	JPanel allScoresPanel = new JPanel();
	JPanel bottomPanel = new JPanel();

	private Timer setMoleTimer;
	private Timer setPlantTimer;
	private int score = 0;
	private int hiScore = 0;

	public WhackAMole() {
		setUI();

		Image moleImg = new ImageIcon(getClass().getResource("assets/monty.png")).getImage();
		ImageIcon moleIcon = new ImageIcon(moleImg.getScaledInstance(120, 120, Image.SCALE_SMOOTH));

		Image plantImg = new ImageIcon(getClass().getResource("assets/piranha.png")).getImage();
		ImageIcon plantIcon = new ImageIcon(plantImg.getScaledInstance(120, 120, Image.SCALE_SMOOTH));

		gameBoard = new GameBoard(moleIcon, plantIcon, this::handleTileClick);
		frame.add(gameBoard);

		setupGameLoop();

		frame.setVisible(true);
	}

	private void setUI() {
		frame.setSize(boardWidth, boardHeight);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		scoreLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 50));
		scoreLabel.setBackground(Color.GREEN);
		scoreLabel.setText("Score: " + Integer.toString(score));
		scoreLabel.setOpaque(true);
		scorePanel.setBackground(Color.GREEN);
		scorePanel.add(scoreLabel);

		hiScoreLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		hiScoreLabel.setBackground(Color.GREEN);
		hiScoreLabel.setHorizontalAlignment(JLabel.CENTER);
		hiScoreLabel.setText("High score: " + Integer.toString(hiScore));
		hiScoreLabel.setOpaque(true);

		hiScorePanel.setBackground(Color.GREEN);
		hiScorePanel.add(hiScoreLabel);

		allScoresPanel.setLayout(new GridLayout(2, 1));
		allScoresPanel.setOpaque(true);

		allScoresPanel.add(scorePanel);
		allScoresPanel.add(hiScorePanel);
		allScoresPanel.setBackground(Color.RED);

		frame.add(allScoresPanel, BorderLayout.NORTH);

		resetButton.setFont(new Font("Arial", Font.PLAIN, 20));
		resetButton.addActionListener(e -> restartGame());
	}

	private void handleTileClick(Tile tile) {
		if (tile == gameBoard.getMoleTile()) {
			score += 10;
			scoreLabel.setText("Score: " + score);
		} else if (tile == gameBoard.getPlantTile()) {
			setMoleTimer.stop();
			setPlantTimer.stop();

			scoreLabel.setText("Game Over: " + score);

			if (score > hiScore) {
				hiScore = score;
				hiScoreLabel.setText("High Score: " + hiScore);
			}

			gameBoard.disableAllTiles();

			bottomPanel.add(resetButton);
			frame.add(bottomPanel, BorderLayout.SOUTH);
		}
	}

	private void setupGameLoop() {
		setMoleTimer = new Timer(900, _ -> gameBoard.setMoleAtRandomTile());
		setPlantTimer = new Timer(900, _ -> gameBoard.setPlantAtRandomTile());

		setMoleTimer.start();
		setPlantTimer.start();
	}

	private void restartGame() {
		setMoleTimer.stop();
		setPlantTimer.stop();

		score = 0;
		scoreLabel.setText("Score: " + score);
		hiScoreLabel.setText("High Score: " + hiScore);

		gameBoard.clearMole();
		gameBoard.clearPlant();
		gameBoard.enableAllTiles();

		bottomPanel.remove(resetButton);
		frame.revalidate();
		frame.repaint();

		setMoleTimer.start();
		setPlantTimer.start();
	}
}