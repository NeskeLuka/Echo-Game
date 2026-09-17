package drawGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import maze.Maze;

class Player {
	int playerX = 0, playerY = 0;
	final int playerSize = 20;
	final int speed = 4;
	// movement
	private boolean up, down, left, right;

	public void setUp(boolean pressed) {
		this.up = pressed;
	}

	public void setDown(boolean pressed) {
		this.down = pressed;
	}

	public void setRight(boolean pressed) {
		this.right = pressed;
	}

	public void setLeft(boolean pressed) {
		this.left = pressed;
	}

	public void update(Maze maze) {
		int moveX = 0, moveY = 0;
		if (up)
			moveY -= speed;
		if (down)
			moveY += speed;
		if (left)
			moveX -= speed;
		if (right)
			moveX += speed;

		if (moveX != 0 && moveY != 0) {
			moveX = (int) Math.round(moveX * 0.7071);
			moveY = (int) Math.round(moveY * 0.7071);
		}

		int row = playerY / Maze.TILE_SIZE;
		int col = playerX / Maze.TILE_SIZE;

		if (moveX != 0) {
			int leadingEdgeX = moveX > 0 ? playerX + moveX + playerSize - 1 : playerX + moveX;
			int targetCol = leadingEdgeX / Maze.TILE_SIZE;
			boolean blocked = targetCol != col && maze.hasWall(row, col, moveX > 0 ? Maze.EAST : Maze.WEST);
			if (!blocked)
				playerX += moveX;
		}

		if (moveY != 0) {
			int leadingEdgeY = moveY > 0 ? playerY + moveY + playerSize - 1 : playerY + moveY;
			int targetRow = leadingEdgeY / Maze.TILE_SIZE;
			boolean blocked = targetRow != row && maze.hasWall(row, col, moveY > 0 ? Maze.SOUTH : Maze.NORTH);
			if (!blocked)
				playerY += moveY;
		}
	}
}

public class GamePanel extends JPanel {
	private static final int WIDTH = 960, HEIGHT = 720; // dimension of a panel
	private final Player player = new Player();
	private final Maze maze = new Maze();

	private void initPlayerMovement() {
		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed W"), "upPressed");
		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed D"), "rightPressed");
		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed S"), "downPressed");
		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed A"), "leftPressed");

		getActionMap().put("upPressed", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent event) {
				player.setUp(true);
			}
		});
		getActionMap().put("rightPressed", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent event) {
				player.setRight(true);
			}
		});
		getActionMap().put("downPressed", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent event) {
				player.setDown(true);
			}
		});
		getActionMap().put("leftPressed", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent event) {
				player.setLeft(true);
			}
		});

		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released W"), "upReleased");

		getActionMap().put("upReleased", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent event) {
				player.setUp(false);
			}
		});
		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released D"), "rightReleased");

		getActionMap().put("rightReleased", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent event) {
				player.setRight(false);
			}
		});
		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released S"), "downReleased");

		getActionMap().put("downReleased", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent event) {
				player.setDown(false);
			}
		});
		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released A"), "leftReleased");

		getActionMap().put("leftReleased", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent event) {
				player.setLeft(false);
			}
		});
	}

	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.BLACK);
		this.initPlayerMovement();
		Timer timer = new Timer(16, event -> {
			player.update(maze);
			repaint();
		});
		timer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		maze.draw(g);
		g.setColor(Color.CYAN);
		g.fillOval(player.playerX, player.playerY, player.playerSize, player.playerSize);
	}
}
