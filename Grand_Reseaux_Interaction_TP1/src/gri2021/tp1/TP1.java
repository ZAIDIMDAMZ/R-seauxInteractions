package gri2021.tp1;
import java.lang.ClassNotFoundException;



public class TP1 {

	public static void main(String[] args) {
//		Ancien code
//		System.out.println("arg0 = "+args[0]);
//		Lecteur_Fichier Reader = new Lecteur_Fichier(args[0]);
//		Reader.Read();
//		la lecture ce fait dans la classe Graph qui fait appelle � Lecture_Fichier
		
		Graph gr = new Graph(args[0]);
		System.out.println("Nombre de sommets est "+ gr.getNbS());
		System.out.println("Nombre d'arr�ts est "+ gr.getNbA());
//		Ce qui me reste est la distance
//		la cr�ation des sommets grace a la classe sommet
//		attribution � chaque sommet une liste d'adjassence

	}

}
