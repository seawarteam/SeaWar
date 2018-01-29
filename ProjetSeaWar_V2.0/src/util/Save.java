package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import erreur.FichierExistant;
import erreur.FichierNonExistant;
import partie.Canons;
import partie.Navire;
import partie.Partie;
import partie.Plateau;

public class Save {

	public static Path path;
	public final static String pathOptions = getPath()+"/Reglages/Options/";
	
	
	public static void init() throws FichierExistant {
		try {
			chargerOptions();
		} catch (FichierNonExistant e) {
			e.printStackTrace();
		}
	}
	
	private static String getPath() {
		return System.getProperty("user.dir");
	}
	
	public static String pathMap() {
		return path.pathMap;
	}
	
	public static String pathBateau() {
		return path.pathBateau;
	}
	
	public static String pathCanon() {
		return path.pathCanon;
	}
	
	public static String pathPartie() {
		return  path.pathPartie;
	}

	public static void setDirectory(String dir) throws FichierExistant {
		path.setPathBateau(dir);
		path.setPathCanon(dir);
		path.setPathMap(dir);
		path.setPathPartie(dir);
		sauvegarderOptions();
	}
	
	
	
	public static void chargerOptions() throws FichierNonExistant, FichierExistant {
		String name = pathOptions+"paths";
		File pathF = new File(name);
		if (pathF.exists()) {
			ObjectInputStream ois = null;
			try {
				final FileInputStream fichier = new FileInputStream(name);
				ois = new ObjectInputStream(fichier);
				path = (Path) ois.readObject();
			} catch (final java.io.IOException e) {
				e.printStackTrace();
			} catch (final ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					if (ois != null) {
						ois.close();
					}
				} catch (final IOException ex) {
					ex.printStackTrace();
				}
			}
		} else {
			path = new Path();
			sauvegarderOptions();
		}
	}
	
	public static void sauvegarderOptions() throws FichierExistant { // ex : partie.sauvegarder ( "Test01" );
		String name = pathOptions + "paths";
		ObjectOutputStream oos = null;
		File pathF = new File(name);
		if (!pathF.exists()) {
			try {
				final FileOutputStream fichier = new FileOutputStream(name);
				oos = new ObjectOutputStream(fichier);
				oos.writeObject(path);
				oos.flush();
			} catch (final java.io.IOException e) {
				File paths = new File(pathOptions);
				paths.mkdirs();
			} finally {
				try {
					final FileOutputStream fichier = new FileOutputStream(name);
					oos = new ObjectOutputStream(fichier);
					if (oos != null) {
						oos.writeObject(path);
						oos.flush();
						oos.close();
					}
				} catch (final IOException ex) {
					ex.printStackTrace();
				}
			}
		} else {
			boolean b = pathF.delete();
			if(b) {
				try {
					final FileOutputStream fichier = new FileOutputStream(name);
					oos = new ObjectOutputStream(fichier);
					oos.writeObject(path);
					oos.flush();
				} catch (final java.io.IOException e) {
				} 
				
			}else {
				System.out.println("erreur supression");
			}
		}
	}
	
	public static void sauvegarderMap(String nomFichier, Plateau map) throws FichierExistant { // ex : partie.sauvegarder ( "Test01" );
		String name = path.pathMap + nomFichier;
		ObjectOutputStream oos = null;
		File pathF = new File(name);
		if (!pathF.exists()) {
			try {
				final FileOutputStream fichier = new FileOutputStream(name);
				oos = new ObjectOutputStream(fichier);
				oos.writeObject(map);
				oos.flush();
			} catch (final java.io.IOException e) {
				File paths = new File(path.pathMap);
				paths.mkdirs();
			} finally {
				try {
					final FileOutputStream fichier = new FileOutputStream(name);
					oos = new ObjectOutputStream(fichier);
					if (oos != null) {
						oos.writeObject(map);
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
	
	public static void sauvegarderCanon(String nomFichier, Canons canonP) throws FichierExistant{ // ex : partie.sauvegarder ( "Test01" );
		String name = path.pathCanon + nomFichier;
		ObjectOutputStream oos = null;
		File pathF = new File(name);
		if (!pathF.exists()) {
			try {
				final FileOutputStream fichier = new FileOutputStream(name);
				oos = new ObjectOutputStream(fichier);
				oos.writeObject(canonP);
				oos.flush();
			} catch (final java.io.IOException e) {
				File paths = new File(path.pathCanon);
				paths.mkdirs();
			} finally {
				try {
					final FileOutputStream fichier = new FileOutputStream(name);
					oos = new ObjectOutputStream(fichier);
					if (oos != null) {
						oos.writeObject(canonP);
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
	
	public static void sauvegarderNavire(String nomFichier, Navire navire) throws FichierExistant{ // ex : partie.sauvegarder ( "Test01" );
		String name = path.pathBateau + nomFichier;
		ObjectOutputStream oos = null;
		File pathF = new File(name);
		if (!pathF.exists()) {
			try {
				final FileOutputStream fichier = new FileOutputStream(name);
				oos = new ObjectOutputStream(fichier);
				oos.writeObject(navire);
				oos.flush();
			} catch (final java.io.IOException e) {
				File paths = new File(path.pathBateau);
				paths.mkdirs();
			} finally {
				try {
					final FileOutputStream fichier = new FileOutputStream(name);
					oos = new ObjectOutputStream(fichier);
					if (oos != null) {
						oos.writeObject(navire);
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
	
	public static void sauvegarderPartie(String nomFichier, Partie partie) throws FichierExistant {
		String name = path.pathPartie + nomFichier;
		ObjectOutputStream oos = null;
		File pathF = new File(name);
		if (!pathF.exists()) {
			try {
				final FileOutputStream fichier = new FileOutputStream(name);
				oos = new ObjectOutputStream(fichier);
				oos.writeObject(partie);
				oos.flush();
			} catch (final java.io.IOException e) {
				File pathbis = new File(path.pathPartie);
				pathbis.mkdirs();
			} finally {
				try {
					final FileOutputStream fichier = new FileOutputStream(name);
					oos = new ObjectOutputStream(fichier);
					if (oos != null) {
						oos.writeObject(partie);
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


	public Plateau chargerMap(String nomFichier) throws FichierNonExistant {
		String name = path.pathMap + nomFichier;
		Plateau plateau = null;
		File pathF = new File(name);
		if (pathF.exists()) {
			ObjectInputStream ois = null;
			try {
				final FileInputStream fichier = new FileInputStream(name);
				ois = new ObjectInputStream(fichier);
				plateau = (Plateau) ois.readObject();
			} catch (final java.io.IOException e) {
				e.printStackTrace();
			} catch (final ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					if (ois != null) {
						ois.close();
					}
				} catch (final IOException ex) {
					ex.printStackTrace();
				}
			}
		} else {
			throw new FichierNonExistant(nomFichier);
		}
		return plateau;
	}

	public Canons chargerCanon(String nomFichier) throws FichierNonExistant {
		String name = path.pathCanon + nomFichier;
		Canons canon = null;
		File pathF = new File(name);
		if (pathF.exists()) {
			ObjectInputStream ois = null;
			try {
				final FileInputStream fichier = new FileInputStream(name);
				ois = new ObjectInputStream(fichier);
				canon = (Canons) ois.readObject();
			} catch (final java.io.IOException e) {
				e.printStackTrace();
			} catch (final ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					if (ois != null) {
						ois.close();
					}
				} catch (final IOException ex) {
	ex.printStackTrace();
				}
			}
		} else {
			throw new FichierNonExistant(nomFichier);
		}
		return canon;
	}

	public Navire chargerNavire(String nomFichier) throws FichierNonExistant {
		String name = path.pathBateau + nomFichier;
		Navire navire = null;
		File pathF = new File(name);
		if (pathF.exists()) {
			ObjectInputStream ois = null;
			try {
				final FileInputStream fichier = new FileInputStream(name);
				ois = new ObjectInputStream(fichier);
				navire = (Navire) ois.readObject();
			} catch (final java.io.IOException e) {
				e.printStackTrace();
			} catch (final ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					if (ois != null) {
						ois.close();
					}
				} catch (final IOException ex) {
					ex.printStackTrace();
				}
			}
		} else {
			throw new FichierNonExistant(nomFichier);
		}
		return navire;
	}
	
	public static Partie chargerPartie(String nomFichier) throws FichierNonExistant {
		String name = path.pathPartie + nomFichier;
		Partie partie = null;
		File pathF = new File(name);
		if (pathF.exists()) {
			ObjectInputStream ois = null;
			try {
				final FileInputStream fichier = new FileInputStream(name);
				ois = new ObjectInputStream(fichier);
				partie = (Partie) ois.readObject();
			} catch (final java.io.IOException e) {
				e.printStackTrace();
			} catch (final ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					if (ois != null) {
						ois.close();
					}
				} catch (final IOException ex) {
					ex.printStackTrace();
				}
			}
		} else {
			throw new FichierNonExistant(nomFichier);
		}
		return partie;
	}

	

}
