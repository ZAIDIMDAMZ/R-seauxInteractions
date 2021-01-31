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

	public static void main(String[] args) throws Exception {
		//Le fichier doit être lu avant de construire le graph
		String action = args[0];
		String fichier = args[1];
		int estim = Integer.parseInt(args[2]);//Une estimation du nbr d'arcs
		int u = Integer.parseInt(args[3]);//Le point de départ du parcours
		if(estim <= 0) {
			throw new Exception("Le paramètre 'estimation du nombre d'arcrs' est erroné");
		}
		System.out.println("action = "+action+", fichier = "+fichier +", arg1 = "+estim+" et on part du point "+u);
		if(action.compareTo("2-sweep") == 0) {
			Lecteur_Fichier Reader = new Lecteur_Fichier(fichier, estim);
			//System.out.println("Lecture du graph en cours:");
			Reader.Read();
			//System.out.println("Lecture terminée:");
			mem();
			//On génère un Graph pour mieux stocker les données qui viennent d'être lues
			Graph g = new Graph(Reader);
			//System.out.println("n = "+g.get_nbS()+", m = "+g.get_nbA()+", degMax = "+g.get_degMax());
			//print_voisins(u,g);
			int v = g.get_plus_eloigne(u);
			//TODO: a tester plus tard, 1 chose à la fois
			int w = g.get_plus_eloigne(v);
			int diam = g.get_d_entreXY(v, w);
			System.out.println("v="+v);
			System.out.println("w="+w);
			System.out.println("diam>="+diam);
			mem();
		}else if(action.compareTo("4-sweep") == 0) {
			//TODO les else autres actions
		}else {
			throw new Exception("L'action <<"+action+">> est inconnue du programme!");
		}
		//FINI
	}

}
