package fenetre;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
 
import fenetre.FenetrePrincipale.ActionBateau;
import fenetre.FenetrePrincipale.BoutonFinTour;
import fenetre.FenetrePrincipale.DrawingPanel;
import fenetre.FenetrePrincipale.InfoCase;
import fenetre.FenetrePrincipale.InfoJoueur;
import fenetre.FenetrePrincipale.MenuGauche;
import fenetre.FenetrePrincipale.SliderTaille;
import fenetre.FenetrePrincipale.DrawingPanel.MyMouseListener;
 
import partie.Case;
import partie.Controleur;
import partie.ControleurModif;
import partie.Editeur;
import partie.Joueur;
import partie.Navire;
import partie.Partie;
import partie.Plateau;
import partie.Position;




public class FenetreModif extends JFrame implements Observer{
	public static void main(String [] args){
		FenetreModif f = new FenetreModif();
	}
	
	private static final long serialVersionUID = 1L;
	public final static String titreFenetre = "Sea War"; //titre de la fenetre de jeu
	public final static int nBoutonsHaut = 3; //different de zero, nombre de boutons en haut
	public final static int largeurMenuHaut = 2; //approximativement la largeur du menu en haut en pourcentage
	public final static int largeurMenuGauche = 30; //approximativement la largeur du menu a gauche en pourcentage
	public static int nCasesX;
	public static int nCasesY;

	private static int longueurCote;	//distance le centre et un point = longueur d'un cote
	private static int apotheme; //apotheme = distance entre le centre et le milieu d'un cote
	private static int resteX;	//resteX : longueur du 'triangle' sur un cote de l'hexagone
	
	
	private DrawingPanel plateau;
	private JScrollPane scroll;
	
	private JButton valider;
	
	private EditCarte editCarte;
 	private EditCanon editCanon;
	private static Editeur editeur;
	private static ControleurModif controleur;
	public JPanel panPrincipal;
	public GridBagConstraints gbc;
	
	public Graphics2D cg;
	
	public FenetreModif() {
		Position.initTabPosition(20,20);
		this.setTitle(titreFenetre);
		this.setExtendedState(MAXIMIZED_BOTH); // La fenetre est cree en plein ecran
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //TODO Demander a sauvegarder en fermant?
		
		editeur = new Editeur(20, 20, this);
		controleur = new ControleurModif(editeur);
		plateau = new DrawingPanel();
		
		nCasesX = Position.getTailleX();
		nCasesY = Position.getTailleY();
		
		setTailleHex(30);
		
		panPrincipal = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();

		gbc.weightx = (int) (100-largeurMenuGauche)/nBoutonsHaut;
		gbc.weighty = largeurMenuHaut;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		/*JButton b1 = new JButton("Bouton1");
		panPrincipal.add(b1, gbc);


		gbc.gridx = 1;
		JButton b2 = new JButton("Bouton2");
		panPrincipal.add(b2,  gbc);

		gbc.gridx = 2;
		JButton b3 = new JButton("Bouton3");
		panPrincipal.add(b3, gbc);*/

		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = largeurMenuGauche;
		gbc.weighty = 100;
		gbc.gridx = nBoutonsHaut;
		gbc.gridheight = 2;
		MenuDroite menuDroite = new MenuDroite();
		panPrincipal.add(menuDroite, gbc);
		
		gbc.weightx = 100-largeurMenuGauche;
		gbc.weighty = 100-largeurMenuHaut;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = nBoutonsHaut;

		
		plateau = new DrawingPanel();
		scroll = new JScrollPane(plateau);
		scroll.getVerticalScrollBar().setUnitIncrement(2*apotheme);
		scroll.getHorizontalScrollBar().setUnitIncrement(resteX+longueurCote);

		panPrincipal.add(scroll, gbc);
		
		this.setContentPane(panPrincipal);
		this.setVisible(true);

	}
	
	public void initFenetrePrincipale() {

		this.setVisible(true);
	}
	
	
	public static void drawHex(int i, int j, Graphics2D g2) { //Cree un hexagone en (i,j)
		int x = i * (longueurCote+resteX);
		int y = j * apotheme*2 + (i%2) * apotheme;
		Polygon polyg = hexagone(x,y);
		Case elt = editeur.getMap().getCases()[i][j];
		elt.poly = polyg;
		g2.setColor(elt.col);
		g2.fillPolygon(polyg);
		g2.setColor(Color.BLACK);
		g2.drawPolygon(polyg);
	}
	
	//cree un hexagone au coordonnees pixel x0,y0 (!!! pour l'insant, x0 et y0 sont les coordonnees en pixels)
	public static Polygon hexagone(int x0, int y0) {
		int x = x0;
		int y = y0;

		int[] cx, cy; // tableau de coordonnees x et y de tous les points d'un hexagone en commencant par le point en haut a  gauche

		cx = new int[] {x+resteX,x+longueurCote+resteX,x+longueurCote+resteX+resteX,x+longueurCote+resteX,x+resteX,x,x+resteX};
		cy = new int[] {y,y,y+apotheme,y+apotheme+apotheme,y+apotheme+apotheme,y+apotheme,y};
		return new Polygon(cx,cy,6);
	}
	
	public static Point posHextoHex(Position pos){
		return new Point(pos.getX(), pos.getY() + (int) pos.getX()/2);
		
	}
	
	public static Point pxtoHex(int mx, int my) { //on a clique sur le pixel (mx,my) et on renvoie le polygone correspondant
		Point p = new Point(-1,-1);
		Case elt;
		for(int i=0;i<nCasesX;i++) {	
			for(int j=0;j<nCasesY;j++) {
				elt = editeur.getMap().getCases()[i][j];
				if(elt.poly.contains(mx, my)) {
					p.x=i;
					p.y=j;
				}
			}
		}
		return p;
	}
	
	public static Position pxtoPosHex(int mx, int my) { //on a clique sur le pixel (mx,my) et on renvoie le polygone correspondan
		Case elt;
		for(int i=0;i<nCasesX;i++) {
			for(int j=0;j<nCasesY;j++) {
				elt = editeur.getMap().getCases()[i][j];
				if(elt.poly.contains(mx, my)) {
					return Position.tabPosition[i*nCasesY+j];
				}
			}
		}
		return null;
	}
	
	public void setTailleHex(int tailleCote) {
		longueurCote = tailleCote;
		double dlcote = longueurCote;
		apotheme = (int) (dlcote * (Math.sqrt(3)/2)); 
		double dapotheme = apotheme;
		resteX =(int) (dapotheme - (dlcote/4));	
	}
	
	class DrawingPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public DrawingPanel() {
			setPreferredSize(new Dimension(nCasesX*(resteX+longueurCote)+resteX,(2*apotheme)*nCasesY+apotheme));
			setBackground(Color.BLACK);
			MyMouseListener ml = new MyMouseListener();            
			addMouseListener(ml);
		}

		class MyMouseListener extends MouseAdapter	{
			public void mouseClicked(MouseEvent e) { 
				Position pos = pxtoPosHex(e.getX(),e.getY());
				if(pos != null) {
					System.out.println("Position sélectionnée :"+pos.toString());
					controleur.hexClique(pos);
				}
			}		
		}
		
		public void paintComponent(Graphics g) { //Utile pour l'affichage en fonction des configurations d'un environnement ÃƒÆ’  l'autre
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //permet d'eviter des effets de bords moches
			cg = g2;
			super.paintComponent(g);
			for (int i=0;i<nCasesX;i++) {
				for (int j=0;j<nCasesY;j++) {
					drawHex(i,j,g2);
				}
			}
		}
	}
	
	public void update(Observable obs, Object o) {
		if(o instanceof Case) {
			updateCase((Case) o);
		}
	}
	
	private void updateCase(Case c) {	
			repaint();
			revalidate();
		}
	
	
class EditCarte extends JPanel{
		private static final long serialVersionUID = 448318553800706885L;
		private ButtonEdit editButton;
		private JButton ajoutRocher;
		private JButton ajoutEau;
		private JButton ajoutPhare;
		private GridLayout grid;
		private JButton ajoutBases;
		JList<String> listBase;
		String[] data = {"Joueur1", "Joueur2", "Joueur3", "Joueur4", "Joueur5", "Joueur6"};
		public EditCarte(){
			grid = new GridLayout(4, 1);
			
			listBase = new JList<String>(data);
			listBase.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent arg0) {
					String str = null;
					switch(listBase.getAnchorSelectionIndex()) {
					case 0:
						str = "Joueur1";
						break;
					case 1:
						str="Joueur2";
						break;
					case 2:
						str = "Joueur3";
						break;
					case 3:
						str = "Joueur4";
						break;
					case 4:
						str = "Joueur5";
						break;
					case 5:
						str = "Joueur6";
						break;
					}
					controleur.demandeAjoutBase(str);
				}
				public void mouseEntered(MouseEvent arg0) {
				}
				public void mouseExited(MouseEvent arg0) {
				}
				public void mousePressed(MouseEvent arg0) {	
				}
				public void mouseReleased(MouseEvent arg0) {
				}
				
					
			});
			ajoutRocher = new JButton("Ajouter rochers");
			ajoutRocher.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controleur.demandeAjoutRocher();
				}
			});
			
			ajoutEau = new JButton("Ajouter eau");
			ajoutEau.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controleur.demandeAjoutEau();
				}
			});
			ajoutPhare = new JButton("Ajout phares");
			ajoutPhare.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controleur.demandeAjoutPhare();
				}
			});
			editButton = new ButtonEdit("Editer carte", "Valider");
			editButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controleur.demandeModifMap();
				}
			});
			JPanel j = new JPanel();
			j.setLayout(grid);
			j.add(editButton);
			j.add(ajoutEau);
			j.add(ajoutRocher);
			j.add(ajoutPhare);
			//j.add(ajoutBases);
			j.add(listBase);
			add(j);
		}
	}
 	
 	

	class EditCanon extends JPanel{
		private static final long serialVersionUID = 448318553800706885L;
		private ButtonEdit editButtonP;
		private ButtonEdit editButtonS;
		private GridLayout grid;
		
		public EditCanon(){
			grid = new GridLayout(4, 1);
			editButtonP = new ButtonEdit("Editer Canon Primaire", "Valider");
			editButtonP.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controleur.demandeModifCanonP();
				}
			});
			editButtonS = new ButtonEdit("Editer Canon Secondaire", "Valider");
			editButtonS.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controleur.demandeModifCanonS();
				}
			});
			JPanel j = new JPanel();
			j.setLayout(grid);
			j.add(editButtonP);
			j.add(editButtonS);
			add(j);
		}
	}
 	
	class ButtonEdit extends JButton implements MouseListener{
		private static final long serialVersionUID = 9161144644862571496L;
		private String label1;
		private String label2;
		private String label;
		public ButtonEdit(String l1, String l2) {
			super(l1);
			label1 = l1;
			label2 = l2;
			label = label1;
			setBackground(Color.WHITE);
			this.addMouseListener(this);
			setFont(new Font(Font.SERIF,Font.BOLD,15));
		}
		
		private void commuteLabel(){
			if(label.equals(label1)){
				label = label2;
				setText(label2);
			}else{
				label = label1;
				setText(label1);
			}
		}
		public void mouseClicked(MouseEvent arg0) {
			commuteLabel();
			
		}

		public void mouseEntered(MouseEvent arg0) {
			setBackground(Color.GRAY);
			
		}

		public void mouseExited(MouseEvent arg0) {
			setBackground(Color.WHITE);
			
		}

		public void mousePressed(MouseEvent arg0) {
			// TODO Stub de la méthode généré automatiquement
			
		}

		public void mouseReleased(MouseEvent arg0) {
			// TODO Stub de la méthode généré automatiquement
			
		}

	}
		
	
	
	
	class MenuDroite extends JPanel{
		private static final long serialVersionUID = 1L;
		
		public MenuDroite() {
			super(new GridBagLayout());
			GridBagConstraints g = new GridBagConstraints();
			editCarte = new EditCarte();
			editCanon = new EditCanon();
			


			g.weightx = 100;
			g.weighty = 10;
			g.fill = GridBagConstraints.BOTH;
			g.gridx = 0;
			g.gridy = 0;
			add(editCarte, g);
			
			g.gridy = 1;
			g.weighty = 50;
			add(editCanon,g);
			
			g.gridy = 2;
			g.weighty = 30;
			//add(actionsBateau,g);
			//actionsBateau.setVisible(false);
			
			g.gridy = 3;
			g.weighty = 5;
			//add(new SliderTaille(), g);
			
			g.gridy = 4;
			g.weighty = 5;
			//add(finTour,g);
			
			
		}
	}
}
	
	
