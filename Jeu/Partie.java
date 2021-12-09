package Jeu;


/***
 * la classe Partie
 * un objet permettant de stocker les informations nécessaires 
 * sur un jeu de Puissance 4 
 * un compteur (int counter) permettant de tracker le nombre de tours 
 * effectués et, avec un calcul basé sur la parité, de déterminer quel
 * joueur doit jouer le tour suivant  
 */
public class Partie {
    public int[][] gameboard;
    public int counter = 1;

    public Partie(int[][] gameboard){
        this.gameboard = gameboard;
    }

    /***
     * méthode permettant de mettre à jour le tableau contenant les données 
     * de la grille après qu'un joueur ait choisie une colonne
     * @param playerIndex l'identifiant (int :1 ou 2) qui a choisie une colonne
     * @param column la colonne que le joueur a choisie
     */
    public void chooseColumn(int playerIndex, int column) {
        int rowMax = gameboard.length - 1;
        for(int row = rowMax; true; row--){
            if(gameboard[row][column] == 0) {
                gameboard[row][column] = playerIndex;
                break;
            }
        }
    }

    /***
     * méthode permettant de vérifier si il y a 4 pions alignés dans la grille : 
     * parcours le tableau et vérifie si trois cases adjacents au case courant 
     * y est égal
     * @return result
     */
    public boolean checkAlign(){
        boolean result = false;
        for(int i=0; i<gameboard.length; i++) {
            for(int j=0; j<gameboard[i].length; j++) {
                int current = gameboard[i][j];
                if(j<=3) {
                    if(current == gameboard[i][j+1] &&
                    current == gameboard[i][j+2] &&
                    current == gameboard[i][j+3] &&
                    current !=0 ){
                        result = true;
                    }
                }
                if(i<=2){
                    if(current == gameboard[i+1][j] && 
                    current == gameboard [i+2][j] &&
                    current == gameboard[i+3][j] && 
                    current != 0){
                        result = true;
                    }
                }
                if(i<=2 && j<=3){
                    if(current == gameboard[i+1][j+1]&&
                    current == gameboard[i+2][j+2]&&
                    current == gameboard[i+3][j+3]&&
                    current != 0){
                        result = true;
                    }
                }
                if(i>=3 && j<=3) {
                    if(current == gameboard[i-1][j+1]&&
                    current == gameboard[i-2][j+2]&&
                    current == gameboard[i-3][j+3]&&
                    current != 0){
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    /***
     * Méthode permettant de vérifier si le jeu se termine en match nul 
     * c'est à dire si toutes les cases sont occupées 
     * la variable result qui sera retournée prend en valeur initial true, 
     * en parcourant le tableau, dès qu'une case est vide (la valeur est 
     * égale à 0) result est mis sur false
     * @return
     */
    public boolean checkDraw(){
        boolean result = true;
        // parcours de tableau
        for(int i=0; i<gameboard.length; i++) {
            for(int j=0; j<gameboard[i].length; j++) {
                // vérification
                if(gameboard[i][j] == 0) {
                    result = false;
                }
            }
        }
        return result;
    }

    /***
     * méthode permettant de réinitialiser le jeu, 
     * remettre tous les éléments à leur valeurs initiaux,
     * utilisé pour commencer une nouvelle partie
     */
    public void reset(){
        // le compteur de tours est remis à 1
        counter = 1;
        for(int i=0; i<gameboard.length; i++) {
            for(int j=0; j<gameboard[0].length; j++) {
                // toutes les cases non vides de la grille sont vidées 
                if(gameboard[i][j] != 0) {
                    gameboard[i][j] = 0;
                }
            }
        }
    }
}
