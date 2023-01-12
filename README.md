# JEU DE LA VIE

## Règles
- Une cellule a deux états
	- vivante
	- morte

## Structure du projet
cellule
grille
quadtree


## Idées
### Options
- voir pour changer la configuration des bordures (infinies, finies, tor)
- pouvoir changer le temps d'exécution entre chaque étape
	- mettre en mode évolution automatique
	- mettre un bouton pour passer à l'étape suivante
- choisir le nombre d'étapes ou le mettre en infini
	- commencer une visualisation à partir de la n-ième étape
- intégrer des patterns de base (avion, lanceurs, etc)
- modification des paramètres d'évolution
	- paramètres de voisinage
		- changer la direction d'évolution (voisin juste au sud, ou au nord...)
	- nombre de cellules voisines pour avoir une naissance
	- nombre de cellules voisines pour avoir une mort
	- les cellules peuvent avoir plus de deux états (cellules enfants, cellules adultes, cellules vieillissantes, cellule tueuse...)
	- 
- forme du plateau de jeu
- différents profils de paramètres
	- sauvegarde
	- import
- changer la forme du plateau de d'évolution
- un bouton pour lancer une génération aléatoire de cellules
- selection des cellules à la création du plateau
	- cocher et décocher une cellule
	- maintenir pour faire une sélection de plusieurs cellules (lignes ou rectangles)
	- 
- enregistrement externe d'une évolution
	- sous forme d'image à n étapes
	- sous forme de vidéo, une image par étape
- statistiques
	- en direct
		- nombres de cellules en vie (un affichage par type de cellule)
		- nombre de cellules mortes
	- à la fin
		- nombre de naissances (au total)
		- nombre de morts (au total)
		- nombre d'étapes


### Structures de données
- Quadtrees
- Tableaux

### Implémentation des fonctionnalités
