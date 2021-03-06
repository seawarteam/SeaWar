package fenetre;

import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.event.*;

import partie.*;

public class FenetrePrincipale extends JFrame implements Observer{
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

	private static Partie partie;
	private Controleur controleur;
	
	private DrawingPanel plateau;
	private JScrollPane scroll;
	private InfoJoueur infoJoueur;
	private InfoCase infoCase;
	private ActionBateau actionsBateau;
	private JButton finTour;
	
	private FenetrePrincipale me;
	private boolean finPartie;

	public JPanel panPrincipal;
	public GridBagConstraints gbc;
	
	public Graphics2D cg;
	
	public FenetrePrincipale(Partie p, Controleur c) {
		
		me = this;
		this.setTitle(titreFenetre);
		this.setExtendedState(MAXIMIZED_BOTH); // La fenetre est cree en plein ecran
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //TODO Demander a sauvegarder en fermant?

		
		//Partie p = new Partie(new String[]{"J1","J2"}, nCasesX, nCasesY, 2, 25, this);
		plateau = new DrawingPanel();
		partie =  p;
		controleur = c;//new Controleur(partie);
		nCasesX = Position.getTailleX();
		nCasesY = Position.getTailleY();
		finPartie = false;
		
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
		MenuGauche menuGauche = new MenuGauche();
		panPrincipal.add(menuGauche, gbc);
		
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
	
	public Partie getPartie() {
		return partie;
	}

	public void initFenetrePrincipale() {

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
	
	public void update(Observable obs, Object o) {
		if(o instanceof Navire) {updateNavire((Navire) o);}
		else if(o instanceof Case) {
			updateCase((Case) o);
		} else if(o instanceof Partie) {
			updatePartie((Partie) o);
		}
	}
	
	
	private void updateCase(Case c) {	
		actionsBateau.setVisible(false);
		infoCase.setNomCase(c);
		infoCase.revalidate();
		repaint();
		revalidate();
	}
	
	private void updateNavire(Navire n) {
		Navire navireCourant = partie.currentJ.getCurrentN();
		if(n.equals(navireCourant)) {
			actionsBateau.setVisible(true);
		} else {
			actionsBateau.setVisible(false);
		}
		infoCase.setNomCase(partie.plateau.getCases()[posHextoHex(n.getPos()).x][posHextoHex(n.getPos()).y]);
		infoCase.setBateau(n,partie.getCaseOnPos(n.getPos()));
		infoCase.revalidate();
		plateau.repaint();
	}
	
	private void updatePartie(Partie p) {
		if(!finPartie)	 {
			infoJoueur.changerJoueur(partie.currentJ.getNom());
			if(p.currentJ.getNavEtatCourant()==null) {
				actionsBateau.setVisible(false);
			}
			if(p.gagne){
				finPartie = true;
				JOptionPane.showOptionDialog(this, "Victoire de "+ partie.jGagnant.getNom(), "Bravo !", JOptionPane.DEFAULT_OPTION	, JOptionPane.INFORMATION_MESSAGE, null, null, null);
				new FenetreMenuDepart();
				this.dispose();
	
			}
			plateau.repaint();
		}
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
				Position pos = pxtoPosHex(e.getX(),e.getY());
				if(pos != null) {
					controleur.hexClique(pos);
				}
			}		
		}


		public void paintComponent(Graphics g) { //Utile pour l'affichage en fonction des configurations d'un environnement ÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢  l'autre
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
	
	
	class InfoCase extends JPanel{
		private static final long serialVersionUID = -8644027093047733015L;
		private JLabel typeCase;
		private String txtCase;
		
		public InfoCase() {
			super(new GridLayout(1,1));
			typeCase = new JLabel("");
			this.add(typeCase);
		}
		
		public void setNomCase(Case type) {
			typeCase.setText("Case de type "+type);
			txtCase = "Case de type "+type;
			if(type instanceof Phare) {
				infoCase.setNomPhare(type.toString(), ((Phare) type).occupeeDefinitivementPar);
			}
		}
		
		private void setNomPhare(String type, Navire proprio) {
			String sPh = ("Case de type "+type);
			if(proprio==null) {
				sPh += " pas occupee";
			} else {
				sPh += " occupee par " + proprio.getNomJ();
			}
			typeCase.setText(sPh);
			txtCase = sPh;
		}
		
		public void setBateau(Navire nav,Case cas) {
			String sBat = ("<html>" + txtCase);
			if(nav!=(null)) {
				sBat += "<br><br>";
				sBat += "<table><tr><td>Proprietaire : </td><td>"+nav.getNomJ()+"</td></tr>";
				sBat += "<tr><td>Nom du bateau : </td><td>"+nav.getNom()+"</td></tr>";
				sBat += "<tr><td>PV restants : </td><td>"+nav.getPV()+"</td></tr>";
				sBat += "<tr><td>Dep restants : </td><td>"+nav.getDep()+"</td></tr>";
				sBat += "<tr><td>Etat : </td><td>"+nav.getEtatCourant()+"</td></tr>";
				sBat += "<tr><td>Orientation : </td><td>"+nav.getOrientation()+"</td></tr></table><br>";
				sBat += "<table><tr><td>Canon Principal : </td><td>"+nav.getCanonP().getNom()+"</td></tr>";
				sBat += "<tr><td>Degats : </td><td>"+nav.getCanonP().getDegat()+"</td></tr>";
				sBat += "<tr><td>Temps rechargement : </td><td>"+(nav.getCanonP().getTpsRech())+"</td></tr>";
				sBat += "<tr><td>Prochain Tir : </td><td>"+(nav.getCanonP().getTpsRest())+"</td></tr></table><br>";
				sBat += "<table><tr><td>Canon Secondaire : </td><td>"+nav.getCanonS().getNom()+"</td></tr>";
				sBat += "<tr><td>Degats : </td><td>"+nav.getCanonS().getDegat()+"</td></tr>";
				sBat += "<tr><td>Temps rechargement : </td><td>"+(nav.getCanonS().getTpsRech())+"</td></tr>";
				sBat += "</tr></td>Prochain Tir : </td><td>"+(nav.getCanonS().getTpsRest())+"</td></tr></table>";
				
				
				
			}
			sBat +="</html>";
			typeCase.setText(sBat);
			
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
			actionsBateau.setVisible(false);
			
			g.gridy = 3;
			g.weighty = 5;
			add(new SliderTaille(), g);
			
			g.gridy = 4;
			g.weighty = 5;
			add(finTour,g);
			
			JButton option = new JButton("Options");
			option.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					new PopupOption(me);
					me.setEnabled(false);
				}
			});
			
			g.gridy = 5;
			g.weighty = 5;
			add(option,g);
			
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
		//FenetrePrincipale f = new FenetrePrincipale();
	}
}
