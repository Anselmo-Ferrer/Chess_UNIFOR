public class Bispo extends Peca {

    public Bispo(String cor, int x, int y) {
        super(cor, x, y);
    }

    @Override
    public boolean movimentoValido(int newX, int newY, Peca[][] tabuleiro) {
        int dx = Math.abs(newX - x);
        int dy = Math.abs(newY - y);

        if (dx != dy) {
            return false;
        }

        int stepX = (newX > x) ? 1 : -1;
        int stepY = (newY > y) ? 1 : -1;

        int cx = x + stepX;
        int cy = y + stepY;

        while (cx != newX || cy != newY) {
            if (cx < 0 || cx >= 8 || cy < 0 || cy >= 8) {
                return false;
            }

            if (tabuleiro[cx][cy] != null) {
                return false;
            }

            cx += stepX;
            cy += stepY;
        }

        return tabuleiro[newX][newY] == null ||
                !tabuleiro[newX][newY].getCor().equals(this.cor);
    }

    @Override
    public int getValor() {
        return 3;
    }
}