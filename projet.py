#! /usr/bin/python

import os

'''
**** METTEZ VOTRE NOM AU NIVEAU DU CODE QUE VOUS ECRIVEZ ****
Ca permet de savoir à qui demander des précisions ensuite :)

Louis Grison : C'est la classe Synchroniser qui va effectuer le travail de synchronisation.
getReference sur A (A est un dossier) renvoie O (O est un dossier), car A est une copie de O. Ca renvoie vers la dernière version dont on a dérivé.
dirtyPAth : tout ce qui a été modifié entre O et A.
computeDirty(O,A,"") (avec "" le chemin relatif dans l'arborescence), et doit renvoyer la liste des différences.
On dirait que dirtyA correspond à l'écart (aux différences) entre O et A. Pour synchroniser deux copies A et B d'un même dossier O, il semblerait qu'il faille calculer "O-A"=dirtyA, "O-B"=dirtyB et ensuite appliquer dirtyA à B et dirtyB à A.
'''

class LocalFileSystem :
    #def __init__(self,...) == creation de classe
    def rootOfFileSystem(self):
        return '/'

    def getRoot(self):
        return self.rootOfFileSystem()


    def getParent(self,path):
        # Louis Grison
        return (  (path.rsplit('/',1))[0] )

    def getChildren(self, path):
        # Louis Grison
        #returns the list of files and folders that are in the directory
        return os.listdir(path)

    def replace(self, absolutePathTargetFileSystem, fsSource, absolutePathSourceFileSystem):
        return ''

lfs=LocalFileSystem()









