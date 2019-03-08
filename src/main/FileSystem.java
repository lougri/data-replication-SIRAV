package main;

import java.io.File;
import java.util.List;

public interface FileSystem {
    public String getRoot();
    public String getParent(String path);
    public List<String> getChildren(String path);
    public void replace(String absolutePathTargetFileSystem, FileSystem fsSource, String absolutePathSourceFileSystem);
    // fileCopy et fileDelete utilisent des chemins absolus et peuvent donc être appelés par rapport à fs1 ou fs2
    public void fileCopy(String input, String output);
    public void fileDelete(String input);
    public FileSystem getReference();
    public void makedir(String path);
}
