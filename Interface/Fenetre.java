package Interface;

import javax.swing.*;
import Jeu.Partie;
import java.awt.*;
import java.awt.event.*;
import Main.App;
 
/****
 * Classe fenetre 
 * Un objet héritant la classe javax.swing.JFrame 
 * affichant l'interface d'un jeu en cours 
 * il contient une zone où la grille s'affiche, une autre pour les informations 
 * sur le jeu, et une barre de menu pour gérer la partie
 */
public class Fenetre extends JFrame {
    /***
     * la zone où la grille s'affiche, un objet TableDeJeu, héritant du JPanel 
     * affiche une grille composée de sphères correspondant à une case dont la couleur 
     * montre son état (vide/occupé par 1/occupé par 2)
     */
    private TableDeJeu tableDeJeu;
    // un JPanel contenant les informations sur le jeu
    private JPanel panneauInformations;
    private JMenuItem nouvellePartie;
    private JMenuItem quitterPartie;
    private int margin;
    public JLabel affichageTour;
    public JLabel affichageJoueur;
    public JLabel affichageVictoire;
    public Partie partie;
    //une chaine de caractères qui contient le message à afficher lorsque l'aide est demandée 
    public String aideMessage = "PUISSANCE 4 : Aide\n"
            + "Ce jeu se joue à deux joueurs sur une grille rectangulaire 7 x 6\n"
            + "supposée verticale. Chacun à leur tour, les joueurs posent un pion\n"
            + "en haut d'une colonne non pleine : ce pion glisse vers le bas jusqu'à\n"
            + "être arrêté par un autre pion ou par le bas de la grille. Le gagnant est\n"
            + "le premier qui aligne 4 de ses pions horizontalement, verticalement ou en diagonale";

    /***
     * méthode constructeur 
     * @param title pour servir de titre au JFrame 
     * @param table le tableau associé au jeu 
     * @param margin une valeur qui servira de marge à l'affichage (méthode getInsets())
     */
    public Fenetre(String title, int[][] table, int margin) {
        super(title);
        this.margin = margin;
        JPanel panneauDeJeu = new JPanel(new BorderLayout());
        JMenuBar barreDeMenu = new JMenuBar();
        JMenu menuPartie = new JMenu("Partie");
        nouvellePartie = new JMenuItem("Nouvelle partie");
        nouvellePartie.addActionListener(new MenuActions());
        quitterPartie = new JMenuItem("Quitter partie");
        quitterPartie.addActionListener(new MenuActions());
        menuPartie.add(nouvellePartie);
        menuPartie.add(quitterPartie);

        JMenu menuAide = new JMenu("Aide");
        JMenuItem afficherAide = new JMenuItem("Afficher les règles du jeu");
        menuAide.add(afficherAide);
        afficherAide.addActionListener(new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(getContentPane(), aideMessage);;
            }
        });

        barreDeMenu.add(menuAide);
        barreDeMenu.add(menuPartie);
        setJMenuBar(barreDeMenu);

        partie = new Partie(table);
        int ecart = 100;
        int pion = 98;
        int wid = ecart*(partie.gameboard[0].length - 1) + pion + margin*2;
        int height = ecart*(partie.gameboard.length + 1) + margin;
        tableDeJeu = new TableDeJeu(this, partie, ecart, pion);

        panneauInformations = new JPanel(new BorderLayout());
        panneauInformations.setSize(300, height);

        JLabel titre = new JLabel("PARTIE EN COURS");
        titre.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
        JPanel titreBox = new JPanel();
        titreBox.add(titre);
        panneauInformations.add(titreBox, BorderLayout.NORTH);
        affichageTour = new JLabel("Tour : " + partie.counter);
        affichageTour.setFont(new Font(Font.SANS_SERIF, 0, 26));
        affichageJoueur = new JLabel("Joueur 1");
        affichageJoueur.setFont(new Font(Font.SANS_SERIF, 0, 26));
        affichageVictoire = new JLabel("");

        JPanel affichageInformations = new JPanel(new BorderLayout());
        affichageInformations.add(affichageTour, BorderLayout.CENTER);
        affichageInformations.add(affichageJoueur, BorderLayout.EAST);
        panneauInformations.add(affichageInformations, BorderLayout.CENTER);

        JPanel panneauVersMenuPrincipal = new JPanel();
        JButton boutton = new JButton("Menu Principal");
        boutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = confirm("Retourner au menu principal et quitter la partie en cours ?");
                if(choice == JOptionPane.OK_OPTION){
                    versMenuPrincipal();
                } else {
                    // rien ne se passe
                }
            }
        });
        panneauVersMenuPrincipal.add(boutton);
        panneauInformations.add(panneauVersMenuPrincipal, BorderLayout.SOUTH);

        setSize(wid+panneauInformations.getSize().width, height);

        panneauDeJeu.add(tableDeJeu, BorderLayout.CENTER);
        panneauDeJeu.add(panneauInformations, BorderLayout.EAST);

        getContentPane().add(panneauDeJeu);
        addWindowListener(new WindowActions());
    }

    // ajoute la valeur margin(int) à la valeur par défaut des marges dans l'objet JFrame
    public Insets getInsets() {
        Insets normal = super.getInsets();
        return new Insets(normal.top+margin, normal.left+margin, normal.bottom+margin, normal.right+margin);
    }

    /***
     * méthode permettant d'afficher le menu principal en créant une instance de la classe App()
     * contenant les éléments du menu
     */
    public void versMenuPrincipal() {
        this.dispose();
        new App();
    }

    /***
     * permet d'afficher une confirmation dont le message à afficher est prise en paramètre :
     * @param text et d'obtenir un entier qui déterminera le choix de l'utilisateur grâce à la méthode
     * showConfirmDialog de la classe JOptionPane
     * @return
     */
    public int confirm(String text) {
        int choice = JOptionPane.showConfirmDialog(this, text);
        return choice;
    }

    /***
     * annoncer la victoire d'un joueur dont le numéro est indiqué par la paramètre joueur 
     * sur une petite fenêtre JOptionPane 
     * @param joueur
     */
    public void annonceVictoire(int joueur) {
        JOptionPane.showMessageDialog(this, "Joueur" + joueur + " a gagné");
    }

    /***
     * listener permettant d'arrêter l'éxécution du programme quand l'utilisateur 
     * ferme la fenêtre
     */
    private class WindowActions extends WindowAdapter{
        public void windowClosing(WindowEvent e){
            System.exit(0);
            // stopper l'application
        }
    }

    private class MenuActions extends AbstractAction{
        public void actionPerformed(ActionEvent e) {
            if((JMenuItem)e.getSource() == nouvellePartie) {
                tableDeJeu.resetBoard();
                affichageJoueur.setText("Joueur 1");
                affichageTour.setText("Tour " + partie.counter);
            } else if ((JMenuItem)e.getSource() == quitterPartie){
                int choice = confirm("Voulez vous vraiment quitter la partie ?");
                if(choice == JOptionPane.OK_OPTION){
                    versMenuPrincipal();
                }
                else {
                    // rien
                }
            }
        }
    }
}
