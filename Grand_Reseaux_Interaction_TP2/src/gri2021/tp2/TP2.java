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

	//2-sweep g.txt 6 1 ; 4-sweep g.txt 6 1
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
			//initialiser les valeurs
			for(int i = 0;i<sumdist.length;i++) {
				sumdist[i] = 0;
			}
			int v = g.get_plus_eloigne(u);
			//sumdist[v] = g.get_dmax_pl();
			//???TODO????
			sumdist = g.maj_sumdist(sumdist);
			int depart = get_higher(sumdist);
			int w = g.get_plus_eloigne(depart);
			//MAJ des somme de distances
			sumdist = g.maj_sumdist(sumdist);
			depart = get_higher(sumdist);//Recherche du nouveau départ
			int x = g.get_plus_eloigne(depart);
			sumdist = g.maj_sumdist(sumdist);
			//!TODO! Je n'ai pas compris ce qui est demandé par "ecc"
			int diam;
			if((sumdist[u] > sumdist[v]) && (sumdist[u] > sumdist[w]) && (sumdist[u] > sumdist[x])) {
				diam = sumdist[u];
			}else if((sumdist[v] > sumdist[u]) && (sumdist[v] > sumdist[w]) && (sumdist[v] > sumdist[x])) {
				diam = sumdist[v];
			}else if((sumdist[w] > sumdist[u]) && (sumdist[w] > sumdist[v]) && (sumdist[w] > sumdist[x])) {
				diam = sumdist[w];
			}else {
				diam = sumdist[x];
			}
			System.out.println("diam?="+diam+"??");
			
		}else {
			throw new Exception("L'action <<"+action+">> est inconnue du programme!");
		}
		//FINI
	}

}
