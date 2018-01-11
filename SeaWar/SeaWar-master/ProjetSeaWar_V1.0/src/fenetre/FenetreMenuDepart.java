package fenetre;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FenetreMenuDepart extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel imagePanel;
	private String filePath = "bateau.jpg";

	public FenetreMenuDepart() {
		setTitle("Menu Principal");
		setSize(600,400);
		setLocationRelativeTo(null);	
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		
		
		imagePanel = new JPanel() {
			private static final long serialVersionUID = 1L;
			public void paint(Graphics g) {
				try {
					BufferedImage image = ImageIO.read(new File(filePath));
					g.drawImage(image, 0, 0, null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		imagePanel.setPreferredSize(new Dimension(620, 412));
		
		
		JPanel pan1 = new JPanel();
		JPanel pan2 = new JPanel();
		pan1.setLayout(new GridLayout(3, 1));
		pan1.add(new BoutonPartieRapide());
		pan1.add(new BoutonPartiePersonalisee());
		pan1.add(new BoutonOptions());
		/*pan2.add(pan1);
		imagePanel.add(pan2);*/
		imagePanel.add(pan1);
		setContentPane(imagePanel);
		
		pack();
		setVisible(true);
	}
	
	class BoutonPartieRapide extends JButton implements MouseListener{
		private static final long serialVersionUID = 1L;
		private final static String titre = "Partie Rapide"; 
		
		public BoutonPartieRapide() {
			super(titre);
			addMouseListener(this);
		}

		
		public void mouseClicked(MouseEvent arg0) {
			FenetreChoixNoms f = new FenetreChoixNoms();
			dispose();
			
		}

		
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class BoutonPartiePersonalisee extends JButton implements MouseListener{
		private static final long serialVersionUID = 1L;
		private final static String nom = "Partie Personnalisee (non disponible)";
		
		public BoutonPartiePersonalisee() {
			super(nom);
			addMouseListener(this);
			setEnabled(true);
		}

		
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class BoutonOptions extends JButton implements MouseListener{
		private static final long serialVersionUID = 1L;
		private static final String nom = "Options (non disponible)";
		
		public BoutonOptions() {
			super(nom);
			addMouseListener(this);
			setEnabled(true);
		}
		

		
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}

	public static void main(String[] args) {
		FenetreMenuDepart f = new FenetreMenuDepart();
	}
}