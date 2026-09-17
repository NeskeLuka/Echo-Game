package player;

import java.util.EnumMap;
import java.util.Map;

class Movement {
	private final Map<Direction, Boolean> active = new EnumMap<>(Direction.class);

	Movement() {
		for (Direction direction : Direction.values()) {
			active.put(direction, false);
		}
	}

	void setActive(Direction direction, boolean isActive) {
		active.put(direction, isActive);
	}

	int[] computeDelta(int speed) {
		int moveX = 0;
		int moveY = 0;

		if (active.get(Direction.UP))
			moveY -= speed;
		if (active.get(Direction.DOWN))
			moveY += speed;
		if (active.get(Direction.LEFT))
			moveX -= speed;
		if (active.get(Direction.RIGHT))
			moveX += speed;

		if (moveX != 0 && moveY != 0) {
			moveX = (int) Math.round(moveX / Math.sqrt(2));
			moveY = (int) Math.round(moveY / Math.sqrt(2));
		}

		return new int[] { moveX, moveY };
	}
}