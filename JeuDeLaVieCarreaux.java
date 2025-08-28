import java.util.Scanner;

public class JeuDeLaVieCarreaux {

    private static final int TAILLE_GRILLE = 5;
    private boolean[][] grilleActuelle;

    public JeuDeLaVieCarreaux(boolean[][] grilleInitiale) {
        this.grilleActuelle = grilleInitiale;
    }

    /**
     * Affiche la grille en utilisant des caractères Unicode pour dessiner
     * des carreaux, comme sur l'image de référence.
     */
    public void afficherGrille(int generation) {
        // En-tête de l'affichage
        System.out.println("\n╔═══════════════════════════════╗");
        System.out.println("║   Jeu de la Vie - Génération " + generation + "  ║");
        System.out.println("╚═══════════════════════════════╝");

        // Affichage de la grille avec des lignes et des carreaux
        for (int i = 0; i < TAILLE_GRILLE; i++) {
            // Ligne du haut des carreaux
            System.out.print("┌");
            for (int j = 0; j < TAILLE_GRILLE; j++) {
                System.out.print("───");
                if (j < TAILLE_GRILLE - 1) {
                    System.out.print("┬");
                }
            }
            System.out.println("┐");

            // Ligne de contenu (cellules vivantes/mortes)
            for (int j = 0; j < TAILLE_GRILLE; j++) {
                System.out.print("│");
                // Caractère pour la cellule : '█' pour vivante, '   ' (trois espaces) pour morte
                System.out.print(grilleActuelle[i][j] ? " █ " : "   ");
            }
            System.out.println("│");

            // Ligne du bas des carreaux
            if (i < TAILLE_GRILLE - 1) {
                System.out.print("├");
                for (int j = 0; j < TAILLE_GRILLE; j++) {
                    System.out.print("───");
                    if (j < TAILLE_GRILLE - 1) {
                        System.out.print("┼");
                    }
                }
                System.out.println("┤");
            }
        }
        
        // Dernière ligne de bordure de la grille
        System.out.print("└");
        for (int j = 0; j < TAILLE_GRILLE; j++) {
            System.out.print("───");
            if (j < TAILLE_GRILLE - 1) {
                System.out.print("┴");
            }
        }
        System.out.println("┘");
    }

    /**
     * Calcule la prochaine génération de la grille en appliquant les règles du Jeu de la Vie.
     */
    public void calculerProchaineGeneration() {
        boolean[][] prochaineGrille = new boolean[TAILLE_GRILLE][TAILLE_GRILLE];
        for (int i = 0; i < TAILLE_GRILLE; i++) {
            for (int j = 0; j < TAILLE_GRILLE; j++) {
                int voisinesVivantes = compterVoisinesVivantes(i, j);
                if (grilleActuelle[i][j]) {
                    prochaineGrille[i][j] = (voisinesVivantes == 2 || voisinesVivantes == 3);
                } else {
                    prochaineGrille[i][j] = (voisinesVivantes == 3);
                }
            }
        }
        this.grilleActuelle = prochaineGrille;
    }

    /**
     * Compte le nombre de voisines vivantes d'une cellule.
     */
    private int compterVoisinesVivantes(int ligne, int colonne) {
        int compteur = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                int voisineLigne = ligne + i;
                int voisineColonne = colonne + j;
                if (voisineLigne >= 0 && voisineLigne < TAILLE_GRILLE &&
                    voisineColonne >= 0 && voisineColonne < TAILLE_GRILLE) {
                    if (grilleActuelle[voisineLigne][voisineColonne]) {
                        compteur++;
                    }
                }
            }
        }
        return compteur;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean[][] configurationInitiale = {
            {true, false, true, false, false},
            {false, false, true, false, false},
            {true, true, false, true, false},
            {false, false, false, false, false},
            {false, true, false, false, true}
        };

        JeuDeLaVieCarreaux jeu = new JeuDeLaVieCarreaux(configurationInitiale);
        int generation = 0;

        System.out.println("Bienvenue dans le Jeu de la Vie en console (version carreaux) !");
        
        while (true) {
            jeu.afficherGrille(generation);
            System.out.println("\nAppuyez sur 'Entrée' pour passer à la prochaine génération ou tapez 'q' pour quitter.");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("q")) {
                System.out.println("Fin du jeu. Au revoir !");
                break;
            } else {
                jeu.calculerProchaineGeneration();
                generation++;
            }
        }
        scanner.close();
    }
}