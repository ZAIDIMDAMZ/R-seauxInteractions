package gri2021.tp3;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

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
		if(arg.compareTo("triangles") == 0 || arg.compareTo("clust") == 0 || arg.compareTo("k-coeur") == 0) {
			return true;
		}
		return false;
	}
	
	private static PriorityQueue<Paire> initier_paires(int n, PriorityQueue<Paire> file, Marquage m) {
		for(int i = 0;i<n;i++) {
			file.add(new Paire(i, m));
		}
		
		return file;
	}
	
	//V2: Fonctionne mais est trop lente
	/*private static PriorityQueue<Paire> maj_queue(PriorityQueue<Paire> file, Marquage m) {
		Paire x;
		for(int i = 0;i<file.size();i++) {
			//Pas d'autre moyen de mettre à jours la queu que de retirer et remettre les éléments modifiés...
			x = file.poll();
			file.add(x);
		}
		
		return file;
	}*/
	
	//triangles test.txt 7 1 ; triangles as-caida20071105-simple.txt 53381 123 ; triangles as20000102-simple.txt 12572 123
	// clust test.txt 7 ; clust as-caida20071105-simple.txt 53381 ; clust as20000102-simple.txt 12572
	// k-coeur test.txt 7 ; k-coeur as20000102-simple.txt 12572 ; k-coeur ca-AstroPh-simple.txt 198050
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
		}else if(action.compareTo("k-coeur")==0) {
			//mem();
			Marquage monitor = new Marquage(g);
			//Ce comparator indique l'ordre de comparaison à suivre
			Comparator<Paire> compa = new Comparator<Paire>(){

				@Override
				public int compare(Paire o1, Paire o2) {
					//Doit retourner un négatif, 0, ou un positif en fonction de si le 1er argument est plus élevé ou plus petit que le second
					return (o1.get_deg() - o2.get_deg());
				}
				
			};
			PriorityQueue<Paire> file = new PriorityQueue<Paire>(g.nbS, compa);//Créer une Priority Queu de capacité initiale égale au nombre de sommet
			file = initier_paires(g.nbS, file, monitor);
			int k = 0;
			int nb_sm= g.get_nbS();//Indique le nombre de sommets marqués pour la valeur actuelle de k
			Paire x = file.poll();
			while(k < g.nbS) {
				//x = file.poll();
				if(x == null) {break;}
				//System.out.println("x = ("+x.get_s()+", "+x.get_deg()+")  -> k = "+k);
				if(x.get_deg() <= k) {
					//V1:/*
					//monitor.desactiver_S(x.get_s());//On désactive le sommet 
					//file = maj_queue(file, monitor);
					//V1*/
					//V2: La file a tendance à avoir des dédoublement (sans conséquence sur le résultat) mais est plus optimal en temps
					file = monitor.desactiver_S(x.get_s(),  file);//On met à jours
					x = file.poll();
				}else {
					//S'il ne reste plus de sommet marqué, on retourne
					if(!monitor.reste_marque()) {
						break;
					}
					k++;
					nb_sm = monitor.get_nb_sm();
				}
			}
			//mem();
			System.out.println(""+k);
			System.out.println(""+nb_sm);
		}else {
			throw new Exception("L'action <<"+action+">> est inconnue du programme!");
		}
	}
}
