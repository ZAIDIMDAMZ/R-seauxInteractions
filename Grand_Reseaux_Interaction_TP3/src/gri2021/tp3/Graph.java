package gri2021.tp3;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

//Nouvelle version du graph qui est basée sur la correctio du TP1, l'ancienne version était trop instable pour être réutilisée
//J'ai essayé de ne pas trop recopier ce que je ne comprend pas
public class Graph implements Iterable<Integer> {
	int nbS, nbA;//nb sommets et arrêtes
	int[] adja;// la liste des adjasences concaténées
	int [] offset; // offset[u] renvoie l'index des voisins de u dans son adjasence
	int degMax=0;//Le degré maximal d'un sommet dans tout le graph
	//Parcour_Largeur pl = null;//Le graph peut garder en mémoire le résultat d'1 parcours en longueur à la fois, cela poura être utile pour réutiliser un résultat de parcours déjà effectué
	
	
	//Renvoie un "Iterable" des voisins du points u: la fonction ".iterator()" du résultat permet d'obtenir un iterateur contenant tout les voisin de u
	public Iterable<Integer> neighbors(int u) {
		return new Iterable<Integer>() {
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {
                    int off_beg = offset[u];
                    int off_end = offset[u+1];
                    int off_nxt = off_beg; // offset of next neighobr
                    public boolean hasNext() { return off_nxt < off_end; }
                    public Integer next() {
                        if (off_nxt >= off_end) {
                            throw new NoSuchElementException();
                        }
                        return adja[off_nxt++];
                    }
                    //UnsupportedOperationException
                };
            }
        };
	}
	
	//"symmetrize" indique si le graphe est non-orienté, "true" signifie que c'est non orienté
	public Graph(Lecteur_Fichier read, boolean symmetrize) {
		this.nbS = read.nbr_sommets();
		this.nbA = read.nbr_arretes();
		
		//Il est plus logique que le lecteur de fichier calcul directement les degrés
		int [] deg = read.get_degs(symmetrize);//La liste des degrés des sommets du graph
		offset = new int[nbS+1];
		offset[0] = 0;
		for(int i = 0; i < nbS;i++) {
			offset[i+1] = offset[i] + deg[i];
			deg[i] = 0; //La liste sera réuttilisée en tant qu'index de "neighb[u]" pour gagner de la mémoire
		}
		adja = new int[offset[nbS]];
		for (int i = 0; i < nbA; i++) {
			int d = read.get_depart_at(i);
			int a = read.get_arive_at(i);
			adja[offset[d] + deg[d]] = a;
			deg[d]++;
			if(symmetrize) {
				adja[offset[a] + deg[a]] = d;
				deg[a]++;
			}
		}
	}
	
	public int get_nbS() {
		return nbS;
	}
	
	public int get_nbA() {
		return nbA;
	}
	
	public int get_degMax() {
		return degMax;
	}
	
	//Retourne le degré d'un sommet
	public int get_deg(int s) {
		return offset[s+1] - offset[s];
	}

	@Override
	public Iterator<Integer> iterator() {
		return IntStream.range(0, nbS).iterator();
	}
	
}
