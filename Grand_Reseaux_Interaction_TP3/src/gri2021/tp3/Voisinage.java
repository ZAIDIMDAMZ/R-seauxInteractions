package gri2021.tp3;

import java.util.Iterator;

//Cette fonction sert à vérifier quels sont les voisins d'un sommet
public class Voisinage {
	boolean[] is_v/*, deja_vu*/;
	boolean done = false;//Indique si la fonction a déjà été effectuée
	int triangles = 0;//Le nombre de triangles
	
	Voisinage(int n){
		is_v = new boolean[n];
		//deja_vu = new boolean[n];
		for(int i = 0;i<n;i++) {
			is_v[i] = false;
			//deja_vu[i] = false;
		}
	}
	

	//???
	public void clear() {
		for(int i=0; i < is_v.length;i++) {
			is_v[i] = false;
		}
		triangles = 0;
		done = false;
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
	
	public int nb_triangles(int u, Graph G) {
		if(!done) {
			//??? Je ne comprend
			Iterator<Integer> ls_v = G.neighbors(u).iterator();
			while(ls_v.hasNext()) {
				is_v[ls_v.next()] = true;
			}
			//???
			
			//On indique quels sont les sommets voisins du sommet u
			for(int i = 0; i < is_v.length;i++) {
				//Si un sommet est voisin de u et n'a pas encore été vu
				if(is_v[i]) {
					for(int j = i+1;j < is_v.length;j++) {
						if(is_v[j]) {
							//System.out.println(""+i+" et "+j+" sont ils voisins?");
							if(are_voisins(i, j, G) /*&& !deja_vu[j]*/) {
								triangles++;
							}
						}
					}
					//deja_vu[i] = true;
				}
			}
			done = true;
		}
		return triangles;
	}
}
