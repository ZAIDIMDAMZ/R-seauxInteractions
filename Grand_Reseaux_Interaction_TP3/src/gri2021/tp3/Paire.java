package gri2021.tp3;

//Modelisation des paire (sommet,deg) pour le k-coeur
public class Paire {
	private int s/*, deg*/;
	private Marquage monitor;//devra être lié à un monitor pour toujours avoir la valeur mise à jours de deg
	Paire(int s, Marquage monitor){
		this.s = s;
		this.monitor = monitor;
		//deg = monitor.get_deg_marque(s);
	}
	
	public int get_s() {
		return s;
	}
	
	public int get_deg() {
		//Je pensais que la "PriorityQueue" se mettrait à jours à la moindre modification de deg, mais dans les faits non, il va falloir mettre à jours les degré à la main 
		return monitor.get_deg_marque(s);
		//return deg;
	}
	/*
	public void maj_deg(int deg) {
		this.deg = deg;
	}
	*/
}
