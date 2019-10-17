# TP1_IHM2_Polytech
## BADAT Leya, WYKLAND Damien, CUAU Victor
## TP1 IHM Avancée - INFO5 Polytech - 2019-2020

Le but de ce premier TP est d'implémenter un Range Slider. Le sujet détaillé est disponible sur ce dépot Git. 

**Deadline de remise du TP :** Vendredi 18 octobre à 8h du matin. 

**Modalités de remise du TP :** Partagez le dépôt Git de votre groupe avec les enseignants pour que nous puissions accéder à votre travail. Nous récupérerons vos projets directement sur ces dépôts juste après la deadline. 
Pensez à ajouter un Readme dans votre dépôt avec le nom des membres du groupe et une courte explication de ce que vous avez effectué : quels sont vos choix de conceptions, quels éléments ont été implémentés, quelles touches utiliser pour interagir avec votre solution, etc. Aucun rapport de TP n'est attendu.

## Range Slider
Notre Range Slider se base sur JSlider. 
Ses 2 extrémités peuvent être déplacé en enfonçant le bouton gauche de la souris puis en déplaçant du curseur. L'actualisation de la carte se fait en temps réel au fur et à mesure du déplacement. Le déplacement se termine lorsque l'on relâche le bouton gauche de la souris. 
De même, on peut enfoncer le bouton gauche de la souris lorsque le curseur se trouve entre les 2 extrémités, puis déplacer le curseur. Cela va déplacer l'intervale dans son ensemble. Si pendant ce déplacement, l'une des deux extrémités atteint sa limite haute ou basse, l'intervale se réduit. Le déplacement se termine lorsque l'on relâche le bouton gauche de la souris. 
Si l'on clique ailleurs que sur une extrémité du Range Slider, c'est-à-dire avant, entre ou après les extrémités, l'extrémité la plus proche du curseur fait un "saut" et prend la valeur pointée par le curseur.

## Home Finder
Notre programme présente 2 Range Slider, symbolisant le prix et le nombre de pièce des maisons. 
Au démarrage, une carte est créée et des maisons sont ajoutés dessus. Ces maisons ont une position X, une position Y, un prix et un nombre de pièce. Ces 4 données sont générées de façon aléatoire à chaque lancement du programme.