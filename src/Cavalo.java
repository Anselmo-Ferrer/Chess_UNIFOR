public class Cavalo extends Peca {

    public Cavalo(String cor, int x, int y) {
        super(cor, x, y);
    }

    @Override
    public boolean movimentoValido(int newX, int newY, Peca[][] tabuleiro) {
        int dx = Math.abs(newX - x);
        int dy = Math.abs(newY - y);

        boolean movimentoEmL = (dx == 2 && dy == 1) || (dx == 1 && dy == 2);

        if (!movimentoEmL) {
            return false;
        }

        return tabuleiro[newX][newY] == null ||
                !tabuleiro[newX][newY].getCor().equals(this.cor);
    }

    @Override
    public int getValor() {
        return 3;
    }
}