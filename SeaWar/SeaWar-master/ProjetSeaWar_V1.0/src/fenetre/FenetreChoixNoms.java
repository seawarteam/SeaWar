package fenetre;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import partie.SeaWar;

public class FenetreChoixNoms extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField j1;
	private JTextField j2;

	public FenetreChoixNoms() {
		setTitle("Selection des Joueurs");
		setSize(400,500);
		setLocationRelativeTo(null);	
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLayout(new GridLayout(5,1));

		add(new JLabel(" Nom du joueur 1 :"));

		j1 = new JTextField();
		add(j1);

		add(new JLabel(" Nom du joueur 2 :"));

		j2 = new JTextField();
		add(j2);

		add(new BoutonValider());

		setVisible(true);
	}

	class BoutonValider extends JButton implements MouseListener{
		private static final long serialVersionUID = 1L;
		private final static String nom= "Commencer la partie";

		public BoutonValider() {
			super(nom);
			addMouseListener(this);
		}


		public void mouseClicked(MouseEvent arg0) {
			String jou1 = j1.getText();
			String jou2 = j2.getText();

			if(jou1.equals("") || jou2.equals("")){
				// TODO afficher à l'utilisateur qu'il ne faut pas un nom vide
			} else if(jou1.equals(jou2)){
				// TODO dire qu'il faut pas les memes noms
			} else {
				SeaWar.partieRapide(jou1, jou2);
				dispose();
			}

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

	public static void main(String[] args){
		FenetreChoixNoms f = new FenetreChoixNoms();
	}

}
