package affichage;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class FenetrePrincipale extends JFrame {
	private static final long serialVersionUID = 1L;
	public final static String titreFenetre = "Sea War"; //titre de la fenetre de jeu
	public final static int nBoutonsHaut = 3; //différent de zéro, nombre de boutons en haut
	public final static int largeurMenuHaut = 2; //approximativement la largeur du menu en haut en pourcentage
	public final static int largeurMenuGauche = 30; //approximativement la largeur du menu a gauche en pourcentage
	public final static int nCasesX = 19;
	public final static int nCasesY = 6;

	private static int longueurCote=80;	//distance le centre et un point = longueur d'un cote
	private static int apotheme; //apotheme = distance entre le centre et le milieu d'un cote
	private static int resteX;	//resteX : longueur du 'triangle' sur un cote de l'hexagone

	private static Graphics2D cg;

	private static PolyCar tab[][] = new PolyCar[nCasesX][nCasesY];
	


	public FenetrePrincipale() {
		this.setTitle(titreFenetre);
		this.setExtendedState(MAXIMIZED_BOTH); // La fenetre est créée en plein ecran
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //TODO Demander a sauvegarder en fermant?

		double dlcote = longueurCote;
		apotheme = (int) (dlcote * (Math.sqrt(3)/2)); 
		double dapotheme = apotheme;
		resteX =(int) (dapotheme - (dlcote/4));

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
		JPanel menuGauche = new JPanel(new GridLayout(1,3));
		panPrincipal.add(menuGauche, gbc);

		gbc.weightx = 100-largeurMenuGauche;
		gbc.weighty = 100-largeurMenuHaut;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = nBoutonsHaut;


		DrawingPanel plateau = new DrawingPanel();
		JScrollPane scroll = new JScrollPane(plateau);
		scroll.getVerticalScrollBar().setUnitIncrement(2*apotheme);
		scroll.getHorizontalScrollBar().setUnitIncrement(resteX+longueurCote);

		panPrincipal.add(scroll, gbc);


		this.setContentPane(panPrincipal);
		this.setVisible(true);

	}	

	public static class PolyCar{ //combinaison d'un polygone , une cooleur et plus peut etre
		public Polygon poly;
		public Color col;
		public PolyCar(Polygon p, Color c) {
			poly = p;
			col = c;
		}
	}
	//crée un hexagone au coordonnées pixel x0,y0 (!!! pour l'insant, x0 et y0 sont les coordonnées en pixels)
	public static Polygon hexagone(int x0, int y0) {
		int x = x0;
		int y = y0;

		int[] cx, cy; // tableau de coordonnées x et y de tous les points d'un hexagone en commencant par le point en haut à gauche

		cx = new int[] {x+resteX,x+longueurCote+resteX,x+longueurCote+resteX+resteX,x+longueurCote+resteX,x+resteX,x,x+resteX};
		cy = new int[] {y,y,y+apotheme,y+apotheme+apotheme,y+apotheme+apotheme,y+apotheme,y};

		return new Polygon(cx,cy,6);
	}

	// Le Panel du plateau d'hexagones
	class DrawingPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private int deb=0;
		public DrawingPanel() {
			setPreferredSize(new Dimension(nCasesX*(resteX+longueurCote)+resteX,(2*apotheme)*nCasesY+apotheme));
			setBackground(Color.BLACK);
			MyMouseListener ml = new MyMouseListener();            
			addMouseListener(ml);
		}

		class MyMouseListener extends MouseAdapter	{
			public void mouseClicked(MouseEvent e) { 
				Point p = new Point( pxtoHex(e.getX(),e.getY()) );

				if (p.x < 0 || p.y < 0 || p.x >= nCasesX || p.y >= nCasesY) return;
				if(tab[p.x][p.y].col==Color.GRAY) {tab[p.x][p.y].col=Color.CYAN;}
				else {tab[p.x][p.y].col=Color.GRAY;}
				repaint();
			}		

		}


		public void paintComponent(Graphics g) { //Utile pour l'affichage en fonction des configurations d'un environnement à l'autre
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //permet d'éviter des effets de bords moches
			super.paintComponent(g);
			cg = g2;
			if(deb==0) {
				for (int i=0;i<nCasesX;i++) {	//TODO Cela ne marche que pour une grille carrée. Créer une méthode si le type de grille change
					for (int j=0;j<nCasesY;j++) {
						drawHex(i,j,g2);
					}
				}
				deb++;
			} else {
				for (int i=0;i<nCasesX;i++) {	//TODO Cela ne marche que pour une grille carrée. Créer une méthode si le type de grille change
					for (int j=0;j<nCasesY;j++) {
						fillHex(i,j);
					}
				}
			}
		}
	}

	public static void drawHex(int i, int j, Graphics2D g2) { //Cee un hexagone en (i,j)
		int x = i * (longueurCote+resteX);
		int y = j * apotheme*2 + (i%2) * apotheme;
		Polygon poly = hexagone(x,y);
		tab[i][j]=new PolyCar(poly, Color.CYAN);
		g2.setColor(tab[i][j].col);
		g2.fillPolygon(poly);
		g2.setColor(Color.BLACK);
		g2.drawPolygon(poly);
	}

	public static void fillHex(int i, int j) { //remplit avec la lettre n et couleur
		cg.setColor(tab[i][j].col);
		cg.fillPolygon(tab[i][j].poly);
		cg.setColor(Color.BLACK);
		cg.drawPolygon(tab[i][j].poly);
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


	public static void main(String[] args) {
		@SuppressWarnings("unused")
		FenetrePrincipale f = new FenetrePrincipale();
	}
}
