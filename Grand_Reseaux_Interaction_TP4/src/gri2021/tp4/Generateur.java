package gri2021.tp4;

import java.util.Random;

//Socupe de la génération aléatoire du graph
public class Generateur {
	private int[] E;
	
	private void echanger(int x, int y) {
		int tampon = E[y];
		E[y] = E[x];
		E[x] = tampon;
	}
	
	private void permutations() {
		Random alea;
		for(int j = E.length-1;j>=1;j--) {
			alea = new Random();
			int i = alea.nextInt(j+1);//j est inclus
			echanger(i, j);
		}
	}
	
	private int size_d(int n, int[] d) {
		int s = 0;
		for(int i = 0;i<n;i++) {
			s += d[i];
		}
		return s;
	}
	
	//Remplit le E avec les valeurs initiales
	private void fill_E(int[] d) {
		int k = 0;//La position relative d'une élément sur le tableau
		for(int i = 0; i < d.length; i++) {
			int j = 0;
			while(j < d[i]) {
				E[k] = i;
				k++;
				j++;
			}
		}
	}
	
	//Donner un tableau "d" des degré correspond à un ex de base (suceptible de changer):
	public Generateur(int n, int[] d) {
		this.E = new int[size_d(n, d)];
		fill_E(d);
		permutations();//On permute les valeurs de E pour finir la génération
	}
	
	//Donner un tableau "d" des degré correspond à un ex de base (suceptible de changer):
	public Generateur(Sequence_Degres sd) {
		this.E = new int[sd.get_sum_d()];
		fill_E(sd.get_d());
		permutations();//On permute les valeurs de E pour finir la génération
	}
	
	//Affiche en sortie les valeur du graph tel que demandé par l'énoncé
	public void affiche_graph() {
		for(int i=0;i < E.length; i = i + 2) {
			System.out.println(""+E[i]+" "+E[i+1]);
		}
	}
	
}
