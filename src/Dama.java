public class Dama extends Peca {

    public Dama(String cor, int x, int y) {
        super(cor, x, y);
    }

    @Override
    public boolean movimentoValido(int newX, int newY, Peca[][] tabuleiro) {
        int dx = Math.abs(newX - x);
        int dy = Math.abs(newY - y);

        if (!(dx == dy || newX == x || newY == y)) {
            return false;
        }

        int stepX = Integer.compare(newX, x);
        int stepY = Integer.compare(newY, y);

        int i = x + stepX;
        int j = y + stepY;
        while (i != newX || j != newY) {
            if (tabuleiro[i][j] != null) {
                return false; // há peça no caminho
            }
            i += stepX;
            j += stepY;
        }

        return tabuleiro[newX][newY] == null ||
                !tabuleiro[newX][newY].getCor().equals(this.cor);
    }

    @Override
    public int getValor() {
        return 9;
    }
}