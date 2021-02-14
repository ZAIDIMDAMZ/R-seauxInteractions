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
	
	//Indique si l'argument donné est connu ou non de l'exécution
	private static boolean action_connue(String arg) {
		if(arg.compareTo("triangles") == 0 || arg.compareTo("clust") == 0) {
			return true;
		}
		return false;
	}
	
	//triangles test.txt 7 1 ; triangles as-caida20071105-simple.txt 53381 123 ; triangles as20000102-simple.txt 12572 123
	// clust test.txt 7 ; clust as-caida20071105-simple.txt 53381 ; clust as20000102-simple.txt 12572
	public static void main(String[] args) throws Exception {
		//Le fichier doit être lu avant de construire le graph
		String action = args[0];
		String fichier = args[1];
		int estim = Integer.parseInt(args[2]);//Une estimation du nbr d'arcs
		if(estim <= 0) {
			throw new Exception("Le paramètre 'estimation du nombre d'arcs' est erroné");
		}
		if(!action_connue(action)) {
			throw new Exception("L'action <<"+action+">> est inconnue du programme!");
		}
		Lecteur_Fichier Reader = new Lecteur_Fichier(fichier, estim);
		//System.out.println("action = "+action+", fichier = "+fichier +", arg1 = "+estim+" et on part du point "+u);
		//System.out.println("Lecture du graph en cours:");
		Reader.Read();
		//System.out.println("Lecture terminée:");
		//mem();
		//On génère un Graph pour mieux stocker les données qui viennent d'être lues
		Graph g = new Graph(Reader, true);
		Voisinage voisi = new Voisinage(g.nbS);//Étude du voisinage des sommets entre eux
		if(action.compareTo("triangles") == 0) {
			int u = Integer.parseInt(args[3]);//Le point de départ du parcours
			System.out.println(""+voisi.nb_triangles(u, g));
		}else if(action.compareTo("clust") == 0) {
			//double [] cluL = new double[g.get_nbS()];//Le clustering local de cahque sommet
			double cluL = 0.0;
			
			//Clustering local de chaque sommet:
			for(int i = 0;i<g.get_nbS();i++) {
				int tri = voisi.nb_triangles(i, g);//TODO: la fonction est lente à s'exécuter
				//System.out.println("      Le sommet "+i+" est contenu dans "+tri+" triangles");
				if(g.get_deg(i)*(g.get_deg(i) - 1) > 0){
					//System.out.println("      deg("+i+") = "+g.get_deg(i));
					//Peut être utilisé à la place du "+=" de cluL si on souhaite débuger
					/*
					double cluLx = (double) (2*tri)/(g.get_deg(i)*(g.get_deg(i)-1));//cluL devient la somme des clustering locaux	
					System.out.println("      cluL("+i+") = "+cluLx);
					cluL += cluLx;*/
					
					cluL += (double) (2*tri)/(g.get_deg(i)*(g.get_deg(i)-1));//cluL devient la somme des clustering locaux
				}else {
					cluL += (double) (2*tri);
				}
			}
			cluL = cluL/g.get_nbS();//Calul du clustering local moyen
			System.out.format("%.5f\n", cluL);
			//Clustering local moyen:
			//TODO: Ces fonctions sont lentes
			int triG = voisi.nb_triangles_G(g);
			//System.out.println("      Le Graph contient: "+triG+" triangles au total");
			int nv = voisi.get_nv(g);
			//System.out.println("      Le Graph contient: "+nv+" V");
			double cluG = (double) (3 * triG)/(nv);
			System.out.format("%.5f\n", cluG);
		}else {
			throw new Exception("L'action <<"+action+">> est inconnue du programme!");
		}
	}
}
