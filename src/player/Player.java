package player;

import maze.Maze;

public class Player {
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
	
	public int getPlayerX() {
		return this.playerX;
	}
	
	public int getPlayerY() {
		return this.playerY;
	}
	
	public int getPlayerSize() {
		return this.playerSize;
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
