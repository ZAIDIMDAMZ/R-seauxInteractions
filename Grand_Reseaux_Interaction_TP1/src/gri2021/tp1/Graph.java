package gri2021.tp1;

public class Graph {
	int nbS, nbA;//nb sommets et arrêtes
	int[] sommets;
	int[] ls_adja[];
	
	public Graph(int nbS) {
		this.nbS = nbS;
		sommets = new int[nbS];
		ls_adja = new int[nbS][];
		for(int i = 0;i<nbS;i++) {
			ls_adja[i] = new int[nbS];
		}
	}
	
	public void neighbors() {
		//TODO
	}
	
}
