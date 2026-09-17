package drawGame;

import java.awt.Color;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

class Player {
	private static final int WIDTH = 940, HEIGHT = 700; // dimension of a panel
	int playerX = 470, playerY = 350;
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

	public void update() {
		int moveX = 0;
	    int moveY = 0;

	    // 1. Determine intended direction
	    if (up) moveY -= speed;
	    if (down) moveY += speed;
	    if (left) moveX -= speed;
	    if (right) moveX += speed;

	    // 2. Normalize speed if moving diagonally
	    if (moveX != 0 && moveY != 0) {
	        moveX = (int) Math.round(moveX * 0.7071);
	        moveY = (int) Math.round(moveY * 0.7071);
	    }

	    if (playerX + moveX >= 0 && playerX + moveX < WIDTH) {
	        playerX += moveX;
	    }
	    if (playerY + moveY >= 0 && playerY + moveY < HEIGHT) {
	        playerY += moveY;
	    }
	}
}

public class GamePanel extends JPanel {
	private static final int WIDTH = 960, HEIGHT = 720; // dimension of a panel
	private final Player player = new Player();

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
			player.update();
			repaint();
		});
		timer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.CYAN);
		g.fillOval(player.playerX, player.playerY, player.playerSize, player.playerSize);
	}
}
