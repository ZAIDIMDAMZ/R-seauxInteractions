package gri2021.tp2;

import java.util.ArrayDeque;

//Servira à effectuer le parcours en Largeur permettant de déterminer la distance entre 2 sommets dans la graph
public class Parcour_Largeur {
	
	ArrayDeque<Integer> file;//A utt pour la files d'attente (https://docs.oracle.com/javase/8/docs/api/)
	boolean deja_vu[];//indique si un sommet donné a déjà été visité lors du parcours
	int dist[];//indique la distance à laquelle se trouve un sommet donné par rapport à l'origine du parcours en largeur
	boolean done;//Indique si le Parcours en Largeur a été effecuté et si il y a donc des résultat exploitables à en tirer
	
	public Parcour_Largeur(int nbS) {
		//
		file = new ArrayDeque<Integer>(nbS);
		deja_vu = new boolean[nbS];
		for(int i=0;i<nbS;i++) {
			deja_vu[i] = false;//aucun sommet n'a encore été visité
		}
		dist = new int[nbS];
		done = false;//Le parcours n'a pas encore été effecuté il manque l'instruction avec le points de départ et l'objectif visé
	}
	
	private void Parcour(Graph g, int x) {
		file.remove(x);
		int[] ls_v = g.get_neighbors(x);
		if(ls_v != null) {
			/*System.out.println("ls_v de "+x+" = ");
			for(int i=0;i<ls_v.length;i++) {
				System.out.print(""+ls_v[i]+" ");
			}
			System.out.println();*/
			for(int i=0;i<ls_v.length;i++) {//Pour tout les voisins de x
				//System.out.println("i = "+i+", ls_v de taille = "+ls_v.length + "ls_v[i] = " + ls_v[i]);
				if(!deja_vu[ls_v[i]]) {//Si le voisin n'a pas déjà été visité
					file.push(ls_v[i]);//On l'ajoute à la file
					deja_vu[ls_v[i]] = true;//On le marque
					dist[ls_v[i]] = dist[x] + 1; //On indique qu'il est éloigne de x par une distance de 1
				}
			}
		}
	}
	
	//Retourne la distance entre le sommet "ori" et "obj", arrête le BFS une fois l'objectif atteint
	public int  Breadth_First_Search(Graph g, int ori, int obj) {
		dist[ori] = 0;//L'origine est située à une distance 0 d'elle même
		file.push(ori);
		deja_vu[ori] = true;
		int x;//L'élément à partir duquel l'on va effectuer le parcours
		while(!file.isEmpty()) {
			x = file.poll();//On tire un élément de la liste (qui sera retiré ensuite)
			//Si on a atteint le sommet que l'on cherchais on retourne la distance
			if(x == obj) {
				done = true;
				return dist[obj];//Comme obj a été atteint lors du parcours on lui a associé une distance
			}
			Parcour(g, x);
		}
		//On ne peut pas trouver l'objectif en partant de l'origine donnée...
		done = true;
		return -1;//si les 2 sommets ne sont pas liés alors on retourne -1
	}
	
	//Overright: retourne le sommet le plus éloigné du sommet d'origine
	public int  Breadth_First_Search(Graph g, int ori) {
		//System.out.println("=>Parcours en largeur partant de "+ori);
		dist[ori] = 0;//L'origine est située à une distance 0 d'elle même
		file.push(ori);
		deja_vu[ori] = true;
		int x;//L'élément à partir duquel l'on va effectuer le parcours
		int Dmax = -1;//La Distance maximal courrant de ori à un autre point
		int Smax=ori;//le Sommet le plus éloigné de ori, si le point est isolé il se renvera lui-même 
		while(!file.isEmpty()) {
			x = file.poll();//On tire un élément de la liste (qui sera retiré ensuite)
			//System.out.print("Parcours du sommet "+x);
			//Si on a atteint le sommet que l'on cherchais on retourne la distance
			Parcour(g, x);
			//System.out.println(" de distance "+dist[x]+" de "+ori);
			if(dist[x] > Dmax) {
				Dmax = dist[x];
				Smax = x;
			}
		}
		//Le parcours en largeur est terminé
		done = true;
		return Smax;//Le dernier sommet visité devrai être le pts le plus éloigné: si retourn -1 il y a eu un problème...
	}
	
	public int get_dist_max() throws Exception {
		if(done) {
			int max = 0;
			for(int i=0;i<dist.length;i++) {
				if(dist[i] > max) {
					max = dist[i];
				}
			}
			return max;
		}
		throw new Exception("Le parcours en Largeur n'a pas pu être effectué");
	}
	
	//Retourne un point "m" situé au milieu du chemin de v vers w
	public int get_pts_m(Graph g, int w) throws Exception{
		if(done) {
			//On part de w, on regarde ses voisins et on dessent vers un point de moins en moins éloigné de v jusqu'à atteindre un dist moitié moins élevé que celle de w
			int d_actu = dist[w];//La distance actuelle par rapport à v
			int s_actu = w;//le sommet que l'on regarde actuellement
			int d_obj = dist[w]/2;//La distance que l'on veut atteindre, la moitié de la d max
			while(d_actu != d_obj) {
				int[] ls_v = g.get_neighbors(d_actu);//On regarde les voisins du sommet actuellement parcour
				for(int i = 0;i<ls_v.length;i++) {
					//Si le voisin est moins éloigné de v que le sommet actuel on descent vers lui
					if(dist[ls_v[i]] < d_actu) {
						s_actu = ls_v[i];
						break;//inutile de vérifier les autres voisins pour gagner du temps
					}
				}
				d_actu = dist[s_actu];//mise à jours de la distance courrante par rapport à v
			}
			return s_actu;
		}
		throw new Exception("Le parcours en Largeur n'a pas pu être effectué");
	}
	
	public int[] get_dist() throws Exception {
		if(done) {
			return dist;
		}
		throw new Exception("Le parcours en Largeur n'a pas pu être effectué");
	}
	
	public boolean[] get_deja_vu() throws Exception {
		if(done) {
			return deja_vu;
		}
		throw new Exception("Le parcours en Largeur n'a pas pu être effectué");
	}
}
