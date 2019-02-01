#! /usr/bin/python

import os
import filecmp
import system

'''
Louis Grison : C'est la classe Synchroniser qui va effectuer le travail de synchronisation.
getReference sur A (A est un dossier) renvoie O (O est un dossier), car A est une copie de O. Ca renvoie vers la dernière version dont on a dérivé.
dirtyPAth : tout ce qui a été modifié entre O et A.
computeDirty(O,A,"") (avec "" le chemin relatif dans l'arborescence), et doit renvoyer la liste des différences.

'''

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
    String rootOfFileSystem


    public String getRoot():
        return rootOfFileSystem


    public String getParent(String path):
        return (  (path.rsplit('/',1))[0] )

        public List<String> getChildren(String path):
        #returns the list of files and folders that are in the directory
        return os.listdir(path)

    public void replace(String absolutePathTargetFileSystem, FileSystem fsSource, String absolutePathSourceFileSystem):


    }

