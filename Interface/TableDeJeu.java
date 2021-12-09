package Interface;

import javax.swing.*;
import Jeu.*;

import java.awt.*;
import java.awt.event.*;

/***
 * classe TableDeJeu 
 * un objet héritant de JPanel qui affiche une grille de jeu puissance 4 interactive 
 */
public class TableDeJeu extends JPanel {
    private int position_Y;
    public Partie partie;
    private int[][] table;
    private Point choosen;
    public final int ECART;
    public final int TAILLEPION;
    private final Fenetre fenetre;

    /***
     * redéfinition de la méthode paintComponent de la classe parent JPanel permettant 
     * de dessiner et remplir des formes définies, dans le cas du jeu puissance 4, il a été
     * nécéssaire d'avoir dessiné des cercles pour les cases de la grille
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // dessin des cases en fonction du tableau de int contenant les informations de la grille
        for (int i = 0; i<table.length; i++ ) {
            // pour chaque case, la couleur utilisée dépend du statut de la case (vide, joueur 1, joueur 2)
            // la valeur de l'élément du tableau est donc vérifié, et la couleur est définie, selon cette valeur
            for (int j = 0; j<table[i].length; j++) {
                if (table[i][j] == 1) {
                    // utiliser la couleur rouge
                    g.setColor(new Color(255, 0, 0));
                } else if (table[i][j] == 2) {
                    // utiliser une couleur bleue
                    g.setColor(new Color(255, 174, 66));
                } else {
                    // utiliser une couleur blanche
                    g.setColor(Color.WHITE);
                }
                /***
                 * dessiner une ovale à la position ECART x j ; ECART * i, ECART etant l'espace entre le début 
                 * de deux cases, TAILLEPION, le diamètre
                 */
                g.fillOval(ECART * j, ECART * i, TAILLEPION, TAILLEPION);
            }
        }
    }

    /***
     * méthode permettant de réinitialiser la grille de jeu
     */
    public void resetBoard() {
        // les informations sont remis à des valeurs de début de jeu
        fenetre.affichageJoueur.setText("Joueur 1");
        fenetre.affichageTour.setText("Tour : 1");
        partie.reset();

        // appel de méthode permettant d'appeler la méthode paintComponent afin d'afficher les 
        // changements 
        repaint();
    }

    /**
     * méthode constructeur 
     * @param fen l'objet fenêtre qui contient la grille, requis pour synchroniser les informations
     * @param partie l'objet partie associé au jeu actuel
     * @param ecart un int qui permettra de déterminer l'espace entre les positions des cases 
     * @param taillePion un int qui permettra de déterminer le diamètre de chaque case
     */
    public TableDeJeu(Fenetre fen, Partie partie, int ecart, int taillePion) {
        this.fenetre = fen;
        this.position_Y = getY();
        getX();
        this.ECART = ecart;
        this.TAILLEPION = taillePion;
        this.partie = partie;
        this.table = partie.gameboard;
        setBackground(Color.BLUE);
        this.addMouseListener(new MouseAction());
    }

    /***
     * listener qui capture les clics de souris, les positions sont utilisés pour 
     * obtenir la colone choisie, et ainsi faire fonctionner le jeu
     */
    private class MouseAction extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            partie.counter++;
            choosen = e.getPoint();
            int choosen_X = (int) Math.ceil((choosen.x - position_Y) / ECART);
            try{
                partie.chooseColumn(partie.counter%2 + 1, choosen_X);
            } catch(ArrayIndexOutOfBoundsException arrEx) {
                JOptionPane.showMessageDialog(null, "Veuillez choisir une colone\nsur lequel il y a des places libres");
                partie.counter--;
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Une erreur est survenue");
                partie.counter--;
            }
            repaint();
            // appeller la méthode paintComponent pour mettre à jour la grille (re-dessiner les cases 
            // en fonction des nouveaux valeurs des éléments du tableau)
            fenetre.affichageTour.setText("Tour : " + partie.counter);
            fenetre.affichageJoueur.setText("Joueur : " + ((partie.counter+1)%2 + 1));
            /***
             * vérification des cas de fin de partie
             */
            if(partie.checkAlign()) {
                fenetre.annonceVictoire(partie.counter%2 + 1);
                resetBoard();
            } else if (partie.checkDraw() && !partie.checkAlign()) {
                JOptionPane.showMessageDialog(null, "Match Nul");
                resetBoard();
            }
            repaint();
        }
    }
}
