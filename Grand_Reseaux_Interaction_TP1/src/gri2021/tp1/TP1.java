package gri2021.tp1;

public class TP1 {

	public static void main(String[] args) throws Exception {
		System.out.println("arg0 = "+args[0]);
		//Le fichier doit être lu avant de construire le graph
		int estim = Integer.parseInt(args[1]);//Une estimation du nbr d'arcs
		if(estim <= 0) {
			throw new Exception("Le paramètre 'estimation du nombre d'arcrs' est erroné");
		}
		Lecteur_Fichier Reader = new Lecteur_Fichier(args[0], Integer.parseInt(args[1]));
		System.out.println("Lecture du graph en cours:");
		Reader.Read();
		System.out.println("Lecture terminée:");
		System.out.println("Il y a "+Reader.nbr_sommets()+" sommets sur ce graph");
		//
		Graph g = new Graph(Reader);
		System.out.println("Le degré maximal d'un sommet est de: "+g.get_degMax());
		
		//Sorite attendue:
		System.out.println("n="+g.get_nbS());
		System.out.println("m="+g.get_nbA());
		System.out.println("degmax="+g.get_degMax());
	}

}
