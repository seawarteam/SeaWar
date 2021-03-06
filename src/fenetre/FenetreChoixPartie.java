package fenetre;

import java.awt.Dimension;
import java.awt.GridLayout;
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
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListDataListener;

import erreur.ChampsInvalides;
import erreur.FichierNonExistant;
import partie.Canons;
import partie.ControleurChargerPartie;
import partie.Joueur;
import partie.Lanceur;
import partie.Navire;
import partie.Plateau;
import util.Save;

public class FenetreChoixPartie extends JFrame implements Observer{
	private static final long serialVersionUID = -133048637330577451L;

	

	private JButton retour;
	private JButton jouer;
	private ChoixCarte choixCarte;
	private ChoixJoueur choixJoueur;
	private InfoCarte infoCarte;
	private InfoJoueur infoJoueurs;
	private JScrollPane jsp;
	private JLabel info;

	private Lanceur lanceur;
	private ControleurChargerPartie controleur;

	public FenetreChoixPartie() {
		lanceur = new Lanceur(this);
		controleur = new ControleurChargerPartie(lanceur);
		initFenetre();
	}

	private void initFenetre() {
		setTitle("Choix carte");
		setSize(500,700);
		setResizable(true);
		setLocationRelativeTo(null);	
		setDefaultCloseOperation(EXIT_ON_CLOSE);


		choixCarte = new ChoixCarte();
		infoCarte = new InfoCarte();
		choixJoueur = new ChoixJoueur();
		infoJoueurs = new InfoJoueur();
		jsp = new JScrollPane(infoJoueurs);
		jouer = new JButton("Jouer");
		jouer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					controleur.demandeLancerPartie();
				} catch (ChampsInvalides e) {
					info.setText(e.getMessage());
				}
			}
		});

		retour  = new JButton("Retour");
		retour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controleur.demandeRetour();
			}
		});

		info = new JLabel("");
		JPanel pan = new JPanel();
		pan.setOpaque(false);
		pan.setLayout(new GridLayout(4, 1));

		JPanel panCarte = new JPanel();
		panCarte.setLayout(new GridLayout(2,1));
		panCarte.setPreferredSize(new Dimension(300, 50));
		panCarte.add(choixCarte);
		panCarte.add(infoCarte);
		pan.add(panCarte);

		JPanel panJoueur = new JPanel();
		panJoueur.add(choixJoueur);
		pan.add(panJoueur);


		pan.add(jsp);

		JPanel panJouer = new JPanel();
		panJouer.setLayout(new GridLayout(2, 1));
		JPanel panJouerRetourGlobal = new JPanel();
		JPanel panJouerRetour = new JPanel();
		panJouerRetour.setLayout(new GridLayout(1, 2));
		panJouerRetour.add(jouer);
		panJouerRetour.add(retour);
		panJouerRetourGlobal.add(panJouerRetour);
		panJouer.add(panJouerRetourGlobal);
		panJouer.add(info);
		pan.add(panJouer);

		setContentPane(pan);
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
			listmodel = new MyComboBoxModel<String>(Save.pathMap());
			listNomCarte = new JComboBox<String>(listmodel);
			listNomCarte.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						controleur.demandeChargerMap((Plateau)chargerObject((String)listNomCarte.getSelectedItem(), Save.pathMap()));
					} catch (FichierNonExistant e1) {
						info.setText("Impossible de charger cette carte");
					}
				}
			});
			pan = new JPanel();
			pan.setLayout(new GridLayout(1,2));
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
				infos += "<tr><td>Taille : </td><td>"+map.getNCasesX()+"x"+map.getNCasesY()+"</td></tr>";
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
		MyComboBoxModel<String> listmodelCanonS2;
		MyComboBoxModel<String> listmodelCanonP2;
		MyComboBoxModel<String> listmodelBateaux2;
		JComboBox<String> list2NomCanonS;
		JComboBox<String> list2NomCanonP;
		JComboBox<String> list2NomBateaux;
		ButtonGroup group;
		JRadioButton bot;
		JRadioButton j;
		Canons selectedCanonS1 = null;
		Canons selectedCanonP1 = null;
		Navire selectedNavire1 = null;
		Canons selectedCanonS2 = null;
		Canons selectedCanonP2 = null;
		Navire selectedNavire2 = null;
		int nbJ = 1;
		String nom ;
		String nomCS;
		String nomCP;
		String nomN ;
		String nomCS2;
		String nomCP2;
		String nomN2 ;
		JScrollPane spane;
		JPanel pan1;
		JPanel pan2;
		JPanel pan3;
		JPanel pan4;
		JPanel panBot;
		public ChoixJoueur() {
			ln1 = new JLabel("Navire1 :");
			ln2 = new JLabel("Navire2 :");
			lc1 = new JLabel("Canons :");
			lc2 = new JLabel("Canons :");
			valider = new JButton("Valider");
			valider.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String nom = nomJoueur.getText();
					try {
						selectedCanonS1 = Canons.copy((Canons) chargerObject(nomCS, Save.pathCanon()));
						selectedCanonP1 = Canons.copy((Canons) chargerObject(nomCP, Save.pathCanon()));
						selectedNavire1 = Navire.copy((Navire) chargerObject(nomN, Save.pathBateau()));
						selectedCanonS2 = Canons.copy((Canons) chargerObject(nomCS2, Save.pathCanon()));
						selectedCanonP2 = Canons.copy((Canons) chargerObject(nomCP2, Save.pathCanon()));
						selectedNavire2 = Navire.copy((Navire) chargerObject(nomN2, Save.pathBateau()));
						try {
							//TODO: ajouter l'IA !!!!
							
							controleur.demandeChargerJoueur(nom, selectedNavire1, selectedCanonP1, selectedCanonS1, selectedNavire2, selectedCanonP2, selectedCanonS2, bot.isSelected());
							
							nbJ++;
							nomJoueur.setText("Joueur"+nbJ);
							info.setText("Nouveu joueur enregistre");
						} catch (ChampsInvalides e1) {
							info.setText(e1.getMessage());
							
						}
						finally {
							validate();
						}
						
					} catch (FichierNonExistant e1) {
						info.setText(e1.getMessage());
					}finally {
						validate();
					}
					
				}
			});
			lNom = new JLabel("Nom joueur:");
			nomJoueur = new JTextField("Joueur"+nbJ);
			listmodelCanonS = new MyComboBoxModel<String>(Save.pathCanon());
			listmodelCanonP = new MyComboBoxModel<String>(Save.pathCanon());
			listmodelBateaux = new MyComboBoxModel<String>(Save.pathBateau());
			listmodelCanonS2 = new MyComboBoxModel<String>(Save.pathCanon());
			listmodelCanonP2 = new MyComboBoxModel<String>(Save.pathCanon());
			listmodelBateaux2 = new MyComboBoxModel<String>(Save.pathBateau());
			listNomCanonS = new JComboBox<String>(listmodelCanonS);
			listNomCanonS.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					nomCS = (String)listNomCanonS.getSelectedItem();
				}
			});
			listNomCanonP = new JComboBox<String>(listmodelCanonP);
			listNomCanonP.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					nomCP = (String)listNomCanonP.getSelectedItem();
				}
			});  
			listNomBateaux = new JComboBox<String>(listmodelBateaux);
			listNomBateaux.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					nomN = (String)listNomBateaux.getSelectedItem();
				}
			});
			list2NomCanonS = new JComboBox<String>(listmodelCanonS2);
			list2NomCanonS.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					nomCS2 = (String)list2NomCanonS.getSelectedItem();
				}
			});
			list2NomCanonP = new JComboBox<String>(listmodelCanonP2);
			list2NomCanonP.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					nomCP2 = (String)list2NomCanonP.getSelectedItem();
				}
			});
			list2NomBateaux = new JComboBox<String>(listmodelBateaux2);
			list2NomBateaux.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					nomN2 = (String)list2NomBateaux.getSelectedItem();
				}
			});
			group = new ButtonGroup();
			bot = new JRadioButton("Bot");
			j = new JRadioButton("Joueur");
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
			panBot = new JPanel();
			
			panBot.setLayout(new GridLayout(1, 2));
			group.add(bot);
			group.add(j);
			panBot.add(j);
			panBot.add(bot);
			j.setSelected(true);
			
			pan4 = new JPanel();
			pan4.add(valider);
			
			setLayout(new GridLayout(5,1));
			add(pan1);
			add(panBot);
			add(pan2);
			add(pan3);
			add(pan4);
		}
	}

	class InfoJoueur extends JEditorPane{
		private static final long serialVersionUID = 1L;
		public InfoJoueur() {
			super();
			setEditable(false);
		}

		public void setInfoJoueurs() {
			ArrayList<Joueur> joueurs = lanceur.getJoueurs();
			String infos = "";
			if(joueurs != null) {
				for(Joueur j : joueurs) {
					infos += "Nom : "+j.getNom()+"\n";
					ArrayList<Navire> navires = (ArrayList<Navire>) j.getListNavires();
					for(Navire n : navires) {
						infos += "Nom du bateau : "+n.getNom()+"\n";
						infos += "Deplacement : "+n.getDepMax()+"\n";
						infos += "Canon Primaire : "+n.getCanonP().getNom()+"\n";
						infos += "Degats : "+n.getCanonP().getDegat()+"\n";
						infos += "Tps Rechargement : "+n.getCanonP().getTpsRech()+"\n";
						infos += "Canon Secondaire : "+n.getCanonS().getNom()+"\n";
						infos += "Degats : "+n.getCanonS().getDegat()+"\n";
						infos += "Tps Rechargement : "+n.getCanonS().getTpsRech()+"\n\n\n";
					}
				}

			}
			setText(infos);
		}


	}

	public Object chargerObject(String nomFichier, String path) throws FichierNonExistant {
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
			throw new FichierNonExistant(nomFichier);
		}
		return obj;
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
