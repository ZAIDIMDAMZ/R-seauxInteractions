package gri2021.tp3;

import java.util.Iterator;

public class Marquage {
	private boolean [] marque;
	private int [] deg;
	Graph G;
	public Marquage(Graph G) {
		this.G = G;
		this.marque = new boolean[G.get_nbS()];
		this.deg = new int[G.get_nbS()];
		for(int i= 0; i < G.get_nbS(); i++) {
			marque[i] = true;
			deg[i] = G.get_deg(i);
		}
	}
	
	public void desactiver_S(int S) throws Exception {
		if(marque[S]) {
			marque[S] = false;
			Iterator<Integer> ls_v = G.neighbors(S).iterator();
			while(ls_v.hasNext()) {
				//Les voisin de S voient leur degré diminuer de 1
				deg[ls_v.next()]--;
			}
		}else {
			throw new Exception("Le sommet"+ S+  "a déjà été désactivé");
		}
	}
}
