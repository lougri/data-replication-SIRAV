#! /usr/bin/python

import os
import filecmp
import system

class Synchroniser:
    {
    public void synchronize(FileSystem fs1, FileSystem fs2):
        FileSystem refCopy1 = fs1.getReference()
        FileSystem refCopy2 = fs2.getReference()
        List<String> dirtyPath1 = computeDirty(refCopy1, fs1, "")
        List<String> dirtyPath2 = computeDirty(refCopy2, fs2, "")

    public List<String> computeDirty(FileSystem lastSync, FileSystem fs, String currentRelativePath):

    public void mirror(FileSystem fs1, List<String> dirtyPath1, FileSystem fs2, List<String> DirtyPath2, String currentRelativePath):
    }

public interface FileSystem:
    {
    public String getRoot()
    public String getParent(String path)
    public List<String> getChildren(String path)
    public void replace(String absolutePathTargetFileSystem, FileSystem fsSource, String absolutePathSourceFileSystem)
    public void fileCopy(File input, File output)
    public FileSystem getReference()
    }
class LocalFileSystem : FileSystem
    {

    }
