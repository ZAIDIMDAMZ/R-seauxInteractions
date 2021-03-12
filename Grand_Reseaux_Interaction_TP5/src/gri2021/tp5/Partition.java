package gri2021.tp5;

public class Partition {
	Communaute commu[];//Le num de commu associé à chaque sommet
	long modul;//modularite
	long m;
	Graph G;
	
	public long to_long(int x) {
		return (1L * x * x);
	}
	
	public long maj_Q(int u, int v) {
		return ((to_long(4)*m)*(commu[u].get_dC(u) - commu[v].get_dC(v))) - (to_long(2*G.get_deg(u))* (commu[u].getSC() - commu[v].getSC() + to_long(G.get_deg(u))));
	}
	
	//Deplacer u vers la communaute de v
	public void deplace_to(int u, int v) {
		commu[u].supp_s(u);
		commu[v].add_s(u);
		modul += maj_Q(u,v);
	}
	
	Partition(Graph G){
		this.G = G;
		commu = new Communaute[G.get_nbS()];
		for(int i = 0; i < G.get_nbS(); i++) {
			commu[i] = new Communaute(i, G);//Chaque sommet commence dans sa propre communauté
		}
		m = to_long(G.get_nbA());
		//TODO calcul modularite initial
		modul = to_long(0);
		for(int i = 0; i < commu.length;i++) {
			//modul += ((to_long(4)*m)*(to_long(G.get_deg(i)))) - (to_long(2*G.get_deg(i))*(commu[i].getSC()+to_long(G.get_deg(i))));
			modul += (to_long(2)*m) / (to_long(1) - commu[i].getSC());
		}
	}
	
	
	public long get_modularite() {
		return modul;
	}
}
