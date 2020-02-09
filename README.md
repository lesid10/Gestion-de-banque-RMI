# Gestion-de-banque-RMI
PROJET BANQUE
Ce projet a ete realise par :
DIAKITE SOUMAILA
KERE MOHAMED BACHIR
KONE SIDNEY AZIZ KHADER
KOFFI KOUADIO AMANY JACQUES DESIRE
N'GUESSAN HENRI AIME
TOURE HOSSEINE HABRAHAM YOUSSELL

Avant tout propos il serait interessant de creer une base de donnees 'banque' et importer la base de donnees 'banque.sql' livrer.

Ce projet est constitue de 3 entite : 

1) Un serveur qui nous permet de gerer le RMI avec nos differents appels distants

2) Un Banquier qui peut effectuer les operations suivantes: 
	Creer un compte bancaire,
	Obtenir la liste des comptes existant,
	Modifier un compte existant,
	Effectuer une operation de versement,de retrait ou de virement sur compte bancaire

   NB: Pour enregistrer un banquier il faudrait passer par la base de donnees et enregistrer le banquier dans la table 'agentbanque'
       Pour l'instant il existe un seul banquier ****pseudo : ismael***** et *****mot de passe : ismael******

3) Un utilisateur 
Un utilisateur est une personne ayant un compte bancaire dans la banque.
C'est une personne qui peut verifier ses transactions a distance.
Pour acceder a l'interface il faut donner les informations qui sont : numero de compte ( qui est genere automatiquement 
par le systeme lors de l'inscription) et le mot de passe de votre compte(celui que vous avez renseigner lors de l'inscription).

********* POUR LES TESTS ***************
Chaque entite(utilisateur,serveur,banquier) il existe un dossier "dist" qui contient l'executable(un fichier .jar) de chaque entite.
