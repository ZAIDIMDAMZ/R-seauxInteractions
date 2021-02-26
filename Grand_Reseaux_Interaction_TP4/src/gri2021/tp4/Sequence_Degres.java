package gri2021.tp4;

//Gere la génération de sequence de degrés
public class Sequence_Degres {
	private int[] d;
	private int sum_d;
	
	public Sequence_Degres(int n) {
		d = new int[n];
		sum_d = 0;
		for(int i = 0; i<n;i++) {
			d[i] = (int) Math.abs(Math.sqrt(i+1));
			sum_d += d[i];
		}
		//Si la somme des valeurs n'est pas paire:
		if(sum_d % 2 != 0) {
			d[n-1]++;//On ajoute 1
		}
	}
	
	
	
	public int get_sum_d() {
		return sum_d;
	}
	
	public int[] get_d() {
		return d;
	}
}
