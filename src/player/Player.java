package player;

import java.awt.Color;
import java.awt.Graphics;

import maze.Maze;

public class Player {
	private final Position position = new Position(10, 10);
	private Movement movement = new Movement();
	private final int size = 20;
	private final int speed = 4;

	public void setMoving(Direction direction, boolean isMoving) {
		movement.setActive(direction, isMoving);
	}

	public int getX() {
		return position.getX();
	}

	public int getY() {
		return position.getY();
	}

	public int getSize() {
		return size;
	}

	public double getCenterX() {
		return position.getX() + size / 2.0;
	}

	public double getCenterY() {
		return position.getY() + size / 2.0;
	}

	public void update(Maze maze) {
		int[] delta = movement.computeDelta(speed);

		int moveX = delta[0];
		int moveY = delta[1];

		if (maze.canOccupy(position.getX() + moveX, position.getY(), size)) {
			position.translateX(moveX);
		}

		if (maze.canOccupy(position.getX(), position.getY() + moveY, size)) {
			position.translateY(moveY);
		}
	}

	private boolean isBlockedHorizontally(Maze maze, int row, int col, int moveX) {
		int leadingEdgeX = moveX > 0 ? position.getX() + moveX + size - 1 : position.getX() + moveX;
		int targetCol = leadingEdgeX / Maze.TILE_SIZE;
		return targetCol != col && maze.hasWall(row, col, moveX > 0 ? Maze.EAST : Maze.WEST);
	}

	private boolean isBlockedVertically(Maze maze, int row, int col, int moveY) {
		int leadingEdgeY = moveY > 0 ? position.getY() + moveY + size - 1 : position.getY() + moveY;
		int targetRow = leadingEdgeY / Maze.TILE_SIZE;
		return targetRow != row && maze.hasWall(row, col, moveY > 0 ? Maze.SOUTH : Maze.NORTH);
	}

	public void draw(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillOval(position.getX(), position.getY(), size, size);
	}
}