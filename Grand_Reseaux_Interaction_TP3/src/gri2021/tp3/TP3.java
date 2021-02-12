package gri2021.tp3;

public class TP3 {
	
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
	
	/*
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
	*/

	//2-sweep g.txt 6 1 ; 4-sweep g.txt 6 1 ; sum-sweep g.txt 6 1 ; diametre g.txt 6 1 ; bigest-cc g.txt 6
	//bigest-cc g.txt 396160 1
	public static void main(String[] args) throws Exception {
		//Le fichier doit être lu avant de construire le graph
		String action = args[0];
		String fichier = args[1];
		int estim = Integer.parseInt(args[2]);//Une estimation du nbr d'arcs
		int u = Integer.parseInt(args[3]);//Le point de départ du parcours
		if(estim <= 0) {
			throw new Exception("Le paramètre 'estimation du nombre d'arcs' est erroné");
		}
		Lecteur_Fichier Reader = new Lecteur_Fichier(fichier, estim);
		//System.out.println("action = "+action+", fichier = "+fichier +", arg1 = "+estim+" et on part du point "+u);
		if(action.compareTo("triangles") == 0) {
			//System.out.println("Lecture du graph en cours:");
			Reader.Read();
			//System.out.println("Lecture terminée:");
			//mem();
			//On génère un Graph pour mieux stocker les données qui viennent d'être lues
			Graph g = new Graph(Reader);
			Voisinage voisi = new Voisinage(g.nbS);
			System.out.println(""+voisi.nb_triangles(u, g));
			
		}else if(action.compareTo("clust") == 0) {
			/*
			//System.out.println("Lecture du graph en cours:");
			Reader.Read();
			//System.out.println("Lecture terminée:");
			mem();
			//On génère un Graph pour mieux stocker les données qui viennent d'être lues
			Graph g = new Graph(Reader);
			//System.out.println("n = "+g.get_nbS()+", m = "+g.get_nbA()+", degMax = "+g.get_degMax());
			 * */
		}else {
			throw new Exception("L'action <<"+action+">> est inconnue du programme!");
		}
		//FINI
	}
}
