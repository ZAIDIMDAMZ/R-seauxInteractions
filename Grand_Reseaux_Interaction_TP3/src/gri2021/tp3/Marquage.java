package gri2021.tp3;

import java.util.Iterator;
import java.util.PriorityQueue;

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
	
	//V2: désactive le sommet ET met à jours la Queu en même temps
	public PriorityQueue<Paire> desactiver_S(int S, PriorityQueue<Paire> file) throws Exception {
		if(marque[S]) {
			marque[S] = false;
			Iterator<Integer> ls_v = G.neighbors(S).iterator();
			while(ls_v.hasNext()) {
				int v = ls_v.next();
				//On choisit de mettre à jours les voisins encore marqué uniquement
				if(marque[v]) {
					//On supprime le voisin de la file
					file.remove(new Paire(v, this));
					deg[v]--;//Le voisin de S voit son degré diminuer de 1
					file.add(new Paire(v, this));//On ré-insère le voisin
				}
			}
		}else {
			//Cette version ajoute des dédoublement de valeur et j'ignore pourquoi... Lancer un exception empêche la sortie du résultat correct.
			//throw new Exception("Le sommet "+ S+  " a déjà été désactivé");
		}
		return file;
	}
	
	public int get_deg_marque(int x) {
		return deg[x];
	}
	
	//Indique s'il reste au moins un sommet marqué
	public boolean reste_marque() {
		for(int i = 0; i <marque.length; i++) {
			if(marque[i]) {
				return true;
			}
		}
		return false;
	}
	
	public int get_nb_sm() {
		int x = 0;
		for(int i = 0;i < marque.length;i++) {
			if(marque[i]) {
				x++;
			}
		}
		return x;
	}
}
