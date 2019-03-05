package main;

import java.io.File;
import java.util.List;

public class Main {

	public static void main(String[]args) {
		LocalFileSystem ref= new LocalFileSystem("E:\\Travail\\data-replication-SIRAV\\test\\src");//dossier origine local
		LocalFileSystem copy1= new LocalFileSystem(ref,"E:\\Travail\\data-replication-SIRAV\\test\\src2");//dossier modifié 1 local
		LocalFileSystem copy2= new LocalFileSystem(ref,"E:\\Travail\\data-replication-SIRAV\\test\\src3");//dossier modifié 2 local
		Synchroniser.synchronize(copy1, copy2);

	}
	
}