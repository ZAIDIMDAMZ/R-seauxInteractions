package gri2021.tp1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//Va ouvrir les fichier
public class Lecteur_Fichier {
	String path;
	//Stockera les arrêtes qui serons lues dans le document
	int [] data_d;//Les points de départ des arrêtes
	int [] data_a;//Les points d'arrivé des arrêtes
	//
	int max = -1;//L'identifiant de somet avec la valeur la plus élevée, initialement égal à -1
	int nb_arrete;//le nombre d'arrete uttilisées en pratique
	
	public Lecteur_Fichier(String path, int estima) {
		this.path = path;
		//Le nombre d'arrête donné n'est qu'une estimation, par sécurité on rajoute 3 de cette estimation pour définir la taille du tableau data
		int secu = (int) (estima + (estima * 0.03));
		data_d = new int[secu];
		data_a = new int[secu];
	}
	
	public int nbr_sommets() {
		//retourne le nombre de sommets du graph, le plus grand index trouvé + 1
		return (max + 1);
	}
	
	//retourne le nbr d'arrêtes qui a été lu dans le graph en pratique
	public int nbr_arretes() {
		return nb_arrete;
	}
	
	public int get_depart_at(int i) {
		return data_d[i];
	}
	
	public int get_arive_at(int i) {
		return data_a[i];
	}
	
	//Extrait les information intéressantes des la lignes
	private void lire_arrete(String ligne, int i) {
		boolean Is_ok;
		//Si le premier caractère de la ligne est un #, c'est que la ligne est un commentaire à ignorer
		if(Character.compare(ligne.charAt(0), '#') == 0) {
			Is_ok = false;
			System.out.println("Commentaire");
		}else {
			Is_ok = true;
		}
		if(Is_ok) {
			//On extrait les chifres.
			//Tabulation = "\t"
			ligne = ligne.replace("\t", " ");//Il y a des fois où les sommets sont séparés par une tabulation et non un espace
			//System.out.println("actu = "+ligne.substring(0, ligne.indexOf(" ")));
			int d = Integer.parseInt(ligne.substring(0, ligne.indexOf(" ")));
			//System.out.println(""+ligne.substring(ligne.indexOf(" ")+1));
			int a = Integer.parseInt(ligne.substring(ligne.lastIndexOf(" ")+1));
			//System.out.println("arrête: Départ = "+d+", arrivé = "+a);
			data_d[i] = d;
			data_a[i] = a;
			//On met à jours quel est le plus grand index de sommet rencontré
			if(d > max) {
				max = d;
			}
			if(a > max) {
				max = a;
			}
		}
	}
	
	public void Read() throws Exception {
		int i = 0;
		try {
			BufferedReader read = new BufferedReader(new FileReader(path));
			try {
				String str;
				while((str = read.readLine()) != null) {
					//Si on n'a plus de place pour stocker les nombre on arrête tout
					if(i >= data_d.length) {
						read.close();
						throw new Exception("Votre estimation du nombre d'arrête était trop petite!");
					}
					//System.out.println(""+ str);
					lire_arrete(str, i);
					i++;
				}
				nb_arrete = i;
				read.close();
			} catch (IOException e) {
				System.err.println(e);
			}
		} catch (FileNotFoundException e) {
			System.err.println(e);
		}
	}
}
