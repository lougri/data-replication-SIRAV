package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocalFileSystem implements FileSystem {

	private FileSystem reference;
	private String rootPath;

	public LocalFileSystem(FileSystem reference,String root){
		this.reference=reference;
		this.rootPath=root;
	}

	public LocalFileSystem(String root){
		this.reference=null;
		this.rootPath=root;
	}

	@Override
	public String getParent(String path) {
		// TODO Auto-generated method stub
		String res=null;
		if(path!=rootPath) {
			File f=new File(path);
			res=f.getParentFile().toString();
		}
		return res;
	}

	@Override
	public List<String> getChildren(String path) {
		// TODO Auto-generated method stub
		ArrayList<String> res=new ArrayList<String>();
		File f=new File(path);
		File[] liste=f.listFiles();
		if(liste!=null) {
			for(int i=0;i<liste.length;i++) {
				res.add(liste[i].toString());
			}
		}
		return res;
	}

	@Override
	public void replace(String absolutePathTargetFileSystem, FileSystem fsSource, String absolutePathSourceFileSystem) {
		// TODO Auto-generated method stub
		this.fileDelete(absolutePathTargetFileSystem);
		this.fileCopy(absolutePathSourceFileSystem, absolutePathTargetFileSystem);
	}

	@Override
	public void fileCopy(String input, String output) {
		// TODO Auto-generated method stub
		try {
			Files.copy(new File(input).toPath(), new File(output).toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void fileDelete(String input) {
		File f=new File(input);
		try {
			if(f.isDirectory()) {
				deleteDir(f);
			}else {
				Files.delete(f.toPath());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void deleteDir(File file) {
	    File[] contents = file.listFiles();
	    if (contents != null) {
	        for (File f : contents) {
	            if (! Files.isSymbolicLink(f.toPath())) {
	                deleteDir(f);
	            }
	        }
	    }
	    file.delete();
	}
	@Override
	public void makedir(String path) {
		// TODO Auto-generated method stub
		File f=new File(path);
		if(!f.exists()) {
			boolean created=f.mkdirs();
			if(!created) {
				System.out.println("oups, une erreur est survenue lors de la creation d'un dossier");
			}
		}
	}

	@Override
	public FileSystem getReference() {
		// TODO Auto-generated method stub
		return reference;
	}


	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return rootPath;
	}

}
