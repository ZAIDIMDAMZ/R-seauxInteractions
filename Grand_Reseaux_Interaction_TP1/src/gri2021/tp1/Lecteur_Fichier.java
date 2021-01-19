package gri2021.tp1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//Va ouvrir les fichier

public class Lecteur_Fichier {
	String path;
	Object[]res;
	int [] tabN1;
	int [] tabN2;
	
	public Lecteur_Fichier(String path) {
		this.path = path;
		res= new Object[2];
		tabN1= new int[0];
		tabN2= new int[0];
	}
	
	public Object[] Read() {
		try {
			BufferedReader read = new BufferedReader(new FileReader(path));
			try {
				String str;
				while((str = read.readLine()) != null) {
					//	Couper la Ligne 
					String [] splitLine= str.split(" ");
					
					//	Sauver les valeurs des tableaux courants
					int [] oldTab1= tabN1;
					int [] oldTab2= tabN2;
					
					int nbLines = tabN1.length;
					
					tabN1= new int [nbLines+1];
					tabN2= new int [nbLines+1];
					
					//	Copier le contenu de l'ancien Tableau
					System.arraycopy(oldTab1, 0, tabN1,0,nbLines);
					System.arraycopy(oldTab2, 0, tabN2,0,nbLines);
					
					tabN1[nbLines]=new Integer(splitLine[0]);
					tabN2[nbLines]=new Integer(splitLine[1]);
				}
				read.close();
			} catch (IOException e) {
				System.err.println(e);
			}
		} catch (FileNotFoundException e) {
			System.err.println(e);
		}
		
		res[0]=tabN1;
		res[1]=tabN2;
		return res;
	}
}
