package main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import drawGame.*;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame window = new JFrame("ECHO");
			GamePanel gp = new GamePanel();
			window.setContentPane(gp);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.pack();
			window.setLocationRelativeTo(null);
			window.setVisible(true);
		});
	}
}
