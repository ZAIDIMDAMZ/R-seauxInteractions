package gri2021.tp5;

public class TP5 {

	
	//Indique si l'argument donné est connu ou non de l'exécution
	private static boolean action_connue(String arg) {
		if(arg.compareTo("delta") == 0 || arg.compareTo("delta12321")==0) {
			return true;
		}
		return false;
	}
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
		if(!action_connue(action)) {
			throw new Exception("L'action <<"+action+">> est inconnue du programme!");
		}
		
		if(action.compareTo("delta") == 0) {
			Partition p = new Partition(g);
			System.out.println(""+p.get_modularite());
			int u = Integer.parseInt(args[3]);
			int v = Integer.parseInt(args[4]);
			p.deplace_to(u, v);
			System.out.println(""+p.get_modularite());
		}else if(action.compareTo("delta12321") == 0) {
			//TODO
		}else {
			throw new Exception("Le programme a rencontré une action inconnue...");
		}
		
	}

}
