package gri2021.tp2;

public class Graph {
	private static int NULL = -1;//indique qu'un élément est non-initialisé
	int nbS, nbA;//nb sommets et arrêtes
	//int[] sommets;//Tout les sommets ne vont pas forcément de 0 à (nbs-1)
	int[][] ls_adja;//TODO: voir la correction, cette représentation de l'adjascence est sûrement fausse
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
	
	//On peut maintenant lire le graph dans les 2 sens
	private void stock_adja(int[] origines, int[] extremites) {
		for(int i = 0;i<nbA;i++) {
			if(origines[i] != -1 && (ls_adja[origines[i]] != null)) {//On vérifie si le sommet "i" n'est pas dans le graph car il n'y a pas forcément tt les sommets de 0 à (nbS - 1)
				//On stock le nouvel adjasent
				int j = neighbors(origines[i]);//j = le nombre actuel de voisins observés pour le sommet indiqué par origines[i]
				//System.out.println("j = "+j);
				ls_adja[origines[i]][j] = extremites[i];
				//On stocke l'adjascence de l'extrêmité
				int k = neighbors(extremites[i]);//k = le nombre actuel de voisins observés pour le point origines[i]
				ls_adja[extremites[i]][k] = origines[i];
				//On vérivie si cela ne marque pas le nouveau degré maxiaml attenit
				if((j+1) > degMax) {
					degMax = (j+1);
				}else if((k+1) > degMax) {
					degMax = (k+1);
				}
			}
		}
	}
	
	//Va indiquer combiens de voisins possède le sommet passé en paramètre
		public int get_neighborsX(int x, int[] origines, int[] extremites) {
			int v=0;//Indique le nombre de voisins
			for(int i=0;i<nbS;i++) {
				if((origines[i] == x) || ((extremites[i] == x))) {
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
			int v_de_i = get_neighborsX(i, origines, extremites);//Le nombre de voisins du point "i", n'est calculé qu'une fois par i pour gagner du temps
			if(v_de_i > 0) {
				ls_adja[i] = new int[v_de_i];
				//System.out.println("Le sommet "+i+" a "+v_de_i+" voisins");
				//Initialisation adjasences
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
	
	//Retourne la distance entre X et Y
	public int get_d_entreXY(int x, int y) {
		Parcour_Largeur pl = new Parcour_Largeur(nbS);
		//Commande un BFS partiel (qui prend fin une fois l'objectif atteint)
		return pl.Breadth_First_Search(this, x, y);
	}
	
	//Retourne le sommet avec le éloigné de u
	public int get_plus_eloigne(int u) {
		//On va effectuer un parcours en Largeur complet.
		Parcour_Largeur pl = new Parcour_Largeur(nbS);
		//Commande un BFS total partant de u
		return pl.Breadth_First_Search(this, u);
	}
	
}
