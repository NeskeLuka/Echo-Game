package drawGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private static final int WIDTH  = 960, HEIGHT = 720; // dimension of a panel

	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH , HEIGHT));
		setBackground(Color.BLACK);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.setColor(Color.CYAN);
		g.fillOval(480, 360, 20, 20);
	}
}
