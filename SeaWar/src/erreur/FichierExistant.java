package erreur;

public class FichierExistant extends Exception {
	private static final long serialVersionUID = 5161842025150424110L;
	
	public FichierExistant(String nomFichierExistant) {
		super(nomFichierExistant + " est un nom deja utilise");
	}

}
