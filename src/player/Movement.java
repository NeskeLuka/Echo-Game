package player;

import java.util.EnumMap;
import java.util.Map;

class Movement {
	private final Map<Direction, Boolean> active = new EnumMap<>(Direction.class);
	private Position position;

	Movement(Position position) {
		for (Direction direction : Direction.values()) {
			active.put(direction, false);
		}
		this.position = position;
	}

	void setActive(Direction direction, boolean isActive) {
		active.put(direction, isActive);
	}

	int[] computeDelta(int speed) {
		int moveX = 0, moveY = 0;
		if (active.get(Direction.UP) && (position.getY() - speed >= 0))
			moveY -= speed;
		if (active.get(Direction.DOWN) && (position.getY() + speed < 700))
			moveY += speed;
		if (active.get(Direction.LEFT) && (position.getX() - speed >= 0))
			moveX -= speed;
		if (active.get(Direction.RIGHT) && (position.getX() + speed < 960))
			moveX += speed;

		if (moveX != 0 && moveY != 0) {
			moveX = (int) Math.round(moveX * 0.7071);
			moveY = (int) Math.round(moveY * 0.7071);
		}
		return new int[] { moveX, moveY };
	}
}