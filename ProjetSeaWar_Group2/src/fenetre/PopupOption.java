package fenetre;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import partie.SeaWar;

public class PopupOption extends JFrame {

	private ImagePanel imagePanel;
	private FenetrePrincipale fP;
	private String filePath = "Fight_of_the_Poursuivante.jpg";
	private JButton sauvegarde;
	private JButton quitter;
	private JButton annuler;
	private PopupOption popThis;
	private static final long serialVersionUID = 1L;

	public PopupOption(FenetrePrincipale f) {
		popThis = this;
		fP = f;
		Point p = ImagePanel.getTailleImage(filePath);
		setSize(p.x, p.y);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setUndecorated(true);
		
		
		setAlwaysOnTop(true);

		//imagePanel.getSize();//TODO:

		imagePanel = new ImagePanel(filePath);
		imagePanel.setPreferredSize(new Dimension(220,300));
		//setSize(imagePanel.getSize());

		sauvegarde = new JButton("Sauvegarder");
		sauvegarde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PopupSave(fP, "Veuillez saisir un nom :");
			}
		});
		
		quitter = new JButton("Quitter la partie en cours");
		quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new FenetreMenuDepart();
				fP.dispose();
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
		pan.setLayout(new GridLayout(3, 1));
		pan.add(sauvegarde);
		pan.add(quitter);
		pan.add(annuler);
		/*
		JPanel pan2 = new JPanel();
		
		
		
		pan2.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		g.weightx = 100;
		g.weighty = 10;
		g.fill = GridBagConstraints.BOTH;
		g.gridx = 0;
		g.gridy = 0;
		
		//g.gridy = 4;
		g.weighty = 5;
		invalidate();
		pan2.add(pan);
		*/
		imagePanel.add(pan);
		setContentPane(imagePanel);
		setVisible(true);

	}
	
	
	
}
