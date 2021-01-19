package gri2021.tp1;

import java.util.Arrays;

public class Graph {
	Object [] datas;
	int [] noeudDepart;
	int [] noeudArrive;
	int [] nodesAd;
	private int nbS, nbA;//nb sommets et arrÃªtes
	
	private int[] sommets;
	
	public Graph(String path) {
		Lecteur_Fichier lf = new Lecteur_Fichier(path);
		
		//remplissage des listes 
		datas= lf.Read();
		noeudDepart=(int[]) datas[0];
		noeudArrive= (int[]) datas[1];

		
		//		Ancien code
//		nodes= this.removeDuplicates(noeudArrive);
		
		
//		System.out.println(this.getNbS());
//		this.nbS = nbS;
//		sommets = new int[nbS];
//		ls_adja = new int[nbS][];
//		for(int i = 0;i<nbS;i++) {
//			ls_adja[i] = new int[nbS];
//		}
	}
	
	public int getNbS() {
		//	nombre de noeuds est equal au dernier element +1
		return noeudDepart[noeudDepart.length-1]+1;
	}

	public int getNbA() {
		//	nombre de relations arretes ou arcs entre neudes est égale à la taille de notre dataset n1->n2
		return noeudDepart.length;
	}
	
	public void neighbors(int s) {
		for (int i = 0; i < noeudArrive.length; i++) {
			
		}
	}
	
	public int[] removeDuplicates(int[] arr) { 
	    int[] res = new int[arr.length]; 
	    int index = 0; 
	    for (int ele : arr) { 
			 if (Arrays.toString(arr).contains(""+ele) == false) {
				 res[index++] = ele; 
			 } 
	    } 
	    System.out.println(res.length);
	    return res; 
	}
	
	
}
