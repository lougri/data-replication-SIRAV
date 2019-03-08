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
		List<String> dirtyPath2 = computeDirty(refCopy2, fs2, "");
		System.out.println(dirtyPath2);
		//mirror(fs1,dirtyPath1,fs2,dirtyPath2,"");
	}

	private static List<String> computeDirty(FileSystem lastSync, FileSystem fs, String currentRelativePath){
		ArrayList<String> res=new ArrayList<String>();
		boolean isDirty=false;
		//System.out.println("début : "+fs.getRoot()+currentRelativePath);
		List<String> children=fs.getChildren(fs.getRoot()+currentRelativePath);
		List<String> tmp;
		File curChildren,lsChildren,fsChildren;
		//test si dossier actuel est dirty
		lsChildren=new File(lastSync.getRoot()+currentRelativePath);
		if(!lsChildren.exists()){
			isDirty=true;
		}
		//liste de tout les enfant du chemin relatif actuel
		for(int i=0;i<children.size();i++){
			curChildren=new File(children.get(i));
			//vérifie si le fichier existe (il devrait mais juste au cas ou)
			if(curChildren.exists()) {
				//System.out.println("enfant : "+curChildren.getPath());
				//si l'enfant est un dossier, on appele récursivement la fonction
				if(curChildren.isDirectory()) {
					tmp=computeDirty(lastSync,fs,currentRelativePath+FILE_SEPARATOR+curChildren.getName());
					if(tmp.size()>0){
						res.addAll(tmp);
						isDirty=true;
					}
					//sinon, on regarde si le fichier est différent de l'ancien.
				}else {
					lsChildren=new File(lastSync.getRoot()+currentRelativePath+FILE_SEPARATOR+curChildren.getName());
					//est-ce que ce fichier existait ?
					if(!lsChildren.exists()) {
						//non -> ajout au dirty
						res.add(currentRelativePath+FILE_SEPARATOR+curChildren.getName());
						//System.out.println("check ajout : "+lastSync.getRoot()+FILE_SEPARATOR+curChildren.getName());
						fsChildren=new File(fs.getRoot()+currentRelativePath+FILE_SEPARATOR+curChildren.getName());
						isDirty=true;
					}else {
						//oui -> test si il a été modifié
						if(curChildren.lastModified()!=lsChildren.lastModified()) {
							//ajout a dirty
							//System.out.println("check modif : "+lastSync.getRoot()+FILE_SEPARATOR+curChildren.getName());
							res.add(currentRelativePath+FILE_SEPARATOR+curChildren.getName());
							isDirty=true;
						}
					}
				}
			}

		}
		//test suppresion fichiers
		children=lastSync.getChildren(lastSync.getRoot()+currentRelativePath);			
		for(int i=0;i<children.size();i++){
			curChildren=new File(children.get(i));
			fsChildren=new File(fs.getRoot()+currentRelativePath+FILE_SEPARATOR+curChildren.getName());
			if(!fsChildren.exists()) {
				//System.out.println("check suppr : "+lastSync.getRoot()+FILE_SEPARATOR+curChildren.getName());
				res.add(currentRelativePath+FILE_SEPARATOR+fsChildren.getName());
				isDirty=true;
			}
		}
		//ajout du dossier si un fichier est dirty
		if(isDirty) {
			if(!res.contains(currentRelativePath)&&currentRelativePath!="") {
				res.add(currentRelativePath);
			}
		}
		return res;
	}
	
	/* def reconciliation
	private static void mirror(FileSystem fs1, List<String> dirtyPath1, FileSystem fs2, List<String> dirtyPath2, String currentRelativePath) {
		//cas 1
		if(!(currentRelativePath!=""&&dirtyPath1.contains(currentRelativePath)&&dirtyPath2.contains(currentRelativePath))) {
			File file1=new File(fs1.getRoot()+FILE_SEPARATOR+currentRelativePath);
			File file2=new File(fs2.getRoot()+FILE_SEPARATOR+currentRelativePath);			
			//cas 2
			if(file1.isDirectory()&&file2.isDirectory()) {
				
			}
			//cas 3
			if(!dirtyPath1.contains(file1.getPath())) {
				System.out.println("copier "+file2+" dans "+file1);
			}
			//cas 4
			if(!dirtyPath2.contains(file2.getPath())) {
				System.out.println("copier "+file1+" dans "+file2);
			}
			//cas 5 ne rien faire
		}
	}*/
		
		


	private static void mirror(FileSystem fs1, List<String> dirtyPath1, FileSystem fs2, List<String> dirtyPath2, String currentRelativePath) {
		//cherche tout les dossiers enfants du chemin relatif
		ArrayList<String> res=new ArrayList<String>();
		System.out.println("début : "+fs1.getRoot()+currentRelativePath);
		List<String> children1=fs1.getChildren(fs1.getRoot()+currentRelativePath);
		List<String> children2=fs2.getChildren(fs2.getRoot()+currentRelativePath);
		String root1=fs1.getRoot();
		String root2=fs2.getRoot();
		List<String> childrenBoth=children1;
		childrenBoth.addAll(children2);
		
		List<File> file1=toFile(children1);
		List<File> file2=toFile(children2);
		List<File> fileBoth=toFile(childrenBoth);
		
		int i=0,j=0,k=0;
		File curChildren1,curChildren2,curChildrenBoth;
		
		//int profondeur=currentRelativePath.split(FILE_SEPARATOR).length;
		
		//mirror(fs1,dirtyPath1,fs2,dirtyPath2,currentRelativePath+FILE_SEPARATOR+curChildrenBoth);
		while(k<fileBoth.size()) {
			curChildrenBoth=fileBoth.get(i);
			System.out.println(curChildrenBoth.getName());
			//si dans fs1
			if(file1.contains(new File(root1+currentRelativePath+FILE_SEPARATOR+curChildrenBoth.getName()))) {
				System.out.println("\t dans fs1");
				//si dans fs1 et fs2
				if(file2.contains(new File(root2+currentRelativePath+FILE_SEPARATOR+curChildrenBoth.getName()))) {
					System.out.println("\t dans fs2");
					//si dossier
					if(curChildrenBoth.isDirectory()) {
						//si dans D1 et/ou D2
						if(dirtyPath1.contains(currentRelativePath+FILE_SEPARATOR+curChildrenBoth.getName())||dirtyPath2.contains(currentRelativePath+FILE_SEPARATOR+curChildrenBoth.getName())) {	
							//mirror
							System.out.println("dossier dans un dirty : "+currentRelativePath+FILE_SEPARATOR+curChildrenBoth.getName());
							mirror(fs1,dirtyPath1,fs2,dirtyPath2,currentRelativePath+FILE_SEPARATOR+curChildrenBoth.getName());
						}
					//si fichier
					}else {
						//si dans D1 
						if(dirtyPath1.contains(currentRelativePath+FILE_SEPARATOR+curChildrenBoth.getName())){
							System.out.println("\t\t dans D1");
							//dans D1 et D2 -> conflict, copier plus récent vers plus vieux
							if(dirtyPath2.contains(currentRelativePath+FILE_SEPARATOR+curChildrenBoth.getName())) {
								System.out.println("\t\t dans D2");
								System.out.println(curChildrenBoth.getName()+" -> copier vieux vers recent");
							//sinon dans D1 uniquement -> copier D1 vers D2
							}else {
								System.out.println("copier "+root1+currentRelativePath+FILE_SEPARATOR+curChildrenBoth.getName()+" vers "+root2+currentRelativePath+FILE_SEPARATOR+curChildrenBoth.getName());
							}
						//sinon dans D2 et non D1 -> copier D2 vers D1
						}else {
							System.out.println("copier "+root2+currentRelativePath+FILE_SEPARATOR+curChildrenBoth.getName()+" vers "+root1+currentRelativePath+FILE_SEPARATOR+curChildrenBoth.getName());
						}
					}
				//si fs1 et non fs2
				}else {
					//si dans D1
						//dans D1 et D2 -> conflict ??? (que ce soit un fichier ou un dossier)
						//sinon dans D1 uniquement 
							// si fichier -> copier vers D2
							// si dossier -> créer dossier dans D2 puis mirror
					//sinon dans D2 et non D1 
						// si fichier -> suppr D1
						// si dossier -> supprimer D2
				}
			}
			//si dans fs2 et non dans fs1
				//si dans D1 
					//dans D1 et D2 -> conflict ??? (fichier ou dossier)
					//sinon dans D1 uniquement
						// si fichier -> suppr D2
						// si dossier -> supprimer D2
				//sinon dans D2 et non D1
					// si fichier -> copier vers D1
					// si dossier -> créer le dossier dans D1 puis mirror
			//si dans aucun des deux->seul cas : double suppression, ne rien faire (fichier ou dossier)
			
			i++;
			j++;
			k++;
		}
		
	}
	
	private static List<File> toFile(List<String> paths){
		ArrayList<File> res=new ArrayList<File>();
		ArrayList<String> names=new ArrayList<String>();
		File temp;
		for(int i=0;i<paths.size();i++) {
			temp=new File(paths.get(i));
			if(!names.contains(temp.getName())) {
				names.add(temp.getName());
				res.add(temp);
			}
		}
		return res;
	}
	
}
