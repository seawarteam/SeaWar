package fenetre;

import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListDataListener;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import fenetre.FenetreChoixNoms.BoutonValider;
import partie.Canons;
import partie.ControleurChargerPartie;
import partie.Joueur;
import partie.Lanceur;
import partie.Navire;
import partie.Plateau;

public class FenetreChoixPartie extends JFrame implements Observer{
	public static void main(String [] args) {
		FenetreChoixPartie f = new FenetreChoixPartie();
		
	}
	final String pathMap = getPath()+ "/Sauvegardes/Reglages/Cartes/";
	final String pathCanon = getPath()+ "/Sauvegardes/Reglages/Canons/";
	final String pathBateau = getPath()+ "/Sauvegardes/Reglages/Bateaux/";

	private JButton valider;
	private ChoixCarte choixCarte;
	private ChoixJoueur choixJoueur;
	private InfoCarte infoCarte;
	private InfoJoueur infoJoueurs;
	
	private Lanceur lanceur;
	private ControleurChargerPartie controleur;
	
	public FenetreChoixPartie() {
		lanceur = new Lanceur(this);
		controleur = new ControleurChargerPartie(lanceur);
		 initFenetre();
	 }
	
	private void initFenetre() {
		setTitle("Choix carte");
		setSize(400,500);
		setLocationRelativeTo(null);	
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(5,1));
		choixCarte = new ChoixCarte();
		infoCarte = new InfoCarte();
		choixJoueur = new ChoixJoueur();
		infoJoueurs = new InfoJoueur();
		valider = new JButton("Jouer");
		valider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controleur.demandeLancerPartie();
			}
		});
		setLayout(new GridLayout(5, 1));
		add(choixCarte);
		add(infoCarte);
		add(choixJoueur);
		add(infoJoueurs);
		add(valider);
		setVisible(true);
	}
	
	class ChoixCarte extends JPanel{
		private static final long serialVersionUID = 1L;
		JComboBox<String> listNomCarte;
		MyComboBoxModel<String> listmodel;
		JScrollPane spane;
		JPanel pan;
		JLabel label;
		
		public ChoixCarte() {
			label = new JLabel("Choisir carte :");
			listmodel = new MyComboBoxModel<String>(pathMap);
			listNomCarte = new JComboBox<String>(listmodel);
			listNomCarte.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controleur.demandeChargerMap((Plateau)chargerObject((String)listNomCarte.getSelectedItem(), pathMap));
				}
			});
			pan = new JPanel();
			pan.add(label);
			pan.add(listNomCarte);
			add(pan);
		}
	}
	
	class InfoCarte extends JPanel{
		private static final long serialVersionUID = 1L;
		JLabel infosLabel;
		
		public InfoCarte() {
			infosLabel = new JLabel();
			add(infosLabel);
		}
		
		public void setInfoCarte() {
			Plateau map = lanceur.getMap();
			String infos = ("<html>" + "");
			if(map != null) {
				infos += "<table><tr><td>Taille : </td><td>"+map.getNCasesX()+"x"+map.getNCasesY()+"</td></tr>";
				infos += "<tr><td>Nombre de joueurs max : </td><td>"+map.getNbMaxJoueurs()+"</td></tr>";
				infos += "<tr><td>Nombre de phares : </td><td>"+map.getNbPhares()+"</td></tr>";
			}
			infosLabel.setText(infos);
		}
		
		
	}
	class ChoixJoueur extends JPanel{
		private static final long serialVersionUID = 1L;
		JButton valider;
		JTextField nomJoueur;
		JLabel lNom;
		JLabel ln1;
		JLabel ln2;
		JLabel lc1;
		JLabel lc2;
		JComboBox<String> listNomCanonS;
		JComboBox<String> listNomCanonP;
		JComboBox<String> listNomBateaux;
		MyComboBoxModel<String> listmodelCanonS;
		MyComboBoxModel<String> listmodelCanonP;
		MyComboBoxModel<String> listmodelBateaux;
		JComboBox<String> list2NomCanonS;
		JComboBox<String> list2NomCanonP;
		JComboBox<String> list2NomBateaux;
		Canons selectedCanonS1 = null;
		Canons selectedCanonP1 = null;
		Navire selectedNavire1 = null;
		Canons selectedCanonS2 = null;
		Canons selectedCanonP2 = null;
		Navire selectedNavire2 = null;
		String nom = null;
		JScrollPane spane;
		JPanel pan1;
		JPanel pan2;
		JPanel pan3;
		JPanel pan4;
		public ChoixJoueur() {
			ln1 = new JLabel("Navire1 :");
			ln2 = new JLabel("Navire2 :");
			lc1 = new JLabel("Canons :");
			lc2 = new JLabel("Canons :");
			valider = new JButton("Valider");
			valider.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String nom = nomJoueur.getText();
					controleur.demandeChargerJoueur(nom, selectedNavire1, selectedCanonP1, selectedCanonS1, selectedNavire2, selectedCanonP2, selectedCanonS2);
					selectedNavire1 = null;
					selectedNavire2 = null;
					selectedCanonP1 = null;
					selectedCanonS1 = null;
					selectedCanonP2 = null;
					selectedCanonS2 = null;
				}
			});
			lNom = new JLabel("Nom joueur:");
			nomJoueur = new JTextField("");
			listmodelCanonS = new MyComboBoxModel<String>(pathCanon);
			listmodelCanonP = new MyComboBoxModel<String>(pathCanon);
			listmodelBateaux = new MyComboBoxModel<String>(pathBateau);
			listNomCanonS = new JComboBox<String>(listmodelCanonS);
			listNomCanonS.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String nom = (String)listNomCanonS.getSelectedItem();
					selectedCanonS1 = Canons.copy((Canons) chargerObject(nom, pathCanon));
				}
			});
			listNomCanonP = new JComboBox<String>(listmodelCanonS);
			listNomCanonP.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String nom = (String)listNomCanonP.getSelectedItem();
					selectedCanonP1 = Canons.copy((Canons) chargerObject(nom, pathCanon));
				}
			});  
			listNomBateaux = new JComboBox<String>(listmodelBateaux);
			listNomBateaux.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String nom = (String)listNomBateaux.getSelectedItem();
					selectedNavire1 = Navire.copy((Navire) chargerObject(nom, pathBateau));
				}
			});
			list2NomCanonS = new JComboBox<String>(listmodelCanonS);
			list2NomCanonS.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String nom = (String)list2NomCanonS.getSelectedItem();
					selectedCanonS2 = Canons.copy((Canons) chargerObject(nom, pathCanon));
				}
			});
			list2NomCanonP = new JComboBox<String>(listmodelCanonS);
			list2NomCanonP.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String nom = (String)list2NomCanonP.getSelectedItem();
					selectedCanonP2 = Canons.copy((Canons) chargerObject(nom, pathCanon));
				}
			});
			list2NomBateaux = new JComboBox<String>(listmodelBateaux);
			list2NomBateaux.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String nom = (String)list2NomBateaux.getSelectedItem();
					selectedNavire2 = Navire.copy((Navire) chargerObject(nom, pathBateau));
				}
			});
			pan1 = new JPanel();
			pan1.setLayout(new GridLayout(1, 2));
			pan1.add(lNom);
			pan1.add(nomJoueur);
			pan2 = new JPanel();
			pan2.add(ln1);
			pan2.add(listNomBateaux);
			pan2.add(lc1);
			pan2.add(listNomCanonS);
			pan2.add(listNomCanonP);
			pan3 = new JPanel();
			pan3.add(ln2);
			pan3.add(list2NomBateaux);
			pan3.add(lc2);
			pan3.add(list2NomCanonS);
			pan3.add(list2NomCanonP);
			pan4 = new JPanel();
			pan4.add(valider);
			setLayout(new GridLayout(4,1));
			add(pan1);
			add(pan2);
			add(pan3);
			add(pan4);
		}
	}
	
	class InfoJoueur extends JPanel{
		private static final long serialVersionUID = 1L;
		JLabel infosLabel;
		JScrollPane spane;
		public InfoJoueur() {
			infosLabel = new JLabel();
			spane = new JScrollPane (infosLabel);
			add(spane);
		}
		
		public void setInfoJoueurs() {
			ArrayList<Joueur> joueurs = lanceur.getJoueurs();
			String infos = ("<html>" + "");
			if(joueurs != null) {
				for(Joueur j : joueurs) {
					infos += "<table><tr><td>Nom : </td><td>"+j.getNom()+"</td></tr>";
					ArrayList<Navire> navires = (ArrayList<Navire>) j.getListNavires();
					for(Navire n : navires) {
						infos += "<tr><td>Nom du bateau : </td><td>"+n.getNom()+"</td></tr>";
						infos += "<tr><td>Canon Primaire : </td><td>"+n.getCanonP().getNom()+"</td></tr>";
						infos += "<tr><td>Dï¿½gï¿½ts : </td><td>"+n.getCanonP().getDegat()+"</td></tr>";
						infos += "<tr><td>Tps Rechargement : </td><td>"+n.getCanonP().getTpsRech()+"</td></tr>";
						infos += "<tr><td>Canon Secondaire : </td><td>"+n.getCanonS().getNom()+"</td></tr>";
						infos += "<tr><td>Dï¿½gï¿½ts : </td><td>"+n.getCanonS().getDegat()+"</td></tr>";
						infos += "<tr><td>Tps Rechargement : </td><td>"+n.getCanonS().getTpsRech()+"</td></tr>";
					}
				}
				
			}
			infosLabel.setText(infos);
		}
		
		
	}
	
	public Object chargerObject(String nomFichier, String path) {
		String name = path + nomFichier;
		Object obj = null;
		File pathF = new File(name);
		if (pathF.exists()) {
			ObjectInputStream ois = null;
			try {
				final FileInputStream fichier = new FileInputStream(name);
				ois = new ObjectInputStream(fichier);
				obj = (Object) ois.readObject();
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
		return obj;
	}

	
	class MyComboBoxModel<E> extends AbstractListModel implements ComboBoxModel {
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
		public Object getElementAt(int arg0) {
			int i = 0;
			String nom = null;
			Iterator<E> it = nomFiles.iterator();
			while(it.hasNext() && i<=arg0) {
				i++;
				nom = (String) it.next();
			}
			return nom;
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
		@SuppressWarnings("unchecked")
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
		@Override
		public void setSelectedItem(Object anItem) {
		    selection = (String) anItem; 
		}

		  // Methods implemented from the interface ComboBoxModel
		  public Object getSelectedItem() {
		    return selection; // to add the selection to the combo box
		  }
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Plateau) {
			updateMap(arg1);
		}
		if(arg1 instanceof Joueur) {
			updateJoueur(arg1);
		}
		if(arg1 instanceof Lanceur) {
			dispose();
		}
		
	}

	private void updateJoueur(Object arg1) {
		infoJoueurs.setInfoJoueurs();
		infoJoueurs.revalidate();
		
	}

	private void updateMap(Object arg1) {
		infoCarte.setInfoCarte();
		infoCarte.revalidate();
	}
	private String getPath() {		
		return System.getProperty("user.dir");		
	}

	
}
