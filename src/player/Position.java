package player;

public class Position {
	private int x, y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void translateX(int dx) {
		this.x += dx;
	}

	public void translateY(int dx) {
		this.y += dx;
	}
}
