package gri2021.tp2;

public class Graph {
	private static int NULL = -1;//indique qu'un élément est non-initialisé
	int nbS, nbA;//nb sommets et arrêtes
	//int[] sommets;//Tout les sommets ne vont pas forcément de 0 à (nbs-1)
	int[] ls_adja[];//TODO: voir la correction, cette représentation de l'adjascence est sûrement fausse
	int degMax=0;//Le degré maximal d'un sommet dans tout le graph
	
	//Renvoie le nombre de voisin d'un sommet donné??
	public int neighbors(int u) {
		int j = 0;
		//System.out.println("u = "+u+" et j = "+j);
		while((j < ls_adja[u].length) && (ls_adja[u][j] != NULL)) {
				j++;
				if(j == ls_adja[u].length - 1) {
					return j;
				}
		}
		return j;
	}
	
	//retourne la liste des voisins d'un sommet donné
	public int[] get_neighbors(int u) {
		if(ls_adja[u] != null) {
			int[] ls = new int[ls_adja[u].length];
			for(int i=0;i<ls.length;i++) {
				ls[i] = ls_adja[u][i];
			}
			return ls;
		}
		return null;
	}
	
	//TODO: à corriger, on ne peut parcourir les sommets que dans un sens et pas l'autre!
	private void stock_adja(int[] origines, int[] extremites) {
		for(int i = 0;i<nbA;i++) {
			if(origines[i] != -1 && (ls_adja[origines[i]] != null)) {//On vérifie si le sommet "i" n'est pas dans le graph car il n'y a pas forcément tt les sommets de 0 à (nbS - 1)
				//On stock le nouvel adjasent
				int j = neighbors(origines[i]);
				//System.out.println("j = "+j);
				ls_adja[origines[i]][j] = extremites[i];
				//!!! on peut traverser le graph ds les 2 sens!!!
				//ls_adja[origines[i]][extremites[i]] = j;
				//On vérivie si cela ne marque pas le nouveau degré maxiaml attenit
				if((j+1) > degMax) {
					degMax = (j+1);
				}
			}
		}
	}
	
	//Va indiquer combiens de voisins possède le sommet passé en paramètre
		public int get_neighborsX(int x, int[] origines) {
			int v=0;//Indique le nombre de voisins
			for(int i=0;i<nbS;i++) {
				if(origines[i] == x) {
					v++;
				}
			}
			return v;
		}
	
	public Graph(Lecteur_Fichier read) {
		this.nbS = read.nbr_sommets();
		this.nbA = read.nbr_arretes();
		
		//Origines et extremites des arretes:
		int[] origines = read.get_ori();
		int[] extremites = read.get_extremi();
		
		//sommets = new int[nbS];
		ls_adja = new int[nbS][];
		//On initialise les tableaux
		for(int i = 0;i<nbS;i++) {
			//sommets[i] = i;
			//System.out.println("ini tableau i = "+i);
			if(get_neighborsX(i, origines) != 0) {
				ls_adja[i] = new int[get_neighborsX(i, origines)];
				//System.out.println("Le sommet "+0+" a "+read.get_neighborsX(0)+" voisins");
				//Initialisation edjasences
				for(int j = 0;j<ls_adja[i].length;j++) {
					ls_adja[i][j] = NULL;
				}
			}else {
				ls_adja[i] = null;
			}
		}
		stock_adja(origines, extremites);
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
	
	public int get_d_entre(int x, int y) {
		Parcour_Largeur pl = new Parcour_Largeur(nbS);
		return pl.Breadth_First_Search(this, x, y);
	}
	
	//Nouvelle fonction retourne le sommet avec la plus grande distance avec u
	public int get_plus_eloigne(int u) {
		int dmax = 0;//la distance maximal trouvée entre 2 voisins
		int v = u;//Le sommet le plus éloigné, si un sommet est isolé il se renvera lui-même
		int d;//distance observée
		//TODO: Pas bon méthodologie quadratic trop lourde, revoir le "Parcour_Largeur" directement
		for(int i=0;i<nbS;i++) {
			if(i != u) {
				d = get_d_entre(u, i);
				System.out.println("distance entre "+u+" et "+i+" = "+d);
				if(d > dmax) {//1er max courrant trouvé
					dmax = d;
					v = i;
				}
			}
		}
		return v;
	}
	
}
