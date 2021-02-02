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
			//print_voisins(u,g);
			int v = g.get_plus_eloigne(u);
			int w = g.get_plus_eloigne(v);
			//int diam = g.get_d_entreXY(v, w);
			int diam = g.get_dmax_pl();//Inutile de refaire un parcours en largeur
			System.out.println("v="+v);
			System.out.println("w="+w);
			System.out.println("diam>="+diam);
			mem();
		}else if(action.compareTo("4-sweep") == 0) {
			//TODO
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
		}else {
			throw new Exception("L'action <<"+action+">> est inconnue du programme!");
		}
		//FINI
	}

}
