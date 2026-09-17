package drawGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.Timer;

import echo.EchoManager;
import input.KeyBindingManager;
import maze.Maze;
import player.Direction;
import player.Player;

public class GamePanel extends JPanel {
	private static final int WIDTH = 960, HEIGHT = 720; // dimension of a panel
	private boolean spaceHeld;
	private final Player player = new Player();
	private final Maze maze = new Maze();
	private final EchoManager echoManager = new EchoManager();

	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.BLACK);
		initPlayerMovement();
		initEcho();

		Timer timer = new Timer(16, event -> {
			player.update(maze);
			echoManager.update();
			repaint();
		});
		timer.start();
	}

	private void initPlayerMovement() {
		KeyBindingManager keys = new KeyBindingManager(this);
		keys.bindKey("W", () -> player.setMoving(Direction.UP, true), () -> player.setMoving(Direction.UP, false));
		keys.bindKey("A", () -> player.setMoving(Direction.LEFT, true), () -> player.setMoving(Direction.LEFT, false));
		keys.bindKey("S", () -> player.setMoving(Direction.DOWN, true), () -> player.setMoving(Direction.DOWN, false));
		keys.bindKey("D", () -> player.setMoving(Direction.RIGHT, true),
				() -> player.setMoving(Direction.RIGHT, false));
	}

	private void initEcho() {
		KeyBindingManager keys = new KeyBindingManager(this);

		keys.bindKey("SPACE", () -> {
			if (!spaceHeld) {
				spaceHeld = true;
				echoManager.trigger(player.getCenterX(), player.getCenterY());
			}
		}, () -> {
			spaceHeld = false;
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		maze.drawWithEcho(g, echoManager.getPulses());
		player.draw(g);
	}
}