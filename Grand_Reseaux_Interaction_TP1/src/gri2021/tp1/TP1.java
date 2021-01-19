package gri2021.tp1;

public class TP1 {
	
	public static void mem() {
		Runtime rt = Runtime.getRuntime();
		rt.gc();
		System.err.println("Allocated memory :"
				+ (rt.totalMemory() - rt.freeMemory()) / 1000000
				+ " Mb");
		System.err.flush();
	}

	//Exemples de run: "facebook_combined.txt 88234 0 17"
	//Ex cf ennonce: "web-BerkStan.txt 8000000 42 2021" [doit renvoyer: n=685231, m=7600595, degmax=84290, dist=2] et [67 Mb puis 143 Mb]
	//Autre ex cf enonce "ca-AstroPh.txt 400000 127393 57507" [doit renvoyer: n=133280 m=396160 degmax=1008 dist=4] et [4Mb puis 10Mb]
	////Autre ex cf enonce "ca-AstroPh.txt 400000 127393 1" [doit renvoyer: n=133280 m=396160 degmax=1008 dist=2147483647] et [4Mb puis 10Mb]
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
		mem();
		System.out.println("Il y a "+Reader.nbr_sommets()+" sommets sur ce graph");
		//
		System.out.println("Création du Graph:");
		Graph g = new Graph(Reader);
		mem();
		System.out.println("Le degré maximal d'un sommet est de: "+g.get_degMax());
		
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
	}

}
