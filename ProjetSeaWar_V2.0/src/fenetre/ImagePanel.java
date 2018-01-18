package fenetre;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {
	private BufferedImage image;
	
	public ImagePanel(String filePath) {
		try {
			image = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Point getTailleImage(String filePath) {
		
		BufferedImage im ;
		Point p = new Point(0,0);
		try {
			im = ImageIO.read(new File(filePath));
			p.x= im.getWidth();
			p.y= im.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		return p;
	}
	
	public void paintComponent(Graphics g) {
		//super.paintComponents(g);
		g.drawImage(image, 0, 0, null);
	}
}
