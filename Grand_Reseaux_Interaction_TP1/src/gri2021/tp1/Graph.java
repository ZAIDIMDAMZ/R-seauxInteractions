package gri2021.tp1;

public class Graph {
	private static int NULL = -1;//indique qu'un élément est non-initialisé
	int nbS, nbA;//nb sommets et arrêtes
	//int[] sommets;
	int[] ls_adja[];
	int degMax=0;//Le degré maximal d'un sommet dans tout le graph
	
	//Renvoie le nombre de voisin d'un sommet donné??
	public int neighbors(int u) {
		int j = 0;
		while(ls_adja[u][j] != NULL) {
			j++;
		}
		return j;
	}
	
	//retourne la liste des voisins d'un sommet donné
	public int[] get_neighbors(int u) {
		int[] ls = new int[neighbors(u)];
		for(int i=0;i<neighbors(u);i++) {
			ls[i] = ls_adja[u][i];
		}
		return ls;
	}
	
	private void stock_adja(Lecteur_Fichier read) {
		for(int i = 0;i<nbA;i++) {
			//On stock le nouvel adjasent
			int j = neighbors(read.get_depart_at(i));
			ls_adja[read.get_depart_at(i)][j] = read.get_arive_at(i);
			//On vérivie si cela ne marque pas le nouveau degré maxiaml attenit
			if((j+1) > degMax) {
				degMax = (j+1);
			}
		}
	}
	
	public Graph(Lecteur_Fichier read) {
		this.nbS = read.nbr_sommets();
		this.nbA = read.nbr_arretes();
		//sommets = new int[nbS];
		ls_adja = new int[nbS][];
		//On initialise les tableaux
		for(int i = 0;i<nbS;i++) {
			//sommets[i] = i;
			ls_adja[i] = new int[nbS];
			//Initialisation edjasences
			for(int j = 0;j<nbS;j++) {
				ls_adja[i][j] = NULL;
			}
		}
		stock_adja(read);
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
	
}
