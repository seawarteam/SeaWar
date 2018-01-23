package fenetre;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListDataListener;
import partie.Canons;
import partie.Controleur;
import partie.ControleurChargerPartie;
import partie.Joueur;
import partie.Lanceur;
import partie.Navire;
import partie.Partie;
import partie.Plateau;
import partie.Position;

public class FenetreChargerPartie extends JFrame{
	private static final long serialVersionUID = -133048637330577451L;

	public static void main(String [] args) {
		FenetreChargerPartie f = new FenetreChargerPartie();
		
	}
	final String pathPartie = getPath()+ "/Sauvegardes/Parties/";
	
	private JButton retour;
	private JButton jouer;
	private ChoixPartie choixPartie;
	
	
	private Partie partie;
	private FenetrePrincipale fp;
	private Controleur controleurPartie;
	
	private String filePath = "bateau.jpg";
	
	public FenetreChargerPartie() {
		
		 initFenetre();
	 }
	
	private void initFenetre() {
		setTitle("Choix carte");
		Point p = ImagePanel.getTailleImage(filePath);
		setSize(p.x, p.y);
		setResizable(true);
		setLocationRelativeTo(null);	
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		choixPartie = new ChoixPartie();

		jouer = new JButton("Jouer");
		jouer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				initPartie();
			}

		});
		
		retour  = new JButton("Retour");
		retour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FenetreMenuDepart f = new FenetreMenuDepart();
				dispose();
			}
		});
		
		ImagePanel imagePanel = new ImagePanel(filePath);
		JPanel pan = new JPanel();
		pan.setOpaque(false);
		pan.setLayout(new GridLayout(4, 1));
		
		JPanel panCarte = new JPanel();
		panCarte.setLayout(new GridLayout(2,1));
		panCarte.setPreferredSize(new Dimension(300, 50));
		panCarte.add(choixPartie);
		pan.add(panCarte);
		
		
		JPanel panJouer = new JPanel();
		panJouer.add(jouer);
		panJouer.add(retour);
		pan.add(panJouer);
		
		
		imagePanel.add(pan);
		setContentPane(imagePanel);
		setVisible(true);
	}
	
	class ChoixPartie extends JPanel{
		private static final long serialVersionUID = 1L;
		JComboBox<String> listNomPartie;
		MyComboBoxModel<String> listmodel;
		JScrollPane spane;
		JPanel pan;
		JLabel label;
		
		public ChoixPartie() {
			label = new JLabel("Choisir partie :");
			listmodel = new MyComboBoxModel<String>(pathPartie);
			listNomPartie = new JComboBox<String>(listmodel);
			listNomPartie.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					partie = charger((String)listNomPartie.getSelectedItem());
				}
			});
			pan = new JPanel();
			pan.setLayout(new GridLayout(1,2));
			pan.add(label);
			pan.add(listNomPartie);
			add(pan);
		}
	}
	
	public Partie charger(String nomFichier) {
		String name = pathPartie + nomFichier;
		System.out.println(name);
		Partie partie = null;
		File pathF = new File(name);
		if (pathF.exists()) {
			ObjectInputStream ois = null;
			try {
				final FileInputStream fichier = new FileInputStream(name);
				ois = new ObjectInputStream(fichier);
				partie = (Partie) ois.readObject();
			} catch (final java.io.IOException e) {
				e.printStackTrace();
			} catch (final ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					if (ois != null) {
						ois.close();
					}
				} catch (final IOException ex) {
					ex.printStackTrace();
				}
			}
		} else {
			System.out.println("Fichier non existant !");
		}
		return partie;
	}
	
	class MyComboBoxModel<E> extends AbstractListModel<E> implements ComboBoxModel<E> {
		private static final long serialVersionUID = 1L;
		ListDataListener listListener;
		ArrayList<E> nomFiles;
		String selection = null;
		String path;
		public MyComboBoxModel(String path) {
			nomFiles = new ArrayList<E>();
			this.path = path;
			initList(path);
		}
		public E getElementAt(int arg0) {
			int i = 0;
			String nom = null;
			Iterator<E> it = nomFiles.iterator();
			while(it.hasNext() && i<=arg0) {
				i++;
				nom = (String) it.next();
			}
			return (E) nom;
		}

		public int getSize() {
			int i = 0;
			Iterator<E> it = nomFiles.iterator();
			while(it.hasNext()) {
				i++;
				it.next();
			}
			return i;
		}
		public ArrayList<E> findFiles(String path){
			ArrayList<E> nomFiles = new ArrayList<E>();
			File filePath = new File(path); 
			File [] files = filePath.listFiles();
			if(files != null) {
				for(File f : files) {
					nomFiles.add((E)f.getName());
				}
			}
			return nomFiles;
		} 
		
		public void initList(String path) {
			nomFiles = (ArrayList<E>) findFiles(path);
			fireIntervalAdded(this, 0, getSize()-1);
			//System.out.println(nomFiles);
		}
		
		public void setSelectedItem(Object anItem) {
		    selection = (String) anItem; 
		}

		  // Methods implemented from the interface ComboBoxModel
		  public Object getSelectedItem() {
		    return selection; // to add the selection to the combo box
		  }
	}

	private void initPartie() {
		Plateau map = partie.getPlateau();
		int nX = map.getNCasesX();
		int nY = map.getNCasesY();
		Position.initTabPosition(nX, nY);
		controleurPartie = new Controleur(partie);
		fp = new FenetrePrincipale(partie, controleurPartie);
		
		partie.initPartie();
		fp.initFenetrePrincipale();
		partie.initObserver(fp);
		dispose();
	}
	
	private String getPath() {		
		return System.getProperty("user.dir");		
	}

	

	
}
