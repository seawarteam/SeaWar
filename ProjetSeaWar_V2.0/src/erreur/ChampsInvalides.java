package erreur;

public class ChampsInvalides extends Exception {
	private static final long serialVersionUID = 5161842025150424110L;
	
	public ChampsInvalides(String nomErr) {
		super("Champ(s) invalide(s) : "+nomErr);
	}

}
