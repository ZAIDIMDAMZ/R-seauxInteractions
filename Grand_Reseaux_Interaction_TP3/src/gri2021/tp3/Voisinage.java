package gri2021.tp3;

import java.util.Iterator;

//Cette fonction sert à vérifier quels sont les voisins d'un sommet
public class Voisinage {
	boolean[] is_v, deja_vu;
	//boolean done = false;//Indique si la fonction a déjà été effectuée
	int triangles = 0;//Le nombre de triangles
	
	Voisinage(int n){
		is_v = new boolean[n];
		deja_vu = new boolean[n];
		//deja_vu = new boolean[n];
		for(int i = 0;i<n;i++) {
			is_v[i] = false;
			deja_vu[i] = false;
		}
	}
	

	//réinitialise le tableau à faux
	private void clear() {
		for(int i=0; i < is_v.length;i++) {
			is_v[i] = false;
		}
		triangles = 0;
		//done = false;
	}
	
	private void clear_deja_vu() {
		for(int i = 0; i < deja_vu.length;i++) {
			deja_vu[i] = false;
		}
	}
	
	private boolean are_voisins(int x, int y, Graph G) {
		if(x == y) {
			return false;
		}
		//indique si les sommets x et y sont voisins l'un à l'autre
		Iterator<Integer> ls_v = G.neighbors(x).iterator();
		while(ls_v.hasNext()) {
			int v = ls_v.next();
			if(v == y) {
				return true;
			}
		}
		return false;
	}
	
	//TODO: semble correcte mais est assez lente à s'exécuter
	public int nb_triangles(int u, Graph G) {
		//if(done) {
			clear();//On réinitialise le tableau si nécessair
		//}
		//??? Es-ce qui était attendu?
		Iterator<Integer> ls_v = G.neighbors(u).iterator();
		while(ls_v.hasNext()) {
			int x = ls_v.next();//On pioche le nouveau voisin de l'itérateur
			//Si un sommet est "deja_vu" inutile de le considérer comme un voisin
			if(!deja_vu[x]) {
				is_v[x] = true;
			}
		}
		//???
		//On cherche un sommet voisin de u
		for(int i = 0; i < is_v.length;i++) {
			//Si un sommet est voisin de u
			if(is_v[i]) {
				//On regarde parmi les sommets restants
				for(int j = i+1;j < is_v.length;j++) {
					//Si est un autre voisin de u
					if(is_v[j]) {
						//System.out.println(""+i+" et "+j+" sont ils voisins?");
						if(!deja_vu[j]) {
							//On Vérifie si les 2 voisins de u sont voisins entre eux
							if(are_voisins(i, j, G)) {
								triangles++;
							}
						}
					}
				}
				//deja_vu[i] = true;
			}
		}
		//done = true;
		return triangles;
	}
	
	//Retourn le nombre total de triangles contenus dans le Graph
	public int nb_triangles_G(Graph G) {
		int x = 0;
		for(int i = 0;i < is_v.length;i++) {
			x += nb_triangles(i, G);//Combiens de triangles touchent le sommet "i"
			deja_vu[i] = true;//On ne veux plus jamais que "i" soi repris en compte pour les autres triangles
		}
		clear_deja_vu();
		//On a le nombre de triangles du graph
		return x;
	}
	
	//Retourne le nombre de V partant du sommet s
	private int get_V_started(Graph G, int s) {
		clear();
		int V = 0;
		Iterator<Integer> ls_x = G.neighbors(s).iterator();//Liste des voisins directe de s
		Iterator<Integer> ls_y;//Liste des valeurs possibles de y
		while(ls_x.hasNext()) {
			int x = ls_x.next();//On pioche le nouveau voisin de l'itérateur
			ls_y = G.neighbors(x).iterator();
			//On regarde où peut aller le voisin directe de X
			while(ls_y.hasNext()) {
				int y = ls_y.next();
				//S'il ne retourne pas vers X, c'est un V possible
				if(y != s) {
					V++;
				}
			}
		}
		return V;
	}
	
	//Retourne le nombre de "2 arêtes incidentes" (= des triades connexes) dans le graph
	public int get_nv(Graph G) {
		int nv = 0;
		for(int i=0;i<is_v.length;i++) {
			nv += get_V_started(G,i);
		}
		return nv;
	}
}
