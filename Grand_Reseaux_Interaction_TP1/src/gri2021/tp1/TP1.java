package gri2021.tp1;

public class TP1 {

	public static void main(String[] args) {
		System.out.println("arg0 = "+args[0]);
		Lecteur_Fichier Reader = new Lecteur_Fichier(args[0]);
		Reader.Read();
	}

}
