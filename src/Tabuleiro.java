public class Tabuleiro {
    private Peca[][] grid;

    public Tabuleiro() {
        grid = new Peca[8][8];
        inicializarPecas();
    }

    private void inicializarPecas() {
        // Torres
        grid[0][0] = new Torre("Preto", 0, 0);
        grid[0][7] = new Torre("Preto", 0, 7);
        grid[7][0] = new Torre("Branco", 7, 0);
        grid[7][7] = new Torre("Branco", 7, 7);

        // Cavalos
        grid[0][1] = new Cavalo("Preto", 0, 1);
        grid[0][6] = new Cavalo("Preto", 0, 6);
        grid[7][1] = new Cavalo("Branco", 7, 1);
        grid[7][6] = new Cavalo("Branco", 7, 6);

        // Bispos
        grid[0][2] = new Bispo("Preto", 0, 2);
        grid[0][5] = new Bispo("Preto", 0, 5);
        grid[7][2] = new Bispo("Branco", 7, 2);
        grid[7][5] = new Bispo("Branco", 7, 5);

        // Damas
        grid[0][3] = new Dama("Preto", 0, 3);
        grid[7][3] = new Dama("Branco", 7, 3);

        // Reis
        grid[0][4] = new Rei("Preto", 0, 4);
        grid[7][4] = new Rei("Branco", 7, 4);

        // Peões
        for (int i = 0; i < 8; i++) {
            grid[1][i] = new Peao("Preto", 1, i);
            grid[6][i] = new Peao("Branco", 6, i);
        }
    }

    public void imprimir() {
        System.out.println("  A B C D E F G H");
        System.out.println(" +----------------+");
        for (int i = 0; i < 8; i++) {
            System.out.print((8 - i) + "|");
            for (int j = 0; j < 8; j++) {
                if (grid[i][j] != null) {
                    System.out.print(grid[i][j].toString().charAt(0) + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println("|" + (8 - i));
        }

        System.out.println(" +----------------+");
        System.out.println("  A B C D E F G H");
    }

    public Peca[][] getGrid() {
        return grid;
    }

    public void setPeca(int x, int y, Peca peca) {
        grid[x][y] = peca;
        if (peca != null) {
            peca.setX(x);
            peca.setY(y);
        }
    }
}
