package maze;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Random;

public class Maze {
	public static final int TILE_SIZE = 40;
	private static final int ROWS = 18;
	private static final int COLS = 24;

	public static final int NORTH = 1;
	public static final int EAST = 2;
	public static final int SOUTH = 4;
	public static final int WEST = 8;

	private static final double STRAIGHTNESS_BIAS = 0.65;

	private final int[][] tiles = new int[ROWS][COLS];
	private final Random random = new Random();

	public Maze() {
		generate();
	}

	private void generate() {
		for (int[] row : tiles) {
			Arrays.fill(row, NORTH | EAST | SOUTH | WEST);
		}

		boolean[][] visited = new boolean[ROWS][COLS];
		Deque<int[]> stack = new ArrayDeque<>();

		int startRow = random.nextInt(ROWS);
		int startCol = random.nextInt(COLS);
		visited[startRow][startCol] = true;
		stack.push(new int[] { startRow, startCol, -1 });

		while (!stack.isEmpty()) {
			int[] current = stack.peek();
			int row = current[0], col = current[1], lastDirection = current[2];

			List<Integer> options = unvisitedNeighborDirections(row, col, visited);
			if (options.isEmpty()) {
				stack.pop();
				continue;
			}

			int direction = chooseDirection(options, lastDirection);
			carve(row, col, direction);

			int[] next = step(row, col, direction);
			visited[next[0]][next[1]] = true;
			current[2] = direction;
			stack.push(new int[] { next[0], next[1], -1 });
		}

		openEntranceAndExit();
	}

	private int chooseDirection(List<Integer> options, int lastDirection) {
		if (lastDirection != -1 && options.contains(lastDirection) && random.nextDouble() < STRAIGHTNESS_BIAS) {
			return lastDirection;
		}
		return options.get(random.nextInt(options.size()));
	}

	private List<Integer> unvisitedNeighborDirections(int row, int col, boolean[][] visited) {
		List<Integer> options = new ArrayList<>(4);
		if (row > 0 && !visited[row - 1][col])
			options.add(NORTH);
		if (col < COLS - 1 && !visited[row][col + 1])
			options.add(EAST);
		if (row < ROWS - 1 && !visited[row + 1][col])
			options.add(SOUTH);
		if (col > 0 && !visited[row][col - 1])
			options.add(WEST);
		Collections.shuffle(options, random);
		return options;
	}

	private int[] step(int row, int col, int direction) {
		switch (direction) {
		case NORTH:
			return new int[] { row - 1, col };
		case EAST:
			return new int[] { row, col + 1 };
		case SOUTH:
			return new int[] { row + 1, col };
		default:
			return new int[] { row, col - 1 };
		}
	}

	private void carve(int row, int col, int direction) {
		int[] next = step(row, col, direction);
		tiles[row][col] &= ~direction;
		tiles[next[0]][next[1]] &= ~opposite(direction);
	}

	private int opposite(int direction) {
		switch (direction) {
		case NORTH:
			return SOUTH;
		case SOUTH:
			return NORTH;
		case EAST:
			return WEST;
		default:
			return EAST;
		}
	}

	private void openEntranceAndExit() {
		tiles[0][0] &= ~NORTH;
		tiles[ROWS - 1][COLS - 1] &= ~SOUTH;
	}

	public boolean hasWall(int row, int col, int direction) {
		return (tiles[row][col] & direction) != 0;
	}

	public void draw(Graphics g) {
		g.setColor(Color.GRAY);
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				int x = col * TILE_SIZE;
				int y = row * TILE_SIZE;
				int cell = tiles[row][col];

				if ((cell & NORTH) != 0)
					g.drawLine(x, y, x + TILE_SIZE, y);
				if ((cell & SOUTH) != 0)
					g.drawLine(x, y + TILE_SIZE, x + TILE_SIZE, y + TILE_SIZE);
				if ((cell & WEST) != 0)
					g.drawLine(x, y, x, y + TILE_SIZE);
				if ((cell & EAST) != 0)
					g.drawLine(x + TILE_SIZE, y, x + TILE_SIZE, y + TILE_SIZE);
			}
		}
	}

}