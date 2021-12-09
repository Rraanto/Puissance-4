package Main;
import Interface.Fenetre;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*****
 * Projet NFA035, classe App contenue dans le fichier App.java est la classe principale
 * du programme, elle réunit tous les éléments créés et, une fois exécutée, lance le jeu 
 * Puissance 4
 */
public class App {
    /**
     * la matrice principale du jeu, un tableau de int qui est un stockage des
     * données de la grille du jeu Pour chaque élément matricePrincipale[i][j],
     * l'état de la case à la ligne i, colonne j de la grille est représentée : 0
     * vide, 1 occupé par joueur 1, 2 occupé par joueur2
     */

    private static int[][] matricePrincipale = new int[6][7];
    private static JFrame menuPrincipal;
    public static String aideMessage = "PUISSANCE 4 : Aide\n" + 
                                "Ce jeu se joue à deux joueurs sur une grille rectangulaire 7 x 6\n"+
                                "supposée verticale. Chacun à leur tour, les joueurs posent un pion\n"+
                                "en haut d'une colonne non pleine : ce pion glisse vers le bas jusqu'à\n"+
                                "être arrêté par un autre pion ou par le bas de la grille. Le gagnant est\n"+
                                "le premier qui aligne 4 de ses pions horizontalement, verticalement ou en diagonale";

    public App() {

        // le tableau est initialisé, tous les éléments prennent la valeur 0 <=> grille
        // vide
        for (int i = 0; i < matricePrincipale.length; i++) {
            for (int j = 0; j < matricePrincipale[i].length; j++) {
                matricePrincipale[i][j] = 0;
            }
        }

        /***
         * fenêtre contenant le menu principal
         */
        menuPrincipal = new JFrame("Puissance 4");
        menuPrincipal.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        menuPrincipal.setSize(350, 350);
        JPanel panneauPrincipal = new JPanel(new BorderLayout());
        JPanel menusPanel = new JPanel(new GridLayout(3, 1));
        String[] menusItemsText = { "Nouvelle partie", "Règles du jeu", "Quitter" };
        JButton[] menuItemsButton = new JButton[3];
        for (int i = 0; i < 3; i++) {
            menuItemsButton[i] = new JButton(menusItemsText[i]);
            JPanel boutonBox = new JPanel();
            menuItemsButton[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if((JButton)e.getSource() == menuItemsButton[0]){
                        menuPrincipal.dispose();
                        /***
                         * objet de type Fenetre, représentant un JFrame contenant la grille et les
                         * informations sur le Jeu
                         */
                        Fenetre fen = new Fenetre("Puissance 4", matricePrincipale, 10);
                        fen.setVisible(true);
                    } else if((JButton)e.getSource() == menuItemsButton[1]){
                        JOptionPane.showMessageDialog(null, aideMessage);
                    } else if((JButton)e.getSource() == menuItemsButton[2]) {
                        System.exit(0);
                    }
                }
            });
            boutonBox.add(menuItemsButton[i]);
            menusPanel.add(menuItemsButton[i]);
        }
        JLabel grandTitre = new JLabel("MENU");
        grandTitre.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        JPanel placeDuTitre = new JPanel();
        placeDuTitre.add(grandTitre);

        panneauPrincipal.add(placeDuTitre, BorderLayout.NORTH);
        panneauPrincipal.add(menusPanel, BorderLayout.CENTER);

        menuPrincipal.getContentPane().add(panneauPrincipal);
        menuPrincipal.setVisible(true);
    }

    /***
     * dans la méthode main, une instance de la classe App elle-même est créée, 
     * pour pouvoir lancer l'application en compilant le fichier App.java
     * @param args
     */
    public static void main(String[] args){
        new App();
    }
}

/*****
 * 13 JUILLET 2021 02:21:49 
 * Ranto Ny Aina Rakotondrajoa
 * 
 * Tâches à faire : 
 * -mise en forme, ajout menu, aide, documentation, 
 * optimisation UI
 * 
 * -utiliser des variables plus générales pour les dimensions
 * (position des cercles etc) pour faciliter la réutilisation
 */
