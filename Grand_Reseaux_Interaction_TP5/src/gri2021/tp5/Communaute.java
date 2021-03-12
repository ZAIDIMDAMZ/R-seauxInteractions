package gri2021.tp5;

import java.util.Iterator;

public class Communaute {
	int nbs;//nbr de sommets actif ds la communaute
	int ls[];//La liste des sommets de la Commu
	long SC;
	Graph g;
	
	public Communaute(int s, Graph g) {
		this.g = g;
		this.ls = new int[g.get_nbS()];
		for(int i=0;i<ls.length;i++) {
			ls[i] = 1;
		}
		nbs = 1;
		ls[0] = s;
		SC = 1L * g.get_deg(s) * g.get_deg(s);//Somme des degre converti en long
	}
	
	public long getSC() {
		return SC;
	}

	private void maj_lsv(int d) {
		int i = d+1;
		while(ls[i] != -1) {
			ls[i-1] = ls[i];
			ls[i] = -1;
			i++;
		}
	}
	
	
	public boolean is_in(int x) {
		for(int i=0;i<nbs;i++) {
			if(ls[i] == x) {return true;}
		}
		return false;
	}
	
	public long get_dC(int x) {
		int dC = 0;
		Iterator<Integer> ls_v = g.neighbors(x).iterator();
		while(ls_v.hasNext()) {
			int v = ls_v.next();
			if(is_in(v)) {
				dC++;
			}
		}
		return dC;
	}
	
	public void maj_SC() {
		SC = 1L * 0 *0;
		for(int i=0;i<nbs;i++) {
			SC += 1L * g.get_deg(ls[i]) * g.get_deg(ls[i]);
		}
	}
	
	//qd on supprime un sommet de la liste de la communaute
	public void supp_s(int s) {
		nbs--;
		if(nbs > 0) {
			//on cherche où était la val supp de la communaute
			for(int i=0;i<nbs;i++) {
				if(ls[i] == s) {
					ls[i] = -1;
					maj_lsv(i);
					break;
				}
			}
			maj_SC();
		}
	}
	
	public void add_s(int s) {
		ls[nbs] = s;
		nbs++;
		maj_SC();
	}
	
}
