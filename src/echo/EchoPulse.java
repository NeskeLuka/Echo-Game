package echo;

public class EchoPulse {
	private static final double BAND_WIDTH = 1500;

	private final double originX, originY;
	private final double maxRadius;
	private final double speed;
	private double radius = 0;
	private boolean expired = false;

	public EchoPulse(double originX, double originY, double maxRadius, double speed) {
		this.originX = originX;
		this.originY = originY;
		this.maxRadius = maxRadius;
		this.speed = speed;
	}

	public void update() {
		radius += speed;

		if (radius >= maxRadius + BAND_WIDTH) {
			expired = true;
		}
	}

	public boolean isExpired() {
		return expired;
	}

	public double intensityAt(double x, double y) {
		double distance = Math.hypot(x - originX, y - originY);

		if (distance > maxRadius) {
			return 0;
		}

		double behindEdge = radius - distance;

		if (behindEdge < 0 || behindEdge > BAND_WIDTH) {
			return 0;
		}

		return 1.0 - behindEdge / BAND_WIDTH;
	}
}