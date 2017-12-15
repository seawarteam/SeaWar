package fenetre;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import partie.*;
import mvc.*;

public class FenetrePrincipale extends JFrame implements Observer{
	private static final long serialVersionUID = 1L;
	public final static String titreFenetre = "Sea War"; //titre de la fenetre de jeu
	public final static int nBoutonsHaut = 3; //different de zero, nombre de boutons en haut
	public final static int largeurMenuHaut = 2; //approximativement la largeur du menu en haut en pourcentage
	public final static int largeurMenuGauche = 30; //approximativement la largeur du menu a gauche en pourcentage
	public final static int nCasesX = 20;
	public final static int nCasesY = 20;

	private static int longueurCote;	//distance le centre et un point = longueur d'un cote
	private static int apotheme; //apotheme = distance entre le centre et le milieu d'un cote
	private static int resteX;	//resteX : longueur du 'triangle' sur un cote de l'hexagone

	private static Partie partie;
	private Controleur controleur;
	
	private JPanel plateau;
	private JScrollPane scroll;
	private InfoJoueur infoJoueur;
	private InfoCase infoCase;
	private ActionBateau actionsBateau;
	private JButton finTour;


	public FenetrePrincipale() {
		this.setTitle(titreFenetre);
		this.setExtendedState(MAXIMIZED_BOTH); // La fenetre est cree en plein ecran
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //TODO Demander a sauvegarder en fermant?

		
		plateau = new DrawingPanel();
		partie = new Partie(new String[]{"cc","gg"}, 2, nCasesX, nCasesY, 2, 10, (Observer)this);
		controleur = new Controleur(partie);
		
		setTailleHex(30);

		Position.initTabPosition(20, 20);
		
		JPanel panPrincipal = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.weightx = (int) (100-largeurMenuGauche)/nBoutonsHaut;
		gbc.weighty = largeurMenuHaut;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		JButton b1 = new JButton("Bouton1");
		panPrincipal.add(b1, gbc);


		gbc.gridx = 1;
		JButton b2 = new JButton("Bouton2");
		panPrincipal.add(b2,  gbc);

		gbc.gridx = 2;
		JButton b3 = new JButton("Bouton3");
		panPrincipal.add(b3, gbc);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = largeurMenuGauche;
		gbc.weighty = 100;
		gbc.gridx = nBoutonsHaut;
		gbc.gridheight = 2;
		MenuGauche menuGauche = new MenuGauche();
		panPrincipal.add(menuGauche, gbc);
		
		gbc.weightx = 100-largeurMenuGauche;
		gbc.weighty = 100-largeurMenuHaut;
		gbc.gridx = 0;
		gbc.gridy = 1;
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

	
	public static void drawHex(int i, int j, Graphics2D g2) { //Cree un hexagone en (i,j)
		int x = i * (longueurCote+resteX);
		int y = j * apotheme*2 + (i%2) * apotheme;
		Polygon polyg = hexagone(x,y);
		Case elt = partie.getPlateau().getCases()[i][j];
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
				elt = partie.getPlateau().getCases()[i][j];
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
				elt = partie.getPlateau().getCases()[i][j];
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
	
	public void update(Object o) {	
		if(o instanceof Navire) {updateNavire((Navire) o);}
		else if(o instanceof Case) {updateCase((Case) o);}
	}
	
	private void updateCase(Case c) {	
	/*	La case c a change ses caracteristiques donc il faut afficher les changements:
	 * 		-Sa couleur a change
	 * 		-???
	 * 
	 */
		infoCase.setBateau("");
		infoCase.setNomCase(c.toString());
		infoCase.revalidate();
		repaint();
	}
	
	private void updateNavire(Navire n) {
		/*	Le bateau n a change, c'est par ailleur celui qui est clique
		 * 
		 */
		Navire navireCourant = partie.currentJ.getCurrentN();
		if(n.equals(navireCourant)) {
			actionsBateau.setVisible(true);
		} else {actionsBateau.setVisible(false);}
		infoCase.setNomCase(partie.plateau.getCases()[posHextoHex(n.getPos()).x][posHextoHex(n.getPos()).y].toString());
		infoCase.setBateau(n.toString());
		infoCase.revalidate();
	}
	

	class ActionBateau extends JPanel{
		private static final long serialVersionUID = 3038068045018435496L;
		private JButton deplacement;
		private JButton tirP;
		private JButton tirS;
		
		public ActionBateau() {
			super(new GridBagLayout());
			GridBagConstraints gab = new GridBagConstraints();
			
			deplacement = new JButton("Deplacer");
			deplacement.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controleur.demandeDeplacement();
				}
			});
			
			tirP = new JButton("Tirer (Canon Primaire)");
			tirP.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controleur.demandeTirP();
				}
			});
			
			tirS = new JButton("Tirer (Canon secondaire)");
			tirS.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controleur.demandeTirS();
				}
			});
			
			

			gab.weightx = 100;
			gab.weighty = 50;
			gab.fill = GridBagConstraints.BOTH;
			gab.gridx = 0;
			gab.gridy = 0;
			gab.insets = new Insets(1, 2, 1, 2);
			add(deplacement, gab);
			
			gab.gridy = 1;
			add(tirP,gab);
			
			gab.gridy = 2;
			add(tirS,gab);
		}
		
	}

	class BoutonFinTour extends JButton implements MouseListener{
		private static final long serialVersionUID = 4980038874495857453L;
		
		public BoutonFinTour() {
			super("Fin tour");
			setBackground(Color.WHITE);
			this.addMouseListener(this);
			setFont(new Font(Font.SERIF,Font.BOLD,20));
		}
		
		
		public void mouseClicked(MouseEvent e) {
			controleur.demandeFinTour();
		}
		
		public void mouseEntered(MouseEvent e) {
			this.setBackground(Color.GRAY);
		}
		public void mouseExited(MouseEvent e) {
			setBackground(Color.WHITE);
		}
		public void mousePressed(MouseEvent e) {
		}
		public void mouseReleased(MouseEvent e) {
		}
		
	}
	
	// Le Panel du plateau d'hexagones
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
				controleur.hexClique(pxtoPosHex(e.getX(),e.getY()));
			}		
		}


		public void paintComponent(Graphics g) { //Utile pour l'affichage en fonction des configurations d'un environnement Ã  l'autre
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //permet d'eviter des effets de bords moches
			super.paintComponent(g);
			for (int i=0;i<nCasesX;i++) {
				for (int j=0;j<nCasesY;j++) {
					drawHex(i,j,g2);
					
				}
			}
			
		}
	}
	
	class InfoCase extends JPanel{
		private static final long serialVersionUID = -8644027093047733015L;
		private JLabel typeCase;
		private JLabel bateau;
		
		public InfoCase() {
			super(new GridLayout(2,1));
			typeCase = new JLabel("Case de type :");
			bateau = new JLabel("bateau :");
			this.add(typeCase);
			this.add(bateau);
		}
		
		public void setNomCase(String type) {
			typeCase.setText("Case de type "+type);
		}
		
		public void setBateau(String nomBateau) {
			bateau.setText("bateau : "+ nomBateau);
		}
		
	}

	class InfoJoueur extends JLabel{
		private static final long serialVersionUID = 9053249404465682334L;
		
		public InfoJoueur(String nomJoueur) {
			super();
			setHorizontalAlignment(CENTER);
			setVerticalAlignment(NORTH);
			setFont(new Font(Font.SERIF,Font.PLAIN,32));
			setText("Joueur : " + nomJoueur);
		}
		
		public void changerJoueur(String nomJoueur) {
			setText("Joueur : " + nomJoueur);
		}
	}
	
	class MenuGauche extends JPanel{
		private static final long serialVersionUID = 1L;
		public MenuGauche() {
			super(new GridBagLayout());
			GridBagConstraints g = new GridBagConstraints();

			infoJoueur = new InfoJoueur(partie.currentJ.getNom());
			infoCase = new InfoCase();
			actionsBateau = new ActionBateau();
			finTour = new BoutonFinTour();


			g.weightx = 100;
			g.weighty = 10;
			g.fill = GridBagConstraints.BOTH;
			g.gridx = 0;
			g.gridy = 0;
			add(infoJoueur, g);
			
			g.gridy = 1;
			g.weighty = 50;
			add(infoCase,g);
			
			g.gridy = 2;
			g.weighty = 30;
			add(actionsBateau,g);

			g.gridy = 3;
			g.weighty = 5;
			add(new SliderTaille(), g);
			
			g.gridy = 4;
			g.weighty = 5;
			add(finTour,g);
		}
	}
	
	class SliderTaille extends JPanel{
		private static final long serialVersionUID = 1L;
		JSlider slider;
		JLabel legende;
		
					
		public SliderTaille() {
			super(new GridLayout(2,1));
			slider = new JSlider();
			slider.setMaximum(100);
			slider.setMinimum(10);
			slider.setValue(longueurCote);
			slider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					int valeur = ( (JSlider) e.getSource()).getValue();
					setTailleHex(valeur);
					scroll.repaint();
					scroll.getVerticalScrollBar().setUnitIncrement(2*apotheme);
					scroll.getHorizontalScrollBar().setUnitIncrement(resteX+longueurCote);
					plateau.setPreferredSize(new Dimension(nCasesX*(resteX+longueurCote)+resteX,(2*apotheme)*nCasesY+apotheme));
					scroll.revalidate();
				}
			});
			
			legende = new JLabel("Taille des hexagones : ");
			add(legende);
			add(slider);
		}
	}


	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		FenetrePrincipale f = new FenetrePrincipale();
	}
}

