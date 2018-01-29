package util;

import java.io.Serializable;

public class Path implements Serializable{
	
	private final  	String pathMapFinal = "/Sauvegardes/Reglages/Cartes/";
	private final   String pathCanonFinal = "/Sauvegardes/Reglages/Canons/";
	private final   String pathBateauFinal = "/Sauvegardes/Reglages/Bateaux/";
	private final   String pathPartieFinal = "/Sauvegardes/Parties/";
	private final   String pathOptionsFinal = "/Sauvegardes/Reglages/Options/";
	
	public  String pathMap;
	public  String pathCanon;
	public  String pathBateau;
	public  String pathPartie;
	public String pathOptions;
	
	private static String getPath() {
		return System.getProperty("user.dir");
	}
	
	public Path() {
		super();
		pathMap = getPath()+pathMapFinal;
		pathCanon = getPath()+pathCanonFinal;
		pathBateau = getPath()+pathBateauFinal;
		pathPartie = getPath()+pathPartieFinal;
		pathOptions = getPath()+pathOptionsFinal;
	}

	public void setPathMap(String pathMap) {
		this.pathMap = pathMap+pathMapFinal;
	}


	public void setPathCanon(String pathCanon) {
		this.pathCanon = pathCanon+pathCanonFinal;
	}


	public void setPathBateau(String pathBateau) {
		this.pathBateau = pathBateau+pathBateauFinal;
	}


	public void setPathPartie(String pathPartie) {
		this.pathPartie = pathPartie+pathPartieFinal;
	}
	
	
	
}
