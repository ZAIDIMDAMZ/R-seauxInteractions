package gri2021.tp3;

//Modelisation des paire (sommet,deg) pour le k-coeur
public class Paire {
	private int s;
	private Marquage monitor;//devra être lié à un monitor pour toujours avoir la valeur mise à jours de deg
	Paire(int s, Marquage monitor){
		this.s = s;
		this.monitor = monitor;
	}
	
	public int get_s() {
		return s;
	}
	
	public int get_deg() {
		return monitor.get_deg_marque(s);
	}
	/*
	public void maj_deg(int deg) {
		this.deg = deg;
	}
	*/
}
