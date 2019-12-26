package init;

import javax.swing.JFrame;

public class GabiruAttack {
	public static void main(String[] args) {

		JFrame jFrame = new JFrame();

		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setContentPane(new GamePanel());
		jFrame.setResizable(false);
		jFrame.pack();
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);
	}
}
