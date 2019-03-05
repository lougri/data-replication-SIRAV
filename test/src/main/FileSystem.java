package main;

import java.io.File;
import java.util.List;

public interface FileSystem {
    public String getRoot();
    public String getParent(String path);
    public List<String> getChildren(String path);
    public void replace(String absolutePathTargetFileSystem, FileSystem fsSource, String absolutePathSourceFileSystem);
    public void fileCopy(File input, File output);
    public FileSystem getReference();
    public void makedir(String path);
}
