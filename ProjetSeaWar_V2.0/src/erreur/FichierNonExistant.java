package erreur;

public class FichierNonExistant extends Exception {
	private static final long serialVersionUID = 5161842025150424110L;
	
	public FichierNonExistant(String nomFichierNonExistant) {
		super(nomFichierNonExistant + " non existant");
	}

}
