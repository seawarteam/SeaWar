package fenetre;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FenetreMenuDepart extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private ImagePanel imagePanel;
	private String filePath = "bateau.jpg";

	public FenetreMenuDepart() {
		setTitle("Menu Principal");
		setSize(600,400);
		setLocationRelativeTo(null);	
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		
		imagePanel = new ImagePanel(filePath);
		imagePanel.setPreferredSize(new Dimension(620, 412));
			
		
		JPanel pan1 = new JPanel();
		pan1.setLayout(new GridLayout(5, 1));
		pan1.add(new BoutonPartieRapide());
		pan1.add(new BoutonPartiePersonalisee());
		pan1.add(new BoutonCharger());
		pan1.add(new BoutonEditer());
		pan1.add(new BoutonQuitter());
		
		
		setContentPane(imagePanel);
		getContentPane().add(pan1);
		
		
		setVisible(true);
	}
	


	class BoutonPartieRapide extends JButton implements MouseListener{
		private static final long serialVersionUID = 1L;
		private final static String titre = "Partie Rapide"; 
		
		public BoutonPartieRapide() {
			super(titre);
			addMouseListener(this);
			addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent key) {
					switch (key.getKeyCode()) {
						case KeyEvent.VK_SPACE: {
							new FenetreChoixNoms();
							dispose();
							break;
						}
						case KeyEvent.VK_ENTER: {
							new FenetreChoixNoms();
							dispose();
							break;
						}
					}
				}
				public void keyTyped(KeyEvent arg0) {}
				public void keyReleased(KeyEvent arg0) {}
			});
		}

		
		public void mouseClicked(MouseEvent arg0) {
			new FenetreChoixNoms();
			dispose();
			
		}
		
		public void mouseEntered(MouseEvent arg0) {}		
		public void mouseExited(MouseEvent arg0) {}		
		public void mousePressed(MouseEvent arg0) {}		
		public void mouseReleased(MouseEvent arg0) {}
		
	}
	
	class BoutonPartiePersonalisee extends JButton implements MouseListener{
		private static final long serialVersionUID = 1L;
		private final static String nom = "Partie Personnalisee";
		
		public BoutonPartiePersonalisee() {
			super(nom);
			addMouseListener(this);
			setEnabled(true);
			addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent key) {
					switch (key.getKeyCode()) {
						case KeyEvent.VK_SPACE: {
							new FenetreChoixPartie();
							dispose();
							break;
						}
						case KeyEvent.VK_ENTER: {
							new FenetreChoixPartie();
							dispose();
							break;
						}
					}
				}
				public void keyTyped(KeyEvent arg0) {}
				public void keyReleased(KeyEvent arg0) {}
			});
		}

		
		public void mouseClicked(MouseEvent arg0) {
			new FenetreChoixPartie();
			dispose();		
		}

		
		public void mouseEntered(MouseEvent arg0) {}		
		public void mouseExited(MouseEvent arg0) {}		
		public void mousePressed(MouseEvent arg0) {}		
		public void mouseReleased(MouseEvent arg0) {}
		
	}
	
	class BoutonCharger extends JButton implements MouseListener{
		private static final long serialVersionUID = 1L;
		private final static String titre = "Charger une Partie (non disponible)"; 
		
		public BoutonCharger() {
			super(titre);
			addMouseListener(this);
			addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent key) {
					switch (key.getKeyCode()) {
						case KeyEvent.VK_SPACE: {
							// TODO : 
							break;
						}
						case KeyEvent.VK_ENTER: {
							// TODO : 
							break;
						}
					}
				}
				public void keyTyped(KeyEvent arg0) {}
				public void keyReleased(KeyEvent arg0) {}
			});
		}

		
		public void mouseClicked(MouseEvent arg0) {
			//TODO: 
		}
		
		public void mouseEntered(MouseEvent arg0) {}		
		public void mouseExited(MouseEvent arg0) {}		
		public void mousePressed(MouseEvent arg0) {}		
		public void mouseReleased(MouseEvent arg0) {}
		
	}
	
	class BoutonEditer extends JButton implements MouseListener{
		private static final long serialVersionUID = 1L;
		private final static String titre = "Editeur"; 
		
		public BoutonEditer() {
			super(titre);
			addMouseListener(this);
			addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent key) {
					switch (key.getKeyCode()) {
						case KeyEvent.VK_SPACE: {
							new FenetreModif();
							dispose();
							break;
						}
						case KeyEvent.VK_ENTER: {
							new FenetreModif();
							dispose();
							break;
						}
					}
				}
				public void keyTyped(KeyEvent arg0) {}
				public void keyReleased(KeyEvent arg0) {}
			});
		}

		
		public void mouseClicked(MouseEvent arg0) {
			new FenetreModif();
			dispose();
			
		}
		
		public void mouseEntered(MouseEvent arg0) {}		
		public void mouseExited(MouseEvent arg0) {}		
		public void mousePressed(MouseEvent arg0) {}		
		public void mouseReleased(MouseEvent arg0) {}
		
	}
	
	class BoutonQuitter extends JButton implements MouseListener{
		private static final long serialVersionUID = 1L;
		private static final String nom = "Quitter";
		
		public BoutonQuitter() {
			super(nom);
			addMouseListener(this);
			addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent key) {
					switch (key.getKeyCode()) {
						case KeyEvent.VK_SPACE: {
							System.exit(EXIT_ON_CLOSE);
							break;
						}
						case KeyEvent.VK_ENTER: {
							System.exit(EXIT_ON_CLOSE);
							break;
						}
					}
				}
				public void keyTyped(KeyEvent arg0) {}
				public void keyReleased(KeyEvent arg0) {}
			});
		}
		

		
		public void mouseClicked(MouseEvent arg0) {
			System.exit(EXIT_ON_CLOSE);
		}

		
		public void mouseEntered(MouseEvent arg0) {}		
		public void mouseExited(MouseEvent arg0) {}		
		public void mousePressed(MouseEvent arg0) {}		
		public void mouseReleased(MouseEvent arg0) {}
		
	}

	public static void main(String[] args) {
		new FenetreMenuDepart();
	}
}
