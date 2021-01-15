package gri2021.tp1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//Va ouvrir les fichier
public class Lecteur_Fichier {
	String path;
	
	public Lecteur_Fichier(String path) {
		this.path = path;
	}
	
	public void Read() {
		try {
			BufferedReader read = new BufferedReader(new FileReader(path));
			try {
				String str;
				while((str = read.readLine()) != null) {
					System.out.println(""+ str);
				}
				read.close();
			} catch (IOException e) {
				System.err.println(e);
			}
		} catch (FileNotFoundException e) {
			System.err.println(e);
		}
	}
}
