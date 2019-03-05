package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Synchroniser {

	public static final String FILE_SEPARATOR=System.getProperty("file.separator");
	
	public static void synchronize(FileSystem fs1, FileSystem fs2) {
		FileSystem refCopy1 = fs1.getReference();
		FileSystem refCopy2 = fs2.getReference();
		List<String> dirtyPath1 = computeDirty(refCopy1, fs1, "");
		System.out.println(dirtyPath1);
		//List<String> dirtyPath2 = computeDirty(refCopy2, fs2, "");
	}

	private static List<String> computeDirty(FileSystem lastSync, FileSystem fs, String currentRelativePath){
		ArrayList<String> res=new ArrayList<String>();
		System.out.println("d�but : "+fs.getRoot()+currentRelativePath);
		List<String> children=fs.getChildren(fs.getRoot()+currentRelativePath);
		File curChildren,lsChildren;
		//liste de tout les enfant du chemin relatif actuel
		for(int i=0;i<children.size();i++){
			curChildren=new File(children.get(i));
			//v�rifie si le fichier existe (il devrait mais juste au cas ou)
			if(curChildren.exists()) {
				System.out.println("enfant : "+curChildren.getPath());
				//si l'enfant est un dossier, on appele r�cursivement la fonction
				if(curChildren.isDirectory()) {
					res.addAll(computeDirty(lastSync,fs,FILE_SEPARATOR+curChildren.getName()));
				//sinon, on regarde si le fichier est diff�rent de l'ancien.
				}else {
					lsChildren=new File(lastSync.getRoot()+FILE_SEPARATOR+curChildren.getName());
					//est-ce que ce fichier existait ?
					if(!lsChildren.exists()) {
						//non -> ajout au dirty
						res.add(curChildren.getPath());
					}else {
						//oui -> test si il a �t� modifi�
						if(curChildren.lastModified()!=lsChildren.lastModified()) {
							//ajout a dirty
							res.add(curChildren.toString());
						}
					}
				}
				//TODO si un fichier a �t� supprim�
				
			}
		}
		return res;
	}

	private void mirror(FileSystem fs1, List<String> dirtyPath1, FileSystem fs2, List<String> DirtyPath2, String currentRelativePath) {

	}
}
