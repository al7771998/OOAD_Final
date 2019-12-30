import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;

class ColorIcon implements Icon {
	private final Color color;

	protected ColorIcon(Color color) {
		this.color = color;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.translate(x, y);
		g2.setPaint(color);
		g2.fillRect(1, 1, getIconWidth() - 2, getIconHeight() - 2);
		g2.dispose();
	}

	public int getIconWidth() {
		return 12;
	}

	public int getIconHeight() {
		return 12;
	}

}