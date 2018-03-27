package fenetre;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import erreur.ChampsInvalides;
import erreur.FichierExistant;
import erreur.FichierNonExistant;
import util.Save;


public class FenetreOptions extends JFrame {
		private static final long serialVersionUID = -133048637330577451L;


		private JLabel lPath;
		private JTextField newPath;
		private JButton valider;
		private JButton retour;
		private JPanel pan;
		private JPanel pan1;
		private JPanel pan2;
		private JPanel pan3;
		
		public FenetreOptions() {
			initFenetre();
		}

		private void initFenetre() {
			setTitle("Choix chemin");
			setSize(250,280);
			setResizable(true);
			setLocationRelativeTo(null);	
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			pan1 = new JPanel();
			pan2 = new JPanel();
			pan3 = new JPanel();
			pan = new JPanel();
			lPath = new JLabel("Chemin d'acces :");
			newPath = new JTextField();
			valider = new JButton("Valider");
			valider.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String p = newPath.getText();
					if(!(p==null)) {
						File f = new File(p);
						if(f.isDirectory()) {
							try {
								Save.setDirectory(p);
								new FenetreMenuDepart();
								dispose();
							} catch (FichierExistant e1) {
								e1.printStackTrace();
							}
						}else {
							try {
								throw new ChampsInvalides("Chemin non valide");
							} catch (ChampsInvalides e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			});
			
			retour = new JButton("Retour");
			retour.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new FenetreMenuDepart();
					dispose();
				}
			});
			pan.setLayout(new GridLayout(2,1));
			pan1.setLayout(new GridLayout(1, 2));
			pan1.add(lPath);
			pan1.add(newPath);
			pan3.add(pan1);
			pan2.add(valider);
			pan2.add(retour);
			pan.add(pan3);
			
			pan.add(pan2);
			
			setContentPane(pan);
			setVisible(true);
		}

		


	}


