package gri2021.tp1;

public class TP1 {

	//Exemples de run: "facebook_combined.txt 88234 0 17"
	//Ex cf ennonce: "web-BerkStan.txt 8000000 42 2021" [doit renvoyer: n=685231, m=7600595, degmax=84290, dist=2]
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
		
		int x = Integer.parseInt(args[2]);//Un premier sommet
		int y = Integer.parseInt(args[3]);//Un second sommet
		if(x < 0 || y < 0) {
			throw new Exception("L'un des 2 sommets à donné en argument est erroné");
		}
		
		//Sorite attendue:
		System.out.println("n="+g.get_nbS());
		System.out.println("m="+g.get_nbA());
		System.out.println("degmax="+g.get_degMax());
		//TODO l'allocation de mémoire
		System.out.println("dist="+g.get_d_entre(x, y));//Note: je ne suis pas sûr de si correcte pour cas avec dist > 1 mais je manue d'exemple simple pour débuger
	}

}
