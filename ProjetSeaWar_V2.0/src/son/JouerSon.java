package son;

import java.io.File;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class JouerSon extends Application{
	
	private final static JFXPanel fxPanel = new JFXPanel(); //ne pas enlever

	private static MediaPlayer musique;
	private static MediaPlayer son;
	
	private static double volumeM=1;
	private static double volumeS=1;
	
	private static boolean musicOn = true;
	private static boolean sonOn = true;
	
	public void start(Stage primaryStage) throws Exception {}
	
	private static void jouerSon(String acces, int type) { //0 pour musique, 1 pour son
		if(type == 0) {
			Media m = new Media(new File(acces).toURI().toString());
			musique = new MediaPlayer(m);
			musique.setVolume(volumeM);
			if(musicOn) {
				musique.play();
			}
		} else {
			Media m = new Media(new File(acces).toURI().toString());
			son = new MediaPlayer(m);
			son.setVolume(volumeS);
			if(sonOn) {
				son.play();	
			}
		}
		
		
	}


	public static void TirCanon() {
		jouerSon("tir.wav", 1);
	}
	
	public static void SonEpique1() {
		jouerSon("epic1.mp3", 0);
	}
	
	public static void SonEpique2() {
		jouerSon("epic2.mp3", 0);
	}
	

	public static void volumeMusique(double val) {
		if(musique != null) {
			musique.setVolume(val);
		}
		volumeM = val;
	}
	
	public static void volumeSon(double val) {
		if(son != null) {
			son.setVolume(val);
		}
		volumeS = val;
	}
	
	public static void stopMusique() {
		musique.stop();
	}
	
	

}
