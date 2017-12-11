import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FenetrePrincipale extends JFrame {
	private static final long serialVersionUID = 1L;
	public final static String titreFenetre = "Sea War"; //titre de la fenetre de jeu
	public final static int nBoutonsHaut = 3; //diffÃ©rent de zÃ©ro, nombre de boutons en haut
	public final static int largeurMenuHaut = 2; //approximativement la largeur du menu en haut en pourcentage
	public final static int largeurMenuGauche = 30; //approximativement la largeur du menu a gauche en pourcentage
	public final static int nCasesX = 20;
	public final static int nCasesY = 20;

	private static int longueurCote;	//distance le centre et un point = longueur d'un cote
	private static int apotheme; //apotheme = distance entre le centre et le milieu d'un cote
	private static int resteX;	//resteX : longueur du 'triangle' sur un cote de l'hexagone

	private Graphics2D cg;

	private Partie partie;
	
	private static PolyCar tab[][] = new PolyCar[nCasesX][nCasesY];

	private JPanel plateau;
	private JScrollPane scroll;
	private InfoJoueur infoJoueur;
	private InfoCase infoCase;
	private ActionBateau actionsBateau;
	private JButton finTour;


	public FenetrePrincipale() {
		this.setTitle(titreFenetre);
		this.setExtendedState(MAXIMIZED_BOTH); // La fenetre est crÃ©Ã©e en plein ecran
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //TODO Demander a sauvegarder en fermant?

		setTailleHex(30);
		initTabHex();
		partie = new Partie(new String[]{"J1","J2"}, 2);


		
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

	public void setTailleHex(int tailleCote) {
		longueurCote = tailleCote;
		double dlcote = longueurCote;
		apotheme = (int) (dlcote * (Math.sqrt(3)/2)); 
		double dapotheme = apotheme;
		resteX =(int) (dapotheme - (dlcote/4));	
	}
	

	
	
	public static class PolyCar{ //combinaison d'un polygone , une cooleur et plus peut etre
		public Polygon poly;
		public Color col;
		public PolyCar(Polygon p, Color c) {
			poly = p;
			col = c;
		}
	}
	//crÃ©e un hexagone au coordonnÃ©es pixel x0,y0 (!!! pour l'insant, x0 et y0 sont les coordonnÃ©es en pixels)
	public static Polygon hexagone(int x0, int y0) {
		int x = x0;
		int y = y0;

		int[] cx, cy; // tableau de coordonnÃ©es x et y de tous les points d'un hexagone en commencant par le point en haut Ã  gauche

		cx = new int[] {x+resteX,x+longueurCote+resteX,x+longueurCote+resteX+resteX,x+longueurCote+resteX,x+resteX,x,x+resteX};
		cy = new int[] {y,y,y+apotheme,y+apotheme+apotheme,y+apotheme+apotheme,y+apotheme,y};

		return new Polygon(cx,cy,6);
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
				Point p = new Point( pxtoHex(e.getX(),e.getY()) );

				partie.hexClique(p.x, p.y);
				
				//plateau.repaint();
			}		

		}


		public void paintComponent(Graphics g) { //Utile pour l'affichage en fonction des configurations d'un environnement Ã  l'autre
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //permet d'Ã©viter des effets de bords moches
			super.paintComponent(g);
			cg = g2;
			for (int i=0;i<nCasesX;i++) {	//TODO Cela ne marche que pour une grille carrÃ©e. CrÃ©er une mÃ©thode si le type de grille change
				for (int j=0;j<nCasesY;j++) {
					drawHex(i,j,g2);
				}
			}
			
		}
	}
	private void initTabHex() {
		for (int i=0;i<nCasesX;i++) {
			for (int j=0;j<nCasesY;j++) {
				int x = i * (longueurCote+resteX);
				int y = j * apotheme*2 + (i%2) * apotheme;
				Polygon poly = hexagone(x,y);
				tab[i][j]=new PolyCar(poly, Color.CYAN);
			}
		}
	}
	
	
	public static void drawHex(int i, int j, Graphics2D g2) { //Cee un hexagone en (i,j)
		int x = i * (longueurCote+resteX);
		int y = j * apotheme*2 + (i%2) * apotheme;
		Polygon polyg = hexagone(x,y);
		tab[i][j].poly = polyg;
		g2.setColor(tab[i][j].col);
		g2.fillPolygon(polyg);
		g2.setColor(Color.BLACK);
		g2.drawPolygon(polyg);
	}

	public static Point pxtoHex(int mx, int my) { //on a clique sur le pixel (mx,my) et on renvoie le polygone correspondant
		Point p = new Point(-1,-1);

		for(int i=0;i<nCasesX;i++) {
			for(int j=0;j<nCasesY;j++) {
				if(tab[i][j].poly.contains(mx, my)) {
					p.x=i;
					p.y=j;
				}
			}
		}
		return p;
	}
	
	public void changerColCase(int i, int j, Color c) { //Change la couleur d'une seule case par la couleur c
		tab[i][j].col = c;
		cg.fillPolygon(tab[i][j].poly);
		repaint();
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
	
	class ActionBateau extends JPanel{
		private static final long serialVersionUID = 3038068045018435496L;
		private JButton deplacement;
		private JButton tir;
		
		public ActionBateau() {
			super(new GridBagLayout());
			GridBagConstraints gab = new GridBagConstraints();
			
			deplacement = new JButton("Deplacer");
			deplacement.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// TODO demander au controleur d'afficher les cases ou on peut se deplacer 
					System.out.println("deplacer appuye");
				}
			});
			
			tir = new JButton("Tirer");
			tir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// TODO demander au controleur ou on peut tirer
					System.out.println("tirer appuye");
				}
			});
			

			gab.weightx = 100;
			gab.weighty = 50;
			gab.fill = GridBagConstraints.BOTH;
			gab.gridx = 0;
			gab.gridy = 0;
			gab.insets = new Insets(10, 10, 10, 10);
			add(deplacement, gab);
			
			gab.gridy = 1;
			add(tir,gab);
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
	
	class BoutonFinTour extends JButton implements MouseListener{
		private static final long serialVersionUID = 4980038874495857453L;
		
		public BoutonFinTour() {
			super("Fin tour");
			setBackground(Color.WHITE);
			this.addMouseListener(this);
			setFont(new Font(Font.SERIF,Font.BOLD,20));
		}
		
		
		public void mouseClicked(MouseEvent e) {
			//TODO appeler fintour dans le controleur

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
	
	class MenuGauche extends JPanel{
		private static final long serialVersionUID = 1L;
		public MenuGauche() {
			super(new GridBagLayout());
			GridBagConstraints g = new GridBagConstraints();

			infoJoueur = new InfoJoueur(partie.getJoueur().getNom());
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
	
	public void updateCaseTir() {
		
	}
	
	public void updateCaseDeplacement() {
		
	}
	
	
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		FenetrePrincipale f = new FenetrePrincipale();
	}
}
