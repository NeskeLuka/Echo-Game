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
import player.Player;

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
		g.fillOval(player.getPlayerX(), player.getPlayerY(), player.getPlayerSize(), player.getPlayerSize());
	}
}
