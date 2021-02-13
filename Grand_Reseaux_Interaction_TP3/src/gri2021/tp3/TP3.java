package gri2021.tp3;

import java.util.Iterator;

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
		Iterator<Integer> voisins = g.neighbors(x).iterator();
		System.out.print("Voisins de "+x+": ");
		while(voisins.hasNext()) {
			System.out.print(""+voisins.next()+" ");
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

	//triangles test.txt 7 1 ; triangles as-caida20071105-simple.txt 53381 123
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
			Graph g = new Graph(Reader, true);
			Voisinage voisi = new Voisinage(g.nbS);
			System.out.println(""+voisi.nb_triangles(u, g));
			
		}else if(action.compareTo("clust") == 0) {
			//System.out.println("Lecture du graph en cours:");
			Reader.Read();
			//System.out.println("Lecture terminée:");
			//mem();
			//On génère un Graph pour mieux stocker les données qui viennent d'être lues
			Graph g = new Graph(Reader, true);
			Voisinage voisi = new Voisinage(g.nbS);
			//double [] cluL = new double[g.get_nbS()];//Le clustering local de cahque sommet
			double cluL = 0.0;
			
			//Clustering local de chaque sommet:
			for(int i = 0;i<g.get_nbS();i++) {
				int tri = voisi.nb_triangles(u, g);
				cluL += (double) ((2*tri)/(g.get_deg(u)*(g.get_deg(u) - 1)));
				
			}
			cluL = cluL/g.get_nbS();
			//Clustering local moyen:			
			//TODO: fonction qui calcul nv = le nombre de "2 arêtes incidentes" du graphe
			//double cluG = (3 * triG)/(nv);//TODO triG = nombre de triangles totales dans le graphe
		}else {
			throw new Exception("L'action <<"+action+">> est inconnue du programme!");
		}
		//FINI
	}
}
