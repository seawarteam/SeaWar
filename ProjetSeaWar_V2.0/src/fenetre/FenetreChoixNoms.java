package fenetre;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import partie.SeaWar;

public class FenetreChoixNoms extends JFrame {
	private static final long serialVersionUID = 1L;

	private ImagePanel imagePanel;
	private String filePath = "bateau.jpg";

	private JTextField j1;
	private JTextField j2;

	public FenetreChoixNoms() {
		setTitle("Selection des Joueurs");
		Point p = ImagePanel.getTailleImage(filePath);
		setSize(p.x, p.y);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		imagePanel = new ImagePanel(filePath);
		imagePanel.setPreferredSize(new Dimension(620, 412));

		JPanel pan = new JPanel();
		pan.setLayout(new GridLayout(3, 1));
		JPanel pan2 = new JPanel();
		pan2.add(pan);
		JPanel pan3 = new JPanel();
		pan3.setLayout(new GridLayout(2, 2));

		pan3.add(new JLabel(" Nom du joueur 1 :"));

		j1 = new JTextField();
		pan3.add(j1);

		pan3.add(new JLabel(" Nom du joueur 2 :"));

		j2 = new JTextField();
		pan3.add(j2);

		pan.add(pan3);
		pan.add(new BoutonValider());
		pan.add(new BoutonRetour());

		setContentPane(imagePanel);
		getContentPane().add(pan);

		setVisible(true);
	}

	class BoutonValider extends JButton implements MouseListener {
		private static final long serialVersionUID = 1L;
		private final static String nom = "Commencer la partie";

		public BoutonValider() {
			super(nom);
			addMouseListener(this);
			addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent key) {
					switch (key.getKeyCode()) {
						case KeyEvent.VK_SPACE: {
							valider();
							break;
						}
						case KeyEvent.VK_ENTER: {
							valider();
							break;
						}
						
						
					}
				}
				public void keyTyped(KeyEvent arg0) {}
				public void keyReleased(KeyEvent arg0) {}
			});
		}

		public void mouseClicked(MouseEvent arg0) {
			valider();
		}

		public void mouseEntered(MouseEvent arg0) {
		}

		public void mouseExited(MouseEvent arg0) {
		}

		public void mousePressed(MouseEvent arg0) {
		}

		public void mouseReleased(MouseEvent arg0) {
		}

		private void valider() {
			String jou1 = j1.getText();
			String jou2 = j2.getText();

			if (jou1.equals("") || jou2.equals("")) {
				// TODO afficher à l'utilisateur qu'il ne faut pas un nom vide
			} else if (jou1.equals(jou2)) {
				// TODO dire qu'il faut pas les memes noms
			} else {
				SeaWar.partieRapide(jou1, jou2);
				dispose();
			}
		}

	}

	class BoutonRetour extends JButton implements MouseListener {
		private static final long serialVersionUID = 1L;
		private final static String nom = "Retour au menu principal";

		public BoutonRetour() {
			super(nom);
			addMouseListener(this);
			addKeyListener(new KeyListener() {

				public void keyTyped(KeyEvent arg0) {
				}

				public void keyReleased(KeyEvent arg0) {
				}

				public void keyPressed(KeyEvent arg0) {
					new FenetreMenuDepart();
					dispose();

				}
			});
		}

		public void mouseClicked(MouseEvent arg0) {
			new FenetreMenuDepart();
			dispose();

		}

		public void mouseEntered(MouseEvent arg0) {
		}

		public void mouseExited(MouseEvent arg0) {
		}

		public void mousePressed(MouseEvent arg0) {
		}

		public void mouseReleased(MouseEvent arg0) {
		}
 
	}

	public static void main(String[] args) {
		new FenetreChoixNoms();
	}

}
