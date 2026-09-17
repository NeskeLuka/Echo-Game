package input;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class KeyBindingManager {
	private final JComponent component;

	public KeyBindingManager(JComponent component) {
		this.component = component;
	}

	public void bindKey(String key, Runnable onPressed, Runnable onReleased) {
		InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = component.getActionMap();

		String pressedId = key + "Pressed";
		String releasedId = key + "Released";

		inputMap.put(KeyStroke.getKeyStroke("pressed " + key), pressedId);
		inputMap.put(KeyStroke.getKeyStroke("released " + key), releasedId);

		actionMap.put(pressedId, toAction(onPressed));
		actionMap.put(releasedId, toAction(onReleased));
	}

	private AbstractAction toAction(Runnable action) {
		return new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent event) {
				action.run();
			}
		};
	}
}