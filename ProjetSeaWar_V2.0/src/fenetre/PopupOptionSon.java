package fenetre;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import son.JouerSon;



public class PopupOptionSon extends JFrame{

	private ImagePanel imagePanel;

	private String filePath = "Fight_of_the_Poursuivante.jpg";
	private JSlider musique;
	private JSlider son;
	private JButton quitter;

	private static final long serialVersionUID = 1L;

	public PopupOptionSon() {
		super("Options son");
		
		Point p = ImagePanel.getTailleImage(filePath);
		setSize(p.x, p.y);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);		
		setUndecorated(true);
		
		setAlwaysOnTop(true);

		//imagePanel.getSize();//TODO:

		imagePanel = new ImagePanel(filePath);
		imagePanel.setPreferredSize(new Dimension(220,300));

		JLabel lm = new JLabel("<html><font color='black'>Volume musique</font></html>");
		lm.setOpaque(true);
		lm.setVerticalAlignment(JLabel.CENTER);
		lm.setBackground(new Color(255,255,255));
		
		musique = new JSlider(0,100,50);
		musique.setBackground(new Color(0,200,255));
		musique.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JouerSon.volumeMusique(((double) ((JSlider)e.getSource()).getValue())/100);
			}
		});
		
		JLabel lb = new JLabel("<html><font color='black'>Volume bruitages</font></html>");
		lb.setOpaque(true);
		lb.setVerticalAlignment(JLabel.CENTER);
		lb.setBackground(new Color(255,255,255));
		
		son = new JSlider(0,100,50);
		son.setBackground(new Color(0,200,255));
		son.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JouerSon.volumeSon(((double)((JSlider)e.getSource()).getValue())/100);
			}
		});
		
		quitter = new JButton("Quitter");
		quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		JPanel pan = new JPanel();
		pan.setLayout(new GridLayout(5, 1));
		pan.add(lm);
		pan.add(musique);
		pan.add(lb);
		pan.add(son);
		pan.add(quitter);
		
		imagePanel.add(pan);
		setContentPane(imagePanel);
		setVisible(true);
	}
	
}
