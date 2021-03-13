package gri2021.tp5;

public class TP5 {

	
	//Indique si l'argument donné est connu ou non de l'exécution
	private static boolean action_connue(String arg) {
		if(arg.compareTo("delta") == 0 || arg.compareTo("delta12321")==0) {
			return true;
		}
		return false;
	}
	
	//delta clique3-ring4.txt 16 0 3
	//
	public static void main(String[] args) throws Exception {
		//Le fichier doit être lu avant de construire le graph
		String action = args[0];
		String fichier = args[1];
		int estim = Integer.parseInt(args[2]);//Une estimation du nbr d'arcs
		if(estim <= 0) {
			throw new Exception("Le paramètre 'nombre d'arrêtes' est erroné");
		}
		if(!action_connue(action)) {
			throw new Exception("L'action <<"+action+">> est inconnue du programme!");
		}
		Lecteur_Fichier Reader = new Lecteur_Fichier(fichier, estim);
		Reader.Read();
		//On génère un Graph pour mieux stocker les données qui viennent d'être lues
		Graph g = new Graph(Reader, true);
		Partition p = new Partition(g);
		if(!action_connue(action)) {
			throw new Exception("L'action <<"+action+">> est inconnue du programme!");
		}
		
		if(action.compareTo("delta") == 0) {
			System.out.println(""+p.get_modularite());
			int u = Integer.parseInt(args[3]);
			int v = Integer.parseInt(args[4]);
			p.deplace(u, p.get_commuOF(v));
			System.out.println(""+p.get_modularite());
		}else if(action.compareTo("delta12321") == 0) {
			System.out.println(""+p.get_modularite());
			int u = Integer.parseInt(args[3]);
			int v = Integer.parseInt(args[4]);
			int c = Integer.parseInt(args[5]);
			p.deplace(u, c);
			System.out.println(""+p.get_modularite());
			p.deplace(v, c);
			System.out.println(""+p.get_modularite());
			p.deplace(u, u);
			System.out.println(""+p.get_modularite());
			p.deplace(v, v);
			System.out.println(""+p.get_modularite());
		}else {
			throw new Exception("Le programme a rencontré une action inconnue...");
		}
		
	}

}
