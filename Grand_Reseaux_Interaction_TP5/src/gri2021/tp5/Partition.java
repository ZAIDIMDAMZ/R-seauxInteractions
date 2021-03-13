package gri2021.tp5;

import java.util.Iterator;

public class Partition {
	int commu[];//Le num de commu associé à chaque sommet
	int SC[];
	long modul;//modularite
	long m2;
	Graph G;
	
	//Je m'en servais avant mais je me rend compte que cela consome beaucoup plus que nécessaire alors qu'une boucle permetterait d'avoir les 2 valeur nécessaires
	/*
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
	}*/
	
	public long maj_Q(int u, int B, int C, int degU, int duC, int duB) {
		
		
		
		return 2L*m2*(duC - duB) - (2L*degU* (SC[C] - SC[B] + degU));
	}
	
	public void deplace(int u, int C) {
		int B = commu[u];
		
		//cf correction: je me suis rendu compte qu'elle était plus obtimal car elle fait tout en une boucle
		int degU = 0;
		int duC = 0;
		int duB = 0;
		Iterator<Integer> ls_v = G.neighbors(u).iterator();
		while(ls_v.hasNext()) {
			int v = ls_v.next();
			degU++;
			if(commu[v] == C) {duC++;}
			if(commu[v] == B) {duB++;}
		}
		modul += maj_Q(u,B,C,degU,duC,duB);

		commu[u] = C;
		//Cf correction: avant je mettais à jours les SC avec SC[u] et pas degU, cela générait de l'erreur à long terme...
		SC[C] += degU;
		SC[B] -= degU;
	}

	
	Partition(Graph G){
		this.G = G;
		commu = new int[G.get_nbS()];
		SC = new int[G.get_nbS()];
		for(int i = 0; i < G.get_nbS(); i++) {
			commu[i] = i;//Chaque sommet commence dans sa propre communauté
			SC[i] = G.get_deg(i);
		}
		m2 = 2L * G.get_nbA();
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
	
	public int get_commuOF(int x) {return commu[x];}
	
}
