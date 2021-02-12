package gri2021.tp3;

public class Graph {
	private static int NULL = -1;//indique qu'un élément est non-initialisé
	int nbS, nbA;//nb sommets et arrêtes
	//int[] sommets;//Tout les sommets ne vont pas forcément de 0 à (nbs-1)
	int[][] ls_adja;//TODO: À amméliorer
	int degMax=0;//Le degré maximal d'un sommet dans tout le graph
	//Parcour_Largeur pl = null;//Le graph peut garder en mémoire le résultat d'1 parcours en longueur à la fois, cela poura être utile pour réutiliser un résultat de parcours déjà effectué
	
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
				//Si l'un des "voisins" de u est -1 cela signifie que le point ne se trouve pas sur le Graph
			if(ls_adja[u][i] == -1) {
					return null;
				}
				ls[i] = ls_adja[u][i];
			}
			return ls;
		}
		return null;
	}
	
	//TODO: À corriger, ne marche pas sur certains gros graphes
	//On peut maintenant lire le graph dans les 2 sens
	private void stock_adja(int[] origines, int[] extremites) {
		for(int i = 0;i<nbA;i++) {
			if(origines[i] != -1 && (ls_adja[origines[i]] != null)) {//On vérifie si le sommet "i" n'est pas dans le graph car il n'y a pas forcément tt les sommets de 0 à (nbS - 1)
				//On stock le nouvel adjasent
				int j = neighbors(origines[i]);//j = le nombre actuel de voisins observés pour le sommet indiqué par origines[i]
				System.out.println("orignies[i] = "+origines[i]+" et j = "+j);
				//System.out.println("A "+ls_adja[origines[i]].length+" voisins");
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
	
	//TODO: À corriger, semble parfois renvoyer de mauvaises valeurs
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
				//TODO: construit parfois des liste trop petite
				ls_adja[i] = new int[v_de_i];
				System.out.println("Le sommet "+i+" a "+v_de_i+" voisins");
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
	
	//Des fonctions spécifiques au TP2, ne serons peut-être pas pertinantes dans ce cas.
	/*
	//Retourne la distance entre X et Y
	public int get_d_entreXY(int x, int y) {
		pl = new Parcour_Largeur(nbS);
		//Commande un BFS partiel (qui prend fin une fois l'objectif atteint)
		return pl.Breadth_First_Search(this, x, y);
	}
	
	//Retourne le sommet avec le éloigné de u
	public int get_plus_eloigne(int u) {
		//On va effectuer un parcours en Largeur complet.
		pl = new Parcour_Largeur(nbS);
		//Commande un BFS total partant de u
		return pl.Breadth_First_Search(this, u);
	}
	
	//Retourne la distance maximale observée par un parcours en Largeur terminé
	public int get_dmax_pl() throws Exception {
		if(pl != null) {
			return pl.get_dist_max();
		}
		throw new Exception("Aucun Parcours en Largeur n'a été effectué");
	}
	
	//Retourne un sommet m situé au milieu du chemin entre l'origine et le sommet le plus éloigné d'un parcours en Largeur terminé
	public int get_sommet_m(int w) throws Exception {
		if(pl != null) {
			return pl.get_pts_m(this, w);
		}
		throw new Exception("Aucun Parcours en Largeur n'a été effectué");
	}
	
	//Retourne la somme des distances depuis le sommet d'origine d'un parcours en largeur terminé
	public int[] maj_sumdist(int[] sumdist) throws Exception {
		if(pl != null) {
			int[] dist = pl.get_dist();
			for(int i = 0;i<sumdist.length;i++) {
				//?? On met à jours la somme des distances ??
				sumdist[i] += dist[i];
			}
			return sumdist;
		}
		throw new Exception("Aucun Parcours en Largeur n'a été effectué");
	}
	
	//Met à jours l'excentricité de chaque sommet
	public int[] maj_ecc(int[] ecc) throws Exception {
		if(pl != null) {
			int[] dist = pl.get_dist();
			for(int i = 0;i<ecc.length;i++) {
				//Si la distance d'un sommet donné est plus élevée que sa précédente excentricité, elle deviendra la nouvelle valeur de son excentricité
				if(ecc[i] < dist[i]) {
					ecc[i] = dist[i];
				}
			}
			return ecc;
		}
		throw new Exception("Aucun Parcours en Largeur n'a été effectué");
	}
	
	public int[] get_dist() throws Exception{
		if(pl != null) {
			return pl.get_dist();
		}
		throw new Exception("Aucun Parcours en Largeur n'a été effectué");
	}
	
	//retourne les sommets formant la compossante fortement connexe résultant du dernier parcours en largeur effectué
	public boolean[] get_CC() throws Exception{
		if(pl != null) {
			return pl.get_deja_vu();
		}
		throw new Exception("Aucun Parcours en Largeur n'a été effectué");
	}
	
	//met à jours les sommets qui ont été visités durrant le programme
	public boolean[] maj_deja_lu(boolean [] deja_lu) throws Exception{
		if(pl != null) {
			boolean [] CC_actu = pl.get_deja_vu();
			for(int i=0;i<deja_lu.length;i++) {
				if(CC_actu[i]) {
					deja_lu[i] = true;//On aura déjà ateint ce point, on n'y retournera plus pour gagner du temps
				}
			}
			return pl.get_deja_vu();
		}
		throw new Exception("Aucun Parcours en Largeur n'a été effectué");
	}
	
	//met à jours les sommets qui ont été visités durrant le programme
	public int get_size_CC() throws Exception{
		if(pl != null) {
			boolean [] deja_vu = pl.get_deja_vu();
			int size = 0;
			for(int i=0;i<deja_vu.length;i++) {
				if(deja_vu[i]) {
					size++;//On compte combiens de sommet ont été observés
				}
			}
			return size;
		}
		throw new Exception("Aucun Parcours en Largeur n'a été effectué");
	}
	*/
	
}
