package fenetre;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import erreur.ChampsInvalides;
import erreur.FichierExistant;
import etatModif.EditCanonP;
import fenetre.FenetrePrincipale.SliderTaille;
import partie.Case;
import partie.ControleurModif;
import partie.Editeur;
import partie.Position;




public class FenetreModif extends JFrame implements Observer{
	public static void main(String [] args){
		new FenetreModif();
	}
	
	
	private static final long serialVersionUID = 1L;
	public final static String titreFenetre = "Sea War"; //titre de la fenetre de jeu
	public final static int nBoutonsHaut = 3; //different de zero, nombre de boutons en haut
	public final static int largeurMenuHaut = 2; //approximativement la largeur du menu en haut en pourcentage
	public final static int largeurMenuGauche = 10; //approximativement la largeur du menu a gauche en pourcentage
	public static int nCasesX;
	public static int nCasesY;

	private static int longueurCote;	//distance le centre et un point = longueur d'un cote
	private static int apotheme; //apotheme = distance entre le centre et le milieu d'un cote
	private static int resteX;	//resteX : longueur du 'triangle' sur un cote de l'hexagone
	
	
	private DrawingPanel plateau;
	private JScrollPane scroll;
	
	private ChoixTaille choixTaille;
	private EditCarte editCarte;
 	private EditCanon editCanon;
 	private EditNavire editNavire;
 	private EditInit editInit;
 	
	private static Editeur editeur;
	private static ControleurModif controleur;
	public JPanel panPrincipal;

	
	public Graphics2D cg;
	
	public FenetreModif() {
		
		
		
		this.setTitle(titreFenetre);
		this.setExtendedState(MAXIMIZED_BOTH); // La fenetre est cree en plein ecran
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //TODO Demander a sauvegarder en fermant?
		
		setTailleHex(30);
		setPlateauTaille(20, 20);
		
		panPrincipal = new JPanel(new GridBagLayout());
		GridBagConstraints gbc;
		gbc = new GridBagConstraints();

		gbc.weightx = (int) (100-largeurMenuGauche)/nBoutonsHaut;
		gbc.weighty = largeurMenuHaut;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;

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
	
	public void setPlateauTaille(int x, int y) {
		nCasesX=x;
		nCasesY=y;
		Position.initTabPosition(nCasesX,nCasesY);
		editeur = new Editeur(nCasesX, nCasesY, this);
		controleur = new ControleurModif(editeur);
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
	
	public static Position pxtoPosHex(int mx, int my) { //on a clique sur le pixel (mx,my) et on renvoie le polygone correspondant
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
					controleur.hexClique(pos);
				}
			}		
		}
		
		public void paintComponent(Graphics g) { //Utile pour l'affichage en fonction des configurations d'un environnement ÃƒÆ’Ã†â€™  l'autre
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
		
		
		private MenuDroite menu;
		private JButton ajoutRocher;
		private JButton ajoutEau;
		private JButton ajoutPhare;
		private JButton retour; 
		private JButton sauvegarde;
		private GridLayout grid;
		private JTextField nom;
		private JLabel messageErr= new JLabel("");
		JLabel Lnom;
		JList<String> listBase;
		String[] data = {"Joueur1", "Joueur2", "Joueur3", "Joueur4", "Joueur5", "Joueur6"};
		public EditCarte(MenuDroite m){
			
			//super(new GridBagLayout());
			GridBagConstraints g = new GridBagConstraints();
			
			
			menu = m;
			grid = new GridLayout(5, 1);
			Lnom = new JLabel("Nom carte: ");
			nom = new JTextField("");
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
			
			
			retour = new JButton("Retour");
			retour.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					messageErr.setText("");
					controleur.demandeRetour();
					menu.changeEditInit();
				}
			});
			
			sauvegarde = new JButton("Sauvegarder");
			sauvegarde.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						controleur.demandeSauvegardeMap(nom.getText());
						messageErr.setText("Sauvegarde reussie");
						nom.setText("");
						
					} catch (FichierExistant e1) {
						messageErr.setText(e1.getMessage());
					}catch (ChampsInvalides e2) {
						messageErr.setText(e2.getMessage());
					}
				}
			});
			JPanel pan3 = new JPanel();
			pan3.add(new SliderTaille(), g);
			JPanel pan = new JPanel();
			JPanel pan2 = new JPanel();
			JPanel j = new JPanel();
			j.setLayout(grid);
			pan.setLayout(grid);
			pan.add(Lnom);
			pan.add(nom);
			pan.add(sauvegarde);
			pan.add(retour);
			pan.add(messageErr);
			pan.add(new JPanel());
			pan.add(ajoutEau);
			pan.add(ajoutRocher);
			pan.add(ajoutPhare);
			
			//j.add(ajoutBases);
			pan2.add(listBase);
			j.add(new JPanel());
			j.add(pan);
			j.add(new JPanel());
			j.add(pan2);
			j.add(pan3);
			add(j);
		}
		
		
	}
 	
 	

	class EditCanon extends JPanel{
		private static final long serialVersionUID = 448318553800706885L;
		private GridLayout grid;
		
		
		private JTextField Tnom;
		private JTextField Tdegat;
		private JTextField Trecharge;
		
		private JButton retour;
		private JButton sauvegarde;
		private MenuDroite menu;
		
		private JLabel messageErr = new JLabel("");
		private GridBagConstraints g;
		public EditCanon(MenuDroite m){
			menu = m;
			grid = new GridLayout(5, 1);
			g = new GridBagConstraints();
			
			retour = new JButton("Retour");
			retour.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					messageErr.setText("");
					controleur.demandeRetour();
					menu.changeEditInit();
				}
			});
			
			sauvegarde = new JButton("Sauvegarder");
			sauvegarde.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//TODO: mettre a jour le canon
					try {
						int degat = Integer.parseInt(Tdegat.getText());
						int recharge = Integer.parseInt(Trecharge.getText());
						if(degat > 0 && recharge > 0) {
							editeur.getCanonP().setDegat(degat);
							editeur.getCanonP().setTpsRech(recharge);
							try {
								controleur.demandeSauvegardeCanon(Tnom.getText());
								messageErr.setText("Sauvegarde reussie");
								Tnom.setText("");
								Trecharge.setText("");
								Tdegat.setText("");
								editeur.resetCanon();
								editeur.resetPlateau();
								editeur.getMap().setColor(
										((EditCanonP) EditCanonP.getEtat()).getRefPos(), Color.blue);
							} catch (FichierExistant e1) {
								messageErr.setText("Nom invalide");
							}
							catch (ChampsInvalides e2){
								messageErr.setText(e2.getMessage());
							}
						} else {
							messageErr.setText("degat et recharge doivent etre >0 !");
						}
					} catch (NumberFormatException nfe) {
						messageErr.setText("degat et recharge sont des entiers");
					}
				}
			});
			
			JPanel Ptitre = new JPanel();
			JLabel titre = new JLabel("Creation d'un nouveau Canon");
			Ptitre.add(titre);
			
			JPanel Pcommentaire = new JPanel();/*
			Pcommentaire.setLayout(new GridLayout(2,1));
			JLabel commentaire1 = new JLabel("Selectionner la zone de tire sur la carte.\n");
			JLabel commentaire2 = new JLabel("Le bateau (en bleu est orientÃ© vers le Nord)");
			Pcommentaire.add(commentaire1);
			Pcommentaire.add(commentaire2);*/
			
			String text = ("<html>");
			text += "<tr><td>Selectionner la zone de tire sur la carte.</td></tr>";
			text += "<tr><td>Le bateau (en bleu) est oriente vers le Nord.</td></tr>";
			text += "<tr><td></td></tr>";
			text += "</html>";
			JLabel commentaire = new JLabel(text);
			Pcommentaire.add(commentaire);
			
			
			JLabel Lnom = new JLabel("Nom : ");
			JLabel Ldegat = new JLabel("Dommage : ");
			JLabel Lrecharge = new JLabel("Rechargement : ");
			
			Tnom = new JTextField();
			Tdegat = new JTextField();
			Trecharge = new JTextField();
			
			JPanel j = new JPanel();
			j.setLayout(new GridLayout(6, 1));
			JPanel caracteristiques = new JPanel();
			caracteristiques.setLayout(new GridLayout(3, 2));
			caracteristiques.add(Lnom);
			caracteristiques.add(Tnom);
			caracteristiques.add(Ldegat);
			caracteristiques.add(Tdegat);
			caracteristiques.add(Lrecharge);
			caracteristiques.add(Trecharge);
			JPanel boutonGlobal = new JPanel();
			JPanel bouton = new JPanel();
			bouton.setLayout(new GridLayout(1, 2));
			bouton.add(sauvegarde);
			bouton.add(retour);
			boutonGlobal.add(bouton);
			
			JPanel pan3 = new JPanel();
			pan3.add(new SliderTaille(), g);
			j.add(Ptitre);
			j.add(Pcommentaire);
			j.add(caracteristiques);
			j.add(boutonGlobal);
			j.add(messageErr);
			j.add(pan3);
			add(j);
		}
	}
	
	class ChoixTaille extends JPanel{
		private final static int tailleMin = 5;
		private JButton valider;
		private MenuDroite menu;
		private JLabel probleme;
		private static final long serialVersionUID = -8777138766549253340L;
		private JTextField x = new JTextField();
		private JTextField y = new JTextField();
		private JLabel labx = new JLabel("x : ");
		private JLabel laby = new JLabel("y : ");
		public ChoixTaille(MenuDroite m) {
			JPanel ly = new JPanel(new GridLayout(7,1));
			menu = m;
			probleme = new JLabel(" ");
			ly.add(new JLabel("Choix de la taille de la carte"));
			ly.add(labx);
			x.setText("20");
			ly.add(x);
			ly.add(laby);

			y.setText("20");
			
			ly.add(y);
			
			valider = new JButton("valider");
			valider.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int valx = 0;
					int valy = 0;
					try {
						valx = Integer.parseInt(x.getText());
						valy = Integer.parseInt(y.getText());
					} catch(NumberFormatException nfe) {
						probleme.setText("Il faut x>"+(tailleMin-1)+" et y>"+(tailleMin-1));
						validate();
						valx = 0;
						valy = 0;
					}
					if(valx<tailleMin || valy<tailleMin) {
						probleme.setText("Il faut x>"+(tailleMin-1)+" et y>"+(tailleMin-1));
						validate();
					} else {
						setPlateauTaille(valx, valy);
						repaint();
						menu.changeEditMap();
					}
				}
			});
			ly.add(valider);
			ly.add(probleme);

			add(ly);
		}
		
		
	}
	class EditInit extends JPanel {
		private JButton editMap;
		private JButton editNavire;
		private JButton editCanon;
		private JButton retour;
		private MenuDroite menu;
		
		private static final long serialVersionUID = 1L;

		
		public EditInit(MenuDroite m){
			menu = m;
			GridLayout grid = new GridLayout(4, 1);
			
			editMap = new JButton("Editer une carte");
			editMap.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					menu.changeChoixCarte();
				}
			});
			
			editNavire = new JButton("Editer un navire");
			editNavire.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					menu.changeEditNavire();
				}
			});
			
			editCanon = new JButton("Editer un canon");
			editCanon.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setPlateauTaille(20, 20);
					menu.changeEditCanon();
				}
			});
			
			retour = new JButton("Retour au Menu Principal");
			retour.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new FenetreMenuDepart();
					dispose();
				}
			});
			
			JPanel j = new JPanel();
			j.setLayout(grid);
			j.add(editMap);
			j.add(editNavire);
			j.add(editCanon);
			j.add(retour);
			add(j);
			
		}
	}
	
	class EditNavire extends JPanel{
		private JButton retour;
		private JButton sauvegarde;
		private MenuDroite menu;
		
		private JTextField Tnom;
		private JTextField Tdeplacement;
		private JTextField TpointsDeVie;
		
		private JLabel messageErr = new JLabel("");
		
		private static final long serialVersionUID = 1L;

		
		public EditNavire(MenuDroite m){
			
			menu = m;
			GridLayout grid = new GridLayout(6, 1);
			retour = new JButton("Retour");
			retour.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					messageErr.setText("");
					controleur.demandeRetour();
					menu.changeEditInit();
				}
			});
			sauvegarde = new JButton("Sauvegarder");
			sauvegarde.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//TODO: mettre a jour le navire
					try {
						int deplacement = Integer.parseInt(Tdeplacement.getText());
						int pointsDeVie = Integer.parseInt(TpointsDeVie.getText());
						if(deplacement > 0 && pointsDeVie > 0) {
							editeur.getNavire().setPv(pointsDeVie);
							editeur.getNavire().setDepMax(deplacement);
							try {
								controleur.demandeSauvegardeNavire(Tnom.getText());
								messageErr.setText("Sauvegarde reussie");
								Tnom.setText("");
								Tdeplacement.setText("");
								TpointsDeVie.setText("");
								;
							} catch (FichierExistant e1) {
								messageErr.setText("Nom invalide");
							}  
						} else {
							messageErr.setText("degat et recharge doivent etre >0 !");
						}
					} catch (NumberFormatException nfe) {
						messageErr.setText("PV et deplacement sont des entiers");
					}
				}
			});
			
			
			JPanel Ptitre = new JPanel();
			JLabel titre = new JLabel("Creation d'un nouveau Navire");
			Ptitre.add(titre);
			
						
			JLabel Lnom = new JLabel("nom : ");
			JLabel Ldeplacement = new JLabel("Deplacement : ");
			JLabel LpointsDeVie = new JLabel("Points de vie : ");
			
			Tnom = new JTextField();
			Tdeplacement = new JTextField();
			TpointsDeVie = new JTextField();
			
			JPanel j = new JPanel();
			j.setLayout(grid);
			JPanel caracteristiques = new JPanel();
			caracteristiques.setLayout(new GridLayout(3, 2));
			caracteristiques.add(Lnom);
			caracteristiques.add(Tnom);
			caracteristiques.add(Ldeplacement);
			caracteristiques.add(Tdeplacement);
			caracteristiques.add(LpointsDeVie);
			caracteristiques.add(TpointsDeVie);
			JPanel boutonGlobal = new JPanel();
			JPanel bouton = new JPanel();
			bouton.setLayout(new GridLayout(0, 2));
			bouton.add(sauvegarde);
			bouton.add(retour);
			boutonGlobal.add(bouton);
			j.add(Ptitre);
			j.add(caracteristiques);
			j.add(boutonGlobal);
			j.add(messageErr);
			add(j);
		}
		
	}
 	/*
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
			
		}

		public void mouseReleased(MouseEvent arg0) {
			
		}

	}*/
		
	
	
	
	class MenuDroite extends JPanel{
		private static final long serialVersionUID = 1L;
		
		public MenuDroite() {
			
			super(new GridBagLayout());
			
			GridBagConstraints g = new GridBagConstraints();
			
			choixTaille = new ChoixTaille(this);
			editCarte = new EditCarte(this);
			editCanon = new EditCanon(this);
			editNavire = new EditNavire(this);
			editInit = new EditInit(this);
			
			
			
			//add(new EditInit(this),g);
			add(editInit,g);
			
		}
		
		public void changeEditCanon() {
			
			this.removeAll();
			
			GridBagConstraints g = new GridBagConstraints();
			
			
			/*
			g.weightx = 100;
			g.weighty = 10;
			g.fill = GridBagConstraints.BOTH;
			
			g.gridy = 1;
			g.weighty = 50;*/
			
			g.weightx = 100;
			g.weighty = 10;
			g.fill = GridBagConstraints.BOTH;
			g.gridx = 0;
			g.gridy = 0;
			g.weighty = 0;
			g.weightx=0;
			
			add(editCanon,g);
			controleur.demandeModifCanonP();
			validate();
			
		}

		public void changeEditNavire() {
			
			this.removeAll();
			GridBagConstraints g = new GridBagConstraints();
			g.weightx = 100;
			g.weighty = 10;
			g.fill = GridBagConstraints.BOTH;
			g.gridx = 0;
			g.gridy = 0;
			g.weighty = 0;
			g.weightx=0;
			add(editNavire,g);
			controleur.demandeModifNavire();
			validate();
		}

		public void changeEditMap() {
			this.removeAll();
			GridBagConstraints g = new GridBagConstraints();
			g.weightx = 100;
			g.weighty = 10;
			g.fill = GridBagConstraints.BOTH;
			g.weighty = 0;
			g.weightx=0;
			g.gridx = 0;
			g.gridy = 0;

			add(editCarte,g);
			controleur.demandeModifCarte();
			validate();
			
		}
		
		public void changeChoixCarte() {
			this.removeAll();
			repaint();
			GridBagConstraints g = new GridBagConstraints();
			g.weightx = 100;
			g.weighty = 10;
			g.fill = GridBagConstraints.BOTH;
			g.weighty = 0;
			g.weightx=0;
			g.gridx = 0;
			g.gridy = 0;
			add(choixTaille,g);
			choixTaille.validate();
			repaint();
			validate();
			
		}
		
		public void changeEditInit() {
			
			this.removeAll();
			GridBagConstraints g = new GridBagConstraints();
			g.weightx = 100;
			g.weighty = 10;
			g.fill = GridBagConstraints.BOTH;
			g.weighty = 0;
			g.weightx=0;
			g.gridx = 0;
			g.gridy = 0;
			add(editInit,g);
			
			validate();
			  
		}
	}
}
	
	
