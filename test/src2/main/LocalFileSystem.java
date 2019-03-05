package main;

import java.io.File;
import java.util.List;

public class LocalFileSystem implements FileSystem {

	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getParent(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getChildren(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void replace(String absolutePathTargetFileSystem, FileSystem fsSource, String absolutePathSourceFileSystem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fileCopy(File input, File output) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FileSystem getReference() {
		// TODO Auto-generated method stub
		return null;
	}

}
