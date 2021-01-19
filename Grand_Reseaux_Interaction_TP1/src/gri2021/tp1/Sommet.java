package gri2021.tp1;

public class Sommet {
	int num;
	private int []u; //liste d'adjassence
	public Sommet(int num) {
		this.num= num;
	}
	public int[] getU() {
		return u;
	}
	public void setU(int[] u) {
		this.u = u;
	}
	
}
