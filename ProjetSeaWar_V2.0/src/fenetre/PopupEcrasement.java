package fenetre;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import erreur.FichierExistant;
import partie.SeaWar;

public class PopupEcrasement extends JFrame {

	private JFrame fP;
	private JButton sauvegarde;
	private JButton annuler;
	private JLabel info;
	private JTextField newTitle;
	private static final long serialVersionUID = 1L;
	private Object o;
	
	public PopupEcrasement(Object o, String path, String nom) {
		
		setSize(200, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setUndecorated(true);
		setAlwaysOnTop(true);
		
		info = new JLabel("Fichier déjà existant :"+nom+"\n Veuillez entrez un nouveau nom");
		
		newTitle = new JTextField();
		
		sauvegarde = new JButton("Sauvegarder");
		sauvegarde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = newTitle.getText();
				if(s != null || s != "" ) {
					try {
						sauvegarder(path, s);
					} catch (FichierExistant e1) {
						new PopupEcrasement(o, path, nom);
						dispose();
						e1.printStackTrace();
					}
				}
			}
		});
		
		annuler = new JButton("Annuler");
		annuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fP.setEnabled(true);
				setVisible(false);
			}
		});
		
		JPanel pan = new JPanel();
		JPanel pan2 = new JPanel();
		JPanel pan3 = new JPanel();
		JPanel pan4 = new JPanel();
		pan.setLayout(new GridLayout(3, 1));
		pan2.add(info);
		pan3.add(newTitle);
		pan4.add(sauvegarde);
		pan4.add(annuler);
		pan.add(pan2);
		pan.add(pan3);
		pan.add(pan4);
		setVisible(true);

	}
	
	public void sauvegarder(String path, String nomFichier) throws FichierExistant {
		String name = path + nomFichier;
		ObjectOutputStream oos = null;
		File pathF = new File(name);
		if (!pathF.exists()) {
			try {
				final FileOutputStream fichier = new FileOutputStream(name);
				oos = new ObjectOutputStream(fichier);
				oos.writeObject(o);
				oos.flush();
			} catch (final java.io.IOException e) {
				File pathbis = new File(path);
				pathbis.mkdirs();
			} finally {
				try {
					final FileOutputStream fichier = new FileOutputStream(name);
					oos = new ObjectOutputStream(fichier);
					if (oos != null) {
						oos.writeObject(o);
						oos.flush();
						oos.close();
					}
				} catch (final IOException ex) {
					ex.printStackTrace();
				}
			}
		} else {
			throw new FichierExistant(nomFichier);
		}
	}

	
}
