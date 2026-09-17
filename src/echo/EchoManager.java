package echo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EchoManager {
	private static final double MAX_RADIUS = 500;
	private static final double SPEED = 8;

	private final List<EchoPulse> pulses = new ArrayList<>();

	public void trigger(double originX, double originY) {
		pulses.add(new EchoPulse(originX, originY, MAX_RADIUS, SPEED));
	}

	public void update() {
		Iterator<EchoPulse> iterator = pulses.iterator();
		while (iterator.hasNext()) {
			EchoPulse pulse = iterator.next();
			pulse.update();
			if (pulse.isExpired()) {
				iterator.remove();
			}
		}
	}

	public List<EchoPulse> getPulses() {
		return pulses;
	}
}