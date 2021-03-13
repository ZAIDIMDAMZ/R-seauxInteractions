package gri2021.tp5;

import java.util.Iterator;

public class Partition {
	int commu[];//Le num de commu associé à chaque sommet
	int SC[];
	long modul;//modularite
	long m;
	Graph G;
	
	//J'avais mal compirs l'énoncé, cette opération ne convertie pas en long
	/*public long to_long(int x) {
		return (1L * x * x);
	}*/
	
	public long get_dC(int x, int C) {
		int dC = 0;
		Iterator<Integer> ls_v = G.neighbors(x).iterator();
		while(ls_v.hasNext()) {
			int v = ls_v.next();
			if(commu[v] == C) {
				dC++;
			}
		}
		return dC;
	}
	
	public long maj_Q(int u, int v, int B) {
		//return ((to_long(4)*m)*(commu[u].get_dC(u) - commu[v].get_dC(v))) - (to_long(2*G.get_deg(u))* (commu[u].getSC() - commu[v].getSC() + to_long(G.get_deg(u))));
		return (4*m)*(get_dC(u, commu[v]) - get_dC(v, B)) - (2*G.get_deg(u)* (SC[v] - SC[u] + G.get_deg(u)));
	}
	
	//Deplacer u vers la communaute de v
	public void deplace_to(int u, int v) {
		/*commu[u].supp_s(u);
		commu[v].add_s(u);*/
		int B = commu[u];
		commu[u] = commu[v];
		modul += maj_Q(u,v,B);
		SC[v] += SC[u];
		SC[u] = SC[v];
	}
	
	Partition(Graph G){
		this.G = G;
		commu = new int[G.get_nbS()];
		SC = new int[G.get_nbS()];
		for(int i = 0; i < G.get_nbS(); i++) {
			commu[i] = i;//Chaque sommet commence dans sa propre communauté
			SC[i] = G.get_deg(i);
		}
		m = G.get_nbA();
		System.out.println("m = "+m);
		//TODO calcul modularite initial
		modul = 0;
		for(int i = 0; i < commu.length;i++) {
			//modul += ((2*m)/G.get_deg(i)) - (SC[i]^2);//Tentative échouée
			//Cf squelette correction, j'ai essayé en vais d'appiquer la formule que m'a montré l'enseignant
			modul -= SC[i] * SC[i];
		}
	}
	
	
	public long get_modularite() {
		return modul;
	}
}
