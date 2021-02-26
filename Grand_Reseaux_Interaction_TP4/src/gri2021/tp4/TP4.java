package gri2021.tp4;

public class TP4 {

	
	//Indique si l'argument donné est connu ou non de l'exécution
	private static boolean action_connue(String arg) {
		if(arg.compareTo("exemple") == 0 || arg.compareTo("racine")==0) {
			return true;
		}
		return false;
	}
	public static void main(String[] args) throws Exception {
		String action = args[0];
		if(!action_connue(action)) {
			throw new Exception("L'action <<"+action+">> est inconnue du programme!");
		}
		
		if(action.compareTo("exemple") == 0) {
			//L'exemple fixe:
			int[] ex = new int[4];
			ex[0] = 1;
			ex[1] = 2;
			ex[2] = 1;
			ex[3] = 4;
			Generateur G = new Generateur(4,ex);
			G.affiche_graph();
		}else if(action.compareTo("racine")==0) {
			int n = Integer.parseInt(args[1]);
			Sequence_Degres sd = new Sequence_Degres(n);
			Generateur G = new Generateur(sd);
			G.affiche_graph();
		}else {
			throw new Exception("Le programme a rencontré une action inconnue...");
		}
		
	}

}
