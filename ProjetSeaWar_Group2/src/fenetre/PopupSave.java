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
import util.Save;

public class PopupSave extends JFrame {

	private ImagePanel imagePanel;
	private FenetrePrincipale fP;
	private String filePath = "Fight_of_the_Poursuivante.jpg";
	private JButton sauvegarde;
	private JButton annuler;
	private JTextField title;
	private JLabel info;
	private static final long serialVersionUID = 1L;
	public PopupSave(FenetrePrincipale f, String infoSave) {

		
		fP = f;
		Point p = ImagePanel.getTailleImage(filePath);
		setSize(p.x, p.y);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setUndecorated(true);
		
		setAlwaysOnTop(true);

		imagePanel = new ImagePanel(filePath);
		imagePanel.setPreferredSize(new Dimension(220,300));
		
		title = new JTextField();
		info = new JLabel(infoSave);
		
		sauvegarde = new JButton("Sauvegarder");
		sauvegarde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = title.getText();
				if(s != null || s != "" ) {
					try {
						Save.sauvegarderPartie(s, fP.getPartie());
					} catch (FichierExistant e1) {
						new PopupSave(fP, "<html> <table><tr>Nom déjà utilisé,<tr>veuillez en saisir un autre :");
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
		pan.setLayout(new GridLayout(4, 1));
		pan.add(info);
		pan.add(title);
		pan4.add(sauvegarde);
		pan4.add(annuler);
		pan.add(pan4);
		imagePanel.add(pan);
		setContentPane(imagePanel);
		setVisible(true);

	}
	
	
}
