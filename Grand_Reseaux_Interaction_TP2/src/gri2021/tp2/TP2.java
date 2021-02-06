package gri2021.tp2;

public class TP2 {
	
	public static void mem() {
		Runtime rt = Runtime.getRuntime();
		rt.gc();
		System.err.println("Allocated memory :"
				+ (rt.totalMemory() - rt.freeMemory()) / 1000000
				+ " Mb");
		System.err.flush();
	}
	
	//Fonction de debuggage: affiche la liste des voisins
	public static void print_voisins(int x, Graph g) {
		int [] lsV = g.get_neighbors(x);
		System.out.print("Voisins de "+x+": ");
		for(int i = 0;i<lsV.length;i++) {
			System.out.print(""+lsV[i]+" ");
		}
	}
	
	//Retourne le plus grand élément d'un tableau
	private static int get_higher(int[] tbl) {
		int max = -1;
		int s = -1;
		for(int i=0;i<tbl.length;i++) {
			if(tbl[i] > max) {
				s = i;
			}
		}
		return s;
	}

	//2-sweep g.txt 6 1 ; 4-sweep g.txt 6 1 ; sum-sweep g.txt 6 1 ; diametre g.txt 6 1 ; bigest-cc g.txt 6
	public static void main(String[] args) throws Exception {
		//Le fichier doit être lu avant de construire le graph
		String action = args[0];
		String fichier = args[1];
		int estim = Integer.parseInt(args[2]);//Une estimation du nbr d'arcs
		int u = Integer.parseInt(args[3]);//Le point de départ du parcours
		if(estim <= 0) {
			throw new Exception("Le paramètre 'estimation du nombre d'arcrs' est erroné");
		}
		Lecteur_Fichier Reader = new Lecteur_Fichier(fichier, estim);
		System.out.println("action = "+action+", fichier = "+fichier +", arg1 = "+estim+" et on part du point "+u);
		if(action.compareTo("2-sweep") == 0) {
			//System.out.println("Lecture du graph en cours:");
			Reader.Read();
			//System.out.println("Lecture terminée:");
			mem();
			//On génère un Graph pour mieux stocker les données qui viennent d'être lues
			Graph g = new Graph(Reader);
			//System.out.println("n = "+g.get_nbS()+", m = "+g.get_nbA()+", degMax = "+g.get_degMax());
			int v = g.get_plus_eloigne(u);
			int w = g.get_plus_eloigne(v);
			//int diam = g.get_d_entreXY(v, w);
			int diam = g.get_dmax_pl();//Inutile de refaire un parcours en largeur
			System.out.println("v="+v);
			System.out.println("w="+w);
			System.out.println("diam>="+diam);
			mem();
		}else if(action.compareTo("4-sweep") == 0) {
			//System.out.println("Lecture du graph en cours:");
			Reader.Read();
			//System.out.println("Lecture terminée:");
			mem();
			//On génère un Graph pour mieux stocker les données qui viennent d'être lues
			Graph g = new Graph(Reader);
			//System.out.println("n = "+g.get_nbS()+", m = "+g.get_nbA()+", degMax = "+g.get_degMax());
			//print_voisins(u,g);
			int v = g.get_plus_eloigne(u);
			int w = g.get_plus_eloigne(v);
			//System.out.println("v="+v+", et w = "+w);//Debugage
			//On trouve le sommet m à partir du parcours en largeur déjà effectué
			int m = g.get_sommet_m(w);
			//System.out.println("m="+m);
			mem();
			//On refait un double parcours en largeur
			int vm = g.get_plus_eloigne(m);
			/*int wm = g.get_plus_eloigne(vm);
			 * System.out.println("vw = "+vw)
			 */
			g.get_plus_eloigne(vm);//Inutile de stocker ce point on ne nous demande pas de l'afficher
			int diam = g.get_dmax_pl();
			System.out.println("diam>="+diam);
			mem();
		}else if(action.compareTo("sum-sweep") == 0) {
			//System.out.println("Lecture du graph en cours:");
			Reader.Read();
			//System.out.println("Lecture terminée:");
			mem();
			//On génère un Graph pour mieux stocker les données qui viennent d'être lues
			Graph g = new Graph(Reader);
			//System.out.println("n = "+g.get_nbS()+", m = "+g.get_nbA()+", degMax = "+g.get_degMax());
			int[] sumdist = new int[g.get_nbS()];
			int[] ecc = new int[g.get_nbS()];//Met à jours l'excentricité de chaque sommet à tout moment du graphe
			//initialiser les valeurs
			for(int i = 0;i<sumdist.length;i++) {
				sumdist[i] = 0;
				ecc[i] = -1;//L'excentricité d'un sommet est la distance la plus élevée observée sur tout les parcours en largeur (ds les limites de notre programme)
			}
			int v = g.get_plus_eloigne(u);
			//sumdist[v] = g.get_dmax_pl();
			//???TODO????
			sumdist = g.maj_sumdist(sumdist);
			ecc = g.maj_ecc(ecc);
			int depart = get_higher(sumdist);
			int w = g.get_plus_eloigne(depart);
			//MAJ des somme de distances
			sumdist = g.maj_sumdist(sumdist);
			ecc = g.maj_ecc(ecc);
			depart = get_higher(sumdist);//Recherche du nouveau départ
			int x = g.get_plus_eloigne(depart);
			sumdist = g.maj_sumdist(sumdist);
			ecc = g.maj_ecc(ecc);
			//ecc = plus longue distance observée en paratant d'un point donné vers tout les autres possibles
			int diam;
			if((ecc[u] > ecc[v]) && (ecc[u] > ecc[w]) && (ecc[u] > ecc[x])) {
				diam = ecc[u];
			}else if((ecc[v] > ecc[u]) && (ecc[v] > ecc[w]) && (ecc[v] > ecc[x])) {
				diam = ecc[v];
			}else if((ecc[w] > ecc[u]) && (ecc[w] > ecc[v]) && (ecc[w] > ecc[x])) {
				diam = ecc[w];
			}else {
				diam = ecc[x];
			}
			System.out.println("diam="+diam);
		}else if(action.compareTo("diametre") == 0) {
			//System.out.println("Lecture du graph en cours:");
			Reader.Read();
			//System.out.println("Lecture terminée:");
			mem();
			//On génère un Graph pour mieux stocker les données qui viennent d'être lues
			Graph g = new Graph(Reader);
			//System.out.println("n = "+g.get_nbS()+", m = "+g.get_nbA()+", degMax = "+g.get_degMax());
			
			//TODO
			
			int[] eccsup = new int[g.get_nbS()];//borne supérieur pour chaque sommet
			//initialiser les valeurs
			for(int i = 0;i<eccsup.length;i++) {
				eccsup[i] = estim +1;//Il faut donné une valeur initiale assez importante, comme c'est initialement égal au nombre d'arrêtes + 1 on est sûr qu'il y aurra plus petit dés la première verif
			}
			int diamlow = -1;
			int a = u;//Le premier a est "u"
			boolean [] CCu = null;//Les sommets faissant partie de la compossante connexe partant de "u"
			//tant qu'on ne retourne pas l'estimation du diamettre
			while(true) {
				//???TODO: À vérifier???
				g.get_plus_eloigne(a);//Effectuer un parcours en largeur à partir dea.
				int ecc_a = g.get_dmax_pl();
				//Le premier PL effectué part de "u", "CCu" ne sera définit qu'une seule fois
				if(CCu == null) {
					//Les sommets qui ont été marqués comme "deja_vu" durrant le parcours en largeur partant de "u" forment sa composante connexe:
					CCu = g.get_CC();
					//(Le but est d'éviter de séparer le premier PL de la boucle while princiapale)
				}
				//Si ecc(a)> diamlow, alors mettre à jour diamlow:=ecc(a)
				if(ecc_a > diamlow) {diamlow = ecc_a;}
				int [] b = new int[g.get_nbS()];//La borne de chaque sommet
				int [] dist = g.get_dist();//Les distances de a à un point donné
				int max = -1;//La borne la plus élevée observée
				for(int i = 0;i<b.length;i++) {
					//TODO: Y a t'il un problème avec les sommets qui n'apparaissent pas sur le Graph? (il n'y a pas forcément tout les sommet de 0 à nbS...)
					b[i] = dist[i] +ecc_a;//Initialisation des bornes:
					if(b[i] < eccsup[i]) {
						eccsup[i] = b[i];
					}
					//Choisir un sommet a € Cu de borne supérieur maximum
					if(CCu[i] && eccsup[i] > max){
						max = eccsup[i];
						a = i;
					}
				}
				if(eccsup[a] <= diamlow) {
					break;//On a l'estimation du diamètre
				}
			}
			System.out.println("diam="+diamlow);
			
			//L'instruction bigest-cc retournera un point de la plus grande composante
		}else if(action.compareTo("bigest-cc") == 0) {
			//System.out.println("Lecture du graph en cours:");
			Reader.Read();
			//System.out.println("Lecture terminée:");
			mem();
			//On génère un Graph pour mieux stocker les données qui viennent d'être lues
			Graph g = new Graph(Reader);
			boolean [] deja_lu = new boolean[g.get_nbS()];//Indique si tout les sommets du graph ont été visités au moin une fois
			for(int i = 0;i<deja_lu.length;i++) {deja_lu[i] = false;}//On indique qu'aucun sommet n'a été visité
			int cc_max = 0;//Indique la distance maximale atteinte parmi toutes les compossantes du graph
			int s = -1;//Le sommet que l'on retournera
			//On va essayer de parcourir tout les sommets du Graph
			for(int i = 0;i<g.get_nbS();i++) {
				//On effectue un parcours en largeur du premier point pas encore visité que l'on trouve
				if(deja_lu[i] == false) {
					System.out.println("BFS partant de "+i);
					g.get_plus_eloigne(i);//On fait un parcours en largeur complet en partant du point
					int dmax = g.get_dmax_pl();//La distance maxiamle observée pour ce BFS
					//Ce point ammène t'il à la plus grande CC?
					if(dmax > cc_max) {
						cc_max = dmax;
						s = i;
					}
					//On met à jours les sommets visités lors des parcours en largeur
					g.maj_deja_lu(deja_lu);//Inutile de refaire un BFS partant d'un point qui aura déjà été atteint
				}
				mem();
			}
			System.out.println("La plus grande compossante du graph est de taille = "+cc_max+", elle peut être atteinte par le sommet:"+s);
		
		}else {
			throw new Exception("L'action <<"+action+">> est inconnue du programme!");
		}
		//FINI
	}

}
