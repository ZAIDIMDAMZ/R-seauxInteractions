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
		}
		//System.out.println("Il y a "+Reader.nbr_sommets()+" sommets sur ce graph");
		//System.out.println("Création du Graph:");
		/*
		Graph g = new Graph(Reader);
		//System.out.println("Le degré maximal d'un sommet est de: "+g.get_degMax());
		
		int x = Integer.parseInt(args[2]);//Un premier sommet
		int y = Integer.parseInt(args[3]);//Un second sommet
		if(x < 0 || y < 0) {
			throw new Exception("L'un des 2 sommets à donné en argument est erroné");
		}
		//Reader.print_data_d();//debug
		//Sorite attendue:
		System.out.println("n="+g.get_nbS());
		System.out.println("m="+g.get_nbA());
		System.out.println("degmax="+g.get_degMax());
		//TODO l'allocation de mémoire
		System.out.println("dist="+g.get_d_entre(x, y));//Note: je ne suis pas sûr de si correcte pour cas avec dist > 1 mais je manue d'exemple simple pour débuger
		mem();
		*/
	}

}
