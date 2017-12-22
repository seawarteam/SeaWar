package fenetre;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class FenetreMenuDepart extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public FenetreMenuDepart(){
		setTitle("Menu Principal");
		setSize(400,500);
		setLocationRelativeTo(null);	
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setLayout(new GridLayout(3, 1));
		
		add(new BoutonPartieRapide());
		add(new BoutonPartiePersonalisee());
		add(new BoutonOptions());
		
		
		
		
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
			setEnabled(false);
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
			setEnabled(false);
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

	public static void main(String[] args){
		FenetreMenuDepart f = new FenetreMenuDepart();
	}
}
