# JEU DE LA VIE
## Règles
- Une cellule a deux états
	- vivante
	- morte

## Utilitaires du projet
### Ant
voir pour faire la compilation auto
### Wiki
voir pour faire en sorte que le wiki se génère automatiquement
## Structure du projet
```
gol
	META-INF
		manifest.mf
	core
		cells
			// all cells
		boards
			// all boards
		quadtree
	gui
		components
			buttons
			// mettre les autres composants ici
	executable
		// mettre les fichiers exécutables ici
	
test
	META-INF
		manifest.mf
	core
		cells
			// all tests for cells
		boards
			// all tests for boards
	game
		// tests pour la modification des données
```

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


main.core.Cell -> classe abstraite
	-packages nécessaires
		- org.javatuples.Pair
	- attributs
		- protected boolean alive
		- protected Pair<Int> coordinates
		- protected ArrayList<Cell> neighbors (toutes les cellules voisines)
		- protected int maxNeighbors
	- méthodes
		- public abstract void setState(type state)
		- public boolean isAlive()
		- public Int getCoordinateX()
		- public Int getCoordinateY()
		- public ArrayList<Cell> getNeighbors(int radius)

main.core.Board -> classe concrète
	- packages nécessaires
		- private QuadTree quadTreeBoard
		- private ArrayList<ArrayList<Cell>> arrayBoard
	- attributs
		- 
	- méthodes
		- 
